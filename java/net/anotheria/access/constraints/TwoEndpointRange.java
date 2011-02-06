package net.anotheria.access.constraints;

import java.io.Serializable;
/**
 * A range with two fixed endpoints.
 * @author another
 *
 */
public class TwoEndpointRange implements Range , Serializable{
	/**
	 * Left endpoint.
	 */
	RangeEndpoint left;
	/**
	 * Right endpoint.
	 */
	RangeEndpoint right;
	
	public TwoEndpointRange(RangeEndpoint aLeftEndpoint, RangeEndpoint aRightEndpoint){
		left = aLeftEndpoint;
		right = aRightEndpoint;
	}
	/**
	 * Returns true if the checkpoint is between both endpoints. 
	 */
	@Override public boolean isIn(long checkpoint) {
		return left.isValueRightOf(checkpoint) && right.isValueLeftOf(checkpoint);
	}
	
	@Override public String toString(){
		return left.toLeftString()+" .. "+right.toRightString();
	}
	
	@Override public String describe(){
		return left.describeLeft()+" .. "+right.describeRight(); 
	}
	
	
	
}
