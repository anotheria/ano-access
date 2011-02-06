package net.anotheria.access.storage.dlp;

public class PersistenceException extends Exception{
	public PersistenceException(String message){
		super(message);
	}

	public PersistenceException(Exception source){
		super("Persistence failed: "+source.getMessage(), source);
	}
}
