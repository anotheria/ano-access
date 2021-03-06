package net.anotheria.access.custom;

import net.anotheria.access.Ontology;
/**
 * Is met if the registration country of both object and subject are equal.
 * @author another
 *
 */
public class RegAgencyIsEqualConstraint extends SubjectAttributeIsEqualToObjectAttributeConstraint{

	@Override
	protected String getAttributeName() {
		return Ontology.ATT_REG_AGENCY;
	}
}
