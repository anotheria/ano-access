package net.anotheria.access;

import java.io.Serializable;

/**
 * This object is used as a container for a permission reply.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class PermissionReply implements Serializable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -3429572459053498217L;

	/**
	 * If permission if allowed it <code>true</code> otherwise it <code>false</code>.
	 */
	private boolean allow;

	/**
	 * Permission priority.
	 */
	private int priority;

	/**
	 * Permission name.
	 */
	private String permissionName;

	/**
	 * Role name.
	 */
	private String roleName;

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(final String aPermissionName) {
		this.permissionName = aPermissionName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(final String aRoleName) {
		this.roleName = aRoleName;
	}

	public boolean isAllow() {
		return allow;
	}

	public void setAllow(final boolean aAllow) {
		this.allow = aAllow;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(final int aPriority) {
		this.priority = aPriority;
	}

	@Override
	public String toString() {
		return "Result: " + allow + ", priority: " + priority + ", pName: " + permissionName + ", rName: " + roleName;
	}

}
