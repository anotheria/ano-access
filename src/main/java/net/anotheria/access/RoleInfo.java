package net.anotheria.access;

import java.io.Serializable;

/**
 * Description of a role used for information methods to provide runtime info about bouncer internal data.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class RoleInfo implements Serializable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 1665053797089235026L;

	/**
	 * The name of the role.
	 */
	private String roleName;

	/**
	 * The type of the role, i.e. dynamic or static.
	 */
	private String roleType;

	/**
	 * Description of the constraints attached to the role.
	 */
	private String constraints;

	/**
	 * Default constructor.
	 */
	public RoleInfo() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param aRoleName
	 *            - role name
	 * @param aRoleType
	 *            - role type
	 * @param aConstraints
	 *            - role constraints
	 */
	public RoleInfo(String aRoleName, String aRoleType, String aConstraints) {
		this.roleName = aRoleName;
		this.roleType = aRoleType;
		this.constraints = aConstraints;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(final String aRoleName) {
		this.roleName = aRoleName;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(final String aRoleType) {
		this.roleType = aRoleType;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(final String aConstraints) {
		this.constraints = aConstraints;
	}

	@Override
	public String toString() {
        return roleName + " [T:" + roleType + ", C:" + constraints + ']';
	}

}
