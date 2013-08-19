package net.anotheria.access.custom;

import net.anotheria.access.Ontology;
/**
 * Met if the object has the premium user status.
 * @author another
 *
 */
public class PremiumObjectConstraint extends AbstractObjectStatusConstraint{

	@Override
	public String getRequiredStatus() {
		return Ontology.ATT_VAL_STATUS_PREMIUM;
	}
	
	@Override public String toString(){
		return "Object is premium";
	}
}
