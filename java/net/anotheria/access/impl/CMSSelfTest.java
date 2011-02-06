package net.anotheria.access.impl;

import org.apache.log4j.Logger;
/**
 * This class is called upon each bouncer service start and ensures that the content of the cms is properly filled. This reduces the complexity of the release process, since it allows 
 * creation of data onthefly.
 * @author another
 *
 */
public class CMSSelfTest {

	private static Logger log = Logger.getLogger(CMSSelfTest.class);

	private static final int[] GTC_ALL_SIZES = { 3, 5, 10, 15, 20, 25, 30, 50 };

	private static final int[] GTC_FAV_SIZES = { 3, 5, 10, 15, 20, 25 };

	private static final int[] GTC_MSG_SIZES = { 5, 10, 15, 20, 25, 30, 50 };

	private static final int[] CREDIT_CONSTRAINT_FOTO_SHOW = { 3, 5, 10, 15, 20, 30, 40, };


	CMSSelfTest() {
	}
/*
	void performSelfTest() {
		try {
			out("==== START SELF TEST V10 ====");
			this.checkConstraints();
			this.out("==== SELF TEST FINISHED ====");
		} catch (Exception e) {
			log.fatal("SELF TEST FAILED", e);
		}
	}

	private void checkConstraints() throws Exception {
		this.checkCustomConstraints();
		this.checkPermissions();
		this.checkRoles();
	}

	private void checkCustomConstraints() throws Exception {
		generateCustomConstraint("FemaleObject", "de.friendscout.bouncer.constraints.custom.FemaleObjectConstraint", null);
		generateCustomConstraint("FemaleSubject", "de.friendscout.bouncer.constraints.custom.FemaleSubjectConstraint", null);
		generateCustomConstraint("MaleObject", "de.friendscout.bouncer.constraints.custom.MaleObjectConstraint", null);
		generateCustomConstraint("MaleSubject", "de.friendscout.bouncer.constraints.custom.MaleSubjectConstraint", null);

		generateCustomConstraint("MemberObject", "de.friendscout.bouncer.constraints.custom.MemberObjectConstraint", null);
		generateCustomConstraint("MemberSubject", "de.friendscout.bouncer.constraints.custom.MemberSubjectConstraint", null);
		generateCustomConstraint("PremiumObject", "de.friendscout.bouncer.constraints.custom.PremiumObjectConstraint", null);
		generateCustomConstraint("PremiumSubject", "de.friendscout.bouncer.constraints.custom.PremiumSubjectConstraint", null);

		generateCustomConstraint("RegAgencyIsEqual", "de.friendscout.bouncer.constraints.custom.RegAgencyIsEqualConstraint", null);
		generateCustomConstraint("RegCountryIsEqual", "de.friendscout.bouncer.constraints.custom.RegCountryIsEqualConstraint", null);
		generateCustomConstraint("RegLanguageIsEqual", "de.friendscout.bouncer.constraints.custom.RegLanguageIsEqualConstraint", null);
		generateCustomConstraint("RegAgencyIsNotEqual", "de.friendscout.bouncer.constraints.custom.RegAgencyIsNotEqualConstraint", null);
		generateCustomConstraint("RegCountryIsNotEqual", "de.friendscout.bouncer.constraints.custom.RegCountryIsNotEqualConstraint", null);
		generateCustomConstraint("RegLanguageIsNotEqual", "de.friendscout.bouncer.constraints.custom.RegLanguageIsNotEqualConstraint", null);
		
		generateCustomConstraint("NotSelf", NotSelfConstraint.class.getName(), null);
		
		generateCustomConstraint("ObjectRegCountryIsFrance", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsConstraint", "FR");
		generateCustomConstraint("ObjectRegCountryIsSpain", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsConstraint", "ES");
		generateCustomConstraint("ObjectRegCountryIsItaly", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsConstraint", "IT");
		generateCustomConstraint("ObjectRegCountryIsGermany", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsConstraint", "DE");
		generateCustomConstraint("ObjectRegCountryIsAustria", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsConstraint", "AT");
		generateCustomConstraint("ObjectRegCountryIsSwiss", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsConstraint", "CH");
		generateCustomConstraint("ObjectRegCountryIsNetherlands", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsConstraint", "NL");

		generateCustomConstraint("SubjectRegCountryIsFrance", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsConstraint", "FR");
		generateCustomConstraint("SubjectRegCountryIsSpain", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsConstraint", "ES");
		generateCustomConstraint("SubjectRegCountryIsItaly", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsConstraint", "IT");
		generateCustomConstraint("SubjectRegCountryIsGermany", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsConstraint", "DE");
		generateCustomConstraint("SubjectRegCountryIsAustria", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsConstraint", "AT");
		generateCustomConstraint("SubjectRegCountryIsNetherlands", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsConstraint", "NL");
		
		generateCustomConstraint("ObjectRegCountryIsNotFrance", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsNotConstraint", "FR");
		generateCustomConstraint("ObjectRegCountryIsNotSpain", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsNotConstraint", "ES");
		generateCustomConstraint("ObjectRegCountryIsNotItaly", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsNotConstraint", "IT");
		generateCustomConstraint("ObjectRegCountryIsNotGermany", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsNotConstraint", "DE");
		generateCustomConstraint("ObjectRegCountryIsNotAustria", "de.friendscout.bouncer.constraints.custom.ObjectRegCountryIsNotConstraint", "AT");

		generateCustomConstraint("SubjectRegCountryIsNotFrance", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsNotConstraint", "FR");
		generateCustomConstraint("SubjectRegCountryIsNotSpain", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsNotConstraint", "ES");
		generateCustomConstraint("SubjectRegCountryIsNotItaly", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsNotConstraint", "IT");
		generateCustomConstraint("SubjectRegCountryIsNotGermany", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsNotConstraint", "DE");
		generateCustomConstraint("SubjectRegCountryIsNotAustria", "de.friendscout.bouncer.constraints.custom.SubjectRegCountryIsNotConstraint", "AT");

		generateCustomConstraint("CountEqualConstraint0" , "de.friendscout.bouncer.constraints.custom.CountEqualConstraint", "0");

		
		for (int element : GTC_ALL_SIZES) {
			generateCustomConstraint("CountGreaterThan" + element, "de.friendscout.bouncer.constraints.custom.CountGreaterThanConstraint", ""
					+ element);
		}

		for (int element : CREDIT_CONSTRAINT_FOTO_SHOW) {
			generateCreditConstraint("Credits." + element, element);
		}

	}

	private void checkRoles() throws Exception {
		for (int element : GTC_FAV_SIZES) {
			generatePermissionSet("favorites.limit." + element, "favorite.add.morethan."
					+ element);
			generateRole("favorites.limit." + element, RoleTypeUtils.Static, "favorites.limit."
					+ element);
		}

		for (int element : GTC_MSG_SIZES) {
			generatePermissionSet("messaging.limit." + element, "messaging.inboxsize.morethan."
					+ element);
			generateRole("messaging.limit." + element, RoleTypeUtils.Static, "messaging.limit."
					+ element);
		}

		generatePermissionSet("premium.limits.override", "favorite.add.unlimited", "messaging.inboxsize.unlimited");
		generateRole("premium.limits.override", RoleTypeUtils.Static, "premium.limits.override");

		// foto + search
		generatePermissionSet("member.limitations.searchandfoto", "search.execute.extended.deny", "profile.fotoview.enlarge.deny");
		generatePermissionSet("premium.limitations.override.searchandfoto", "search.execute.extended.allow", "profile.fotoview.enlarge.allow");

		generateRole("member.limitations.searchandfoto", RoleTypeUtils.Static, "member.limitations.searchandfoto");
		generateRole("premium.limitations.override.searchandfoto", RoleTypeUtils.Static, "premium.limitations.override.searchandfoto");

		for (int element : CREDIT_CONSTRAINT_FOTO_SHOW) {
			generatePermissionSet("profile.fotoview.enlarge.allowcredited."
					+ element, "profile.fotoview.enlarge.allowcredited."
					+ element);
			generateRole("profile.fotoview.enlarge.allowcredited."
					+ element, RoleTypeUtils.Dynamic, "profile.fotoview.enlarge.allowcredited."
					+ element);
		}

		// ///////// spain female payment
		generatePermissionSet("female.member.plus", "messaging.message.write.allow.femplus", "chat.start.allow.femplus");
		generateRole("female.member.plus", RoleTypeUtils.Static, "female.member.plus");

		generatePermissionSet("male.member.minus", "chat.accept.deny.maleminus", "messaging.message.reply.deny.maleminus", "messaging.message.replyonce.deny.maleminus", "messaging.flirtcontact.reply.deny.maleminus");
		generateRole("male.member.minus", RoleTypeUtils.Static, "male.member.minus");

		generatePermissionSet("male.member.minus.override", "chat.accept.allow.maleminusoverride", "messaging.message.reply.allow.maleminusoverride", "messaging.message.replyonce.allow.maleminusoverride","messaging.flirtcontact.reply.allow.maleminusoverride");
		generateRole("male.member.minus.override", RoleTypeUtils.Static, "male.member.minus.override");

		generatePermissionSet("member", "messaging.flirtcontact.reply.allow", "messaging.message.replyonce.allow", "messaging.flirtcontact.write.allow",
				"search.execute.lovepoint.allow.it-member", "search.execute.lovepoint.allow.es-member", "search.execute.lovepoint.allow.de-member", "search.execute.lovepoint.allow.at-member", "search.execute.lovepoint.allow.ch-member","search.execute.lovepoint.allow.nl-member");
		generateRole("member", RoleTypeUtils.Static, "member");
		
		generatePermissionSet("premium", "messaging.message.write.allow", "messaging.message.reply.allow",
				"search.execute.lovepoint.allow.it-premium", "search.execute.lovepoint.allow.es-premium", "search.execute.lovepoint.allow.de-premium", "search.execute.lovepoint.allow.at-premium", "search.execute.lovepoint.allow.ch-premium","search.execute.lovepoint.allow.ch-premium","search.execute.lovepoint.allow.nl-premium");
		generateRole("premium", RoleTypeUtils.Static, "premium");

		//new permissionset for frenchmen and other species (countries with premium for all)
		//new permissions - premium.deny-foreigncountry rolle.
		generatePermissionSet("premium.deny-foreigncountry", "messaging.message.write.deny-foreigncountry", 
												 "messaging.message.reply.deny-foreigncountry", 
												 "messaging.flirtcontact.reply.deny-foreigncountry", 
												 "messaging.flirtcontact.write.deny-foreigncountry", 
												 "chat.start.deny-foreigncountry");
		generateRole("premium.deny-foreigncountry", RoleTypeUtils.Static, "premium.deny-foreigncountry");

		//accompanying deny role.
		generatePermissionSet("premium.deny-foreigncountry.override",
				 "messaging.message.write.deny-foreigncountry.override", 
				 "messaging.message.reply.deny-foreigncountry.override", 
				 "messaging.flirtcontact.reply.deny-foreigncountry.override", 
				 "messaging.flirtcontact.write.deny-foreigncountry.override", 
				 "chat.start.deny-foreigncountry.override");
		generateRole("premium.deny-foreigncountry.override", RoleTypeUtils.Static, "premium.deny-foreigncountry.override");

		
	}

	private void generateRole(String name, int type, String permissionSet)
			throws Exception {
		List<RoleDef> alreadyThere = rolesManagementService.getRoleDefsByProperty(RoleDef.PROP_NAME, name);
		if (alreadyThere.size() == 1) {
			return;
		}

		RoleDef def = RoleDefFactory.createRoleDef();
		def.setName(name);
		def.setType(type);

		List<PermissionSet> psets = rolesManagementService.getPermissionSetsByProperty(PermissionSet.PROP_NAME, permissionSet);
		if (psets.size() != 1) {
			throw new RuntimeException("Can't find permission set: "
					+ permissionSet);
		}

		def.setPermissions(psets.get(0).getId());

		rolesManagementService.createRoleDef(def);
		out("created " + def);

	}

	private void generatePermissionSet(String name, String... permissions)
			throws Exception {
		List<PermissionSet> alreadyThere = rolesManagementService.getPermissionSetsByProperty(PermissionSet.PROP_NAME, name);
		if (alreadyThere.size() == 1) {
			PermissionSet existing = alreadyThere.get(0);
			if (existing.getPermissionsSize()!=permissions.length){
				out("updating existing: "+existing);
				while(existing.getPermissionsSize()!=0)
					existing.removePermissionsElement(0);
				for (String pname : permissions) {
					List<PermissionDef> perm = rolesManagementService.getPermissionDefsByProperty(PermissionDef.PROP_NAME, pname);
					if (perm.size() != 1) {
						throw new RuntimeException("PermissionDef with name " + pname + " not found or not unique");
					}
					existing.addPermissionsElement(perm.get(0).getId());
				}
				out("updated existing: "+existing);
				rolesManagementService.updatePermissionSet(existing);
			}
			return;
		}

		PermissionSet s = PermissionSetFactory.createPermissionSet();
		s.setName(name);
		for (String pname : permissions) {
			List<PermissionDef> perm = rolesManagementService.getPermissionDefsByProperty(PermissionDef.PROP_NAME, pname);
			if (perm.size() != 1) {
				throw new RuntimeException("PermissionDef with name " + pname
						+ " not found or not unique");
			}
			s.addPermissionsElement(perm.get(0).getId());
		}

		rolesManagementService.createPermissionSet(s);
		out("created " + s);
	}

	private void checkPermissions() throws Exception {
		for (int element : GTC_FAV_SIZES) {
			generatePermission("favorite.add.morethan." + element, "favorite.add", PriorityUtils.P1, false, "CountGreaterThan"
					+ element);
		}

		for (int element : GTC_MSG_SIZES) {
			generatePermission("messaging.inboxsize.morethan." + element, "messaging.inboxsize", PriorityUtils.P1, false, "CountGreaterThan"
					+ element);
		}

		generatePermission("favorite.add.unlimited", "favorite.add", PriorityUtils.P5, true, (String[]) null);
		generatePermission("messaging.inboxsize.unlimited", "messaging.inboxsize", PriorityUtils.P5, true, (String[]) null);

		// add extended search deny permission and fotoview enlarge deny
		// permission
		generatePermission("search.execute.extended.deny", "search.execute.extended", PriorityUtils.P1, false, (String[]) null);
		generatePermission("profile.fotoview.enlarge.deny", "profile.fotoview.enlarge", PriorityUtils.P1, false, (String[]) null);
		generatePermission("search.execute.lovepoint.allow.it-member", "search.execute.lovepoint", PriorityUtils.P1, true, "ObjectRegCountryIsItaly", "CountEqualConstraint0");
		generatePermission("search.execute.lovepoint.allow.es-member", "search.execute.lovepoint", PriorityUtils.P1, true, "ObjectRegCountryIsSpain", "CountEqualConstraint0");		
		generatePermission("search.execute.lovepoint.allow.de-member", "search.execute.lovepoint", PriorityUtils.P1, true, "ObjectRegCountryIsGermany", "CountEqualConstraint0");
		generatePermission("search.execute.lovepoint.allow.at-member", "search.execute.lovepoint", PriorityUtils.P1, true, "ObjectRegCountryIsAustria", "CountEqualConstraint0");
		generatePermission("search.execute.lovepoint.allow.ch-member", "search.execute.lovepoint", PriorityUtils.P1, true, "ObjectRegCountryIsSwiss", "CountEqualConstraint0");

		generatePermission("search.execute.lovepoint.allow.it-premium", "search.execute.lovepoint", PriorityUtils.P5, true, "ObjectRegCountryIsItaly");
		generatePermission("search.execute.lovepoint.allow.es-premium", "search.execute.lovepoint", PriorityUtils.P5, true, "ObjectRegCountryIsSpain");		
		generatePermission("search.execute.lovepoint.allow.de-premium", "search.execute.lovepoint", PriorityUtils.P5, true, "ObjectRegCountryIsGermany");
		generatePermission("search.execute.lovepoint.allow.at-premium", "search.execute.lovepoint", PriorityUtils.P5, true, "ObjectRegCountryIsAustria");
		generatePermission("search.execute.lovepoint.allow.ch-premium", "search.execute.lovepoint", PriorityUtils.P5, true, "ObjectRegCountryIsSwiss");

		// add extended search deny permission and fotoview enlarge ALLOW
		// permission for premium
		generatePermission("search.execute.extended.allow", "search.execute.extended", PriorityUtils.P5, true, (String[]) null);
		generatePermission("profile.fotoview.enlarge.allow", "profile.fotoview.enlarge", PriorityUtils.P5, true, (String[]) null);

		// generate allow permissions for members and limited fotos
		for (int element : CREDIT_CONSTRAINT_FOTO_SHOW) {
			generatePermission("profile.fotoview.enlarge.allowcredited."
					+ element, "profile.fotoview.enlarge", PriorityUtils.P3, true, "Credits."
					+ element);
		}

		// permissions for male.member.minus, male.member.minus.override and
		// female.member.plus
		// permissions for female.member.plus
		// allowed to chat to females
		generatePermission("chat.start.allow.femplus", "chat.start", PriorityUtils.P1, true, "MaleSubject", "RegCountryIsEqual");
		// allowed to write to females
		generatePermission("messaging.message.write.allow.femplus", "messaging.message.write", PriorityUtils.P1, true, "MaleSubject", "RegCountryIsEqual");
		// forbid to accept chats from members
		generatePermission("chat.accept.deny.maleminus", "chat.accept", PriorityUtils.P1, false, "MemberSubject");
		// override forbid to accept chats from members
		generatePermission("chat.accept.allow.maleminusoverride", "chat.accept", PriorityUtils.P3, true, "MemberSubject");

		// forbid to reply to messages from members and according override
		generatePermission("messaging.message.reply.deny.maleminus", "messaging.message.reply", PriorityUtils.P2, false, "MemberSubject");
		generatePermission("messaging.message.reply.allow.maleminusoverride", "messaging.message.reply", PriorityUtils.P3, true, "MemberSubject");
		generatePermission("messaging.message.replyonce.deny.maleminus", "messaging.message.replyonce", PriorityUtils.P2, false, "MemberSubject");
		generatePermission("messaging.message.replyonce.allow.maleminusoverride", "messaging.message.replyonce", PriorityUtils.P3, true, "MemberSubject");

		// forbid to reply to flirtcontacts from members and according override
		generatePermission("messaging.flirtcontact.reply.deny.maleminus", "messaging.flirtcontact.reply", PriorityUtils.P2, false, "MemberSubject");
		generatePermission("messaging.flirtcontact.reply.allow.maleminusoverride", "messaging.flirtcontact.reply", PriorityUtils.P3, true, "MemberSubject");

		
		
		//permissions for normal user roles
		generatePermission("messaging.flirtcontact.reply.allow", "messaging.flirtcontact.reply", PriorityUtils.P1, true, "NotSelf", "FemaleObject");
		generatePermission("messaging.flirtcontact.write.allow", "messaging.flirtcontact.write", PriorityUtils.P1, true, "NotSelf");
		generatePermission("messaging.message.replyonce.allow", "messaging.message.replyonce", PriorityUtils.P1, true, "NotSelf");
		
		
		//permissions for premium user roles
		generatePermission("messaging.message.write.allow", "messaging.message.write", PriorityUtils.P1, true, "NotSelf");
		generatePermission("messaging.message.reply.allow", "messaging.message.reply", PriorityUtils.P1, true, "NotSelf");
		
		
		//new permissions - premium.deny-foreigncountry rolle.
		generatePermission("messaging.message.write.deny-foreigncountry", "messaging.message.write", PriorityUtils.P9, false, "RegCountryIsNotEqual"); 
		generatePermission("messaging.message.reply.deny-foreigncountry", "messaging.message.reply", PriorityUtils.P9, false,  "RegCountryIsNotEqual");
		generatePermission("messaging.flirtcontact.reply.deny-foreigncountry", "messaging.flirtcontact.reply", PriorityUtils.P9, false, "RegCountryIsNotEqual");
		generatePermission("messaging.flirtcontact.write.deny-foreigncountry", "messaging.flirtcontact.write", PriorityUtils.P9, false, "RegCountryIsNotEqual");
		generatePermission("chat.start.deny-foreigncountry", "chat.start", PriorityUtils.P9, false, "RegCountryIsNotEqual");

		//new permissions - premium.deny-foreigncountry.override rolle.
		generatePermission("messaging.message.write.deny-foreigncountry.override", "messaging.message.write", PriorityUtils.P10, true, "RegCountryIsNotEqual");
		generatePermission("messaging.message.reply.deny-foreigncountry.override", "messaging.message.reply", PriorityUtils.P10, true, "RegCountryIsNotEqual");
		generatePermission("messaging.flirtcontact.reply.deny-foreigncountry.override", "messaging.flirtcontact.reply", PriorityUtils.P10, true, "RegCountryIsNotEqual");
		generatePermission("messaging.flirtcontact.write.deny-foreigncountry.override", "messaging.flirtcontact.write", PriorityUtils.P10, true, "RegCountryIsNotEqual");
		generatePermission("chat.start.deny-foreigncountry.override", "chat.start", PriorityUtils.P10, true, "RegCountryIsNotEqual");

	}

	private void generatePermission(String name, String action, int priority,
			boolean allow, String... constraintnames) throws Exception {
		List<PermissionDef> alreadyThere = rolesManagementService.getPermissionDefsByProperty(PermissionDef.PROP_NAME, name);
		if (alreadyThere.size() == 1) {
			PermissionDef existing = alreadyThere.get(0);
			if (constraintnames != null
					&& existing.getConstraintsSize() != constraintnames.length) {
				out("updating " + existing);
				for (int i = 0; i < existing.getConstraintsSize(); i++) {
					existing.removeConstraintsElement(i);
				}
				for (String constraintname : constraintnames) {
					List<ConstraintDef> constraints = constraintFacadeService.getConstraintDefsByProperty(ConstraintDef.PROP_NAME, constraintname);
					if (constraints.size() != 1) {
						throw new Exception("Couldn't find the constraint "
								+ constraintname);
					}
					existing.addConstraintsElement(constraints.get(0).getId());
				}
				rolesManagementService.updatePermissionDef(existing);
			}
			
			if (existing.getPriority()!=priority){
				out("updating priority of " + existing);
				existing.setPriority(priority);
				rolesManagementService.updatePermissionDef(existing);
			}
			return;
		}

		PermissionDef def = PermissionDefFactory.createPermissionDef();
		def.setAction(action);
		def.setName(name);
		def.setPriority(priority);
		def.setType(allow ? PermissionTypeUtils.Allow
				: PermissionTypeUtils.Deny);

		if (constraintnames != null) {
			for (String constraintname : constraintnames) {
				List<ConstraintDef> constraints = constraintFacadeService.getConstraintDefsByProperty(ConstraintDef.PROP_NAME, constraintname);
				if (constraints.size() != 1) {
					throw new Exception("Couldn't find the constraint "
							+ constraintname);
				}
				def.addConstraintsElement(constraints.get(0).getId());
			}
		}

		rolesManagementService.createPermissionDef(def);
		out("created " + def);

	}

	private void generateCustomConstraint(String name, String clazz,
			String parameter) throws Exception {

		if (parameter == null) {
			parameter = "";
		}
		List<CustomConstraintDef> alreadyThere = constraintManagementService.getCustomConstraintDefsByProperty(CustomConstraintDef.PROP_NAME, name);
		if (alreadyThere.size() == 1) {
			return;
		}
		CustomConstraintDef def = CustomConstraintDefFactory.createCustomConstraintDef();
		def.setName(name);
		def.setParameter(parameter);
		def.setClazz(clazz);
		constraintManagementService.createCustomConstraintDef(def);
		out("Created: " + def);
	}

	private void generateCreditConstraint(String name, int credits)
			throws Exception {
		List<CreditConstraintDef> alreadyThere = constraintManagementService.getCreditConstraintDefsByProperty(CreditConstraintDef.PROP_NAME, name);
		if (alreadyThere.size() == 1) {
			return;
		}
		CreditConstraintDef def = CreditConstraintDefFactory.createCreditConstraintDef();
		def.setName(name);
		def.setCredits(credits);
		constraintManagementService.createCreditConstraintDef(def);
		out("Created: " + def);

	}

	private void out(Object o) {
		String msg = "[" + getClass().getName() + "] " + o;
		System.out.println(msg);
		log.info(msg);
	}
*/	
}
