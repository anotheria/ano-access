package net.anotheria.access.impl;

import java.io.Serializable;
/**
 * Constaint is a guarding condition which can be applied to a role or permissions. Constaints must be met, otherwise the target to which the constaint has been applied should be considered
 * invalid.
 * @author another
 *
 */
public interface Constraint extends Serializable {
	/**
	 * Returns true if the constraint is met. The permission is valid only if all constraints are met.
	 * @return
	 */
	public boolean isMet();
}
