package edu.unicen.surfforecaster.gwt.server;

import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.unicen.surfforecaster.common.services.MyServiceI;
import edu.unicen.surfforecaster.common.services.dto.HelloDTO;
import edu.unicen.surfforecaster.gwt.client.Area;
import edu.unicen.surfforecaster.gwt.client.Country;
import edu.unicen.surfforecaster.gwt.client.ForecastCommonServices;
import edu.unicen.surfforecaster.gwt.client.SessionData;
import edu.unicen.surfforecaster.gwt.client.Spot;
import edu.unicen.surfforecaster.gwt.client.User;
import edu.unicen.surfforecaster.gwt.client.Zone;
import edu.unicen.surfforecaster.gwt.server.util.SpringGWTServlet;

public class ForecastCommonServicesImpl extends SpringGWTServlet implements ForecastCommonServices {

	private MyServiceI service;

	public String testService(){
		return "This is a service test";
	}
	
	/**
	 * Check if exists any user with the username and password passed as parameters
	 * @param String userName
	 * @param String password
	 * @return User user if exist any user with that values or Null
	 */
	public User login(String userName, String password){
		try {
			Thread.sleep(2500);
			if (userName.equals("admin") && password.equals("admin")){
				User user = new User();
				HttpSession session = this.getSession();
				session.setMaxInactiveInterval(120); //120seg
				session.setAttribute("gwtForecast-UserName", user.getUserName());
				session.setAttribute("gwtForecast-UserType", user.getType());
				return user;
			} else{
				return null;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Area getArea(){
		return new Area("AN","America del norte");
	}
	
	public Vector<Area> getAreas(){
		Area a1 = new Area("AN","America del norte");
		Area a2 = new Area("AS","America del sur");
		Area a3 = new Area("EU","Europa");
		Area a4 = new Area("OC","Oceania");
		
		Vector<Area> result = new Vector<Area>();
		result.add(a1);result.add(a2);result.add(a3);result.add(a4);
		return result;
	}
	
	public Vector<Country> getCountries(String area){
		Country a1 = new Country("AR","Argentina","AS");
		Country a2 = new Country("VE","Venezuela", "AS");
		Country a3 = new Country("HW","Hawaii", "AN");
		Country a4 = new Country("FR","Francia", "EU");
		
		Vector<Country> result = new Vector<Country>();
		result.add(a1);result.add(a2);result.add(a3);result.add(a4);
		
		Vector<Country> result2 = new Vector<Country>();
		Iterator<Country> i = result.iterator();
		while (i.hasNext()) {
			Country c = i.next();
			if (c.getParent().equals(area))
				result2.add(c);
		}
		
		return result2;
	}
	
	public Vector<Zone> getZones(String country){
		Zone a1 = new Zone("1","Mar del Plata Centro", "AR");
		Zone a2 = new Zone("2","OAHU North Shore", "HW");
		Zone a3 = new Zone("3","OAHU South Shore", "HW");
		Zone a4 = new Zone("4","Mar del Plata Sur", "AR");
		Zone a5 = new Zone("5","Marsella", "FR");
		
		Vector<Zone> result = new Vector<Zone>();
		result.add(a1);result.add(a2);result.add(a3);result.add(a4);result.add(a5);

		Vector<Zone> result2 = new Vector<Zone>();
		Iterator<Zone> i = result.iterator();
		while (i.hasNext()) {
			Zone c = i.next();
			if (c.getParent().equals(country))
				result2.add(c);
		}
		
		return result2;
	}
	
	public Vector<Spot> getSpots(String zone){
		Spot a1 = new Spot("1","Le pistole", "5");
		Spot a2 = new Spot("2","Margarita", "1");
		Spot a3 = new Spot("3","La paloma", "4");
		Spot a4 = new Spot("4","Chapa", "4");
		Spot a5 = new Spot("5","Pipeline", "2");
		Spot a6 = new Spot("6","Waimea", "2");
		Spot a7 = new Spot("7","Diamond head", "3");
		Spot a8 = new Spot("8","Ala moana", "3");
		Spot a9 = new Spot("9","La maquinita", "4");
		Spot a10 = new Spot("10","Mariano", "4");
		
		Vector<Spot> result = new Vector<Spot>();
		result.add(a1);result.add(a2);result.add(a3);result.add(a4);result.add(a5);result.add(a6);
		result.add(a7);result.add(a8);result.add(a9);result.add(a10);

		Vector<Spot> result2 = new Vector<Spot>();
		Iterator<Spot> i = result.iterator();
		while (i.hasNext()) {
			Spot c = i.next();
			if (c.getParent().equals(zone))
				result2.add(c);
		}
		
		return result2;
	}
	
	/**
	 * Return a sessionData object with all the values stored in the current session
	 * @return SessionData or null if not exists any session values
	 */
	public SessionData getSessionData() {
		HttpSession session = this.getSession();
		
		if ((String)session.getAttribute("gwtForecast-UserName") == null || ((String)session.getAttribute("gwtForecast-UserName")).equals("")) {
			System.out.println("the session is null or empty, this is the result after request for username: " + (String)session.getAttribute("gwtForecast-UserName"));
			return null;
		} else {
			
			SessionData sessionData = new SessionData();
			sessionData.setUserName(session.getAttribute("gwtForecast-UserName").toString());
			sessionData.setUserType(new Integer(session.getAttribute("gwtForecast-UserType").toString()));
			return sessionData;
		}
		
	}
	
	
	/**
	 * Returns the currents user session in the browser
	 * @return HttpSession session
	 */
	private HttpSession getSession(){
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		return session;
	}
	
	/**
	 * Removes all the session values stored in the current session
	 */
	public void closeSession() {
		HttpSession session = this.getSession();
		
		session.removeAttribute("gwtForecast-UserName");
		session.removeAttribute("gwtForecast-UserType");
	}
	/**
	 * @param service the service to set
	 */
	public void setService(MyServiceI service) {
		this.service = service;
	}

	/**
	 * @return the service
	 */
	public MyServiceI getService() {
		return service;
	}
	public HelloDTO test2(){
		return service.sayHello();
	};

}
