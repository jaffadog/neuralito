/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ErrorCode;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.server.dao.UserDAO;
import edu.unicen.surfforecaster.server.domain.entity.User;

/**
 * Implementation of {@link UserService}
 * 
 * @author esteban
 * 
 */
public class UserServiceImplementation implements UserService {
	/**
	 * The user DAO for data access.
	 */
	UserDAO userDAO;

	/**
	 * 
	 * @see edu.unicen.surfforecaster.common.services.UserService#addUser(java.lang
	 *      .String, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public Integer addUser(final String name, final String lastName,
			final String email, final String username, final String password,
			final String userType) throws NeuralitoException {
		validate(name, lastName, email, username, password, userType);
		User user = new User(name, lastName, username, password, email,
				userType);
		try {
			user = userDAO.saveOrUpdate(user);
		} catch (final DataAccessException e) {
			throw new NeuralitoException(ErrorCode.DATABASE_ERROR);
		}
		return user.getId();
	}

	/**
	 * Performs all validations for the {@link UserService#addUser()} method.
	 * When a validation error occurs throws the general
	 * {@link NeuralitoException} with the corresponnding {@link ErrorCode}
	 */
	private void validate(final String name, final String lastName,
			final String email, final String username, final String password,
			final String userType) throws NeuralitoException {
		// Validates username is not empty
		if (StringUtils.isEmpty(username))
			throw new NeuralitoException(ErrorCode.USERNAME_EMPTY);
		// Validates password is not empty
		if (StringUtils.isEmpty(password))
			throw new NeuralitoException(ErrorCode.PASSWORD_EMPTY);
		// Validates email is not empty
		if (StringUtils.isEmpty(email))
			throw new NeuralitoException(ErrorCode.EMAIL_EMPTY);
		// Validates usertype is not empty
		if (StringUtils.isEmpty(userType))
			throw new NeuralitoException(ErrorCode.USER_TYPE_EMPTY);
		// Validates username not already exists
		if (userDAO.getUserByUserName(username) != null)
			throw new NeuralitoException(ErrorCode.DUPLICATED_USER_USERNAME);
		// Validates email not already exists
		if (userDAO.getUserByEmail(email) != null)
			throw new NeuralitoException(ErrorCode.DUPLICATED_USER_EMAIL);
		// Validates password is not the same as username.
		if (password.equals(username))
			throw new NeuralitoException(ErrorCode.INVALID_PASSWORD);
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.UserService#loginUser(java.
	 *      lang.String, java.lang.String)
	 */
	@Override
	public UserDTO loginUser(final String userName, final String password)
			throws NeuralitoException {
		validate(userName, password);
		final User user = userDAO.getUserByUserName(userName);
		if (user == null || !password.equals(user.getPassword()))
			// user not found for given username or password was invalid.
			throw new NeuralitoException(ErrorCode.INVALID_LOGIN);

		return new UserDTO(user.getId(), user.getName(), user.getLastName(),
				user.getEmail(), user.getUserName(), user.getUserType());
	}

	/**
	 * Validations for {@link UserService#loginUser()}
	 * 
	 * @param userName
	 * @param password
	 */
	private void validate(final String username, final String password)
			throws NeuralitoException {
		// Validates username is not empty
		if (StringUtils.isEmpty(username))
			throw new NeuralitoException(ErrorCode.USERNAME_EMPTY);
		// Validates password is not empty
		if (StringUtils.isEmpty(password))
			throw new NeuralitoException(ErrorCode.PASSWORD_EMPTY);
	}

	/**
	 * @param userDAO
	 *            the userDAO to set
	 */
	public void setUserDAO(final UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
