package net.anotheria.access.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.anotheria.access.Role;
import net.anotheria.access.RoleInfo;

/**
 * Configuration storage.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public enum MetaInfoStorage {

	/**
	 * Instance.
	 */
	INSTANCE;

	/**
	 * Roles.
	 */
	private final ConcurrentMap<String, Role> roles = new ConcurrentHashMap<>();

	/**
	 * Permissions collections.
	 */
	private final ConcurrentMap<String, PermissionCollection> collections = new ConcurrentHashMap<>();

	/**
	 * Get {@link Role} by name.
	 * 
	 * @param roleName
	 *            - role name
	 * @return {@link Role}
	 */
	public Role getRole(final String roleName) {
		Role role = roles.get(roleName);
		if (role == null)
			throw new IllegalArgumentException("Role[" + roleName + "] doesn't exists.");

		return role;
	}

	/**
	 * Add {@link Role}.
	 * 
	 * @param role
	 *            - {@link Role} to add
	 */
	public void addRole(final Role role) {
		if (role != null)
			roles.put(role.getName(), role);
	}

	/**
	 * Delete {@link Role}.
	 * 
	 * @param role
	 *            - {@link Role} to delete
	 * @return <code>true</code> if deleted or <code>false</code>
	 */
	public boolean deleteRole(Role role) {
		return roles.remove(role.getName(), role);

	}

	/**
	 * Get roles.
	 * 
	 * @return {@link List} of {@link Role}
	 */
	public List<Role> getRoles() {
		return new ArrayList<>(roles.values());
	}

	/**
	 * Get roles info.
	 * 
	 * @return {@link List} of {@link RoleInfo}
	 */
	public List<RoleInfo> getRoleInfos() {
		List<RoleInfo> result = new ArrayList<>();
		for (Role role : getRoles())
			result.add(convertToRoleInfo(role));

		return result;
	}

	/**
	 * Get roles info for given roles names.
	 * 
	 * @param roleNames
	 *            - roles names
	 * @return {@link List} of {@link RoleInfo}
	 */
	public List<RoleInfo> getRoleInfos(final Collection<String> roleNames) {
		List<RoleInfo> result = new ArrayList<>();
		for (Role role : roles.values())
			if (roleNames.contains(role.getName()))
				result.add(convertToRoleInfo(role));

		return result;
	}

	/**
	 * Has role.
	 * 
	 * @param roleName
	 *            - role name
	 * @return <code>true</code> if has or <code>false</code>
	 */
	public boolean hasRole(final String roleName) {
		return roles.containsKey(roleName);
	}

	/**
	 * Get permission collection by name.
	 * 
	 * @param collectionName
	 *            - collection name
	 * @return {@link PermissionCollection}
	 */
	public PermissionCollection getPermissionCollection(final String collectionName) {
		PermissionCollection collection = collections.get(collectionName);
		if (collection == null)
			throw new IllegalArgumentException("PermissionCollection[" + collectionName + "] doesn't exists.");

		return collection;
	}

	/**
	 * Add permission collection.
	 * 
	 * @param collection
	 *            - {@link PermissionCollection} to add
	 */
	public void addPermissionCollection(final PermissionCollection collection) {
		if (collection != null)
			collections.put(collection.getId(), collection);
	}

	/**
	 * Has permission collection.
	 * 
	 * @param collectionName
	 *            - collection name
	 * @return <code>true</code> if has or <code>false</code>
	 */
	public boolean hasPermissionCollection(final String collectionName) {
		return collections.containsKey(collectionName);
	}

	/**
	 * Convert {@link Role} to {@link RoleInfo}.
	 * 
	 * @param role
	 *            - original {@link Role}
	 * @return {@link RoleInfo}
	 */
	private static RoleInfo convertToRoleInfo(final Role role) {
		RoleInfo result = new RoleInfo();
		result.setRoleName(role.getName());
		result.setRoleType((role instanceof StaticRole) ? "StaticRole" : "DynamicRole");
		return result;
	}

	/**
	 * Reset internal configuration and caches.
	 */
	public synchronized void reset() {
		roles.clear();
		collections.clear();
	}

}
