package net.anotheria.access.config;

import net.anotheria.access.AccessService;

public interface ConfigurationProvider {
	void configure(AccessService service);
}
