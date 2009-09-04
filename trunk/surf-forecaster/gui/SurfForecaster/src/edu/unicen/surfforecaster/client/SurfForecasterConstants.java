package edu.unicen.surfforecaster.client;

import com.google.gwt.i18n.client.Constants;

public interface SurfForecasterConstants extends Constants {
	
	//Commons
	@DefaultStringValue("Iniciar sesi\u00F3n")
	String signIn();
	
	//UserStatePanel
	@DefaultStringValue("Idioma")
	String language();
	
	@DefaultStringValue("Ayuda")
	String help();
	
	@DefaultStringValue("Cerrar sesi\u00F3n")
	String signOut();
	
	@DefaultStringValue("Configuraci\u00F3n")
	String settings();
	
	//LoginBox
	@DefaultStringValue("Usuario y/o contrase\u00F1a inv\u00E1lido/a")
	String invalidUserPass();
	
	@DefaultStringValue("Usuario")
	String userName();
	
	@DefaultStringValue("Contrase\u00F1a")
	String password();
	
	@DefaultStringValue("Registrarse")
	String register();
	
	@DefaultStringValue("Espere por favor")
	String waitPlease();
	
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
