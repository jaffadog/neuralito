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
	
	@DefaultStringValue("Pron\u00F3stico")
	String forecast();
	
	@DefaultStringValue("Area")
	String area();
	
	@DefaultStringValue("Pa\u00EDs")
	String country();
	
	@DefaultStringValue("Ola")
	String spot();
	
	@DefaultStringValue("Zona")
	String zone();
	
	@DefaultStringValue("Grabar")
	String save();
	
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
	@DefaultStringValue("Espere por favor")
	String waitPlease();
	
	//LocalizationPanel
	@DefaultStringValue("Seleccione una ola")
	String selectSpot();
	
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
	
	//NewSpotPanel
	@DefaultStringValue("Registre una nueva ola")
	String newSpotSectionTitle();
	
	@DefaultStringValue("Zona horaria")
	String timeZone();
	
	@DefaultStringValue("Localizaci\u00F3n geogr\u00E1fica")
	String geographicLocalization();
	
	@DefaultStringValue("Entrenar")
	String train();
	
	@DefaultStringValue("P\u00FAblica")
	String public_();
	
	@DefaultStringValue("Privada")
	String private_();
	
	@DefaultStringValue("Visibilidad de ola")
	String spotVisibility();
	
	//ErrorMessages
	@DefaultStringValue("Los cambios se guardaron exitosamente !!!")
	String CHANGES_SAVED_SUCCESFULLY();
	
	@DefaultStringValue("Los campos marcados con (*) son obligatorios.")
	String MANDATORY_FIELDS();
	
	@DefaultStringValue("Usuario y/o contrase\u00F1a inv\u00E1lido/a")
	String INVALID_USER_OR_PASS();
	
	@DefaultStringValue("Fallo la conexion con la base de datos, intentelo nuevamente mas tarde...")
	String DATABASE_ERROR();
	
	@DefaultStringValue("El nombre de usuario especificado ya a sido utilizado por otra persona, por favor ingrese uno distinto.")
	String DUPLICATED_USER_USERNAME();
	
	@DefaultStringValue("El email especificado ya a sido utilizado por otra persona, por favor ingrese uno distinto.")
	String DUPLICATED_USER_EMAIL();
	
	@DefaultStringValue("El password no puede ser vacio o igual al nombre de usuario, por favor ingrese uno distinto.")
	String INVALID_PASSWORD();
	
	
}
