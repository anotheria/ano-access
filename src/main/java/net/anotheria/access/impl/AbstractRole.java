package net.anotheria.access.impl;

import net.anotheria.access.Role;

/**
 * Base class for {@link Role} implementation.
 * 
 * @author another
 * 
 */
public abstract class AbstractRole extends Constraintable implements Role {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -5881856648501113435L;

	/**
	 * Role name.
	 */
	private String name;

	/**
	 * Default constructor.
	 * 
	 * @param aName
	 *            - role name
	 */
	protected AbstractRole(final String aName) {
		this.name = aName;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	@Override
	public String toString() {
        return "Role: " + name + ' ' + super.toString();
	}

}
