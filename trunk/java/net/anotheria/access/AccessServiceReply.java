package net.anotheria.access;

/**
 * Returned by calls to the bouncer functions. This is a simple container class used to return both the reply and debug the data.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class AccessServiceReply {

	/**
	 * {@link AccessServiceReply} instance, used as undecided reply.
	 */
	public static final AccessServiceReply UNDECIDED = new AccessServiceReply();

	/**
	 * True if the bouncer was able to decide the request.
	 */
	private boolean answered;

	/**
	 * True if bouncers decision was positive. Only valid if answered is <code>true</code>.
	 */
	private boolean allowed;

	/**
	 * Returns the name of the permission which was used to make the decision.
	 */
	private String decidedByPermission;

	/**
	 * Returns the priority of the permission which was used to make the decision.
	 */
	private int decidedByPermissionPriority;

	/**
	 * Returns the name of the which was used to make the decision and contained the above permission.
	 */
	private String decidedByRole;

	/**
	 * Default constructor.
	 */
	public AccessServiceReply() {
		this.answered = false;
		this.allowed = false;
	}

	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(final boolean aAnswered) {
		this.answered = aAnswered;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(final boolean aAllowed) {
		this.allowed = aAllowed;
	}

	public String getDecidedByPermission() {
		return decidedByPermission;
	}

	public void setDecidedByPermission(final String aDecidedByPermission) {
		this.decidedByPermission = aDecidedByPermission;
	}

	public int getDecidedByPermissionPriority() {
		return decidedByPermissionPriority;
	}

	public void setDecidedByPermissionPriority(final int aDecidedByPermissionPriority) {
		this.decidedByPermissionPriority = aDecidedByPermissionPriority;
	}

	public String getDecidedByRole() {
		return decidedByRole;
	}

	public void setDecidedByRole(final String aDecidedByRole) {
		this.decidedByRole = aDecidedByRole;
	}

	@Override
	public String toString() {
		if (!answered)
			return "undecided";

		return "Allowed: " + allowed + ", decidedByP: " + decidedByPermission + ", decidedByR: " + decidedByRole + ", decidedByPriority: "
				+ decidedByPermissionPriority;
	}

}
