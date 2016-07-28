package net.anotheria.access.impl;

import net.anotheria.access.AccessService;
import net.anotheria.access.AccessServiceException;
import net.anotheria.access.AccessServiceReply;
import net.anotheria.access.PermissionReply;
import net.anotheria.access.Role;
import net.anotheria.access.RoleInfo;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.storage.SecurityBoxStorageService;
import net.anotheria.access.storage.SecurityBoxStorageServiceBoxNotFoundException;
import net.anotheria.access.storage.SecurityBoxStorageServiceException;
import net.anotheria.access.storage.SecurityBoxStorageServiceFactory;
import net.anotheria.access.util.CacheUtil;
import net.anotheria.anoprise.cache.Cache;
import net.anotheria.anoprise.metafactory.MetaFactory;
import net.anotheria.anoprise.metafactory.MetaFactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * The implementation {@link AccessService}.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class AccessServiceImpl implements AccessService {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AccessServiceImpl.class);

	/**
	 * {@link SecurityBoxStorageService} instance.
	 */
	private SecurityBoxStorageService storage;

	/**
	 * Cache of the security boxes.
	 */
	private final Cache<String, SecurityBox> cache;

	/**
	 * Empty roles information list.
	 */
	private static final List<RoleInfo> EMPTY_LIST = new ArrayList<>(0);

	/**
	 * Default constructor.
	 */
	protected AccessServiceImpl() {
		try {
			storage = MetaFactory.get(SecurityBoxStorageService.class);
		} catch (MetaFactoryException e) {
			LOGGER.warn("AccessServiceImpl() SecurityBoxStorageService initialization from MetaFactory fail. Configuring with default implementation.");
			storage = new SecurityBoxStorageServiceFactory().create();
		}
		cache = CacheUtil.createConfigurableSoftReferenceCache("ano-access-cache");
	}

	@Override
	public AccessServiceReply isAllowed(final String action, final SecurityObject object, final SecurityObject subject) throws AccessServiceException {
		AccessContext context = AccessContext.getContext();
		context.reset();
		context.setObject(object);
		context.setSubject(subject);
		out("called isAllowed(" + action + ", " + object + ", " + subject + ')');

		SecurityBox box = loadBox(object);
		Collection<Role> roles = box.getRoles();

		PermissionReply current = null;

		for (Role role : roles) {
			out("Checking with role: " + role);
			PermissionReply pReply = role.isAllowed(action);
			if (current == null)
				current = pReply;

			else if (pReply != null && pReply.getPriority() > current.getPriority()) {
				out(pReply + " overrides " + current);
				current = pReply;
			}
		}

		if (current == null)
			return AccessServiceReply.UNDECIDED;

		AccessServiceReply result = new AccessServiceReply();
		result.setAnswered(true);
		result.setAllowed(current.isAllow());
		result.setDecidedByPermission(current.getPermissionName());
		result.setDecidedByRole(current.getRoleName());
		result.setDecidedByPermissionPriority(current.getPriority());

		return result;
	}

	@Override
	public void notifyPassed(final String action, final SecurityObject object, final SecurityObject subject, AccessServiceReply basedUponReply)
			throws AccessServiceException {
		AccessContext context = AccessContext.getContext();
		context.reset();
		context.setObject(object);
		context.setSubject(subject);
		out("notifyPassed(" + action + ", " + object + ", " + subject + ',' + basedUponReply + ')');

		SecurityBox box = loadBox(object);
		Collection<Role> roles = box.getRoles();

		for (Role r : roles) {
			out("Checking with role: " + r);
			if (r.getName().equals(basedUponReply.getDecidedByRole()) && r instanceof DynamicRole) {
				((DynamicRole) r).firePermissionUpdate(basedUponReply.getDecidedByPermission());
				break;
			}
		}

		if (context.isDirty())
			saveBox(box);
	}

	@Override
	public void grantRole(final SecurityObject object, final String roleName) throws AccessServiceException {
		SecurityBox box = loadBox(object);

		if (box.hasRole(roleName))
			return;

		out("has to grant role " + roleName + " to " + object);

		Role toGrant = MetaInfoStorage.INSTANCE.getRole(roleName);
		out("created role: " + toGrant);

		box.addRole(toGrant);

		saveBox(box);
	}

	@Override
	public void revokeRole(final SecurityObject object, final String roleName) throws AccessServiceException {
		SecurityBox box = loadBox(object);

		if (!box.hasRole(roleName))
			return;

		out("has to revoke role " + roleName + " to " + object);
		box.removeRole(roleName);

		saveBox(box);
	}

	@Override
	public List<RoleInfo> getRoleInfos() {
		return MetaInfoStorage.INSTANCE.getRoleInfos();
	}

	@Override
	public List<RoleInfo> getRoleInfos(final SecurityObject object) {
		SecurityBox box = loadBox(object);
		List<String> roleNames = box.getOwnedRoles();
		if (roleNames == null || roleNames.isEmpty())
			return EMPTY_LIST;

		return MetaInfoStorage.INSTANCE.getRoleInfos(roleNames);
	}

	@Override
	public List<Role> getRoles() {
		return MetaInfoStorage.INSTANCE.getRoles();
	}

	@Override
	public Role getRole(String roleName) {
		return MetaInfoStorage.INSTANCE.getRole(roleName);
	}

	@Override
	public void addRole(Role role) {
		MetaInfoStorage.INSTANCE.addRole(role);
	}

	@Override
	public boolean deleteRole(final Role role) {
		return MetaInfoStorage.INSTANCE.deleteRole(role);
	}

	@Override
	public void addPermissionCollection(PermissionCollection collection) {
		MetaInfoStorage.INSTANCE.addPermissionCollection(collection);
	}

	@Override
	public PermissionCollection getPermissionCollection(String collectionName) {
		return MetaInfoStorage.INSTANCE.getPermissionCollection(collectionName);
	}

	@Override
	public void deleteSecurityObject(SecurityObject object) throws AccessServiceException {
		try {
			SecurityBox fromStorage = storage.loadSecurityBox(object.getId());
			if (fromStorage != null)
				deleteBox(fromStorage);
		} catch (SecurityBoxStorageServiceBoxNotFoundException e) {
			LOGGER.error("Can't delete box. ", e);
			throw new AccessServiceException("Box not found. " + e.getMessage());
		} catch (SecurityBoxStorageServiceException e) {
			LOGGER.error("Can't delete box. ", e);
			throw new AccessServiceException("Can't delete box. " + e.getMessage());
		}

	}

	@Override
	public void reset() {
		synchronized (this) {
			MetaInfoStorage.INSTANCE.reset();
			cache.clear();
		}
	}

	@Override
	public void reset(String ownerId) {
		cache.remove(ownerId);
	}

	/**
	 * Load {@link SecurityBox}.
	 * 
	 * @param object
	 *            - security object
	 * @return {@link SecurityBox}
	 */
	private SecurityBox loadBox(SecurityObject object) {
		SecurityBox fromCache = cache.get(object.getId());
		if (fromCache != null)
			return fromCache;

		try {
			SecurityBox fromStorage = storage.loadSecurityBox(object.getId());
			if (fromStorage != null)
				cache.put(object.getId(), fromStorage);

			return fromStorage;
		} catch (SecurityBoxStorageServiceBoxNotFoundException notFound) {
			SecurityBox newBox = new SecurityBox(object.getId());
			cache.put(object.getId(), newBox);
			return newBox;
		} catch (Exception e) {
			return new SecurityBox(object.getId());
		}
	}

	/**
	 * Save a {@link SecurityBox}.
	 * 
	 * @param box
	 *            - security box
	 * @throws AccessServiceException
	 */
	private void saveBox(SecurityBox box) throws AccessServiceException {
		try {
			cache.put(box.getOwnerId(), box);
			storage.saveSecurityBox(box);
		} catch (SecurityBoxStorageServiceException e) {
			LOGGER.error("saveBox(box of " + box.getOwnerId() + ')', e);
			throw new AccessServiceException("Can't save security box", e);
		}
	}

	/**
	 * Delete {@link SecurityBox}.
	 * 
	 * @param box
	 *            - {@link SecurityBox}
	 * @throws AccessServiceException
	 */
	private void deleteBox(SecurityBox box) throws AccessServiceException {
		try {
			cache.remove(box.getOwnerId());
			storage.deleteSecurityBox(box);
		} catch (SecurityBoxStorageServiceException e) {
			LOGGER.error("deleteBox", e);
			throw new AccessServiceException("Can't delete security box: " + e.getMessage());
		}
	}

	/**
	 * Debug-outs the object (which may be a string itself).
	 * 
	 * @param o
	 */
	private static void out(final Object o) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug(String.valueOf(o));
	}

}
