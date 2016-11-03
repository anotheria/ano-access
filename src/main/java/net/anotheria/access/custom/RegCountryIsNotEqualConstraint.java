package net.anotheria.access.custom;

import net.anotheria.access.Ontology;

/**
 * Is met if the registration country of both object and subject are NOT equal.
 * @author another
 *
 */
public class RegCountryIsNotEqualConstraint extends SubjectAttributeIsNotEqualToObjectAttributeConstraint{

	@Override
	protected String getAttributeName() {
		return Ontology.ATT_REG_COUNTRY;
	}

}
