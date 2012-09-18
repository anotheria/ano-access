package net.anotheria.access.storage;

/**
 * {@link SecurityBoxStorageService} main exception.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class SecurityBoxStorageServiceException extends Exception {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 3483900384459754204L;

	/**
	 * Default constructor.
	 */
	public SecurityBoxStorageServiceException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            - exception message
	 */
	public SecurityBoxStorageServiceException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            - exception cause
	 */
	public SecurityBoxStorageServiceException(final Throwable cause) {
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
	public SecurityBoxStorageServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
