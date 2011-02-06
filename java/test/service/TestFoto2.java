package test.service;

import net.anotheria.access.AccessServiceReply;
import net.anotheria.access.AccessService;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.impl.AccessServiceFactory;

public class TestFoto2 extends AbstractTest{
	public static void main(String a[]) throws Exception{
		AccessService service = AccessServiceFactory.createAccessService();
		String uid = "2571458";
		
		revokeRole(service, uid, "member.limitations.searchandfoto");
		revokeRole(service, uid, "premium.limitations.override.searchandfoto");
		revokeRole(service, uid, "profile.fotoview.enlarge.allowcredited.3");
		
		printRolesForUser(uid, service);
		
		grantRole(service, uid, "member.limitations.searchandfoto");
		test(service, uid);

		grantRole(service, uid, "profile.fotoview.enlarge.allowcredited.3");
		printRolesForUser(uid, service);
		test(service, uid);
		test(service, uid);
		test(service, uid);
		test(service, uid);
		
		
}	
	
	private static void test(AccessService service, String uid) throws Exception{
		
		System.out.println("Watching a foto: ");
		AccessServiceReply r2 = service.isAllowed("profile.fotoview.enlarge", new SecurityObject(uid),new SecurityObject("rfu"));
		System.out.println("\tReply: "+r2);
		System.out.println("\t"+mayI(r2));
		service.notifyPassed("profile.fotoview.enlarge", new SecurityObject(uid),new SecurityObject("rfu"), r2);
		
	}
	
	private static boolean mayI(AccessServiceReply reply){
		return reply.isAnswered() ? reply.isAllowed() : true;
	}
}
