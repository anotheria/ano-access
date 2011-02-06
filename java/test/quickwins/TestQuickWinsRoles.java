package test.quickwins;

import net.anotheria.access.AccessService;
import net.anotheria.access.SOAttribute;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.impl.AccessServiceFactory;

public class TestQuickWinsRoles {
	public static void main(String a[]) throws Exception{
		AccessService service = AccessServiceFactory.createAccessService();
		System.out.println(	service.getRoleInfos());
		
		SecurityObject o = new SecurityObject("123");
		SecurityObject s = new SecurityObject("...");
		
		service.grantRole(o, "favorites.limit.20");
		service.grantRole(o, "messaging.limit.20");
		service.revokeRole(o, "premium.limits.override");
		
		System.out.println("FOR MEMBER: ");
		
		for (int i=18; i<23; i++){
			s.addAttribute(new SOAttribute("count", ""+i));
			System.out.println("FAV "+i+" -> "+service.isAllowed("favorite.add", o, s));
			System.out.println("MSG "+i+" -> "+service.isAllowed("messaging.inboxsize", o, s));
		}
		
		System.out.println("FOR PREMIUM: ");
		service.grantRole(o, "premium.limits.override");
		
		for (int i=18; i<23; i++){
			s.addAttribute(new SOAttribute("count", ""+i));
			System.out.println("FAV "+i+" -> "+service.isAllowed("favorite.add", o, s));
			System.out.println("MSG "+i+" -> "+service.isAllowed("messaging.inboxsize", o, s));
		}
	}
}
 