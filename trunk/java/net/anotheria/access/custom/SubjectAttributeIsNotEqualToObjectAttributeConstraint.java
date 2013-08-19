package net.anotheria.access.custom;

/**
 * Base class for all constraints that simply compare an object attribute to a subject's attribute and are only met if both attributes aren't equal. 
 * @author lrosenberg
 *
 */
public class SubjectAttributeIsNotEqualToObjectAttributeConstraint extends SubjectAttributeIsEqualToObjectAttributeConstraint{

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -8921448570038249196L;

	public SubjectAttributeIsNotEqualToObjectAttributeConstraint(){
		super();
	}
	
	public SubjectAttributeIsNotEqualToObjectAttributeConstraint(String attributeName){
		super(attributeName);
	}

	@Override public boolean isMet() {
		return !super.isMet();
	}

}
