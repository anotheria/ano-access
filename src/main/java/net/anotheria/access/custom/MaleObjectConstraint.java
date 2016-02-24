package net.anotheria.access.custom;

import net.anotheria.access.Ontology;
/**
 * Constraint that is only met if the object is male.
 * @author another
 *
 */
public class MaleObjectConstraint extends AbstractObjectGenderConstraint{

	@Override
	public String getRequiredGender() {
		return Ontology.ATT_VAL_MALE;
	}
	@Override public String toString(){
		return "Object is male";
	}

}
