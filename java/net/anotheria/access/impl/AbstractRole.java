package net.anotheria.access.impl;

import java.io.Serializable;

import net.anotheria.access.Role;

/**
 * Base class for constaints.
 * @author another
 *
 */
public abstract class AbstractRole extends Constraintable implements Serializable, Role{
	/**
	 * Name of the role.
	 */
	private String name;
	
	protected AbstractRole(String aName){
		name = aName;
	}
	
	@Override public String toString(){
		return "Role: "+getName()+" "+super.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
