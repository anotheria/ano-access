package net.anotheria.access.storage.dlp;

import net.anotheria.access.storage.IFSSaveable;

import org.apache.log4j.Logger;


public class DualLinkPersistenceServiceImpl<T extends IFSSaveable> implements IDualLinkPersistenceService<T>{
	
	private static Logger log = Logger.getLogger(DualLinkPersistenceServiceImpl.class);
	
	private IDualLinkPersistenceWorker<T> newWorker, oldWorker;
	private DualLinkPersistenceServiceConfig config;
	
	protected DualLinkPersistenceServiceImpl(DualLinkPersistenceServiceConfig aConfig, 
			IDualLinkPersistenceWorker<T> aNewWorker, IDualLinkPersistenceWorker<T> anOldWorker){
		config = aConfig;
		newWorker = aNewWorker;
		oldWorker = anOldWorker;
	}
	

	@Override
	public void delete(String owner) throws PersistenceException {
		try{
			oldWorker.delete(owner);
		}catch(PersistenceException ignore){}
		newWorker.delete(owner);
		
	}

	@Override
	public T load(String owner) throws PersistenceException {
		
		if (log.isDebugEnabled()){
			log.debug("Trying to read from new worker for user "+owner);
		}

		//first read from new storage.
		try{
			T fromNew = newWorker.load(owner);
			return fromNew;
		}catch(PersistedObjectNotFoundException e){
			if (!config.isReadFromBoth())
				throw e;
		}catch(PersistenceException e){
			throw e;
		}

		if (log.isDebugEnabled()){
			log.debug("Trying to read from old worker for user "+owner);
		}
		
		//if we are here, readFromBoth is enabled
		T fromOld = null;

		//we don't catch the exception since we can't recover from it anyway.
		fromOld = oldWorker.load(owner);
		
		//if we are here, we could possible migrate
		if (config.isMigrateOnRead()){
			try{
				newWorker.save(fromOld);
				if (config.isDeleteUponMigration())
					oldWorker.delete(owner);
			}catch(PersistenceException e){
				log.warn("Couldn't commit migration, will try again next time", e);
			}
		}
		return fromOld;
	}

	@Override
	public void migrate(String owner) throws PersistenceException {
		throw new AssertionError("not yet implemented");
	}

	@Override
	public void migrateNewToOld(String owner) throws PersistenceException {
		throw new AssertionError("not yet implemented");
	}

	@Override
	public void migrateOldToNew(String owner) throws PersistenceException {
		throw new AssertionError("not yet implemented");
	}

	@Override
	public void save(T saveable) throws PersistenceException {
		//we let the exception flow through, since we can't react anyway.
		newWorker.save(saveable);
		
		if (config.isMigrateOnWrite()){
			try{
				oldWorker.delete(saveable.getUserId());
			}catch(PersistenceException e){
				log.warn("Couldn't delete old artefact upon migration", e);
			}
		}
	}

}
