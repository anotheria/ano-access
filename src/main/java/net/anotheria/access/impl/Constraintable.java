package net.anotheria.access.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Base class which can be supplied with constraints. Performs some constraint handling functionality.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public abstract class Constraintable implements Serializable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -2881592414409021639L;

	/**
	 * Contained constraints.
	 */
	private List<Constraint> constraints;

	/**
	 * Default constructor.
	 */
	protected Constraintable() {
		constraints = new ArrayList<>();
	}

	/**
	 * Add constraint.
	 * 
	 * @param c
	 *            - constraint
	 * @param additional
	 *            - additional constraints
	 */
	public void addConstraint(Constraint c, Constraint... additional) {
		if (c != null)
			constraints.add(c);

		if (additional != null)
			for (Constraint ac : additional)
				if (ac != null)
					constraints.add(ac);
	}

	/**
	 * Add constraints.
	 * 
	 * @param aConstraints
	 *            - constraints to add
	 */
	public void addConstraints(Collection<Constraint> aConstraints) {
		constraints.addAll(aConstraints);
	}

	/**
	 * Remove constraint.
	 * 
	 * @param constraint
	 *            - constraint to remove
	 */
	public void removeConstraint(Constraint constraint) {
		constraints.remove(constraint);
	}

	/**
	 * Get constraints.
	 * 
	 * @return {@link List} of {@link Constraint}
	 */
	public List<Constraint> getConstraints() {
		return constraints;
	}

	/**
	 * Returns <code>true</code> if all constraints are met.
	 * 
	 * @return <code>true</code> if met or <code>false</code>
	 */
	protected boolean constraintsMet() {
		if (constraints == null || constraints.isEmpty())
			return true;

		for (Constraint c : constraints)
			if (c != null && !c.isMet())
				return false;

		return true;
	}

	@Override
	public String toString() {
		return constraints == null || constraints.isEmpty() ? "" : "Constraint: " + constraints;
	}

}
