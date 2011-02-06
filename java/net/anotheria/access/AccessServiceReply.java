package net.anotheria.access;
/**
 * Returned by calls to the bouncer functions. This is a simple container class used to return 
 * both the reply and the debug data.
 * @author another
 */
public class AccessServiceReply {
	/**
	 * True if the bouncer was able to decide the request.
	 */
	private boolean answered;
	/**
	 * True if bouncers decision was positive. Only valid if answered==true.
	 */
	private boolean allowed;
		
	public static final AccessServiceReply UNDECIDED = new AccessServiceReply();
	
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
	 * Creates a new bouncer reply.
	 */
	public AccessServiceReply(){
		answered = false;
		allowed = false;
		
	}
	
	@Override public String toString(){
		if (!answered)
			return "undecided";
		return "Allowed: "+allowed+", decidedByP: "+decidedByPermission+", decidedByR: "+decidedByRole+", decidedByPriority: "+decidedByPermissionPriority;
	}

	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}

	public String getDecidedByPermission() {
		return decidedByPermission;
	}

	public void setDecidedByPermission(String decidedByPermission) {
		this.decidedByPermission = decidedByPermission;
	}

	public int getDecidedByPermissionPriority() {
		return decidedByPermissionPriority;
	}

	public void setDecidedByPermissionPriority(int decidedByPermissionPriority) {
		this.decidedByPermissionPriority = decidedByPermissionPriority;
	}

	public String getDecidedByRole() {
		return decidedByRole;
	}

	public void setDecidedByRole(String decidedByRole) {
		this.decidedByRole = decidedByRole;
	}
}
