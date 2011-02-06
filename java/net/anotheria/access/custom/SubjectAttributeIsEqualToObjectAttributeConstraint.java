package net.anotheria.access.custom;

import net.anotheria.access.SOAttribute;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.constraints.AbstractCustomConstraint;
import net.anotheria.access.impl.AccessContext;
/**
 * Base class for all constraints that simply compare an object attribute to a subject's attribute for equality. For example same gender, same registration country and so on.
 * The constraint is met if both attributes are unset or equal.
 * @author lrosenberg
 *
 */
public abstract class SubjectAttributeIsEqualToObjectAttributeConstraint extends AbstractCustomConstraint{

	@Override public boolean isMet() {
		
		SecurityObject object = AccessContext.getContext().getObject();
		SecurityObject subject = AccessContext.getContext().getSubject();
		SOAttribute objectAttribute = object.getAttribute(getAttributeName());
		SOAttribute subjectAttribute = subject.getAttribute(getAttributeName());
		
		return objectAttribute == null ? subjectAttribute==null : 
			objectAttribute.equals(subjectAttribute);
	}
	
	/**
	 * Returns the name of the attribute to compare.
	 * @return
	 */
	protected abstract String getAttributeName();
	
}
