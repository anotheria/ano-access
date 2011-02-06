package net.anotheria.access.storage.dlp;

import net.anotheria.access.storage.IFSSaveable;


public interface IDualLinkPersistenceService<T extends IFSSaveable> {
	T load(String owner) throws PersistenceException;
	
	void save(T saveable) throws PersistenceException;
	
	void delete(String owner) throws PersistenceException;
	
	void migrate(String owner) throws PersistenceException;
	
	void migrateOldToNew(String owner) throws PersistenceException;
	
	void migrateNewToOld(String owner)throws PersistenceException;
}
