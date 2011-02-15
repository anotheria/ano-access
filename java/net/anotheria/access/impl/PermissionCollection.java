package net.anotheria.access.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.anotheria.access.Permission;
import net.anotheria.access.PermissionReply;


public class PermissionCollection implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private List<Permission> permissions;
	
	public PermissionCollection(String anId){
		id = anId;
		permissions = new ArrayList<Permission>();
	}
	
	public PermissionReply isAllowed(String action){
		PermissionReply current = null;
		
		for (Permission p : permissions){
			PermissionReply r = p.isAllowed(action);
			if (current==null)
				current = r;
			else
				if (r!=null && r.getPriority()>current.getPriority())
					current = r;
		}
		
		return current;
		
	}
	
	public String toString(){
		return "Id "+getId()+" Collection {"+permissions+"}";
	}
	
	public void add(Permission p, Permission ...pp){		
		if (!containsPermission(p)) 
			permissions.add(p);
		if (pp!=null){
			for (Permission fromP : pp) {
				if (!containsPermission(fromP)) 
					permissions.add(fromP);				
			}
		}
	}
	
	private boolean containsPermission(Permission p) {		
		for(Permission pr : permissions) {
			if (pr.getName().equals(p.getName())) 
				return true;
		}
		return false;
	}
	
	public void firePermissionUpdate(String permissionName){
		for (Permission p : permissions){
			if (p.getName().equals(permissionName)){
				p.firePermissionUpdate();
				return;
			}
			
		}
	}

	public String getId() {
		return id;
	}

}
