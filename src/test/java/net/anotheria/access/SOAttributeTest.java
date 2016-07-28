package net.anotheria.access;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SOAttributeTest {
	@Test public void testEquals(){
		assertEquals(new SOAttribute(), new SOAttribute());
		SOAttribute a = new SOAttribute("a","a");
		SOAttribute b = new SOAttribute(); b.setName("a"); b.setValue("a");
		assertEquals(a, b);
		assertEquals(a.toString(), b.toString());
		assertFalse(a.equals(new SOAttribute()));
		assertFalse(new SOAttribute().equals(a));
		assertFalse(a.equals(a.toString()));
	}
}
