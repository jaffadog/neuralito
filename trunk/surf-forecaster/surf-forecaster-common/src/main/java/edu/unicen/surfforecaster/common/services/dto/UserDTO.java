/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

/**
 * User DTO.
 * 
 * @author esteban
 * 
 */
public class UserDTO {

	/**
	 * User id in DB.
	 */
	private final Integer id;
	/**
	 * the user name.
	 */
	private final String name;
	/**
	 * the user last name
	 */
	private final String lastName;
	/**
	 * the user email
	 */
	private final String email;
	/**
	 * the user name
	 */
	private final String username;
	/**
	 * the user type
	 */
	private final String type;

	/**
	 * @param name
	 * @param lastName
	 * @param email
	 * @param username
	 * @param type
	 */
	public UserDTO(final Integer id, final String name, final String lastName,
			final String email, final String username, final String type) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

}
