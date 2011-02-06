package net.anotheria.access.custom;

import net.anotheria.access.Ontology;

public class MemberSubjectConstraint extends AbstractSubjectStatusConstraint{

	@Override
	public String getRequiredStatus() {
		return Ontology.ATT_VAL_STATUS_MEMBER;
	}
	
	public String toString(){
		return "Subject is member";
	}
}
