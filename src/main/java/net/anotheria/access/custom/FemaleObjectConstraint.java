package net.anotheria.access.custom;

import net.anotheria.access.Ontology;

/**
 * Is met if the object is of female gender.
 * @author another
 */
public class FemaleObjectConstraint extends AbstractObjectGenderConstraint{

	@Override
	public String getRequiredGender() {
		return Ontology.ATT_VAL_FEMALE;
	}
	
	public String toString(){
		return "Object is Female";
	}
}
