package net.anotheria.access.custom;

import net.anotheria.access.Ontology;

public class RegAgencyIsNotEqualConstraint extends SubjectAttributeIsNotEqualToObjectAttributeConstraint{

	@Override
	protected String getAttributeName() {
		return Ontology.ATT_REG_AGENCY;
	}
}
