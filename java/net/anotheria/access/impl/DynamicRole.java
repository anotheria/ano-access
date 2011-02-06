package net.anotheria.access.impl;

import java.io.Serializable;

import net.anotheria.access.AccessServiceException;
import net.anotheria.access.PermissionReply;
import net.anotheria.access.Role;

/**
 * A dynamic role is a role which inherits its own permission set. The permission set is cloned from a template upon creation. Dynamic roles are immune to changes in original permission sets upon creation.
 * Dynamic roles can be altered themself without altering the source permission set. Both features are separating them from static roles. Permission roles are especially useful for credit-based permissions.
 * @author lrosenberg
 *
 */
public class DynamicRole extends AbstractRole implements Role, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2735587109456672880L;
	
	private PermissionCollection collection;
	
	private Integer i;
	
	public DynamicRole(String name){
		super(name);
	}
	
	public PermissionReply isAllowed(String action)  throws AccessServiceException {
		
		if (!constraintsMet())
			return null;
		
		PermissionReply myReply = getCollection().isAllowed(action);
		if (myReply!=null)
			myReply.setRoleName(getName());
		return myReply;
	}

	public PermissionCollection getCollection() {
		return collection;
	}

	public void setCollection(PermissionCollection collection) {
		this.collection = collection;
	}

	public void firePermissionUpdate(String permissionName){
		getCollection().firePermissionUpdate(permissionName);
	}
}
