package net.anotheria.access.storage;

import net.anotheria.access.impl.SecurityBox;
import net.anotheria.anoprise.dualcrud.CrudServiceException;
import net.anotheria.anoprise.dualcrud.DualCrudConfig;
import net.anotheria.anoprise.dualcrud.DualCrudService;
import net.anotheria.anoprise.dualcrud.DualCrudServiceFactory;
import net.anotheria.anoprise.dualcrud.ItemNotFoundException;

import org.apache.log4j.Logger;

public class SecurityBoxStorageImpl implements SecurityBoxStorage {

	private DualCrudService<SecurityBox> dcPersistenceService;

	protected SecurityBoxStorageImpl() {
		dcPersistenceService = DualCrudServiceFactory.createDualCrudService(new SecurityBoxCrudService(
				SecurityBoxStorageConfig.getInstance()), null,// reserved for
																// future
																// migration
																// projects
				DualCrudConfig.useLeftOnly());
	}

	private static Logger log = Logger.getLogger(SecurityBoxStorageImpl.class);

	public SecurityBox loadSecurityBox(String objectId) throws SecurityBoxStorageException,
			SecurityBoxStorageBoxNotFoundException {
		try {
			return dcPersistenceService.read(objectId);
		} catch (ItemNotFoundException notFound) {
			throw new SecurityBoxStorageBoxNotFoundException(notFound.getMessage());
		} catch (CrudServiceException e) {
			log.error("loadSecurityBox(" + objectId + ")", e);
			throw new SecurityBoxStorageException(e.getMessage());
		}
	}

	public void saveSecurityBox(SecurityBox box) throws SecurityBoxStorageException {
		try {
			dcPersistenceService.save(box);
		} catch (CrudServiceException e) {
			log.error("saveSecurityBox(" + box + ")", e);
			throw new SecurityBoxStorageException(e.getMessage());
		}
	}

	public void deleteSecurityBox(SecurityBox box) throws SecurityBoxStorageException {
		try {
			dcPersistenceService.delete(box);
		} catch (CrudServiceException e) {
			throw new SecurityBoxStorageException(e.getMessage());
		}
	}
}
