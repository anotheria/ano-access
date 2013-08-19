package net.anotheria.access.constraints;

/**
 * A helper class for Ranges.
 * @author another
 *
 */
public class RangeHelper {
	//Todo replace with timeunit.
	public static final long SECOND = 1000;
	public static final long MINUTE = SECOND * 60;
	public static final long HOUR   = MINUTE * 60;
	public static final long DAY    = HOUR * 24;
	/**
	 * Creates a new exclusive range between two endpoints.
	 * @param start
	 * @param end
	 * @return
	 */
	public static final Range exclusiveFromToRange(long start, long end){
		return new TwoEndpointRange(RangeEndpoint.exclusive(start), RangeEndpoint.exclusive(end));
	}

	/**
	 * Creates a new inclusive range between two endpoints.
	 * @param start
	 * @param end
	 * @return
	 */
	public static final Range inclusiveFromToRange(long start, long end){
		return new TwoEndpointRange(RangeEndpoint.inclusive(start), RangeEndpoint.inclusive(end));
	}

	public static final Range exclusiveFromInclusiveToRange(long start, long end){
		return new TwoEndpointRange(RangeEndpoint.exclusive(start), RangeEndpoint.inclusive(end));
	}

	public static final Range inclusiveFromExclusiveToRange(long start, long end){
		return new TwoEndpointRange(RangeEndpoint.inclusive(start), RangeEndpoint.exclusive(end));
	}
	/**
	 * Creates a new range between now and offset milliseconds.
	 * @param offset time for the range to be valid in milliseconds.
	 * @return
	 */
	public static final Range next(long offset){
		return inclusiveFromToRange(System.currentTimeMillis(), System.currentTimeMillis()+offset);
	}
	/**
	 * Creates a new ranges between now and next 'seconds' seconds.
	 * @param seconds
	 * @return
	 */
	public static final Range nextSeconds(int seconds){
		return next(seconds*SECOND);
	}
	
	public static final Range nextMinutes(int minutes){
		return next(MINUTE*minutes);
	}
	
	public static final Range nextHours(int hours){
		return next(HOUR * hours);
	}
	
	public static final Range nextDays(int days){
		return next(DAY * days);
	}
}
 