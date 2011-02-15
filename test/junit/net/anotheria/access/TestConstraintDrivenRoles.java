package net.anotheria.access;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.anotheria.access.custom.FemaleObjectConstraint;
import net.anotheria.access.custom.MaleObjectConstraint;
import net.anotheria.access.custom.MaleSubjectConstraint;
import net.anotheria.access.custom.SubjectAttributeIsEqualToObjectAttributeConstraint;
import net.anotheria.access.impl.AccessServiceFactory;
import net.anotheria.access.impl.PermissionCollection;
import net.anotheria.access.impl.PermissionImpl;
import net.anotheria.access.impl.StaticRole;

import org.junit.BeforeClass;
import org.junit.Test;

//this test simulates product logic in which
//product contains two types of roles, 
//male and female,
//and two type of users, basic and premium,
//premium users are allowed to write to anyone, and read anything
//basic female users are allowed to write to male in the same locale
//basic male users aren't allowed to read messages from other male users.
public class TestConstraintDrivenRoles {
	private static AccessService service;
	
	private static SecurityObject bm_at, bm_de, bm_ch, bm_no;
	private static SecurityObject bf_at, bf_de, bf_ch, bf_no;
	private static SecurityObject pm_at, pm_de, pm_ch, pm_no;
	private static SecurityObject pf_at, pf_de, pf_ch, pf_no;
	
	@BeforeClass public static void init() throws AccessServiceException{
		service = AccessServiceFactory.createAccessService();
		System.out.println("Service "+service);
		setupRolesAndPermissions();
		setupUsers();
		
	}
	 
	private static void setupRolesAndPermissions() throws AccessServiceException{
		
		PermissionImpl premiumWrite = new PermissionImpl();
		premiumWrite.setAction("write");
		premiumWrite.setAllow(true);
		premiumWrite.setName("premium.write");
		premiumWrite.setPriority(5);//best practice to set priority for premium permissions to 5
		
		PermissionImpl premiumRead = new PermissionImpl();
		premiumRead.setAction("read");
		premiumRead.setAllow(true);
		premiumRead.setName("premium.read");
		premiumRead.setPriority(5);//best practice to set priority for premium permissions to 5
		
		PermissionCollection premiumCollection = new PermissionCollection("premium");
		premiumCollection.add(premiumRead, premiumWrite);
		
		service.addPermissionCollection(premiumCollection);
		
		StaticRole premiumRole = new StaticRole("premium");
		service.addRole(premiumRole);
		
		
		PermissionImpl basicRead = new PermissionImpl();
		basicRead.setAction("read");
		basicRead.setAllow(true);
		basicRead.setName("basic.read");
		basicRead.setPriority(1);//best practice to set priority for basic permissions to 1

		//this role only allows females to write to male if they are in same locale
		PermissionImpl femaleToMaleWrite = new PermissionImpl();
		femaleToMaleWrite.setAction("write");
		femaleToMaleWrite.setAllow(true);
		femaleToMaleWrite.setPriority(1);//best practice to set priority for basic roles to 1
		femaleToMaleWrite.addConstraint(new FemaleObjectConstraint(), new MaleSubjectConstraint(), new SubjectAttributeIsEqualToObjectAttributeConstraint("locale"));
		
		//this role only allows females to write to male if they are in same locale
		PermissionImpl maleFromMailRead = new PermissionImpl();
		maleFromMailRead.setAction("read");
		maleFromMailRead.setAllow(false);
		maleFromMailRead.setPriority(3);//best practice to set priority for deny basic roles to 3
		maleFromMailRead.addConstraint(new MaleObjectConstraint(), new MaleSubjectConstraint());

		PermissionCollection basicCollection = new PermissionCollection("basic");
		basicCollection.add(femaleToMaleWrite, maleFromMailRead, basicRead);
		service.addPermissionCollection(basicCollection);
		StaticRole basicRole = new StaticRole("basic");
		service.addRole(basicRole);
		
		
	}
	
	public static void setupUsers() throws AccessServiceException{
		bm_at = createUser("bm_at", "at", true, false);
		bm_de = createUser("bm_de", "de", true, false);
		bm_ch = createUser("bm_ch", "ch", true, false);
		bm_no = createUser("bm_no", "no", true, false);

		bf_at = createUser("bf_at", "at", false, false);
		bf_de = createUser("bf_de", "de", false, false);
		bf_ch = createUser("bf_ch", "ch", false, false);
		bf_no = createUser("bf_no", "no", false, false);

		pm_at = createUser("pm_at", "at", true, true);
		pm_de = createUser("pm_de", "de", true, true);
		pm_ch = createUser("pm_ch", "ch", true, true);
		pm_no = createUser("pm_no", "no", true, true);

		pf_at = createUser("pf_at", "at", false, true);
		pf_de = createUser("pf_de", "de", false, true);
		pf_ch = createUser("pf_ch", "ch", false, true); 
		pf_no = createUser("pf_no", "no", false, true);
	}
	
	private static SecurityObject createUser(String code, String locale, boolean male, boolean premium) throws AccessServiceException{
		
		SecurityObject ret = new SecurityObject(code);
		ret.addAttribute(new SOAttribute("locale", locale));
		ret.addAttribute(new SOAttribute("gender", male ? "male" : "female"));

		service.grantRole(ret, "basic");
		if (premium)
			service.grantRole(ret, "premium");
		return ret;
		
	}	
	
	@Test(expected=RuntimeException.class) public void testGetNotCreatedPermissionCollection() {
		service.getPermissionColecction("temprr");
	}
	
	
	@Test public void testPremium2PremiumWrite() throws AccessServiceException{
		assertTrue(allowed("write", pm_at, pm_at));
		assertTrue(allowed("write", pm_at, pf_at));
		assertTrue(allowed("write", pf_at, pf_at));
		assertTrue(allowed("write", pf_at, pm_at));
		assertTrue(allowed("write", pm_at, pm_de));
		assertTrue(allowed("write", pm_at, pf_de));
		assertTrue(allowed("write", pf_at, pf_de));
		assertTrue(allowed("write", pf_at, pm_de));
	}
	@Test public void testPremium2BasicWrite() throws AccessServiceException{
		assertTrue(allowed("write", pm_at, bm_at));
		assertTrue(allowed("write", pm_at, bf_at));
		assertTrue(allowed("write", pf_at, bf_at));
		assertTrue(allowed("write", pf_at, bm_at));
		assertTrue(allowed("write", pm_at, bm_de));
		assertTrue(allowed("write", pm_at, bf_de));
		assertTrue(allowed("write", pf_at, bf_de));
		assertTrue(allowed("write", pf_at, bm_de));
	}
	
	@Test public void testPremiumFromPremiumRead() throws AccessServiceException{
		assertTrue(allowed("read", pm_at, pm_at));
		assertTrue(allowed("read", pm_at, pf_at));
		assertTrue(allowed("read", pf_at, pf_at));
		assertTrue(allowed("read", pf_at, pm_at));
		assertTrue(allowed("read", pm_at, pm_de));
		assertTrue(allowed("read", pm_at, pf_de));
		assertTrue(allowed("read", pf_at, pf_de));
		assertTrue(allowed("read", pf_at, pm_de));
	}
	
	@Test public void testPremiumFromBasicRead() throws AccessServiceException{
		assertTrue(allowed("read", pm_at, bm_at));
		assertTrue(allowed("read", pm_at, bf_at));
		assertTrue(allowed("read", pf_at, bf_at));
		assertTrue(allowed("read", pf_at, bm_at));
		assertTrue(allowed("read", pm_at, bm_de));
		assertTrue(allowed("read", pm_at, bf_de));
		assertTrue(allowed("read", pf_at, bf_de));
		assertTrue(allowed("read", pf_at, bm_de));
	}

	//basic male isn't allowed to write to anyone
	@Test public void testBasicMaleWrite() throws AccessServiceException{
		assertFalse(allowed("write", bm_at, pm_at));
		assertFalse(allowed("write", bm_at, pm_de));
		assertFalse(allowed("write", bm_at, pf_at));
		assertFalse(allowed("write", bm_at, pf_de));
		assertFalse(allowed("write", bm_at, bm_at));
		assertFalse(allowed("write", bm_at, bm_de));
		assertFalse(allowed("write", bm_at, bf_at));
		assertFalse(allowed("write", bm_at, bf_de));
		
	}

	//basic male isn't allowed to read from premium male
	@Test public void testBasicMaleRead() throws AccessServiceException{
		assertFalse(allowed("read", bm_at, pm_at));
		assertFalse(allowed("read", bm_at, pm_de));
		assertTrue(allowed("read", bm_at, pf_at));
		assertTrue(allowed("read", bm_at, pf_de));
		assertFalse(allowed("read", bm_at, bm_at));
		assertFalse(allowed("read", bm_at, bm_de));
		assertTrue(allowed("read", bm_at, bf_at));
		assertTrue(allowed("read", bm_at, bf_de));
		
	}

	//basic male isn't allowed to write to anyone
	@Test public void testBasicFemaleWrite() throws AccessServiceException{
		assertTrue(allowed("write", bf_at, pm_at));
		assertFalse(allowed("write", bf_at, pm_de));
		assertFalse(allowed("write", bf_at, pf_at));
		assertFalse(allowed("write", bf_at, pf_de));
		assertTrue(allowed("write", bf_at, bm_at));
		assertFalse(allowed("write", bf_at, bm_de));
		assertFalse(allowed("write", bf_at, bf_at));
		assertFalse(allowed("write", bf_at, bf_de));

		assertTrue(allowed("write", bf_de, bm_de));
		assertTrue(allowed("write", bf_de, pm_de));
		
		assertTrue(allowed("write", bf_ch, bm_ch));
		assertTrue(allowed("write", bf_ch, pm_ch));
	}

	private boolean allowed(String action, SecurityObject object, SecurityObject subject) throws AccessServiceException{
		AccessServiceReply reply = service.isAllowed(action, object, subject);
		System.out.println("for "+action+" "+object+" on "+subject+" -> "+reply);
		return reply.isAnswered() && reply.isAllowed();
	}
}
