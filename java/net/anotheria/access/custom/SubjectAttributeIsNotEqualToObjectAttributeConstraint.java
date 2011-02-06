package net.anotheria.access.custom;

/**
 * Base class for all constraints that simply compare an object attribute to a subject's attribute and are only met if both attributes aren't equal. 
 * @author lrosenberg
 *
 */
public abstract class SubjectAttributeIsNotEqualToObjectAttributeConstraint extends SubjectAttributeIsEqualToObjectAttributeConstraint{

	@Override public boolean isMet() {
		return !super.isMet();
	}

}
