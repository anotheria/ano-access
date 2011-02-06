package net.anotheria.access.custom;

import net.anotheria.access.Ontology;

public class MemberObjectConstraint extends AbstractObjectStatusConstraint{

	@Override
	public String getRequiredStatus() {
		return Ontology.ATT_VAL_STATUS_MEMBER;
	}
	
	public String toString(){
		return "Object is member";
	}
}
