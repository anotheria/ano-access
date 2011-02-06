package net.anotheria.access.storage.dlp;


public interface IPersistenceNotFoundExceptionFactory<T extends PersistedObjectNotFoundException> {
	public T createNotFoundException(String owner);
}
 