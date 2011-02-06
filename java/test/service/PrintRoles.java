package test.service;

import java.util.List;

import net.anotheria.access.AccessService;
import net.anotheria.access.RoleInfo;
import net.anotheria.access.impl.AccessServiceFactory;


public class PrintRoles {
	public static void main(String[] args)  throws Exception{
		AccessService service = AccessServiceFactory.createAccessService();
		List<RoleInfo> roles = service.getRoleInfos();
		for (RoleInfo info : roles)
			System.out.println("Role "+info);
	}
}
