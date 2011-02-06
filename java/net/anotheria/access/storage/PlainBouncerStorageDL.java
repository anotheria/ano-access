package net.anotheria.access.storage;

import net.anotheria.access.impl.SecurityBox;
import net.anotheria.access.storage.dlp.DualLinkPersistenceServiceConfig;
import net.anotheria.access.storage.dlp.DualLinkPersistenceServiceFactory;
import net.anotheria.access.storage.dlp.DualLinkPersistenceWorkerConfig;
import net.anotheria.access.storage.dlp.IDualLinkPersistenceService;
import net.anotheria.access.storage.dlp.IPersistenceNotFoundExceptionFactory;

import org.apache.log4j.Logger;


public class PlainBouncerStorageDL implements BouncerStorage{
	
	private IDualLinkPersistenceService<SecurityBox> dlPersistenceService;
	
	public static final String EXTENSION = ".sb";
	public static final String DEF_PATH = "frsbouncer";
	
	protected PlainBouncerStorageDL(){
		dlPersistenceService = DualLinkPersistenceServiceFactory.createService(
				DualLinkPersistenceServiceConfig.newWorkerOnlyMode(), 
				new DualLinkPersistenceWorkerConfig(EXTENSION, "/work/"+DEF_PATH+"/data/boxes/"), 
				new DualLinkPersistenceWorkerConfig(EXTENSION, "/work/data/"+DEF_PATH+"/boxes/"), 
				new IPersistenceNotFoundExceptionFactory<BouncerStorageBoxNotFoundException>(){
					@Override
					public BouncerStorageBoxNotFoundException createNotFoundException(String owner) {
						return new BouncerStorageBoxNotFoundException(owner);
					}
				}
		);

	}
	
	private static Logger log = Logger.getLogger(PlainBouncerStorageDL.class);

	public SecurityBox loadSecurityBox(String objectId)	throws BouncerStorageException, BouncerStorageBoxNotFoundException {
		try{
			return dlPersistenceService.load(objectId);
		}catch(BouncerStorageBoxNotFoundException throwthrow){
			throw throwthrow;
		}catch(Exception e){
			log.error("loadSecurityBox("+objectId+")",e );
			throw new BouncerStorageException(e.getMessage());
		}
	}


	public void saveSecurityBox(SecurityBox box) throws BouncerStorageException {
		try{
			dlPersistenceService.save(box);
		}catch(Exception e){
			log.error("saveSecurityBox("+box+")", e);
			throw new BouncerStorageException(e.getMessage());
		}
	}
	
	
}
