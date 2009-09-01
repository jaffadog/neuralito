package edu.unicen.surfforecaster.client;

import com.google.gwt.i18n.client.Constants;

public interface SurfForecasterConstants extends Constants {
	
	//UserStatePanel
	@DefaultStringValue("Iniciar sesi\u00F3n")
	String signIn();

	@DefaultStringValue("Idioma")
	String language();
	
	//LoginBox
	@DefaultStringValue("Usuario y/o contrase\u00F1a inv\u00E1lido/a")
	String invalidUserPass();
	
	@DefaultStringValue("Usuario")
	String userName();
	
	@DefaultStringValue("Contrase\u00F1a")
	String password();
	
	@DefaultStringValue("Registrarse")
	String register();
	
	//LocalizationPanel
	@DefaultStringValue("Seleccione una ola")
	String selectWave();
	
	@DefaultStringValue("Area")
	String area();
	
	@DefaultStringValue("Pa\u00EDs")
	String country();
	
	@DefaultStringValue("Ola")
	String wave();
	
	@DefaultStringValue("Zona")
	String zone();
	
	@DefaultStringValue("Pron\u00F3stico")
	String forecast();
}
