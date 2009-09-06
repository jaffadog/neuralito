package edu.unicen.surfforecaster.gwt.client;

import com.google.gwt.i18n.client.Constants;

public interface SurfForecasterConstants extends Constants {
	
	//Commons
	@DefaultStringValue("Iniciar sesi\u00F3n")
	String signIn();
	
	@DefaultStringValue("Volver")
	String goBack();
	
	@DefaultStringValue("Usuario")
	String userName();
	
	@DefaultStringValue("Contrase\u00F1a")
	String password();
	
	@DefaultStringValue("Registrarse")
	String register();
	
	@DefaultStringValue("Los campos marcados con (*) son obligatorios.")
	String starFields();
	
	@DefaultStringValue("Pron\u00F3stico")
	String forecast();
	
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
	
	@DefaultStringValue("Espere por favor")
	String waitPlease();
	
	//LocalizationPanel
	@DefaultStringValue("Seleccione una ola")
	String selectSpot();
	
	@DefaultStringValue("Area")
	String area();
	
	@DefaultStringValue("Pa\u00EDs")
	String country();
	
	@DefaultStringValue("Ola")
	String spot();
	
	@DefaultStringValue("Zona")
	String zone();
	
	//RegisterNewUserPanel
	@DefaultStringValue("Registrarse como nuevo usuario")
	String registerSectionTitle();
	
	@DefaultStringValue("Nombre")
	String name();
	
	@DefaultStringValue("Apellido")
	String lastName();
	
	@DefaultStringValue("Correo electr\u00F3nico")
	String email();
	
	@DefaultStringValue("Administrador")
	String administrator();
	
	//ForecastTabPanel
	@DefaultStringValue("Descripci\u00F3n de ola")
	String spotDescription();
	
	@DefaultStringValue("Comparar olas")
	String spotComparator();
	
	@DefaultStringValue("Nueva ola")
	String newSpot();
	
}
