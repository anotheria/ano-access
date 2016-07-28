package net.anotheria.access.impl;

import net.anotheria.access.Permission;
import net.anotheria.access.PermissionReply;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Collection of the configured permissions.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class PermissionCollection implements Serializable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -921766926092186575L;

	/**
	 * Collection id.
	 */
	private String id;

	/**
	 * Permissions list.
	 */
	private List<Permission> permissions;

	/**
	 * Public constructor.
	 * 
	 * @param aId
	 *            - collection id
	 */
	public PermissionCollection(final String aId) {
		this.id = aId;
		this.permissions = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * Add permission.
	 * 
	 * @param permission
	 *            - permission to add, adding will be skipped if it already exist
	 * @param additional
	 *            - additional permissions, adding of each additional permission will be skipped if it already exist
	 */
	public void add(final Permission permission, final Permission... additional) {
		if (permission != null && !containsPermission(permission))
			permissions.add(permission);

		if (additional != null)
			for (Permission aPermission : additional)
				if (aPermission != null && !containsPermission(aPermission))
					permissions.add(aPermission);
	}

	/**
	 * Remove permission.
	 * 
	 * @param permission
	 */
	public void remove(final Permission permission) {
		permissions.remove(permission);
	}

	/**
	 * Check is permission already exist.
	 * 
	 * @param permission
	 *            - permission to check
	 * @return <code>true</code> if already exist or <code>false</code>
	 */
	private boolean containsPermission(final Permission permission) {
		return permissions.contains(permission);
	}

	/**
	 * Is action allowed.
	 * 
	 * @param action
	 *            - action
	 * @return {@link PermissionReply}
	 */
	public PermissionReply isAllowed(final String action) {
		PermissionReply result = null;

		for (Permission permission : permissions) {
			PermissionReply reply = permission.isAllowed(action);
			if (result == null)
				result = reply;

			else if (reply != null && reply.getPriority() > result.getPriority())
				result = reply;
		}

		return result;

	}

	/**
	 * Fire permission update.
	 * 
	 * @param permissionName
	 *            - permission name
	 */
	public void firePermissionUpdate(final String permissionName) {
		for (Permission permission : permissions) {
			if (permission.getName().equals(permissionName)) {
				permission.firePermissionUpdate();
				return;
			}
		}
	}

	@Override
	public String toString() {
        return "Id " + id + " Collection {" + permissions + '}';
	}

}
