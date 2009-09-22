/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ErrorCode;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.server.SpringContextAwareTest;

/**
 * Integration tests for {@link UserServiceImplementation}.
 * 
 * @author esteban
 * 
 */
public class UserServiceImplementationTest extends SpringContextAwareTest {

	private UserService userService;

	@Before
	public void init() {
		userService = (UserService) context.getBean("userService");
	}

	/**
	 * Correct addition of new user and login.
	 */
	@Test
	public void addAndLoginUser() {
		final String name = "Jhon";
		final String lastName = "Cook";
		final String email = "jhon@cook.com";
		final String username = "jhonCook89";
		final String password = "jhonnieCook";
		final String type = "admin";
		try {
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
			Assert.assertTrue("generated id should be greater than 0", id > 0);

			final UserDTO user = userService.loginUser(username, password);

			Assert.assertEquals(name, user.getName());
			Assert.assertEquals(lastName, user.getLastName());
			Assert.assertEquals(email, user.getEmail());
			Assert.assertEquals(username, user.getUsername());
			Assert.assertEquals(type, user.getType());
			Assert.assertEquals(id, user.getId());

		} catch (final NeuralitoException e) {
			Assert.fail(e.getErrorCode().name());
		}
	}

	/**
	 * Incorrect login. Bad password.
	 */
	@Test
	public void incorrectLoginBadPassword() {
		final String name = "Jhon";
		final String lastName = "Cook";
		final String email = "jhon@cook.com";
		final String username = "jhonCook89";
		final String password = "jhonnieCook";
		final String type = "admin";
		try {
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
			Assert.assertTrue("generated id should be greater than 0", id > 0);

			final UserDTO user = userService.loginUser(username,
					"wrongPasswords");
			Assert.fail("Login should have failed and thrown exception.");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.INVALID_LOGIN.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.INVALID_LOGIN));
		}
	}

	/**
	 * Incorrect login. Bad username.
	 */
	@Test
	public void incorrectLoginBadUserName() {
		final String name = "Jhon";
		final String lastName = "Cook";
		final String email = "jhon@cook.com";
		final String username = "jhonCook89";
		final String password = "jhonnieCook";
		final String type = "admin";
		try {
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
		} catch (final NeuralitoException e) {
			Assert
					.fail("User should ve been added with no problems. Instead error:"
							+ e.getErrorCode().name() + " was found.");
		}
		try {
			userService.loginUser("cualquierasdj987a", password);
			Assert.fail("After bad login expecting error:"
					+ ErrorCode.INVALID_LOGIN.name() + " but was not found.");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.INVALID_LOGIN + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.INVALID_LOGIN));
		}

	}

	/**
	 * Incorrect addittion of new user. Duplicated username.
	 */
	@Test
	public void addAnAlreadyUsedUserName() {
		final String name = "Jhon";
		final String lastName = "Cook";
		final String email = "jhon@cook.com";
		final String username = "jhonCook89";
		final String password = "jhonnieCook";
		final String type = "admin";

		final String name2 = "Jhon2";
		final String lastName2 = "Cook2";
		final String email2 = "jhon@cook.com3";
		final String username2 = username;
		final String password2 = "jhonnieCook3";
		final String type2 = "admin";
		try {
			// Add the first user.
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
		} catch (final NeuralitoException e) {
			Assert
					.fail("User should ve been added with no problems. Instead error:"
							+ e.getErrorCode().name() + " was found.");
		}
		try {
			// Add a second user with the same username.This should throw
			// exception.
			userService.addUser(name2, lastName2, email2, username2, password2,
					type2);
			Assert.fail("Expecting:" + ErrorCode.DUPLICATED_USER_USERNAME
					+ " but error was not found.");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.DUPLICATED_USER_USERNAME.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.DUPLICATED_USER_USERNAME));
		}
	}

	/**
	 * Incorrect addittion of new user. Duplicated email.
	 */
	@Test
	public void addAnAlreadyUsedEmail() {
		final String name = "Jhon";
		final String lastName = "Cook";
		final String email = "jhon@cook.com";
		final String username = "jhonCook89";
		final String password = "jhonnieCook";
		final String type = "admin";

		final String name2 = "Jhon2";
		final String lastName2 = "Cook2";
		final String email2 = email;
		final String username2 = "whanchanken";
		final String password2 = "jhonnieCook3";
		final String type2 = "admin";
		try {
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
		} catch (final NeuralitoException e) {
			Assert
					.fail("User should ve been added with no problems. Instead error:"
							+ e.getErrorCode().name() + " was found.");
		}
		try {
			// Add a second user with the same username.This should throw
			// exception.
			userService.addUser(name2, lastName2, email2, username2, password2,
					type2);
			Assert.fail("Expecting:" + ErrorCode.DUPLICATED_USER_EMAIL
					+ " but error was not found.");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.DUPLICATED_USER_EMAIL.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.DUPLICATED_USER_EMAIL));
		}
	}

	/**
	 * Incorrect addition of new user. Empty username.
	 */
	@Test
	public void addUserWithEmptyUserName() {
		final String name = "Jhon";
		final String lastName = "Cook";
		final String email = "jhon@cook.com";
		String username = "";
		final String password = "jhonnieCook";
		final String type = "admin";
		try {
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
			Assert
					.fail("User should not ve been added because username was empty");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.USERNAME_EMPTY.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.USERNAME_EMPTY));
		}
		try {
			username = null;
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
			Assert
					.fail("User should not ve been added because username was empty");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.USERNAME_EMPTY.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.USERNAME_EMPTY));
		}
	}

	/**
	 * Incorrect addition of new user. Empty password.
	 */
	@Test
	public void addUserWithEmptyPassword() {
		final String name = "Jhon";
		final String lastName = "Cook";
		final String email = "jhon@cook.com";
		final String username = "asdasd";
		String password = "";
		final String type = "admin";
		try {
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
			Assert
					.fail("User should not ve been added because password was empty");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.PASSWORD_EMPTY.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.PASSWORD_EMPTY));
		}
		try {
			password = null;
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
			Assert
					.fail("User should not ve been added because password was empty");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.PASSWORD_EMPTY.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.PASSWORD_EMPTY));
		}
	}

	/**
	 * Incorrect addition of new user. Empty email.
	 */
	@Test
	public void addUserWithEmptyEmail() {
		final String name = "Jhon";
		final String lastName = "Cook";
		String email = "";
		final String username = "asdasd";
		final String password = "asdasda";
		final String type = "admin";
		try {
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
			Assert
					.fail("User should not ve been added because email was empty");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.EMAIL_EMPTY.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.EMAIL_EMPTY));
		}
		try {
			email = null;
			final Integer id = userService.addUser(name, lastName, email,
					username, password, type);
			Assert
					.fail("User should not ve been added because email was empty");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.EMAIL_EMPTY.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.EMAIL_EMPTY));
		}
	}
}