package net.anotheria.access.storage;

import net.anotheria.access.impl.SecurityBox;

public interface SecurityBoxStorage {
	
	/**
	 * Loads a security box from storage.
	 * @param objectId
	 * @return
	 * @throws SecurityBoxStorageException
	 * @throws SecurityBoxStorageBoxNotFoundException
	 */
	SecurityBox loadSecurityBox(String objectId) throws SecurityBoxStorageException, SecurityBoxStorageBoxNotFoundException;
	/**
	 * Saves a security box
	 * @param box
	 * @throws SecurityBoxStorageException
	 */
	void saveSecurityBox(SecurityBox box) throws SecurityBoxStorageException;

}
