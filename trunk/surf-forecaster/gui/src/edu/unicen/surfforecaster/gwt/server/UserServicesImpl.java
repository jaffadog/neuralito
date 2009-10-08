package edu.unicen.surfforecaster.gwt.server;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;
import edu.unicen.surfforecaster.gwt.client.UserServices;

public class UserServicesImpl extends ServicesImpl implements UserServices {
	/**
	 * Logger.
	 */
	Logger logger = Logger.getLogger(UserServicesImpl.class);
	/**
	 * The user services interface.
	 */
	private UserService userService;
	
	/**
	 * @param service the service to set
	 */
	public void setUserService(final UserService service) {
		userService = service;
	}

	/**
	 * @return the user service
	 */
	public UserService getUserService() {
		return userService;
	}
	
	

	/**
	 * Check if exists any user with the username and password passed as parameters
	 * 
	 * @param String userName
	 * @param String password
	 * @return User user if exist any user with that values or Null
	 */
	public UserDTO login(final String userName, final String password) throws NeuralitoException{
		logger.log(Level.INFO,"ForecastCommonServicesImpl - login - Finding User: '" + userName + "'.");
		//try{
			UserDTO userDTO = userService.loginUser(userName, password);
			final HttpSession session = this.getSession();
			session.setMaxInactiveInterval(1200); // 120seg
			session.setAttribute("gwtForecast-UserName", userDTO.getUsername());
			session.setAttribute("gwtForecast-UserType", userDTO.getType());
			session.setAttribute("gwtForecast-UserId", userDTO.getId());
			session.setAttribute("gwtForecast-User", userDTO);
			logger.log(Level.INFO,"ForecastCommonServicesImpl - login - User: '" + userDTO.getUsername() + "' retrieved.");
			return userDTO;
		//} catch(Exception e) {System.out.println(e);}
	}

	public Integer addUser(String name, String lastname, String email,
			String username, String password, int type) throws NeuralitoException {
		
		logger.log(Level.INFO,"ForecastCommonServicesImpl - addUser - Adding user with username: '" + username + "'.");
		Integer result = userService.addUser(name, lastname, email, username, password, UserType.REGISTERED_USER);
		logger.log(Level.INFO,"ForecastCommonServicesImpl - addUser - User with username: '" + username + "' successfully added.");
		
		return result;
	}

}
