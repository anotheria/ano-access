package net.anotheria.access.service;

import java.util.List;

import net.anotheria.access.AccessService;
import net.anotheria.access.RoleInfo;
import net.anotheria.access.SecurityObject;


public class AbstractTest {
	protected static void printRolesForUser(String uid, AccessService service) throws Exception{
		System.out.println("=== ROLES START ===");
		List<RoleInfo> infos = service.getRoleInfos(new SecurityObject(uid));
		for (RoleInfo i : infos){
			System.out.println("- "+i);
		}
		System.out.println("=== ROLES END ===");
	}

	protected static void grantRole(AccessService service, String uid, String role) throws Exception{
		service.grantRole(new SecurityObject(uid), role);
	}
	protected static void revokeRole(AccessService service, String uid, String role) throws Exception{
		service.revokeRole(new SecurityObject(uid), role);
	}
}
