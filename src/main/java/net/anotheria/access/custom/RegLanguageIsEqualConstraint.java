package net.anotheria.access.custom;

import net.anotheria.access.Ontology;

/**
 * Is met if the registration language of both object and subject are equal. Registration language is derived from the locale of the registration agency.
 * @author another
 *
 */
public class RegLanguageIsEqualConstraint extends SubjectAttributeIsEqualToObjectAttributeConstraint{

	@Override
	protected String getAttributeName() {
		return Ontology.ATT_REG_LANG;
	}

}
