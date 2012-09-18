package net.anotheria.access.storage;

import net.anotheria.access.impl.SecurityBox;
import net.anotheria.access.storage.persistence.SecurityBoxPersistenceService;
import net.anotheria.anoprise.dualcrud.CrudServiceException;
import net.anotheria.anoprise.dualcrud.DualCrudConfig;
import net.anotheria.anoprise.dualcrud.DualCrudService;
import net.anotheria.anoprise.dualcrud.DualCrudServiceFactory;
import net.anotheria.anoprise.dualcrud.ItemNotFoundException;
import net.anotheria.anoprise.metafactory.Extension;
import net.anotheria.anoprise.metafactory.MetaFactory;
import net.anotheria.anoprise.metafactory.MetaFactoryException;

import org.apache.log4j.Logger;

/**
 * {@link SecurityBoxStorageService} implementation.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class SecurityBoxStorageServiceImpl implements SecurityBoxStorageService {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(SecurityBoxStorageServiceImpl.class);

	/**
	 * {@link DualCrudConfig} instance.
	 */
	private final DualCrudConfig dualCrudServiceConfig;

	/**
	 * {@link DualCrudService} instance.
	 */
	private final DualCrudService<SecurityBox> dualCrudService;

	/**
	 * {@link SecurityBoxPersistenceService} instance.
	 */
	private final SecurityBoxPersistenceService leftCrud;

	/**
	 * {@link SecurityBoxPersistenceService} instance.
	 */
	private final SecurityBoxPersistenceService rightCrud;

	/**
	 * Default constructor.
	 */
	protected SecurityBoxStorageServiceImpl() {
		this.dualCrudServiceConfig = DualCrudConfig.useLeftOnly(); // TODO: can be configurable in future
		try {
			this.leftCrud = MetaFactory.get(SecurityBoxPersistenceService.class, Extension.CRUD_LEFT);
			this.rightCrud = null; // TODO: should be configurable via MetaFactory
		} catch (MetaFactoryException e) {
			throw new RuntimeException("SecurityBoxStorageServiceImpl() initialization fail.", e);
		}
		this.dualCrudService = DualCrudServiceFactory.createDualCrudService(leftCrud, rightCrud, dualCrudServiceConfig);
	}

	@Override
	public SecurityBox loadSecurityBox(String boxOwnerId) throws SecurityBoxStorageServiceException, SecurityBoxStorageServiceBoxNotFoundException {
		try {
			return dualCrudService.read(boxOwnerId);
		} catch (ItemNotFoundException notFound) {
			throw new SecurityBoxStorageServiceBoxNotFoundException(notFound.getMessage());
		} catch (CrudServiceException e) {
			LOGGER.error("loadSecurityBox(" + boxOwnerId + ") fail.", e);
			throw new SecurityBoxStorageServiceException(e.getMessage());
		}
	}

	@Override
	public void saveSecurityBox(SecurityBox box) throws SecurityBoxStorageServiceException {
		try {
			dualCrudService.save(box);
		} catch (CrudServiceException e) {
			LOGGER.error("saveSecurityBox(" + box + ") fail.", e);
			throw new SecurityBoxStorageServiceException(e.getMessage());
		}
	}

	@Override
	public void deleteSecurityBox(SecurityBox box) throws SecurityBoxStorageServiceException {
		try {
			dualCrudService.delete(box);
		} catch (CrudServiceException e) {
			LOGGER.error("deleteSecurityBox(" + box + ") fail.", e);
			throw new SecurityBoxStorageServiceException(e.getMessage());
		}
	}

}