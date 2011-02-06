package net.anotheria.access.constraints;

import java.io.Serializable;

import net.anotheria.util.NumberUtils;
/**
 * Represents an endpoint of a range.
 * @author another
 *
 */
public class RangeEndpoint implements Serializable{
	/**
	 * Limit in millis.
	 */
	private long timeMills;
	/**
	 * True if the point is inclusive.
	 */
	private boolean inclusive;
	
	public RangeEndpoint(long aTimeMillis, boolean anInclusive){
		timeMills = aTimeMillis;
		inclusive = anInclusive;
	}
	
	public boolean isValueRightOf(long value){
		return inclusive ? 
				value >= timeMills : value >timeMills;
	}
	
	public boolean isValueLeftOf(long value){
		return inclusive ? 
				value <= timeMills : value <timeMills;
	}
	
	@Override public String toString(){
		return timeMills+" ("+(inclusive?"incl":"excl")+")";
	}
	
	public String toLeftString(){
		return "" + (inclusive ? '[' : '(' ) + timeMills;
	}

	public String describeLeft(){
		return "" + (inclusive ? '[' : '(' ) + NumberUtils.makeISO8601TimestampString(timeMills);
	}

	public String toRightString(){
		return "" + timeMills +(inclusive ? ']' : ')' );
	}
	
	public String describeRight(){
		return "" + NumberUtils.makeISO8601TimestampString(timeMills) +(inclusive ? ']' : ')' );
	}

	/**
	 * Creates a new exclusive RangeEndpoint.
	 * @param timeMillis
	 * @return
	 */
	public static final RangeEndpoint exclusive(long timeMillis){
		return new RangeEndpoint(timeMillis, false);
	}

	/**
	 * Creates a new inclusive RangeEndpoint.
	 * @param timeMillis
	 * @return
	 */
	public static final RangeEndpoint inclusive(long timeMillis){
		return new RangeEndpoint(timeMillis, true);
	}
}

