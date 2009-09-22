/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.Validate;

/**
 * A User.
 * 
 * @author esteban
 * 
 */
@Entity
public class User {
	/**
	 * Entity autogenerated id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * the user name.
	 */
	private String name;

	/**
	 * the user surname.
	 */
	private String lastName;

	/**
	 * the user email.
	 */
	private String email;

	/**
	 * the login password.
	 */
	private String password;

	/**
	 * the username.
	 */
	private String userName;

	/**
	 * the user type.
	 */
	private String userType;
	/**
	 * the spots comparations this user have created and saved.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private final Set<Comparation> comparations = new HashSet<Comparation>();;

	/**
	 * the zones this user have created and saved.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private final Set<Zone> zones = new HashSet<Zone>();

	/**
	 * 
	 */
	protected User() {
		// For ORM purpose.
	}

	public User(final String name, final String lastName,
			final String userName, final String password, final String email,
			final String userType) {
		Validate.notEmpty(userName, "The user name should not be empty.");
		Validate.notEmpty(password, "The user password should not be empty.");
		Validate.notEmpty(password, "The email should not be empty.");
		Validate.notEmpty(userType, "The user type should not be empty.");
		setName(name);
		setLastName(lastName);
		setUserName(userName);
		setPassword(password);
		setEmail(email);
		setUserType(userType);

	}

	/**
	 * @return the comparations
	 */
	public Set<Comparation> getComparations() {
		return Collections.unmodifiableSet(comparations);
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the surname
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @return the zones
	 */
	public Set<Zone> getZones() {
		return Collections.unmodifiableSet(zones);
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(final String email) {
		Validate.notEmpty(email);
		// TODO: validate it does not already exists.
		this.email = email;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param password
	 *            the password to set
	 * @throws Exception
	 */
	public void setPassword(final String password) {
		Validate.notEmpty(password, "The password should not be empty");
		if (getUserName() == password)
			// TODO:Should throw exception
			throw new IllegalArgumentException();
		this.password = password;
	}

	/**
	 * @param lastName
	 *            the surname to set
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param theUserName
	 *            the userName to set
	 */
	public void setUserName(final String userName) {
		Validate.notEmpty(userName, "The user name should not be empty");
		// TODO: Validate user name not already exists.
		this.userName = userName;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(final String userType) {
		Validate.notEmpty(userType, "The user type should not be empty.");
		this.userType = userType;
	}

	/**
	 * Add a new comparation to the user.
	 */
	public void addComparation(final Comparation comparation) {
		Validate.notNull(comparation, "The comparation should not be null");
		comparations.add(comparation);
	}

	/**
	 * Removes a comparation from the user.
	 */
	public void removeComparation(final Comparation comparation) {
		Validate.notNull(comparation, "The comparation should not be null");
		comparations.remove(comparation);
	}

	/**
	 * Add a new zone to the user.
	 */
	public void addZone(final Zone zone) {
		Validate.notNull(zone, "The zone should not be null");
		zones.add(zone);
	}

	/**
	 * Removes a zone from the user.
	 */
	public void removeZone(final Zone zone) {
		Validate.notNull(zone, "The zone should not be null");
		zones.remove(zone);
	}
}
