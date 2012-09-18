package net.anotheria.access.storage;

import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * Factory for instantiating {@link SecurityBoxStorageService} main implementation.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class SecurityBoxStorageServiceFactory implements ServiceFactory<SecurityBoxStorageService> {

	@Override
	public SecurityBoxStorageService create() {
		return new SecurityBoxStorageServiceImpl();
	}

}
