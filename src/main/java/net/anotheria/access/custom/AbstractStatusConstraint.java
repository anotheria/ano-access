package net.anotheria.access.custom;

import net.anotheria.access.Ontology;
import net.anotheria.access.SOAttribute;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.constraints.AbstractCustomConstraint;

/**
 * Base constraint class 
 * @author another
 *
 */
public abstract class AbstractStatusConstraint extends AbstractCustomConstraint{
	public abstract SecurityObject getRelatedObject();
	
	public abstract String getRequiredStatus();

	public boolean isMet() {
		
		SecurityObject obj = getRelatedObject();
		if (obj==null)
			return false;
		SOAttribute status = obj.getAttribute(Ontology.ATT_STATUS);
		
		if (status==null)
			return false;
		return status.getValue().equals(getRequiredStatus());
	}
}
