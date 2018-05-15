package net.anotheria.access.constraints;

import java.io.Serializable;

import net.anotheria.access.impl.AccessContext;
import net.anotheria.access.impl.Constraint;

/**
 * A credit constraint is only met as long as there are remaining credits.
 * @author another
 *
 */
public class CreditConstraint implements Constraint, Serializable, FireableConstraint{
	/**
	 * Current amount of credits.
	 */
	private int credits;
	/**
	 * Creates a new CreditConstraint.
	 * @param someCredits
	 */
	public CreditConstraint(int someCredits){
		credits = someCredits;
	}
	/**
	 * Returns true if the internal amount of credits is &gt; 0.
	 */
	public boolean isMet() {
		return credits>0;
	}
	/**
	 * Returns current amount of credits.
	 * @return
	 */
	public int getCredits(){
		return credits;
	}
	
	@Override public String toString(){
		return "CreditConstraint "+getCredits();
	}

	@Override public void notifyFired() {
		credits--;
		AccessContext.getContext().setDirty(true);
	}
}
