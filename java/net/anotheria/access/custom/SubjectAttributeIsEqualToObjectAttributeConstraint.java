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
public class SubjectAttributeIsEqualToObjectAttributeConstraint extends AbstractCustomConstraint{

	private String attributeName;
	
	public SubjectAttributeIsEqualToObjectAttributeConstraint(){
	}

	public SubjectAttributeIsEqualToObjectAttributeConstraint(String anAttributeName){
		attributeName = anAttributeName;
	}
	
	@Override public boolean isMet() {
		
		String aName = getAttributeName();
		if (aName==null)
			throw new IllegalArgumentException("attributename neither set, not overwritten");
		SecurityObject object = AccessContext.getContext().getObject();
		SecurityObject subject = AccessContext.getContext().getSubject();
		SOAttribute objectAttribute = object.getAttribute(aName);
		SOAttribute subjectAttribute = subject.getAttribute(aName);
		
		return objectAttribute == null ? subjectAttribute==null : 
			objectAttribute.equals(subjectAttribute);
	}
	
	/**
	 * Returns the name of the attribute to compare.
	 * @return
	 */
	protected String getAttributeName(){
		return attributeName;
	}
	
}
