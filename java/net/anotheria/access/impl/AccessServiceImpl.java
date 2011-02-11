package net.anotheria.access.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.anotheria.access.AccessService;
import net.anotheria.access.AccessServiceException;
import net.anotheria.access.AccessServiceReply;
import net.anotheria.access.PermissionReply;
import net.anotheria.access.Role;
import net.anotheria.access.RoleInfo;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.storage.SecurityBoxStorage;
import net.anotheria.access.storage.SecurityBoxStorageBoxNotFoundException;
import net.anotheria.access.storage.SecurityBoxStorageException;
import net.anotheria.access.storage.SecurityBoxStorageFactory;
import net.anotheria.anoprise.cache.Cache;
import net.anotheria.anoprise.cache.Caches;

import org.apache.log4j.Logger;

/**
 * The implementation of the access service.
 * @author another
 *
 */
public class AccessServiceImpl implements AccessService{
	/**
	 * Internal box storage.
	 */
	private SecurityBoxStorage storage;
	/**
	 * Log.
	 */
	private static Logger log = Logger.getLogger(AccessServiceImpl.class);
	/**
	 * Cache of the security boxes.
	 */
	private Cache<String, SecurityBox> cache;
	
	private static List<RoleInfo> EMPTY_LIST = new ArrayList<RoleInfo>(0);
	
	
	AccessServiceImpl(){
		storage = SecurityBoxStorageFactory.createStorage();
		try{
			cache = Caches.createConfigurableSoftReferenceCache("ano-access.cache");
		}catch(IllegalArgumentException e){
			log.warn("Cache is not configured");
			cache = Caches.createSoftReferenceCache("ano-access.cache", 1000, 5000);
		}
		new Timer().scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				log.info("Access Cache state: "+cache.getCacheStats().toString());
			}
		}, 0, 1000L*60*5);
	}

	@Override public AccessServiceReply isAllowed(String action, SecurityObject object, SecurityObject subject) throws   AccessServiceException{
		AccessContext context = AccessContext.getContext();
		context.reset();
		context.setObject(object);
		context.setSubject(subject);
		out("called isAllowed("+action+", "+object+", "+subject+")");
		
		SecurityBox box = loadBox(object);
		Collection<Role> roles = box.getRoles();
		
		PermissionReply current = null;
		
		for (Role r : roles){
			out("Checking with role: "+r);
			PermissionReply pr = r.isAllowed(action);
			if (current==null)
				current = pr;
			else
				if (pr!=null && pr.getPriority()>current.getPriority()){
					out(pr+" overrides "+current);
					current = pr;
				}
		}
		
		if (current == null)
			return AccessServiceReply.UNDECIDED;
		AccessServiceReply ret = new AccessServiceReply();
		ret.setAnswered(true);
		ret.setAllowed(current.isAllow());
		ret.setDecidedByPermission(current.getPermissionName());
		ret.setDecidedByRole(current.getRoleName());
		ret.setDecidedByPermissionPriority(current.getPriority());
		
		return ret;
	}
	
	@Override public void notifyPassed(String action, SecurityObject object, SecurityObject subject, AccessServiceReply basedUponReply) throws   AccessServiceException{
		AccessContext context = AccessContext.getContext();
		context.reset();
		context.setObject(object);
		context.setSubject(subject);
		out("notifyPassed("+action+", "+object+", "+subject+"," +basedUponReply+")");
		
		SecurityBox box = loadBox(object);
		Collection<Role> roles = box.getRoles();
		
		for (Role r : roles){
			out("Checking with role: "+r);
			if (r.getName().equals(basedUponReply.getDecidedByRole())){
				if (r instanceof DynamicRole){
					((DynamicRole)r).firePermissionUpdate(basedUponReply.getDecidedByPermission());
					break;
				}
			}
		}
		
		if (context.isDirty())
			saveBox(box);
		
	}

	@Override public void grantRole(SecurityObject object, String roleName)  throws AccessServiceException{

		SecurityBox box = loadBox(object);
		
		if (box.hasRole(roleName))
			return;
		
		out("has to grant role "+roleName+" to "+object);
		
		Role toGrant = MetaInfoStorage.INSTANCE.getRole(roleName);//cmsConnector.getRole(roleName);
		out("created role: "+toGrant);
		
		box.addRole(toGrant);
		
		saveBox(box);
	}
	
	@Override public void revokeRole(SecurityObject object, String roleName)  throws AccessServiceException{

		SecurityBox box = loadBox(object);
		
		if (!box.hasRole(roleName))
			return;
		
		out("has to revoke role "+roleName+" to "+object);
		
		box.removeRole(roleName);
		
		saveBox(box);
	}

	
	
	
	@Override public List<RoleInfo> getRoleInfos(SecurityObject object) throws AccessServiceException{
		SecurityBox box = loadBox(object);
		List<String> roleNames = box.getOwnedRoles();
		if (roleNames==null || roleNames.size()==0)
			return EMPTY_LIST;
		return null;//cmsConnector.getRoleInfos(roleNames);
	} 

	@Override public List<RoleInfo> getRoleInfos() throws AccessServiceException {
		//return null; //cmsConnector.getRoleInfos();
		
		return MetaInfoStorage.INSTANCE.getRoleInfos();
	}
	
	@Override
	public List<Role> getRoles() throws AccessServiceException {
		// TODO Auto-generated method stub
		return MetaInfoStorage.INSTANCE.getRoles();
	}
	
	
	/**
	 * Loads a security box.
	 * @param object
	 * @return
	 */
	private SecurityBox loadBox(SecurityObject object){
		
		SecurityBox fromCache = cache.get(object.getId());
		if (fromCache!=null)
			return fromCache;
		
		try{
			SecurityBox fromStorage = storage.loadSecurityBox(object.getId());
			if (fromStorage!=null)
				cache.put(object.getId(), fromStorage);
			return fromStorage;
		}catch (SecurityBoxStorageBoxNotFoundException notFound){
			SecurityBox newBox = new SecurityBox(object.getId());
			cache.put(object.getId(), newBox);
			return newBox;
		}catch(Exception e){
			return new SecurityBox(object.getId());
		}
	}
	
	/**
	 * Saves a security box.
	 * @param box
	 * @throws AccessServiceException
	 */
	private void saveBox(SecurityBox box) throws AccessServiceException{
		try {
			cache.put(box.getOwnerId(), box);
			storage.saveSecurityBox(box);
		}catch (SecurityBoxStorageException e) {
			log.error("saveBox", e);
			throw new AccessServiceException("Can't save security box: "+e.getMessage() );
		}
	}
	
	/**
	 * Debug-outs the object (which may be a string itself).
	 * @param o
	 */
	private void out(Object o){
		if (AccessContext.getContext().debugOutOn())
			log.debug("[BouncerServiceImpl] "+o);
	}

	@Override
	public void addRole(Role toAdd) throws AccessServiceException {
		MetaInfoStorage.INSTANCE.addRole(toAdd);
	}
	
	@Override public void addPermissionCollection(PermissionCollection collection) throws AccessServiceException{
		MetaInfoStorage.INSTANCE.addPermissionCollection(collection);
	}

	
}
