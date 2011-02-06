package net.anotheria.access.custom;

import net.anotheria.access.SecurityObject;
import net.anotheria.access.constraints.CustomConstraint;
import net.anotheria.access.impl.AccessContext;

import org.apache.log4j.Logger;


/**
 * Is met if the value of the submitted count attribute is equal to the configured parameter value.
 * @author another
 *
 */
public class CountEqualConstraint implements CustomConstraint {
	
	private static Logger log = Logger.getLogger(CountEqualConstraint.class);
	
	private int count = Integer.MAX_VALUE;
	
	@Override public void setParameter(String parameter) {
		try{
			count = Integer.parseInt(parameter);
		}catch(Exception e){
			log.error("Can't parse parameter: "+parameter, e);
		}
	}

	@Override public boolean isMet() {
		
		SecurityObject subject = AccessContext.getContext().getSubject();
		String sCount = subject.getAttributeValue("count");
		if (sCount==null)
			return false;
		int countToCheck = 0;
		try{
			countToCheck = Integer.parseInt(sCount);
		}catch(NumberFormatException e){
			log.error("isMet.parseInt("+sCount+")", e);
		}
		
		return count == countToCheck;
	}
	
	@Override public String toString(){
		return "Count equal "+count;
	}

}
