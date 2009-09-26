/**
 * 
 */
package edu.unicen.surfforecaster.common.services.dto;

import java.io.Serializable;

/**
 * User DTO.
 * 
 * @author esteban
 * 
 */
public class UserDTO implements Serializable {
	/**
	 * 
	 */
	public UserDTO() {
		// gwt purpose
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * User id in DB.
	 */
	private Integer id;
	/**
	 * the user name.
	 */
	private String name;
	/**
	 * the user last name
	 */
	private String lastName;
	/**
	 * the user email
	 */
	private String email;
	/**
	 * the user name
	 */
	private String username;
	/**
	 * the user type
	 */
	private UserType type;

	/**
	 * @param name
	 * @param lastName
	 * @param email
	 * @param username
	 * @param type
	 */
	public UserDTO(final Integer id, final String name, final String lastName,
			final String email, final String username, final UserType type) {
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
	public UserType getType() {
		return type;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

}
