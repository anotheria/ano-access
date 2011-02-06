package net.anotheria.access;

/**
 * A role is a collection of permissions which can be guarded by constraints and assigned to a user. Instead of assigning each permission to a user separately the role is used as container. 
 * The role can not only contain multiple permissions, but also be guarded by constraints and change itself over time (dynamic role).
 * @author lrosenberg
 */
public interface Role {
	
	/**
	 * Returns a permission reply if one of the permissions were able to provide answer to the request whether the action is allowed to be executed in current context.
	 * @param action
	 * @return
	 * @throws AccessServiceException
	 */
	PermissionReply isAllowed(String action) throws  AccessServiceException;
	/**
	 * Returns the name of the role.
	 * @return
	 */
	String getName();
}
