package net.anotheria.access.util;

import net.anotheria.anoprise.cache.Cache;
import net.anotheria.anoprise.cache.CacheProducerWrapper;
import net.anotheria.anoprise.cache.Caches;
import net.anotheria.moskito.core.logging.DefaultStatsLogger;
import net.anotheria.moskito.core.logging.IntervalStatsLogger;
import net.anotheria.moskito.core.logging.SLF4JLogOutput;
import net.anotheria.moskito.core.stats.DefaultIntervals;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
		new DefaultStatsLogger(cacheWrapper, new SLF4JLogOutput(LoggerFactory.getLogger("MoskitoDefault")));
		new IntervalStatsLogger(cacheWrapper, DefaultIntervals.FIVE_MINUTES, new SLF4JLogOutput(LoggerFactory.getLogger("Moskito5m")));
		new IntervalStatsLogger(cacheWrapper, DefaultIntervals.FIFTEEN_MINUTES, new SLF4JLogOutput(LoggerFactory.getLogger("Moskito15m")));
		new IntervalStatsLogger(cacheWrapper, DefaultIntervals.ONE_HOUR, new SLF4JLogOutput(LoggerFactory.getLogger("Moskito1h")));
		new IntervalStatsLogger(cacheWrapper, DefaultIntervals.ONE_DAY, new SLF4JLogOutput(LoggerFactory.getLogger("Moskito1d")));

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
		new DefaultStatsLogger(cacheWrapper, new SLF4JLogOutput(LoggerFactory.getLogger("MoskitoDefault")));
		new IntervalStatsLogger(cacheWrapper, DefaultIntervals.FIVE_MINUTES, new SLF4JLogOutput(LoggerFactory.getLogger("Moskito5m")));
		new IntervalStatsLogger(cacheWrapper, DefaultIntervals.FIFTEEN_MINUTES, new SLF4JLogOutput(LoggerFactory.getLogger("Moskito15m")));
		new IntervalStatsLogger(cacheWrapper, DefaultIntervals.ONE_HOUR, new SLF4JLogOutput(LoggerFactory.getLogger("Moskito1h")));
		new IntervalStatsLogger(cacheWrapper, DefaultIntervals.ONE_DAY, new SLF4JLogOutput(LoggerFactory.getLogger("Moskito1d")));

		return cache;
	}

}
