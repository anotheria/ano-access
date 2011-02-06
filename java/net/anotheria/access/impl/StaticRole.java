package net.anotheria.access.impl;

import net.anotheria.access.AccessServiceException;
import net.anotheria.access.PermissionReply;
import net.anotheria.access.Role;
/**
 * A static role is tied to a permission collection but has no immenent copy. 
 * @author another
 *
 */
public class StaticRole extends AbstractRole implements Role {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String permissionSetId;
	private transient PermissionCollection collection;
	
	public StaticRole(String name){
		super(name);
		setPermissionSetId(getName());
	}
 
	public PermissionReply isAllowed(String action)  throws AccessServiceException {
		
		if (!constraintsMet())
			return null;
		
		PermissionReply myReply = getCollection().isAllowed(action);
		if (myReply!=null)
			myReply.setRoleName(getName());
		return myReply;
	}

	public String getPermissionSetId() {
		return permissionSetId;
	}

	public void setPermissionSetId(String permissionSetId) {
		this.permissionSetId = permissionSetId;
	}
	
	private PermissionCollection getCollection() throws AccessServiceException{
		if (collection==null){
			synchronized(this){
				if (collection==null){
					resolveCollection();
				}
			}
		}
		return collection;
	}
	
	private void resolveCollection() throws AccessServiceException{
		collection = MetaInfoStorage.INSTANCE.getPermissionCollection(getPermissionSetId());
	}
	
	public String toString(){
		return super.toString()+" PSetId: "+getPermissionSetId()+", Coll: "+collection;
	}
}
