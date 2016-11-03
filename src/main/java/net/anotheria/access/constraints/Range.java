package net.anotheria.access.constraints;

/**
 * A range, typically time based, which is used in constraints.
 * @author another
 *
 */
public interface Range {
	/**
	 * Returns true if the point of time represented by the given checkpoint is in the range.
	 * @param checkpoint
	 * @return
	 */
	boolean isIn(long checkpoint);
	
	/**
	 * Returns a human readable description of this range.
	 * @return
	 */
	String describe();
}
