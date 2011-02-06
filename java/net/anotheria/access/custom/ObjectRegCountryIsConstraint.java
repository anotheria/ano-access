package net.anotheria.access.custom;

import net.anotheria.access.Ontology;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.constraints.CustomConstraint;
import net.anotheria.access.impl.AccessContext;

import org.apache.log4j.Logger;


/**
 * Is met if the object's registration country is equal to the parameter country.
 * @author another
 *
 */
public class ObjectRegCountryIsConstraint implements CustomConstraint {
	
	private static Logger log = Logger.getLogger(ObjectRegCountryIsConstraint.class);
	
	/**
	 * The country parameter.
	 */
	private String country; 
	
	@Override public void setParameter(String parameter) {
		try{
			country = parameter;
		}catch(Exception e){
			log.error("Can't parse parameter: "+parameter, e);
		}
	}

	@Override public boolean isMet() {
		
		SecurityObject object = AccessContext.getContext().getObject();
		String usersCountry = object.getAttributeValue(Ontology.ATT_REG_COUNTRY);
		return country.equals(usersCountry);
	}
	
	@Override public String toString(){
		return "ObjectRegCountry is "+country;
	}
	
	protected String getCountry(){
		return country;
	}

}
