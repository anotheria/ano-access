package net.anotheria.access.impl;

import net.anotheria.access.AccessServiceException;
import net.anotheria.access.PermissionReply;

/**
 * A static role is tied to a permission collection but has no imminent copy.
 * 
 * @author Leon Rosenberg
 */
public class StaticRole extends AbstractRole {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 4181910990819498523L;

	/**
	 * Permission set id.
	 */
	private String permissionSetId;

	/**
	 * Permission collection.
	 */
	private transient PermissionCollection collection;

	/**
	 * Public constructor.
	 * 
	 * @param name
	 *            - role name
	 */
	public StaticRole(final String name) {
		super(name);
        this.permissionSetId = getName();
    }

	public String getPermissionSetId() {
		return permissionSetId;
	}

	public void setPermissionSetId(final String aPermissionSetId) {
		this.permissionSetId = aPermissionSetId;
	}

	private PermissionCollection getCollection() throws AccessServiceException {
		if (collection != null)
			return collection;

		synchronized (this) {
			if (collection != null)
				return collection;

            collection = MetaInfoStorage.INSTANCE.getPermissionCollection(permissionSetId);
			return collection;
		}
	}

	/**
	 * Is action allowed.
	 * 
	 * @param action
	 *            - action
	 * @return {@link PermissionCollection}
	 * @throws AccessServiceException
	 */
	public PermissionReply isAllowed(final String action) throws AccessServiceException {
		if (!constraintsMet())
			return null;

		PermissionReply result = getCollection().isAllowed(action);
		if (result != null)
			result.setRoleName(getName());

		return result;
	}

	@Override
	public String toString() {
        return super.toString() + " PSetId: " + permissionSetId + ", PCollection: " + collection;
	}

	@Override
	public int hashCode() {
		final int prime = 42;
		int result = 1;
		result = prime * result + getName().hashCode(); // is it working for subclass?
		result = prime * result + permissionSetId.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StaticRole) {
			StaticRole dest = (StaticRole) obj;
            if (this.getName().equals(dest.getName()) && permissionSetId.equals(dest.permissionSetId)) { // this line could be broken...
				return true;
			}
		}
		return false;
	}

}
