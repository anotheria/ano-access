package net.anotheria.access.custom;

import net.anotheria.access.Ontology;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.constraints.CustomConstraint;
import net.anotheria.access.impl.AccessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SubjectRegCountryIsConstraint implements CustomConstraint {
	
	private static Logger log = LoggerFactory.getLogger(SubjectRegCountryIsConstraint.class);
	
	private String country; 
	
	public void setParameter(String parameter) {
		try{
			country = parameter;
		}catch(Exception e){
			log.error("Can't parse parameter: "+parameter, e);
		}
	}

	public boolean isMet() {
		
		SecurityObject subject = AccessContext.getContext().getSubject();
		String usersCountry = subject.getAttributeValue(Ontology.ATT_REG_COUNTRY);
		return country.equals(usersCountry);
	}
	
	@Override public String toString(){
		return "SubjectRegCountry is "+getCountry();
	}
	
	protected String getCountry(){
		return country;
	}

}
