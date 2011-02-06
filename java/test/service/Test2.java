package test.service;

import net.anotheria.access.AccessServiceReply;
import net.anotheria.access.AccessService;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.impl.AccessServiceFactory;

public class Test2 extends AbstractTest{
	public static void main(String a[]) throws Exception{
		System.out.println("Starting test");
		AccessService service = AccessServiceFactory.createAccessService();
		String uid = "2571459";
		
		printRolesForUser(uid, service);
		
		revokeRole(service, uid, "Test10Sec");
		grantRole(service, uid, "Test10Sec");
		
		SecurityObject o = new SecurityObject(uid);
		
		AccessServiceReply reply = service.isAllowed("messaging.write", o, null);
		System.out.println("1ST Service replied: "+reply);
		
		
		Thread.currentThread().sleep(11000);
		reply = service.isAllowed("messaging.write", o, null);
		System.out.println("2ND Service replied: "+reply);
		

		
		
		
	}
	
	
	
}
