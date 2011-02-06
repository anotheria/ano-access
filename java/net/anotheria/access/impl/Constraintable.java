package net.anotheria.access.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Base class which can be supplied with constaints. Performs some constaint handling functionality.
 * @author another
 *
 */
public abstract class Constraintable implements Serializable{
	
	private static final long serialVersionUID = -2881592414409021639L;
	/**
	 * Contained constraints.
	 */
	private List<Constraint> constraints;
	/**
	 * creates a new constraintable.
	 */
	protected Constraintable(){
		constraints = new ArrayList<Constraint>();
	}
	
	public void addConstraint(Constraint c){
		constraints.add(c);
	}
	
	public void addConstraints(List<Constraint> someConstraints){
		constraints.addAll(someConstraints);
	}
	
	
	public void removeConstraint(Constraint c){
		constraints.remove(c);
	}

	public List<Constraint> getConstraints(){
		return constraints;
	}
	
	@Override public String toString(){
		return constraints==null || constraints.size()==0 ? 
				"" : "Const: "+constraints.toString();
	}
	/**
	 * Returns true if all constraints are met. A constraintable which is guarded by unmet constraints should be considered invalid and be ignored.
	 * @return
	 */
	protected boolean constraintsMet(){
		if (constraints==null || constraints.size()==0)
			return true;
		for (Constraint c : constraints)
			if (!c.isMet())
				return false;
		return true;
	}
}
