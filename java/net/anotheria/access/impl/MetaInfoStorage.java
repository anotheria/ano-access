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
	
	public List<Role> getRoles() {
		return new ArrayList<Role>(roles.values());
	}
	
	public List<RoleInfo> getRoleInfos() {
		List<RoleInfo> roleInfos = new ArrayList<RoleInfo>();
		for (Role role : getRoles()) {			
			roleInfos.add(convertToRoleInfo(role));
		}
		return roleInfos;
	}
	
	public List<RoleInfo> getRoleInfos(List<String> roleNames) {
		List<RoleInfo> roleInfoList = new ArrayList<RoleInfo>();
		for (Role role : roles.values()) {
			if (roleNames.contains(role.getName())) 
				roleInfoList.add(convertToRoleInfo(role));
		}
		return roleInfoList;
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
	
	private RoleInfo convertToRoleInfo(Role role) {
		RoleInfo rInfo = new RoleInfo();
		rInfo.setRoleName(role.getName());
		rInfo.setRoleType((role instanceof StaticRole )? "StaticRole" : "DynamicRole");
		return rInfo;
	}

	public boolean deleteRole(Role toDelete) {
		return roles.remove(toDelete.getName(), toDelete);
		
	}
	
}
