package net.anotheria.access.storage.dlp;


public class PersistedObjectNotFoundException extends PersistenceException {
	public PersistedObjectNotFoundException(String userId){
		super("No object found for user: "+userId);
	}
}
