package test.service;

import net.anotheria.access.AccessServiceReply;
import net.anotheria.access.AccessService;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.impl.AccessServiceFactory;

public class TestFoto extends AbstractTest{
	public static void main(String a[]) throws Exception{
		AccessService service = AccessServiceFactory.createAccessService();
		String uid = "2571458";
		
		revokeRole(service, uid, "member.limitations.searchandfoto");
		revokeRole(service, uid, "premium.limitations.override.searchandfoto");
		
		printRolesForUser(uid, service);
		
		test(service, uid);
		
		grantRole(service, uid, "member.limitations.searchandfoto");
		test(service, uid);
		
		grantRole(service, uid, "premium.limitations.override.searchandfoto");
		test(service, uid);

		revokeRole(service, uid, "premium.limitations.override.searchandfoto");
		test(service, uid);
}	
	
	private static void test(AccessService service, String uid) throws Exception{
		System.out.println("Executing a search: ");
		AccessServiceReply r1 = service.isAllowed("search.execute.extended", new SecurityObject(uid),new SecurityObject("rfu"));
		System.out.println("\tReply: "+r1);
		System.out.println("\t"+mayI(r1));
		
		System.out.println("Watching a foto: ");
		AccessServiceReply r2 = service.isAllowed("profile.fotoview.enlarge", new SecurityObject(uid),new SecurityObject("rfu"));
		System.out.println("\tReply: "+r2);
		System.out.println("\t"+mayI(r2));
		
	}
	
	private static boolean mayI(AccessServiceReply reply){
		return reply.isAnswered() ? reply.isAllowed() : true;
	}
}
