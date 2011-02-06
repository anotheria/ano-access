package net.anotheria.access.storage;

import net.anotheria.access.storage.dlp.PersistedObjectNotFoundException;

public class BouncerStorageBoxNotFoundException extends PersistedObjectNotFoundException{
	public BouncerStorageBoxNotFoundException(String userId){
		super(userId);
	}
}
