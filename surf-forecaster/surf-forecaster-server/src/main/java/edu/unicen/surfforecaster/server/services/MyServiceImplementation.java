package edu.unicen.surfforecaster.server.services;

import edu.unicen.surfforecaster.common.services.MyServiceI;
import edu.unicen.surfforecaster.common.services.dto.HelloDTO;
import edu.unicen.surfforecaster.server.dao.SpotDAO;


public class MyServiceImplementation implements MyServiceI {

	private SpotDAO surfSpotDAO;
	public SpotDAO getSurfSpotDAO() {
		return surfSpotDAO;
	}
	public void setSurfSpotDAO(SpotDAO surfSpotDAO) {
		this.surfSpotDAO = surfSpotDAO;
	}
	@Override
	public HelloDTO sayHello() {
		// TODO Auto-generated method stub
		HelloDTO dto = new HelloDTO();
		dto.setG("ww3");
		return dto;
	}

}
