package net.anotheria.access;

import java.io.Serializable;

/**
 * A role is a collection of permissions which can be guarded by constraints and assigned to a user. <br>
 * Instead of assigning each permission to a user separately the role is used as container. <br>
 * The role can not only contain multiple permissions, but also be guarded by constraints and change itself over time (dynamic role).
 * 
 * @author Leon Rosenberg
 */
public interface Role extends Serializable {

	/**
	 * Returns a permission reply if one of the permissions were able to provide answer to the request whether the action is allowed to be executed in current
	 * context.
	 * 
	 * @param action
	 *            - action
	 * @return {@link PermissionReply}
	 * @throws AccessServiceException
	 */
	PermissionReply isAllowed(String action) throws AccessServiceException;

	/**
	 * Returns role name.
	 * 
	 * @return {@link String}
	 */
	String getName();
}
