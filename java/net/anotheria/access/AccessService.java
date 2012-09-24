package net.anotheria.access;

import java.util.List;

import net.anotheria.access.impl.PermissionCollection;
import net.anotheria.anoprise.metafactory.Service;

/**
 * Interface for the bouncer service which controls access to different parts of the application. <br>
 * Please note that through all the bouncer code the terms "object" and "subject" have been used wrongfully and should be swapped.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public interface AccessService extends Service {

	/**
	 * Returns true if the given action is allowed to be executed by given security object on given security subject.
	 * 
	 * @param action
	 *            - the action to execute
	 * @param object
	 *            - security object
	 * @param subject
	 *            - security subject
	 * @return {@link AccessServiceReply}
	 * @throws AccessServiceException
	 */
	AccessServiceReply isAllowed(String action, SecurityObject object, SecurityObject subject) throws AccessServiceException;

	/**
	 * Same as isAllowed with the difference that it is called AFTER the action has been taken to notify bouncer to update the corresponding roles. <br>
	 * The bouncer can still deny the execution by throwing an exception, however it must be ensured by the application that the action will be canceled.
	 * 
	 * @param action
	 *            - the action to execute
	 * @param object
	 *            - security object
	 * @param subject
	 *            - security subject
	 * @param basedUponReply
	 *            - {@link AccessServiceReply}
	 * @throws AccessServiceException
	 */
	void notifyPassed(String action, SecurityObject object, SecurityObject subject, AccessServiceReply basedUponReply) throws AccessServiceException;

	/**
	 * Grants a {@link Role} to the {@link SecurityObject}.
	 * 
	 * @param object
	 *            - security object
	 * @param roleName
	 *            - {@link Role} name
	 * @throws AccessServiceException
	 */
	void grantRole(SecurityObject object, String roleName) throws AccessServiceException;

	/**
	 * Revokes the {@link Role} from the {@link SecurityObject}.
	 * 
	 * @param object
	 *            - security object
	 * @param roleName
	 *            - {@link Role} name
	 * @throws AccessServiceException
	 */
	void revokeRole(SecurityObject object, String roleName) throws AccessServiceException;

	/**
	 * Returns all known roles as {@link List} of {@link RoleInfo}.
	 * 
	 * @return {@link List} of {@link RoleInfo}
	 */
	List<RoleInfo> getRoleInfos();

	/**
	 * Returns {@link List} of {@link RoleInfo} for a given {@link SecurityObject}.
	 * 
	 * @param object
	 *            - security object
	 * @return {@link List} of {@link RoleInfo}
	 */
	List<RoleInfo> getRoleInfos(SecurityObject object);

	/**
	 * Returns all roles for all objects.
	 * 
	 * @return {@link List} of {@link Role}
	 */
	List<Role> getRoles();

	/**
	 * Get {@link Role} by name.
	 * 
	 * @param roleName
	 *            - {@link Role} name
	 * @return {@link Role}
	 */
	Role getRole(String roleName);

	/**
	 * Adds configured {@link Role}.
	 * 
	 * @param role
	 *            - {@link Role} to add
	 */
	void addRole(Role role);

	/**
	 * Removes given role from storage.
	 * 
	 * @param role
	 *            - {@link Role} to remove
	 * @return <code>true</code> if removed or <code>false</code>
	 */
	boolean deleteRole(Role role);

	/**
	 * Adds configured {@link PermissionCollection}.
	 * 
	 * @param collection
	 *            - {@link PermissionCollection} to add
	 */
	void addPermissionCollection(PermissionCollection collection);

	/**
	 * Returns {@link PermissionCollection} by name.
	 * 
	 * @param collectionName
	 *            - {@link PermissionCollection} name
	 * @return {@link PermissionCollection}
	 */
	PermissionCollection getPermissionCollection(String collectionName);

	/**
	 * Removes {@link SecurityObject}.
	 * 
	 * @param object
	 *            - {@link SecurityObject} to remove
	 * @throws AccessServiceException
	 */
	void deleteSecurityObject(SecurityObject object) throws AccessServiceException;

	/**
	 * Reset runtime configuration and caches. This is workaround for hot and easy re-configuration.
	 */
	void reset();

	/**
	 * Reset runtime configuration for a given owner, it will be reloaded from persistence on next call. This is workaround for hot and easy re-configuration.
	 * 
	 * @param ownerId
	 *            - owner id
	 */
	void reset(String ownerId);

}
