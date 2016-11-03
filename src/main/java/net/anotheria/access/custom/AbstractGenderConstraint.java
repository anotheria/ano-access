package net.anotheria.access.custom;

import net.anotheria.access.Ontology;
import net.anotheria.access.SOAttribute;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.constraints.AbstractCustomConstraint;

/**
 * Basic class for special constraints operating on users gender. This constraint is met if the specified object is of specified gender.
 * @author another
 *
 */
public abstract class AbstractGenderConstraint extends AbstractCustomConstraint{
	/**
	 * Returns the object to operate on.
	 * @return
	 */
	public abstract SecurityObject getRelatedObject();
	/**
	 * Returns the required gender.
	 * @return
	 */
	public abstract String getRequiredGender();

	@Override public boolean isMet() {
		
		SecurityObject obj = getRelatedObject();
		if (obj==null)
			return false;
		SOAttribute gender = obj.getAttribute(Ontology.ATT_GENDER);
		
		if (gender==null)
			return false;
		return gender.getValue().equals(getRequiredGender());
	}
}
