package net.anotheria.access.constraints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * A constraint that depends on time ranges.
 * @author another
 *
 */
public class TimeRangeConstraint extends TimeConstraint implements Serializable{
	private static final long serialVersionUID = -7800044664273755187L;
	/**
	 * Ranges for validity of the constraint.
	 */
	private List<Range> ranges;
	
	public TimeRangeConstraint(){
		ranges = new ArrayList<Range>();
	}
	
	public TimeRangeConstraint(Range initialRange){
		this();
		ranges.add(initialRange);
	}
	
	public TimeRangeConstraint(List<Range> someRanges){
		this();
		ranges.addAll(someRanges);
	}
	
	/**
	 * Returns true if current time is in at least on range.
	 */
	@Override public boolean isMet(){
		long time = System.currentTimeMillis();
		for (Range r : ranges){
			if (r.isIn(time))
				return true;
		}
		return false;
			
	}
	
	public void addRange(Range r){
		ranges.add(r);
	}
	
	@Override public String toString(){
		return "TRC: "+ranges;
	}
	
}
