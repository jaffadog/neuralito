package edu.unicen.surfforecaster.gwt.client;

import com.google.gwt.i18n.client.Constants;
/**
 * This interface set translations GWT methods organizated by panel name.
 * Mantain this file ordered alphabeticaly by panel class name 
 * @author MAXI
 *
 */
public interface SurfForecasterConstants extends Constants {
	
	/**
	 * Unicode character
	 * 
	 * á = \u00E1
	 * é = \u00E9
	 * í = \u00ED
	 * ó = \u00F3
	 * ú = \u00FA
	 * ñ = \u00F1
	 */
	
	/**
	 * ErrorMessages
	 */
	@DefaultStringValue("Los cambios se guardaron exitosamente !!!")
	String CHANGES_SAVED_SUCCESFULLY();
	
	@DefaultStringValue("Los campos marcados con (*) son obligatorios.")
	String MANDATORY_FIELDS();
	
	@DefaultStringValue("Usuario y/o contrase\u00F1a inv\u00E1lido/a.")
	String INVALID_LOGIN();
	
	@DefaultStringValue("La \u00FAltima sesi\u00F3n de usuario iniciada ha expirado. Desea iniciarla nuevamente?")
	String USER_SESSION_EXPIRED();
	
	@DefaultStringValue("El campo \u00E1rea es obligatorio.")
	String MANDATORY_AREA_VALUE();
	
	@DefaultStringValue("El campo pa\u00EDs es obligatorio.")
	String MANDATORY_COUNTRY_VALUE();
	
	@DefaultStringValue("Debe seleccionar una zona de la lista o crear una.")
	String MANDATORY_ZONE_NAME();
	
	@DefaultStringValue("El campo ola es obligatorio.")
	String MANDATORY_SPOT_NAME();
	
	@DefaultStringValue("Debe clickear en algun punto del mapa para poder configurar correctamente la longitud y latitud de la ola.")
	String MANDATORY_SPOT_LAT_LONG();
	
	@DefaultStringValue("Debe seleccionar alguna de las boyas que rodean a la playa de donde se obtendr\u00E1n pron\u00F3sticos.")
	String MANDATORY_BUOY_LAT_LONG();
	
	@DefaultStringValue("Fall\u00F3 la conexi\u00F3n con la base de datos, intentelo nuevamente m\u00E1s tarde...")
	String DATABASE_ERROR();
	
	@DefaultStringValue("El nombre de usuario especificado ya a sido utilizado por otra persona, por favor ingrese uno distinto.")
	String DUPLICATED_USER_USERNAME();
	
	@DefaultStringValue("El email especificado ya a sido utilizado por otra persona, por favor ingrese uno distinto.")
	String DUPLICATED_USER_EMAIL();
	
	@DefaultStringValue("El password no puede ser vacio o igual al nombre de usuario, por favor ingrese uno distinto.")
	String INVALID_PASSWORD();
	
	@DefaultStringValue("Si desea entrenar un clasificador debe completar los horarios de luz solar donde se localiza la ola.")
	String MANDATORY_DAYLIGHT_TIME();
	
	@DefaultStringValue("La hora ingresada es inv\u00E1lida, debe ingresar un valor entre 0 y 23.")
	String INVALID_HOUR_VALUE();
	
	@DefaultStringValue("Los minutos ingresados son inv\u00E1lidos, debe ingresar un valor entre 0 y 59.")
	String INVALID_MINUTES_VALUE();
	
	@DefaultStringValue("Ocurri\u00F3 un error al intentar eliminar la ola seleccionada, por favor intente nuevamente en unos minutos.")
	String ERROR_DELETING_SPOT();
	
	/**
	 * Commons translates for more than one panel
	 */
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
	
	@DefaultStringValue("Viento")
	String wind();
	
	@DefaultStringValue("Dir. viento")
	String wind_direction();
	
	@DefaultStringValue("Dir. ola")
	String wave_direction();
	
	@DefaultStringValue("Altura ola")
	String wave_height();
	
	@DefaultStringValue("Per\u00EDodo")
	String wave_period();
	
	@DefaultStringValue("Vel. viento")
	String wind_speed();
	
	@DefaultStringValue("mts.")
	String meters_abbr();
	
	@DefaultStringValue("km/h")
	String kilometers_per_hour_abbr();
	
	@DefaultStringValue("seg.")
	String seconds_abbr();
	
	@DefaultStringValue("grad.")
	String degrees_abbr();
	
	@DefaultStringValue("m/s")
	String meters_per_second_abbr();
	
	@DefaultStringValue("pi\u00E9s")
	String feets_abbr();
	
	@DefaultStringValue("mph")
	String miles_per_hour_abbr();
	
	@DefaultStringValue("nudos")
	String knots();
	
	@DefaultStringValue("N")
	String north();
	
	@DefaultStringValue("S")
	String south();
	
	@DefaultStringValue("E")
	String east();
	
	@DefaultStringValue("O")
	String west();
	
	@DefaultStringValue("NE")
	String northeast();
	
	@DefaultStringValue("NO")
	String northwest();
	
	@DefaultStringValue("NNE")
	String north_northeast();
	
	@DefaultStringValue("NNO")
	String north_northwest();
	
	@DefaultStringValue("SE")
	String southeast();
	
	@DefaultStringValue("SO")
	String southwest();
	
	@DefaultStringValue("SSE")
	String south_southeast();
	
	@DefaultStringValue("SSO")
	String south_southwest();
	
	@DefaultStringValue("ENE")
	String east_northeast();
	
	@DefaultStringValue("ESE")
	String east_southeast();
	
	@DefaultStringValue("ONO")
	String west_northwest();
	
	@DefaultStringValue("OSO")
	String west_southwest();
	
	@DefaultStringValue("1")
	String num_1();
	
	@DefaultStringValue("2")
	String num_2();
	
	@DefaultStringValue("3")
	String num_3();
	
	@DefaultStringValue("4")
	String num_4();
	
	@DefaultStringValue("5")
	String num_5();
	
	@DefaultStringValue("6")
	String num_6();
	
	@DefaultStringValue("7")
	String num_7();
	
	@DefaultStringValue("8")
	String num_8();
	
	@DefaultStringValue("9")
	String num_9();
	
	@DefaultStringValue("0")
	String num_0();
	
	@DefaultStringValue("h")
	String hour_abbr();
	
	@DefaultStringValue("Cerrar")
	String close();
	
	/**
	 * CurrentForecastPanel
	 */
	@DefaultStringValue("No disponible")
	String not_available();
	
	@DefaultStringValue("Ahora")
	String now();
	
	@DefaultStringValue("horas")
	String hours();
	
	/**
	 * DetailedForecastWgStrategyB
	 */
	@DefaultStringValue("Continuaci\u00F3n")
	String continue_();
	
	/**
	 * ForecastTabPanel
	 */
	@DefaultStringValue("Descripci\u00F3n de ola")
	String spotDescription();
	
	@DefaultStringValue("Comparar olas")
	String spotComparator();
	
	@DefaultStringValue("Nueva ola")
	String newSpot();
	
	@DefaultStringValue("Mis olas")
	String mySpots();
	
	/**
	 * LocalizationPanel
	 */
	@DefaultStringValue("Seleccione una ola")
	String selectSpot();
	
	/**
	 * LoginBox
	 */
	@DefaultStringValue("Espere por favor")
	String waitPlease();
	
	/**
	 * MapPanel
	 */
	@DefaultStringValue("Clickee en el mapa para ubicar geogr\u00E1ficamente la ola que desea pronosticar.")
	String mapHelpTip();
	
	@DefaultStringValue("Latitud")
	String latitude();
	
	@DefaultStringValue("Longitud")
	String longitude();
	
	@DefaultStringValue("Pronosticador (WW3GridPoint)")
	String ww3GridPoint();
	
	@DefaultStringValue("Localizaci\u00F3n de la ola")
	String spotLocation();
	
	@DefaultStringValue("Pronosticador seleccionado")
	String selectedForecaster();
	
	@DefaultStringValue("No hay pronosticadores (WW3 Gridpoints) disponibles cerca de la ola seleccionada. Dicha ola no se puede pronosticar.")
	String notAvailableGridPoints();
	
	@DefaultStringValue("El servicio que recupera los pronosticadores (WW3 Gridpoints) cercanos a la ola seleccionada fallo. Intentelo nuevamente en unos minutos.")
	String getNearbyGridPointsServiceFailed();
	
	/**
	 * MySpotsPanel
	 */
	@DefaultStringValue("Editar")
	String edit();
	
	@DefaultStringValue("Borrar")
	String delete();
	
	@DefaultStringValue("Todav\u00EDa no a creado ninguna ola.")
	String noneSpotsCreated();
	
	/**
	 * NewSpotPanel
	 */
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
	
	@DefaultStringValue("Entrenar un clasificador (Opcional)")
	String trainClassifier();
	
	@DefaultStringValue("Observaciones visuales")
	String visualObservations();
	
	@DefaultStringValue("Horas de luz solar")
	String dayLightTime();
	
	@DefaultStringValue("De")
	String from();
	
	@DefaultStringValue("a")
	String to();
	
	@DefaultStringValue("m")
	String minutes_abbr();
	
	@DefaultStringValue("Elegir una zona")
	String chooseZone();
	
	@DefaultStringValue("Crear una zona")
	String createZone();
	
	/**
	 * RegisterNewUserPanel
	 */
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
	
	/**
	 * UserStatePanel
	 */
	@DefaultStringValue("Idioma")
	String language();
	
	@DefaultStringValue("Ayuda")
	String help();
	
	@DefaultStringValue("Cerrar sesi\u00F3n")
	String signOut();
	
	@DefaultStringValue("Configuraci\u00F3n")
	String settings();
	
	/**
	 * WgTableB and WgTableC
	 */
	@DefaultStringValue("Lun")
	String monday_abbr();
	
	@DefaultStringValue("Mar")
	String tuesday_abbr();
	
	@DefaultStringValue("Mie")
	String wednesday_abbr();
	
	@DefaultStringValue("Jue")
	String thursday_abbr();
	
	@DefaultStringValue("Vie")
	String friday_abbr();
	
	@DefaultStringValue("Sab")
	String saturday_abbr();
	
	@DefaultStringValue("Dom")
	String sunday_abbr();
}
