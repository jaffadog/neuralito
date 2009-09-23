/**
 * 
 */
package edu.unicen.surfforecaster.server.dao;

import edu.unicen.surfforecaster.server.domain.entity.User;

/**
 * @author esteban
 * 
 */
public interface UserDAO {

	/**
	 * Persist user into the DB.
	 * 
	 * @param user
	 * @return
	 */
	public User saveOrUpdate(User user);

	/**
	 * Obtain the user by user name.
	 * 
	 * @param email
	 * @return
	 */
	public User getUserByUserName(String userName);

	/**
	 * Obtain the User by the email.
	 * 
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email);

	/**
	 * @param userId
	 */
	public void removeUser(Integer userId);

}
