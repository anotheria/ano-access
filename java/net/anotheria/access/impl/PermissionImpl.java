package net.anotheria.access.impl;

import java.io.Serializable;
import java.util.List;

import net.anotheria.access.Permission;
import net.anotheria.access.PermissionReply;
import net.anotheria.access.constraints.FireableConstraint;


/**
 * Implementation of a permission.
 * @author another
 *
 */
public class PermissionImpl extends Constraintable implements Serializable, Permission{
	/**
	 * A permission can be allowing or denying. If allow=true the permission is allowing, otherwise its denying.
	 */
	private boolean allow;
	/**
	 * The action the permission has been bound too.
	 */
	private String action;
	/**
	 * The priority of the permission. Higher priority permissions overrule lower priority permissions.
	 */
	private int priority;
	/**
	 * Name of the permission for human understanding.
	 */
	private String name;
	
	public boolean isAllow() {
		return allow;
	}
	public void setAllow(boolean allow) {
		this.allow = allow;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
	public PermissionReply isAllowed(String anAction){
		out("checking for "+anAction+" on "+AccessContext.getContext().getObject());
		
		if (anAction==null || !anAction.equals(action))
			return null;
		
		out("\t"+anAction+" matches my action");
		
		if (!constraintsMet())
			return null;
		
		PermissionReply ret = new PermissionReply();
		
		ret.setAllow(allow);
		ret.setPriority(priority);
		ret.setPermissionName(name);
		
		
		return ret;
	}
	
	private void out(Object o){
		if (AccessContext.getContext().debugOutOn())
			System.out.println("\t\t["+this+"] "+o);
	}
	
	@Override public String toString(){
		return "A: "+getAction()+", All: "+isAllow()+", P: "+getPriority();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void firePermissionUpdate(){
		List<Constraint> constraints = getConstraints();
		for (Constraint c : constraints)
			if (c instanceof FireableConstraint)
				((FireableConstraint)c).notifyFired();
		
	}
}
