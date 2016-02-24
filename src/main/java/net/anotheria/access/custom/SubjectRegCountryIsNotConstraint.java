package net.anotheria.access.custom;


public class SubjectRegCountryIsNotConstraint extends SubjectRegCountryIsConstraint {
	
	public boolean isMet() {
		return !super.isMet();
	}
	
	public String toString(){
		return "SubjectRegCountry is NOT "+getCountry();
	}

}
