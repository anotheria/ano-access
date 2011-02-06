package net.anotheria.access;
/**
 * This object is used as a container for a permission reply. 
 * @author another
 *
 */
public class PermissionReply {
	/**
	 * True if the permission is granted.
	 */
	private boolean allow;
	/**
	 * Priority of the permission.
	 */
	private int priority;
	private String permissionName;
	private String roleName;
	
	public String toString(){
		return "Result: "+allow+", P: "+priority+", pName: "+permissionName+", rName: "+roleName;
	}
	
	
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public boolean isAllow() {
		return allow;
	}
	public void setAllow(boolean allow) {
		this.allow = allow;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	

}
