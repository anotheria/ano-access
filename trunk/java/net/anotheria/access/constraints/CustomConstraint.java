package net.anotheria.access.constraints;

import net.anotheria.access.impl.Constraint;
/**
 * A custom constraint is one that is not a part of the original supplied set, but provided by the developer.
 * @author another
 *
 */
public interface CustomConstraint extends Constraint{
	/**
	 * Sets the parameter from the cms.
	 * @param parameter
	 */
	public void setParameter(String parameter);
}
