package net.anotheria.access;
/**
 * The base class for all bouncer service exceptions.
 * @author another
 *
 */
public class AccessServiceException extends Exception{
	public AccessServiceException(Exception cause){
		super(cause);
	}
	
	public AccessServiceException(String message){
		super(message);
	}
}
