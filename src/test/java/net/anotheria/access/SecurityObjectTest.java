package net.anotheria.access;

import java.util.ArrayList;
import java.util.List;

import net.anotheria.access.SOAttribute;
import net.anotheria.access.SecurityObject;

import org.junit.Test;
import static org.junit.Assert.*;

public class SecurityObjectTest {
	@Test public void testValue(){
		SecurityObject o = new SecurityObject("1");
		assertNull(o.getAttribute("foo"));
		assertNull(o.getAttributeValue("foo"));
		
		o.addAttribute(new SOAttribute("foo","bar"));
		assertNotNull(o.getAttribute("foo"));
		assertNotNull(o.getAttributeValue("foo"));
		assertEquals("bar", o.getAttributeValue("foo"));
		assertEquals("bar", o.getAttribute("foo").getValue());
		
	}

	@Test public void testValues(){
		SecurityObject o = new SecurityObject("1");
		assertNull(o.getAttribute("foo"));
		assertNull(o.getAttributeValue("foo"));
		
		List<SOAttribute> attributes = new ArrayList<SOAttribute>();
		attributes.add(new SOAttribute("foo","bar"));
		attributes.add(new SOAttribute("foo2","notbar"));

		o.addAttributes(attributes);
		
		assertNotNull(o.getAttribute("foo2"));
		assertNotNull(o.getAttributeValue("foo2"));
		assertEquals("bar", o.getAttributeValue("foo"));
		assertEquals("bar", o.getAttribute("foo").getValue());
		
	}
}
