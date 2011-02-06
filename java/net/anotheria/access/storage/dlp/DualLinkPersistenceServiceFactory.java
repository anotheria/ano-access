package net.anotheria.access.storage.dlp;

import java.util.concurrent.atomic.AtomicInteger;

import net.anotheria.access.storage.IFSSaveable;
import net.java.dev.moskito.core.dynamic.MoskitoInvokationProxy;
import net.java.dev.moskito.core.logging.DefaultStatsLogger;
import net.java.dev.moskito.core.logging.IntervalStatsLogger;
import net.java.dev.moskito.core.logging.Log4JOutput;
import net.java.dev.moskito.core.predefined.ServiceStatsCallHandler;
import net.java.dev.moskito.core.predefined.ServiceStatsFactory;
import net.java.dev.moskito.core.stats.DefaultIntervals;

import org.apache.log4j.Logger;


public class DualLinkPersistenceServiceFactory {
	
	private static final AtomicInteger instanceCounter = new AtomicInteger(0);

	public static <T extends IFSSaveable> IDualLinkPersistenceService<T> createService(DualLinkPersistenceServiceConfig serviceConfig, DualLinkPersistenceWorkerConfig newWorkerConfig, 
			DualLinkPersistenceWorkerConfig oldWorkerConfig, IPersistenceNotFoundExceptionFactory<? extends PersistedObjectNotFoundException> exceptionFactory){
		
		IDualLinkPersistenceWorker<T> newWorker = createWorker(newWorkerConfig, exceptionFactory);
		IDualLinkPersistenceWorker<T> oldWorker = createWorker(oldWorkerConfig, exceptionFactory);
		
		
		DualLinkPersistenceServiceImpl<T> serviceInstance = new DualLinkPersistenceServiceImpl<T>(serviceConfig, newWorker, oldWorker);
		MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(
				serviceInstance,
				new ServiceStatsCallHandler(),
				new ServiceStatsFactory(),
				"DualLinkPersistenceService-"+instanceCounter.incrementAndGet(),
				"service",
				"persistence",
				IDualLinkPersistenceService.class
				);
		addLogger(proxy);
		
		
		@SuppressWarnings("unchecked") IDualLinkPersistenceService<T> service = (IDualLinkPersistenceService<T>) proxy.createProxy();
		
		return service;
	}
	
	private static <T extends IFSSaveable>  IDualLinkPersistenceWorker<T> createWorker(DualLinkPersistenceWorkerConfig workerConfig, IPersistenceNotFoundExceptionFactory<? extends PersistedObjectNotFoundException> exceptionFactory){
		MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(
				new DualLinkPersistenceWorkerImpl<T>(workerConfig, exceptionFactory),
				new ServiceStatsCallHandler(),
				new ServiceStatsFactory(),
				"DualLinkPersistenceWorker-"+instanceCounter.incrementAndGet()+"-"+workerConfig.getDefPath(),
				"worker",
				"persistence",
				IDualLinkPersistenceWorker.class
				);
		addLogger(proxy);
		@SuppressWarnings("unchecked") IDualLinkPersistenceWorker<T> ret = (IDualLinkPersistenceWorker<T>) proxy.createProxy();
		return ret;
	}
	
	private static void addLogger(MoskitoInvokationProxy proxy){
		// add moskito logger
		new DefaultStatsLogger(proxy.getProducer(), new Log4JOutput(Logger.getLogger("MoskitoDefault")));
		new IntervalStatsLogger(proxy.getProducer(), DefaultIntervals.FIVE_MINUTES, new Log4JOutput(Logger.getLogger("Moskito5m")));
		new IntervalStatsLogger(proxy.getProducer(), DefaultIntervals.FIFTEEN_MINUTES, new Log4JOutput(Logger.getLogger("Moskito15m")));
		new IntervalStatsLogger(proxy.getProducer(), DefaultIntervals.ONE_HOUR, new Log4JOutput(Logger.getLogger("Moskito1h")));
		new IntervalStatsLogger(proxy.getProducer(), DefaultIntervals.ONE_DAY, new Log4JOutput(Logger.getLogger("Moskito1d")));
		//end moskito logger
	}
}
