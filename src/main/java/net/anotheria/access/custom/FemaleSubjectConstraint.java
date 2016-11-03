package net.anotheria.access.custom;

import net.anotheria.access.Ontology;
/**
 * Is met if the subject is of female gender.
 * @author another
 */
public class FemaleSubjectConstraint extends AbstractSubjectGenderConstraint{

	@Override public String getRequiredGender() {
		return Ontology.ATT_VAL_FEMALE;
	}
	
	@Override public String toString(){
		return "Subject is Female";
	}

}
