package net.anotheria.access.storage;

import net.anotheria.access.impl.SecurityBox;

/**
 * {@link SecurityBoxStorageService} exception, service throw it if requested {@link SecurityBox} not found.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 * 
 */
public class SecurityBoxStorageServiceBoxNotFoundException extends SecurityBoxStorageServiceException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 2391261539029924508L;

	/**
	 * Default constructor.
	 */
	public SecurityBoxStorageServiceBoxNotFoundException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            - exception message
	 */
	public SecurityBoxStorageServiceBoxNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            - exception cause
	 */
	public SecurityBoxStorageServiceBoxNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            - exception message
	 * @param cause
	 *            - exception cause
	 */
	public SecurityBoxStorageServiceBoxNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
