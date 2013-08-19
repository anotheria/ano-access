package net.anotheria.access.service;

import net.anotheria.access.AccessService;
import net.anotheria.access.AccessServiceReply;
import net.anotheria.access.SOAttribute;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.impl.AccessServiceFactory;

public class Test3 extends AbstractTest{
	public static void main(String a[]) throws Exception{
		System.out.println("Starting test for writing female to male.");
		AccessService service = new AccessServiceFactory().create();
		String uid = "2571460";
		
		grantRole(service, uid, "FemaleMessaging");
		
		SecurityObject o = new SecurityObject(uid);
		SecurityObject s = new SecurityObject("recipient");
		

		test(o, s, service);
		
		o.addAttribute(new SOAttribute("gender", "male"));
		
		test(o, s, service);
		
		s.addAttribute(new SOAttribute("gender", "male"));
		
		test(o, s, service);
		
		s.addAttribute(new SOAttribute("gender", "female"));
		test(o, s, service);
		
		o.addAttribute(new SOAttribute("gender", "female"));
		test(o, s, service);
		
		s.addAttribute(new SOAttribute("gender", "male"));
		test(o, s, service);
		
	}
	
	private static void test(SecurityObject o, SecurityObject s, AccessService service) throws Exception{
		System.out.println("--------------");
		System.out.println("Testing whether "+o+" isAllowed to write to "+s);
		AccessServiceReply reply = service.isAllowed("messaging.write", o, s);
		System.out.println("Service replied: "+reply);
	}
	
	
	
	// 	FemaleMessaging
}
