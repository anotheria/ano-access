package net.anotheria.access.util;

import net.anotheria.anoprise.cache.Cache;
import net.anotheria.anoprise.cache.CacheProducerWrapper;
import net.anotheria.anoprise.cache.Caches;
import net.anotheria.moskito.core.logging.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache utility for creating cache with some setting's, moskito and logger's.
 * 
 * @author Alexandr Bolbat
 */
public final class CacheUtil {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheUtil.class.getName());

	/**
	 * Cache start size constant.
	 */
	private static final int CACHE_START_SIZE = 1000;

	/**
	 * Cache max size constant.
	 */
	private static final int CACHE_MAX_SIZE = 5000;

	/**
	 * Private constructor.
	 */
	private CacheUtil() {
		throw new IllegalAccessError();
	}

	/**
	 * Generic cache creation with attaching moskito statistic and loggers.
	 * 
	 * @param <K>
	 *            - key
	 * @param <V>
	 *            - value
	 * @param configFile
	 *            - cache configuration name
	 * @return create cache instance
	 */
	public static <K, V> Cache<K, V> createConfigurableHardwiredCache(final String configFile) {
		Cache<K, V> cache;

		try {
			cache = Caches.createConfigurableHardwiredCache(configFile);
		} catch (IllegalArgumentException e) {
			LOGGER.warn("Can't find cache configuration for " + configFile + ", falling back to min cache.");
			cache = Caches.createHardwiredCache(configFile, CACHE_START_SIZE, CACHE_MAX_SIZE);
		}

		CacheProducerWrapper cacheWrapper = new CacheProducerWrapper(cache, configFile, "cache", "default");
		LoggerUtil.createSLF4JDefaultAndIntervalStatsLogger(cacheWrapper);

		return cache;
	}

	/**
	 * Generic cache creation with attaching moskito statistic and loggers.
	 * 
	 * @param <K>
	 *            - key
	 * @param <V>
	 *            - value
	 * @param configFile
	 *            - cache configuration name
	 * @return create cache instance
	 */
	public static <K, V> Cache<K, V> createConfigurableSoftReferenceCache(final String configFile) {
		Cache<K, V> cache;

		try {
			cache = Caches.createConfigurableSoftReferenceCache(configFile);
		} catch (IllegalArgumentException e) {
			LOGGER.warn("Can't find cache configuration for " + configFile + ", falling back to min cache.");
			cache = Caches.createHardwiredCache(configFile, CACHE_START_SIZE, CACHE_MAX_SIZE);
		}

		CacheProducerWrapper cacheWrapper = new CacheProducerWrapper(cache, configFile, "cache", "default");
		LoggerUtil.createSLF4JDefaultAndIntervalStatsLogger(cacheWrapper);

		return cache;
	}

}
