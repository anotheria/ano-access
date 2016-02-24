package net.anotheria.access;

/**
 * The base class for all bouncer service exceptions.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class AccessServiceException extends Exception {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -7681219295175216002L;

	/**
	 * Default constructor.
	 */
	public AccessServiceException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            - exception message
	 */
	public AccessServiceException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            - exception cause
	 */
	public AccessServiceException(final Throwable cause) {
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
	public AccessServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
