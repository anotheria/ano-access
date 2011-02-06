package net.anotheria.access.impl;

import net.anotheria.access.AccessService;
/**
 * The factory for the bouncer service.
 * @author another
 *
 */
public class AccessServiceFactory {
	
	private static final AccessService instance = new AccessServiceImpl();
	
	public static AccessService createAccessService(){
		return instance;
	}
}
