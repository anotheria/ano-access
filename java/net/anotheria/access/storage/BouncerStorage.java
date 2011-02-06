package net.anotheria.access.storage;

import net.anotheria.access.impl.SecurityBox;

public interface BouncerStorage {
	
	public SecurityBox loadSecurityBox(String objectId) throws BouncerStorageException, BouncerStorageBoxNotFoundException;
	
	public void saveSecurityBox(SecurityBox box) throws BouncerStorageException;

}
