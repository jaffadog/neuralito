package edu.unicen.surfforecaster.server.services;

import edu.unicen.surfforecaster.common.services.MyServiceI;
import edu.unicen.surfforecaster.common.services.dto.HelloDTO;

public class MyServiceImplementation implements MyServiceI {

	@Override
	public HelloDTO sayHello() {
		// TODO Auto-generated method stub
		HelloDTO dto = new HelloDTO();
		dto.setG("ww");
		return dto;
	}

}
