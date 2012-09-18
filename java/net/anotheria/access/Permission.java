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
	public PermissionReply isAllowed(String action);

	/**
	 * Returns permission name.
	 * 
	 * @return {@link String}
	 */
	public String getName();

	/**
	 * Invoke permission update.
	 */
	public void firePermissionUpdate();

}
