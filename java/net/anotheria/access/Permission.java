package net.anotheria.access;

public interface Permission {
	public PermissionReply isAllowed(String action);
	
	public String getName();
	
	public void firePermissionUpdate();
}
