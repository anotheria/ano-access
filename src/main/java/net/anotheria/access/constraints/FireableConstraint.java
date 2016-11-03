package net.anotheria.access.constraints;
/**
 * A fireable constraint is a constraint that can react on changes. For example credit constraints are fireable, as soon as a credit is used the change is "fired" and the constraint 
 * can react by decreasing number of remaining credits.
 * @author another
 *
 */
public interface FireableConstraint {
	/**
	 * Called to announce that the change happened.
	 */
	public void notifyFired();
}
