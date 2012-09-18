package net.anotheria.access.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.anotheria.access.Role;
import net.anotheria.anoprise.dualcrud.CrudSaveable;

/**
 * A {@link SecurityBox} holds information about roles granted to a specific security object owner.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class SecurityBox implements Serializable, CrudSaveable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 6151326668037727657L;

	/**
	 * Unique owner id.
	 */
	private String ownerId;

	/**
	 * Assigned roles.
	 */
	private Map<String, Role> roles;

	/**
	 * Default constructor.
	 */
	public SecurityBox() {
		roles = new HashMap<String, Role>();
	}

	/**
	 * Public constructor.
	 * 
	 * @param aOwnerId
	 *            - unique owner id
	 */
	public SecurityBox(final String aOwnerId) {
		this();
		this.ownerId = aOwnerId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(final String aOwnerId) {
		this.ownerId = aOwnerId;
	}

	/**
	 * Has {@link Role}.
	 * 
	 * @param roleName
	 *            - role name
	 * @return <code>true</code> if has or <code>false</code>
	 */
	public boolean hasRole(final String roleName) {
		return roles.containsKey(roleName);
	}

	/**
	 * Add {@link Role}.
	 * 
	 * @param role
	 *            - {@link Role} to add, can't be <code>null</code>
	 */
	public void addRole(Role role) {
		if (role == null)
			throw new IllegalArgumentException("Can't add an empty role");

		roles.put(role.getName(), role);
	}

	/**
	 * Remove {@link Role}.
	 * 
	 * @param roleName
	 *            - {@link Role} name
	 */
	public void removeRole(final String roleName) {
		roles.remove(roleName);
	}

	/**
	 * Get owned roles names.
	 * 
	 * @return {@link List} of {@link String}
	 */
	public List<String> getOwnedRoles() {
		return new ArrayList<String>(roles.keySet());
	}

	/**
	 * Get owned roles.
	 * 
	 * @return {@link List} of {@link Role}
	 */
	public List<Role> getRoles() {
		return new ArrayList<Role>(roles.values());
	}

	/**
	 * Get user id, the same as owner id.
	 * 
	 * @return {@link String}
	 */
	public String getUserId() {
		return ownerId;
	}

}
