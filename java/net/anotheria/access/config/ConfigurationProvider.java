package net.anotheria.access.config;

import net.anotheria.access.AccessService;

/**
 * Configuration provider interface.
 * 
 * @author Leon Rosenberg
 */
public interface ConfigurationProvider {

	/**
	 * Configure {@link AccessService}.
	 * 
	 * @param service
	 *            - {@link AccessService} instance
	 */
	void configure(AccessService service);
}
