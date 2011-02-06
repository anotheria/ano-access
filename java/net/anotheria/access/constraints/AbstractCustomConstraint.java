package net.anotheria.access.constraints;

import java.io.Serializable;
/**
 * An abstract adapter class for custom constraint if you don't care about the optional parameter. 
 * @author another
 *
 */
public abstract class AbstractCustomConstraint implements CustomConstraint, Serializable{

	/**
	 * Override this if your constraint is really customizeable
	 */
	public void setParameter(String parameter) {
	}


}
