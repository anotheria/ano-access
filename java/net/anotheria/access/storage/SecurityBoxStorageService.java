package net.anotheria.access.storage;

import net.anotheria.access.impl.SecurityBox;
import net.anotheria.anoprise.metafactory.Service;

/**
 * Service interface.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public interface SecurityBoxStorageService extends Service {

	/**
	 * Loads a {@link SecurityBox} from storage.
	 * 
	 * @param boxOwnerId
	 *            - security box owner id
	 * @return {@link SecurityBox}
	 * @throws SecurityBoxStorageServiceException
	 * @throws SecurityBoxStorageServiceBoxNotFoundException
	 */
	SecurityBox loadSecurityBox(String boxOwnerId) throws SecurityBoxStorageServiceException, SecurityBoxStorageServiceBoxNotFoundException;

	/**
	 * Saves a {@link SecurityBox}.
	 * 
	 * @param box
	 *            - {@link SecurityBox} to be saved
	 * @throws SecurityBoxStorageServiceException
	 */
	void saveSecurityBox(SecurityBox box) throws SecurityBoxStorageServiceException;

	/**
	 * Deletes previously created {@link SecurityBox} from storage.
	 * 
	 * @param box
	 *            - {@link SecurityBox} to be deleted
	 * @throws SecurityBoxStorageServiceException
	 */
	void deleteSecurityBox(SecurityBox box) throws SecurityBoxStorageServiceException;
}
