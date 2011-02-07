package net.anotheria.access.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.anotheria.access.Role;
import net.anotheria.anoprise.dualcrud.CrudSaveable;

/**
 * A security box holds information about roles granted to a specific security object owner, i.e. user.
 * @author another
 *
 */
public class SecurityBox implements Serializable, CrudSaveable{
	
	private static final long serialVersionUID = 6151326668037727657L; 

	private String ownerId;
	private Map<String, Role> roles;

	public SecurityBox(){
		roles = new HashMap<String, Role>();
	}
	
	public SecurityBox(String anOwnerId){
		this();
		ownerId = anOwnerId;
	}
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public boolean hasRole(String name){
		return roles.containsKey(name);
	}
	
	public void addRole(Role role){
		if (role==null)
			throw new IllegalArgumentException("Can't add an empty role");
		roles.put(role.getName(), role);
	}
	
	public void removeRole(String roleName){
		roles.remove(roleName);
	}
	
	public List<String> getOwnedRoles(){
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(roles.keySet());
		return ret;
	}
	
	public Collection<Role> getRoles(){
		return roles.values();
	}
	
	public String getUserId() {
		return ownerId;
	}


	
}
