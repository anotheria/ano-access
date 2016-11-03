package net.anotheria.access;

import net.anotheria.access.SOAttribute;

import org.junit.Test;
import static junit.framework.Assert.*;

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
