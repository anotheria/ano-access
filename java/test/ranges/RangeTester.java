package test.ranges;

import java.util.ArrayList;
import java.util.List;

import net.anotheria.access.constraints.Range;
import net.anotheria.access.constraints.RangeHelper;
import net.anotheria.util.NumberUtils;

public class RangeTester {
	public static void main(String a[]) throws Exception{
		
		List<Range> ranges = new ArrayList<Range>();
		
		long now = System.currentTimeMillis();
		long tenSecondsAway = now + 10000;
		
		ranges.add(RangeHelper.exclusiveFromToRange(now, tenSecondsAway));
		ranges.add(RangeHelper.inclusiveFromExclusiveToRange(now, tenSecondsAway));
		ranges.add(RangeHelper.exclusiveFromInclusiveToRange(now, tenSecondsAway));
		ranges.add(RangeHelper.inclusiveFromToRange(now, tenSecondsAway));
		
		ranges.add(RangeHelper.nextSeconds(10));
		
		System.out.println("Checking at "+NumberUtils.makeISO8601TimestampString(System.currentTimeMillis()));
		for (Range r : ranges){
			System.out.println("Range "+r+", Describe: "+r.describe()+" ---> "+r.isIn(System.currentTimeMillis()));
		}
		
		Thread.sleep(5000);

		System.out.println("Checking at "+NumberUtils.makeISO8601TimestampString(System.currentTimeMillis()));
		for (Range r : ranges){
			System.out.println("Range "+r+", Describe: "+r.describe()+" ---> "+r.isIn(System.currentTimeMillis()));
		}
		
		Thread.sleep(6000);
		
		System.out.println("Checking at "+NumberUtils.makeISO8601TimestampString(System.currentTimeMillis()));
		for (Range r : ranges){
			System.out.println("Range "+r+", Describe: "+r.describe()+" ---> "+r.isIn(System.currentTimeMillis()));
		}
	}
}
