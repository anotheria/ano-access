package net.anotheria.access;
/**
 * Description of a role used for information methods to provide runtime info about bouncer internal data.
 * @author another
 *
 */
public class RoleInfo {
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
	 * Creates a new RoleInfo.
	 */
	public RoleInfo(){
		
	}
	
	/**
	 * Creates a RoleInfo.
	 * @param aRoleName
	 * @param aRoleType
	 * @param someConstraints
	 */
	public RoleInfo(String aRoleName, String aRoleType, String someConstraints){
		roleName = aRoleName;
		roleType = aRoleType;
		constraints = someConstraints;
	}
	
	@Override public String toString(){
		return getRoleName()+" [T:"+getRoleType()+", C:"+getConstraints()+"]";
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}
}
