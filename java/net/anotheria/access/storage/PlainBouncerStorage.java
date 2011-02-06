package net.anotheria.access.storage;

import net.anotheria.access.impl.SecurityBox;

import org.apache.log4j.Logger;


public class PlainBouncerStorage extends AbstractFSPersistenceService implements BouncerStorage{
	
	 
	@Override
	public Exception createFileNotFoundException(String owner) {
		return new BouncerStorageBoxNotFoundException(owner);
	}


	@Override
	protected String getExtension() {
		return ".sb";
	}


	@Override
	protected String getPathSegment() {
		return "/frsbouncer/boxes/";
	}

	private static Logger log = Logger.getLogger(PlainBouncerStorage.class);

	public SecurityBox loadSecurityBox(String objectId)	throws BouncerStorageException, BouncerStorageBoxNotFoundException {
		try{
			return (SecurityBox)loadFile(objectId);
		}catch(BouncerStorageBoxNotFoundException throwthrow){
			throw throwthrow;
		}catch(Exception e){
			log.error("loadSecurityBox("+objectId+")",e );
			throw new BouncerStorageException(e.getMessage());
		}
	}


	public void saveSecurityBox(SecurityBox box) throws BouncerStorageException {
		try{
			super.saveFile(box);
		}catch(Exception e){
			log.error("saveSecurityBox("+box+")", e);
			throw new BouncerStorageException(e.getMessage());
		}
	}
	
	
}
