/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ErrorCode;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;

/**
 * Integration tests for {@link UserServiceImplementation}.
 * 
 * @author esteban
 * 
 */
public class UserServiceImplementationTest {

	private final UserService userService;

	protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"/services.xml");

	private Integer createdUserId;
	final String name = "Jhon";
	final String lastName = "Cook";
	final String email = System.currentTimeMillis() + "jhon@cook.com";
	final String username = System.currentTimeMillis() + "jhonCook89";
	final String password = "jhonnieCook";
	final UserType type = UserType.ADMINISTRATOR;

	public UserServiceImplementationTest() {
		userService = (UserService) context.getBean("userService");
	}

	@Before
	public void createTestUser() {
		try {
			createdUserId = userService.addUser(name, lastName, email,
					username, password, type);
		} catch (final NeuralitoException e) {
			Assert
					.fail("Could not create user for performing tests. Exception was:"
							+ e);
		}
	}

	@After
	public void removeTestUser() {
		try {
			if (createdUserId != null) {
				userService.removeUser(createdUserId);
			}
		} catch (final NeuralitoException e) {
			Assert.fail("Could not remove test user. Exception was:" + e);
		}
	}

	/**
	 * Login with the test user.
	 */
	@Test
	public void loginUser() {
		try {
			final UserDTO user = userService.loginUser(username, password);

			Assert.assertEquals(name, user.getName());
			Assert.assertEquals(lastName, user.getLastName());
			Assert.assertEquals(email, user.getEmail());
			Assert.assertEquals(username, user.getUsername());
			Assert.assertEquals(type, user.getType());
			Assert.assertEquals(createdUserId, user.getId());

		} catch (final NeuralitoException e) {
			Assert.fail(e.getErrorCode().name());
		}
	}

	/**
	 * Incorrect login. Bad password.
	 */
	@Test
	public void incorrectLoginBadPassword() {
		try {
			userService.loginUser(username, "wrongPasswords");
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

		final String name2 = "Jhon2";
		final String lastName2 = "Cook2";
		final String email2 = "jhon@cook.com3";
		final String username2 = username;
		final String password2 = "jhonnieCook3";
		final UserType type2 = UserType.ADMINISTRATOR;
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
		final String name2 = "Jhon2";
		final String lastName2 = "Cook2";
		final String email2 = email;
		final String username2 = "whanchanken";
		final String password2 = "jhonnieCook3";
		final UserType type2 = UserType.ADMINISTRATOR;
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
		final String name2 = "Jhon2";
		final String lastName2 = "Cook2";
		final String email2 = "email@asd.com";
		final String username2 = "";
		final String password2 = "jhonnieCook3";
		final UserType type2 = UserType.ADMINISTRATOR;
		try {

			userService.addUser(name2, lastName2, email2, username2, password2,
					type2);
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
		final String name2 = "Jhon2";
		final String lastName2 = "Cook2";
		final String email2 = "email@asd.com";
		final String username2 = "whanchanken";
		String password2 = "";
		final UserType type2 = UserType.ADMINISTRATOR;
		try {
			userService.addUser(name2, lastName2, email2, username2, password2,
					type2);
			Assert
					.fail("User should not ve been added because password was empty");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.PASSWORD_EMPTY.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.PASSWORD_EMPTY));
		}
		try {
			password2 = null;
			userService.addUser(name2, lastName2, email2, username2, password2,
					type2);
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
		final String name2 = "Jhon2";
		final String lastName2 = "Cook2";
		String email2 = "";
		final String username2 = "whanchanken";
		final String password2 = "asdfasfas";
		final UserType type2 = UserType.ADMINISTRATOR;
		try {
			userService.addUser(name2, lastName2, email2, username2, password2,
					type2);
			Assert
					.fail("User should not ve been added because email was empty");
		} catch (final NeuralitoException e) {
			Assert.assertTrue("Error Code different than expected. Expecting:"
					+ ErrorCode.EMAIL_EMPTY.name() + "but was:"
					+ e.getErrorCode().name(), e.getErrorCode().equals(
					ErrorCode.EMAIL_EMPTY));
		}
		try {
			email2 = null;
			userService.addUser(name2, lastName2, email2, username2, password2,
					type2);
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