package edu.unicen.surfforecaster.gwt.server;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.UserService;
import edu.unicen.surfforecaster.common.services.dto.ComparationDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.common.services.dto.UserType;
import edu.unicen.surfforecaster.common.services.dto.ZoneDTO;
import edu.unicen.surfforecaster.gwt.client.UserServices;
import edu.unicen.surfforecaster.gwt.client.utils.SessionData;

@SuppressWarnings("serial")
public class UserServicesImpl extends ServicesImpl implements UserServices {

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
		logger.log(Level.INFO,"UserServicesImpl - login - Finding User: '" + userName + "'...");
		UserDTO userDTO = userService.loginUser(userName, password);
		//Set the user for this session
		SessionData.getInstance().clear();
		final HttpSession session = this.getSession();
		session.setAttribute("surfForecaster-User", userDTO);
		session.setMaxInactiveInterval(super.MAX_INACTIVE_INTERVAL);
		
		logger.log(Level.INFO,"UserServicesImpl - login - User: '" + userDTO.getUsername() + "' retrieved.");
		return userDTO;
	}
	
	/**
	 * Removes all the session values stored in the current session
	 * Remove the username cookie
	 */
	public void closeSession() {
		final HttpSession session = getSession();
		logger.log(Level.INFO, "ServicesImpl - closeSession - Closing the current session...");
		session.removeAttribute("surfForecaster-User");
		SessionData.getInstance().clear();
		logger.log(Level.INFO, "ServicesImpl - closeSession - Session closed.");
	}

	public Integer addUser(String name, String lastname, String email,
			String username, String password, UserType userType) throws NeuralitoException {
		
		logger.log(Level.INFO,"UserServicesImpl - addUser - Adding user with username: '" + username + "'...");
		Integer result = userService.addUser(name, lastname, email, username, password, userType);
		logger.log(Level.INFO,"UserServicesImpl - addUser - User with username: '" + username + "' successfully added.");
		
		return result;
	}

	
	public List<ComparationDTO> getSpotsComparations() throws NeuralitoException {
		logger.log(Level.INFO,"UserServicesImpl - getSpotsComparations - Retrieving spots comparations'...");
		
		if (super.hasAccessTo("addComparation")){
			final Integer userId = super.getLoggedUser().getId();
			List<ComparationDTO> result = new ArrayList<ComparationDTO>();
			
			//TODO pedir al servicio del usuario las comparaciones reales y borrar el siguiente bloque mockeado
			{
				ZoneDTO z1 = new ZoneDTO(1, "", null);
				ZoneDTO z2 = new ZoneDTO(2, "", null);
				ZoneDTO z3 = new ZoneDTO(3, "", null);
				ZoneDTO z4 = new ZoneDTO(4, "", null);
				ZoneDTO z5 = new ZoneDTO(5, "", null);
				TimeZone tz = TimeZone.getTimeZone("UTC");
				//SpotDTO s1 = new SpotDTO(1, "La paloma", point, zone, country, area, userId, publik);
				SpotDTO s1 = new SpotDTO(1, "La paloma", null, z1, null, null, 1, false,tz);
				SpotDTO s2 = new SpotDTO(2, "La onda", null, z3, null, null, 1, false,tz);
				SpotDTO s3 = new SpotDTO(3, "Waikiki", null, z1, null, null, 1, false,tz);
				SpotDTO s4 = new SpotDTO(4, "La parroquia", null, z1, null, null, 1, false,tz);
				SpotDTO s5 = new SpotDTO(5, "Quilombo", null, z1, null, null, 1, false,tz);
				SpotDTO s6 = new SpotDTO(6, "chilena", null, z1, null, null, 1, false,tz);
				SpotDTO s7 = new SpotDTO(7, "Pipeline", null, z4, null, null, 1, false,tz);
				SpotDTO s8 = new SpotDTO(8, "Kakita", null, z5, null, null, 1, false,tz);
				SpotDTO s9 = new SpotDTO(9, "Fortaleza", null, z5, null, null, 1, false,tz);
				
				List<SpotDTO> l1 = new ArrayList<SpotDTO>();
				l1.add(s1);
				l1.add(s2);
				l1.add(s3);
				
				List<SpotDTO> l2 = new ArrayList<SpotDTO>();
				l2.add(s4);
				l2.add(s5);
				
				List<SpotDTO> l3 = new ArrayList<SpotDTO>();
				l3.add(s7);
				l3.add(s8);
				l3.add(s9);
				l3.add(s1);
				
				List<SpotDTO> l4 = new ArrayList<SpotDTO>();
				l4.add(s6);
				l4.add(s3);
				l4.add(s5);
				l4.add(s8);
				l4.add(s7);
				
				ComparationDTO c1 = new ComparationDTO();c1.setUserId(1);c1.setName("comparacion 1");c1.setId(1);c1.setSpots(l1);c1.setDescription("El sistema consiste en una maquina de votar  que se encuentra en cada �cuarto oscuro�, y en una urna electr�nica que se encuentra en cada mesa fiscalizada por un presidente de mesa. En una configuraci�n t�pica del sistema, varias de estas unidades est�n dispersas en el lugar de votaci�n. Tambi�n existen centros de recepci�n de votos, que procesan los resultados de grupos de mesas y emiten los resultados globales del comicio.");
				ComparationDTO c2 = new ComparationDTO();c2.setUserId(1);c2.setName("comparacion 2");c2.setId(2);c2.setSpots(l2);c2.setDescription("El M�todo ADD (Architectural Driven Design) es un m�todo de aproximaci�n sistem�tica  para dise�ar sistemas. Est� basado en conocer tanto los requerimientos funcionales y de calidad y mediante una aproximaci�n recursiva comprobar que se satisfagan los distintos escenarios de calidad del sistema. ");
				ComparationDTO c3 = new ComparationDTO();c3.setUserId(1);c3.setName("comparacion 3");c3.setId(3);c3.setSpots(l3);c3.setDescription("El cliente desea modificar el sistema en tiempo de configuraci�n para poder variar la cantidad de urnas para soportar m�s votantes sin que el tiempo de recolecci�n aumente. El sistema permite la modificaci�n y el tiempo de recolecci�n no aumenta en m�s de 10 minutos.");
				ComparationDTO c4 = new ComparationDTO();c4.setUserId(1);c4.setName("comparacion 4");c4.setId(4);c4.setSpots(l4);c4.setDescription("En esta vista se puede observar la relaci�n que existe entre los m�dulos del subsistema Generador de Votos, donde los m�dulos Selector de Cargo y Selector de Lista son los que se encargan de interactuar, mediante la vista, con el votante para la elecci�n de los candidatos, etc. El Confirmador de Elecci�n es el encargado de informarles a los usuarios si desean confirmar la elecci�n recientemente realizada. Este adem�s le notifica al modulo Registrar Estado cual fue el ultimo estado de la votaci�n confirmado. El Data Manager se encarga de proveer todos los datos requeridos para la votaci�n tales como las listas, cargos, etc. El Escritor de Votos es el modulo encargado de registrar el voto (una vez finalizado el proceso) en el medio correspondiente. El modulo Cargar Data se encarga de agregar o modificar los datos de la votaci�n, ya sean listas, candidatos, cargos, etc.");
				
				result.add(c1);result.add(c2);result.add(c3);result.add(c4);
			}
			//SessionData.getInstance().setUserComparations(result);
			return result;
		}
		return null;
	}

	public Integer saveComparation(String name, String description, List<Integer> spotsIds) throws NeuralitoException {
		return 1;
	}

}
