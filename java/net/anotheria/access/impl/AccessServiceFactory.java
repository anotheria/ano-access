package net.anotheria.access.impl;

import net.anotheria.access.AccessService;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * Factory for instantiating {@link AccessService} main implementation.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class AccessServiceFactory implements ServiceFactory<AccessService> {

	@Override
	public AccessService create() {
		return new AccessServiceImpl();
	}

}
