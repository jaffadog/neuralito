/**
 * 
 */
package edu.unicen.surfforecaster.common.services;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;

/**
 * Service definition for all user related operations.
 * 
 * @author esteban
 * 
 */
public interface UserService {
	/**
	 * Add a user to the system.
	 * 
	 * @param name
	 *            the user first name.
	 * @param lastName
	 *            the user last name.
	 * @param email
	 *            the user email. Cannot be empty. Should be unique among all
	 *            users.
	 * @param username
	 *            the username for login.Cannot be empty. Should be unique among
	 *            all users.
	 * @param password
	 *            the password for login. Cannot be empty.
	 * @param type
	 *            the user type.
	 * @return the id of the added user.
	 * @throws NeuralitoException
	 */
	public Integer addUser(String name, String surname, String email,
			String username, String password, String type)
			throws NeuralitoException;

	/**
	 * Logs a user into the system.
	 * 
	 * @param userName
	 *            Cannot be empty.
	 * @param password
	 *            Cannot be empty.
	 * @return the {@link UserDTO} of the logged user.
	 */
	public UserDTO loginUser(String userName, String password)
			throws NeuralitoException;

}
