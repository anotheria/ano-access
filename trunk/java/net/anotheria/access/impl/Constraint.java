package net.anotheria.access.impl;

import java.io.Serializable;

/**
 * Constraint is a guarding condition which can be applied to a role or permission. <br>
 * Constraint must be met, otherwise the target to which the constraint has been applied should be considered invalid.
 * 
 * @author Leon Rosenberg
 */
public interface Constraint extends Serializable {

	/**
	 * Returns <code>true</code> if the constraint is met.
	 * 
	 * @return <code>true</code> if the constraint is met or <code>false</code>
	 */
	public boolean isMet();

}
