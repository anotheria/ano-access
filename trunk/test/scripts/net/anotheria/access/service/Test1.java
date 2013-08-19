package net.anotheria.access.service;

import net.anotheria.access.AccessService;
import net.anotheria.access.AccessServiceReply;
import net.anotheria.access.SecurityObject;
import net.anotheria.access.impl.AccessServiceFactory;

public class Test1 extends AbstractTest {
	public static void main(String a[]) throws Exception {
		System.out.println("Starting test");
		AccessService service = new AccessServiceFactory().create();
		String uid = "12124028";

		printRolesForUser(uid, service);
		printRolesForUser("12107840", service);

		if (1 == 1)
			return;

		giveLoginRole(uid, service);

		printRolesForUser(uid, service);

		testLogin(uid, service);
		testWriteMessage(uid, service);

		if (1 == 1)
			return;

		// now forbid login

		System.out.println("BLOCKING LOGIN");

		blockLogin(uid, service);
		printRolesForUser(uid, service);

		testLogin(uid, service);

		System.out.println("UNBLOCKING LOGIN");

		unblockLogin(uid, service);
		testLogin(uid, service);
	}

	private static void blockLogin(String uid, AccessService service) throws Exception {
		service.grantRole(new SecurityObject(uid), "BLOCKED");
		// service.grantRole(new SecurityObject(uid), "Dummy1");
		// service.grantRole(new SecurityObject(uid), "Dummy2");
		// service.grantRole(new SecurityObject(uid), "Dummy3");
		// service.grantRole(new SecurityObject(uid), "Dummy4");
	}

	private static void unblockLogin(String uid, AccessService service) throws Exception {
		service.revokeRole(new SecurityObject(uid), "BLOCKED");
	}

	private static void giveLoginRole(String uid, AccessService service) throws Exception {
		service.grantRole(new SecurityObject(uid), "Member");
	}

	private static void testLogin(String uid, AccessService service) throws Exception {
		SecurityObject object = new SecurityObject(uid);
		SecurityObject subject = null;

		AccessServiceReply reply = service.isAllowed("system.login", object, subject);
		System.out.println("Service replied: " + reply);

	}

	private static void testWriteMessage(String uid, AccessService service) throws Exception {
		SecurityObject object = new SecurityObject(uid);
		SecurityObject subject = null;

		AccessServiceReply reply = service.isAllowed("messaging.write", object, subject);
		System.out.println("Service replied: " + reply);

	}

}
