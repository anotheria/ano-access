package net.anotheria.access;

import java.io.Serializable;

/**
 * Permission interface.
 * 
 * @author Leon Rosenberg
 */
public interface Permission extends Serializable {

	/**
	 * Check is permission allowed for the user.
	 * 
	 * @param action
	 *            - action
	 * @return {@link PermissionReply}
	 */
    PermissionReply isAllowed(String action);

	/**
	 * Returns permission name.
	 * 
	 * @return {@link String}
	 */
    String getName();

	/**
	 * Invoke permission update.
	 */
    void firePermissionUpdate();

}
