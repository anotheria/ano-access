package net.anotheria.access.storage.persistence.impl;

import net.anotheria.access.storage.persistence.SecurityBoxPersistenceService;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * Factory for instantiating {@link FSSecurityBoxPersistenceServiceImpl}.
 * 
 * @author Alexandr Bolbat
 */
public class FSSecurityBoxPersistenceServiceFactory implements ServiceFactory<SecurityBoxPersistenceService> {

	@Override
	public SecurityBoxPersistenceService create() {
		return new FSSecurityBoxPersistenceServiceImpl();
	}

}
