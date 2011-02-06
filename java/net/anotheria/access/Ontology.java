package net.anotheria.access;
/**
 * Contains constants which are used through the bouncer service.
 * @author another
 *
 */
public class Ontology {
	
	/**
	 * The gender attribute name.
	 */
	public static final String ATT_GENDER = id("gender");
	/**
	 * The user status attribute name.
	 */
	public static final String ATT_STATUS = id("status");
	/**
	 * registration agency of the user.
	 */
	public static final String ATT_REG_AGENCY = id("regAgency");
	/**
	 * registration country of the user (derived from agency)
	 */
	public static final String ATT_REG_COUNTRY = id("regCountry");
	/**
	 * registration language of the user (derived from agency)
	 */
	public static final String ATT_REG_LANG = id("regLang");
	
	public static final String ATT_VAL_FEMALE = id("female");
	public static final String ATT_VAL_MALE = id("male");
	
	public static final String ATT_VAL_STATUS_MEMBER = id("member");
	public static final String ATT_VAL_STATUS_PREMIUM = id("premium");
	
	
	
	
	
	
	/**
	 * Prevent compiler caching.
	 * @param s
	 * @return
	 */
	private static String id(String s){ return s; }
}
