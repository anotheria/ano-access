package net.anotheria.access;

import java.util.List;

import net.anotheria.access.impl.PermissionCollection;

/**
 * Interface for the bouncer service which controls access to different parts of the application. 
 * Please note that through all the bouncer code the terms "object" and "subject" have been used wrongfully and should be swapped.
 * @author another
 *
 */
public interface AccessService {
	/**
	 * Returns true if the given action is allowed to be executed by given security object on given security subject. 
	 * @param action the action to execute.
	 * @param object
	 * @param subject
	 * @return
	 * @throws AccessServiceException
	 */
	AccessServiceReply isAllowed(String action, SecurityObject object, SecurityObject subject) throws AccessServiceException;
	/**
	 * Same as isAllowed with the difference that it is called AFTER the action has been taken to notify bouncer to update the corresponding roles. The bouncer can still 
	 * deny the execution by throwing an exception, however it must be ensured by the application that the action will be canceled.
	 * @param action
	 * @param object
	 * @param subject
	 * @param basedUponReply
	 * @throws AccessServiceException
	 */
	void notifyPassed(String action, SecurityObject object, SecurityObject subject, AccessServiceReply basedUponReply) throws   AccessServiceException;

	/**
	 * Grants a role to the object.
	 * @param object
	 * @param roleName
	 * @throws AccessServiceException
	 */
	void grantRole(SecurityObject object, String roleName)  throws AccessServiceException;
	
	/**
	 * Revokes the role from the object.
	 * @param object
	 * @param roleName
	 * @throws AccessServiceException
	 */
	void revokeRole(SecurityObject object, String roleName)  throws AccessServiceException;

	/**
	 * Returns all known roles.
	 * @return
	 * @throws AccessServiceException
	 */
	List<RoleInfo> getRoleInfos()  throws AccessServiceException;

	/**
	 * Returns RoleInfos for a given object (user).
	 * @param object
	 * @return
	 */
	List<RoleInfo> getRoleInfos(SecurityObject object) throws AccessServiceException;
	
	/**
	 * Returns all roles for all objects.
	 * @return
	 * @throws AccessServiceException
	 */
	List<Role> getRoles() throws AccessServiceException;
	
	/**
	 * Get role in storage by role name.
	 * @param roleName - role to get
	 * @return
	 * @throws AccessServiceException
	 */
	Role getRoleByName(String roleName) throws AccessServiceException;
	
	//role management
	void addRole(Role toAdd) throws AccessServiceException;
	
	boolean deleteRole(Role toDelete) throws AccessServiceException;
	
	void addPermissionCollection(PermissionCollection collection) throws AccessServiceException;
	
	PermissionCollection getPermissionCollection(String collectionName) throws RuntimeException;
	
}
