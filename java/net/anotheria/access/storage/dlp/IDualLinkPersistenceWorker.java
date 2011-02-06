package net.anotheria.access.storage.dlp;

import net.anotheria.access.storage.IFSSaveable;

public interface IDualLinkPersistenceWorker<T extends IFSSaveable> {
	public T load(String owner) throws PersistenceException;
	
	public void save(T saveable) throws PersistenceException;
	
	public void delete(String owner) throws PersistenceException;
}
