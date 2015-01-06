package de.th.wildau.recruiter.ejb.service.remote;

import java.util.List;

import javax.persistence.NoResultException;

import de.th.wildau.recruiter.ejb.BusinessException;
import de.th.wildau.recruiter.ejb.RoleName;
import de.th.wildau.recruiter.ejb.model.Address;
import de.th.wildau.recruiter.ejb.model.Role;
import de.th.wildau.recruiter.ejb.model.User;

public interface UserServiceRemote {

	/**
	 * Activate user account with email address and activation key.
	 * 
	 * @param email
	 *            email string
	 * @param key
	 *            password
	 * @return true or throw a BusinessError
	 */
	public boolean activate(final String email, final String key)
			throws BusinessException;

	/**
	 * Reset signin attempts. Allowed only for role admin, company and user.
	 * 
	 * @param email
	 */
	public void authenticatePost(final String email);

	/**
	 * Handle signin attempts before signin
	 * 
	 * @param email
	 * @throws BusinessException
	 *             in case of to much attempts
	 */
	public void authenticatePre(final String email) throws BusinessException;

	/**
	 * Change the current password from a user.
	 * 
	 * @param email
	 * @return String token
	 * @throws BusinessException
	 */
	public void changeEmail(final String password, final String oldEmail,
			final String newEmail) throws BusinessException;

	/**
	 * Change the current password from a user.
	 * 
	 * @param email
	 * @return String token
	 * @throws BusinessException
	 */
	public void changePassword(final String email, final String oldPassword,
			final String newPassword) throws BusinessException;

	/**
	 * Delete a user profile/account.
	 * 
	 * @param userId
	 * @throws BusinessException
	 */
	public void delete(final Long userId) throws BusinessException;

	/**
	 * Equals to {@code #getUser(String)}, but return {@code null} if none
	 * entity found.
	 * 
	 * @param email
	 * @return {@code User} or {@code null}
	 */
	public User findUser(final String email);

	/**
	 * Get the address from a user.
	 * 
	 * @param email
	 * @return address or null
	 */
	public Address getAddress(final String email);

	/**
	 * Get current user.
	 * 
	 * @return User or {@code null}
	 */
	public User getCurrentUser();

	/**
	 * Find salt for a specific user.
	 * 
	 * @param email
	 * @return String salt, if email not existing sha265 of email string
	 */
	public String getPasswordSalt(final String email);

	/**
	 * Get a specific role.
	 * 
	 * @param roleName
	 * @return Role
	 */
	public Role getRole(final RoleName role);

	/**
	 * Get all user roles (without admin).
	 * 
	 * @return List of roles
	 */
	public List<Role> getRoles();

	/**
	 * Get user by email address.
	 * 
	 * @param email
	 * @throws NoResultException
	 *             if no entity found
	 * @return User or null if user doesn't exist.
	 */
	public User getUser(final String email, final String... fetch);

	/**
	 * Get all users.
	 * 
	 * @return List of users.
	 */
	public List<User> getUsers();

	/**
	 * Register a new user.
	 */
	public void register(final String email, final String password,
			final RoleName roleName, final User user, final Address address)
			throws BusinessException;

	/**
	 * Update the current user profile.
	 * 
	 * @param user
	 *            with new addess
	 * @throws BusinessException
	 */
	public void updateProfile(final User user) throws BusinessException;

	// /**
	// * Check new Password again the constrains.
	// *
	// * @param password
	// * @throws BusinessException
	// */
	// public void validatePassword(final String password)
	// throws BusinessException;

}
