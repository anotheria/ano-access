package net.anotheria.access.impl;

import net.anotheria.access.SecurityObject;

/**
 * A context of a request. Each request is executed in its own context. <br>
 * The context is thread local hence can be accessed from everywhere by {@link AccessContext} getContext() safely.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class AccessContext {

	/**
	 * Current access context. Unique for each request.
	 */
	private static InheritableThreadLocal<AccessContext> accessContext = new InheritableThreadLocal<AccessContext>() {

		@Override
		protected synchronized AccessContext initialValue() {
			return new AccessContext();
		}

	};

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
	private AccessContext() {
		reset();
	}

	/**
	 * Get current access context.
	 * 
	 * @return {@link AccessContext} instance
	 */
	public static AccessContext getContext() {
		return accessContext.get();
	}

	public SecurityObject getObject() {
		return object;
	}

	public void setObject(final SecurityObject aObject) {
		this.object = aObject;
	}

	public SecurityObject getSubject() {
		return subject;
	}

	public void setSubject(final SecurityObject aSubject) {
		this.subject = aSubject;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(final boolean aDirty) {
		this.dirty = aDirty;
	}

	/**
	 * Called upon association with a Thread, to remove all tracks of the previous service.
	 */
	public void reset() {
		object = null;
		subject = null;
	}

}
