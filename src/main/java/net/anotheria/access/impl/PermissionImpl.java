package net.anotheria.access.impl;

import net.anotheria.access.Permission;
import net.anotheria.access.PermissionReply;
import net.anotheria.access.constraints.FireableConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;


/**
 * Implementation of a permission.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class PermissionImpl extends Constraintable implements Permission {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 743733158241152986L;

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionImpl.class);

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

	public void setAllow(final boolean aAllow) {
		this.allow = aAllow;
	}

	public String getAction() {
		return action;
	}

	public void setAction(final String aAction) {
		this.action = aAction;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(final int aPriority) {
		this.priority = aPriority;
	}

	public String getName() {
		return name;
	}

	public void setName(final String aName) {
		this.name = aName;
	}

	/**
	 * Is action allowed.
	 * 
	 * @return {@link PermissionReply}
	 */
	public PermissionReply isAllowed(final String aAction) {
		out("Checking for " + aAction + " on " + AccessContext.getContext().getObject());

		if (!Objects.equals(aAction, action))
			return null;

		out('\t' + aAction + " matches my action");

		if (!constraintsMet())
			return null;

		PermissionReply ret = new PermissionReply();

		ret.setAllow(allow);
		ret.setPriority(priority);
		ret.setPermissionName(name);

		return ret;
	}

	/**
	 * Fire permission update.
	 */
	public void firePermissionUpdate() {
		List<Constraint> constraints = getConstraints();
		for (Constraint c : constraints)
			if (c instanceof FireableConstraint)
				((FireableConstraint) c).notifyFired();
	}

	/**
	 * Log debug message.
	 * 
	 * @param o
	 *            - object
	 */
	private void out(Object o) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("\t\t[" + this + "] " + o);
	}

	@Override
	public String toString() {
		return "Action: " + action + ", Allowed: " + allow + ", Priority: " + priority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + (allow ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + priority;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermissionImpl other = (PermissionImpl) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (allow != other.allow)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priority != other.priority)
			return false;
		return true;
	}

}
