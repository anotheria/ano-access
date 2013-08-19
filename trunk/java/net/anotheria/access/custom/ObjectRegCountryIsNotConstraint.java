package net.anotheria.access.custom;

/**
 * Is met if the object registration country (derived from locale) is NOT equal to the parameter country.
 * @author lrosenberg
 */
public class ObjectRegCountryIsNotConstraint extends ObjectRegCountryIsConstraint{
	
	@Override public boolean isMet() {
		return !super.isMet();
	}
	
	@Override public String toString(){
		return "ObjectRegCountry is NOT "+getCountry();
	}

}
