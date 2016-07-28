package net.anotheria.access.impl;

import net.anotheria.access.AccessServiceException;
import net.anotheria.access.PermissionReply;

/**
 * A dynamic role is a role which inherits its own permission set. <br>
 * The permission set is cloned from a template upon creation. <br>
 * Dynamic roles are immune to changes in original permission sets upon creation. <br>
 * Dynamic roles can be altered them self without altering the source permission set.<br>
 * Both features are separating them from static roles. <br>
 * Permission roles are especially useful for credit-based permissions.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class DynamicRole extends AbstractRole {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 4989698142564198024L;

	/**
	 * Permissions collection.
	 */
	private PermissionCollection collection;

	/**
	 * Public constructor.
	 * 
	 * @param name
	 *            - role name
	 */
	public DynamicRole(final String name) {
		super(name);
	}

	public PermissionCollection getCollection() {
		return collection;
	}

	public void setCollection(final PermissionCollection aCollection) {
		this.collection = aCollection;
	}

	/**
	 * Is action allowed.
	 * 
	 * @param action
	 *            - action
	 * @return {@link PermissionReply}
	 */
	public PermissionReply isAllowed(final String action) throws AccessServiceException {
		if (!constraintsMet())
			return null;

        PermissionReply result = collection.isAllowed(action);
		if (result != null)
			result.setRoleName(getName());

		return result;
	}

	/**
	 * Fire permission update.
	 * 
	 * @param permissionName
	 *            - permission name
	 */
	public void firePermissionUpdate(String permissionName) {
        collection.firePermissionUpdate(permissionName);
	}

}
