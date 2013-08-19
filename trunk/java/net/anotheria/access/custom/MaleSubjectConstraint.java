package net.anotheria.access.custom;

import net.anotheria.access.Ontology;

/**
 * Constraint that is only met if the subject is male.
 * @author another
 *
 */
public class MaleSubjectConstraint extends AbstractSubjectGenderConstraint{

	@Override
	public String getRequiredGender() {
		return Ontology.ATT_VAL_MALE;
	}
	
	public String toString(){
		return "Subject is male";
	}

}
