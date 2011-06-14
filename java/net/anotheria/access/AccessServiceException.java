package net.anotheria.access;
/**
 * The base class for all bouncer service exceptions.
 * @author another
 *
 */
public class AccessServiceException extends Exception{
	/**
	 * default serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	public AccessServiceException(Exception cause){
		super(cause);
	}
	
	public AccessServiceException(String message){
		super(message);
	}
	
	public AccessServiceException(String message, Exception cause){
		super(message, cause);
	}
}
