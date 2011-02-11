package net.anotheria.access.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.anotheria.access.Role;
import net.anotheria.access.RoleInfo;

public enum MetaInfoStorage {
	INSTANCE;

	private ConcurrentMap<String, Role> roles = new ConcurrentHashMap<String, Role>();
	private ConcurrentMap<String, PermissionCollection> collections = new ConcurrentHashMap<String, PermissionCollection>();

	
	public Role getRole(String roleName) {
		Role role = roles.get(roleName);
		if (role==null)
			throw new IllegalArgumentException("Role "+roleName+" doesn't exists.");
		return role;
	}

	public void addRole(Role role) {
		roles.put(role.getName(), role);
		
	}
	
	//TODO:
	public List<Role> getRoles() {
		//go thru iterator.		
		return new ArrayList<Role>(roles.values());
	}
	
	//TODO:
	public List<RoleInfo> getRoleInfos() {
		List<RoleInfo> roleInfos = new ArrayList<RoleInfo>();
		for (Role role : getRoles()) {
			RoleInfo r = new RoleInfo();
			r.setRoleName(role.getName());
			r.setRoleType((role instanceof StaticRole )? "StaticRole" : "DynamicRole");
			roleInfos.add(r);
		}
		return roleInfos;
	}

	public boolean hasRole(String roleName) {
		return roles.containsKey(roleName);
	}
	
	public PermissionCollection getPermissionCollection(String collectionName) {
		PermissionCollection collection = collections.get(collectionName);
		if (collection==null)
			throw new IllegalArgumentException("PermissionCollection "+collectionName+" doesn't exists.");
		return collection;
	}

	public void addPermissionCollection(PermissionCollection collection) {
		collections.put(collection.getId(), collection);
		
	}

	public boolean hasPermissionCollection(String collectionName) {
		return collections.containsKey(collectionName);
	}
	
}
