package net.anotheria.access.impl;

import net.anotheria.access.SecurityObject;

/**
 * A context of a request. Each request is executed in its own context. The context is thread local hence can be accessed 
 * from everywhere by BouncerContext.getContext() savily.
 * @author another
 *
 */
public class AccessContext {
	/**
	 * The object which performs the execution.
	 */
	private SecurityObject object;
	/**
	 * The subject upon which the execution is performed.
	 */
	private SecurityObject subject;
	/**
	 * If dirty a change to the dynamic roles or other dynamic info has been made and the data needs saving.
	 */
	private boolean dirty;
	/**
	 * Creates a new context.
	 */
	private AccessContext(){
		reset();
	}
	

	/**
	 * Called upon association with a Thread, to remove all tracks of the previous service.
	 */
	public void reset(){
		object = null;
		subject = null;
	}

	
	
	////////////// END ////////////////////
	

	private static InheritableThreadLocal<AccessContext> accessContext = new InheritableThreadLocal<AccessContext>(){
		@Override
		protected synchronized AccessContext initialValue(){
			return new AccessContext();
		}
		
	};
	
	public static AccessContext getContext(){
		return accessContext.get();
	}


	public SecurityObject getObject() {
		return object;
	}


	public void setObject(SecurityObject object) {
		this.object = object;
	}


	public SecurityObject getSubject() {
		return subject;
	}


	public void setSubject(SecurityObject subject) {
		this.subject = subject;
	}
	
	public boolean debugOutOn(){
		return false;
	}


	public boolean isDirty() {
		return dirty;
	}


	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}


}
