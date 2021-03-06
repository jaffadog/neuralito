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
	 * � = \u00E1
	 * � = \u00E9
	 * � = \u00ED
	 * � = \u00F3
	 * � = \u00FA
	 * � = \u00F1
	 */
	
	/**
	 * ErrorMessages
	 */
	@DefaultStringValue("Los cambios se guardaron correctamente.")
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
	
	@DefaultStringValue("Usted no tiene permisos suficientes para ejecutar esta operaci\u00F3n.")
	String USER_ROLE_INSUFFICIENT();
	
	@DefaultStringValue("Error al intentar eliminar la comparaci\u00F3n, por favor intente nuevamente en unos minutos.")
	String ERROR_DELETING_COMPARATION();
	
	@DefaultStringValue("Error al intentar guardar la comparaci\u00F3n, por favor intente nuevamente en unos minutos.")
	String ERROR_SAVING_COMPARATION();
	
	@DefaultStringValue("La ola seleccionada no tiene ningun pronosticador definido.")
	String NOT_SPOT_FORECASTERS_DEFINED();
	
	@DefaultStringValue("Usted no tiene permisos para modificar o eliminar esta ola.")
	String USER_NOT_SPOT_OWNER();
	
	@DefaultStringValue("Usted no tiene permisos para modificar o eliminar esta comparaci\u00F3n.")
	String USER_NOT_COMPARISON_OWNER();
	
	@DefaultStringValue("La cantidad m\u00EDnima de observaciones (con fechas diferentes) que debe ingresar son 50. La " +
			"cantidad recomendada para entrenar un pronosticador es de 100. El pronosticador no fue entrenado, por favor " +
			"intente entrenarlo nuevamente con 50 o m\u00E1s observaciones desde la secci\u00F3n \"Mis olas\".")
	String NOT_ENOUGH_WEKA_INSTANCES();
	
	@DefaultStringValue("Ha ocurrido un error mientras se leia el archivo de observaciones visuales, por favor intente nuevamente un unos minutos " +
			"desde la secci\u00F3n \"Mis olas\".")
	String ERROR_PARSING_OBSERVATIONS_FILE();
	
	@DefaultStringValue("El tipo o formato de archivo de observaciones visuales no es correcto, revise el link \"Formato del archivo\" debajo del mapa.")
	String CONTENT_TYPE_NOT_SUPPORTED();
	
	@DefaultStringValue("Disculpe, pero Surf-Forecaster no dispone de suficiente informaci\u00F3n historica de las olas en esa ubicaci\u00F3n para entrenar un " +
			"pronosticador especializado.")
	String NOT_ENOUGH_WW3_HISTORY();
	
	/**
	 * AnonymousMessagePanel
	 */
	@DefaultStringValue("Esta usando el sistema en forma anonima.")
	String anonymousMessagePart1();
	
	@DefaultStringValue("GRATIS para poder aprovechar todos los servicios de Surf-Forecaster.")
	String anonymousMessagePart2();
	
	@DefaultStringValue("Reg\u00EDstrese")
	String register2();
	
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
	
	@DefaultStringValue("Ver pron\u00F3stico")
	String viewForecast();
	
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
	
	@DefaultStringValue("Aceptar")
	String accept();
	
	@DefaultStringValue("Cancelar")
	String cancel();
	
	@DefaultStringValue("Borrar")
	String delete();
	
	@DefaultStringValue("Debe elegir entre dos y cinco olas para realizar una comparaci\u00F3n.")
	String twoToFiveSpotsToMakeComparation();
	
	/**
	 * ComparationCreatorPanel
	 */
	@DefaultStringValue("Comparador de olas")
	String spotComparator();
	
	@DefaultStringValue("Olas a comparar")
	String spotsToCompare();
	
	@DefaultStringValue("Primero")
	String first();
	
	@DefaultStringValue("Bajar")
	String down();
	
	@DefaultStringValue("Subir")
	String up();
	
	@DefaultStringValue("Ultimo")
	String last();
	
	@DefaultStringValue("Comparar")
	String compare();
	
	@DefaultStringValue("Confirma que desea eliminar esta comparaci\u00F3n?")
	String askForDeleteComp();
	
	@DefaultStringValue("Debe seleccionar una ola para realizar esta acci\u00F3n.")
	String mustSelectSpot();
	
	@DefaultStringValue("Debe seleccionar al menos una ola de la lista de la izquierda para realizar esta acci\u00F3n.")
	String mustSelectAtLeastOneSpotFromLeftList();
	
	@DefaultStringValue("Debe seleccionar al menos una ola de la lista de la derecha para realizar esta acci\u00F3n.")
	String mustSelectAtLeastOneSpotFromRightList();
	
	@DefaultStringValue("Debe elegir entre dos y cinco olas para grabar una comparaci\u00F3n.")
	String twoToFiveSpotsToSaveComparation();
	
	@DefaultStringValue("Guardar...")
	String showSave();
	
	@DefaultStringValue("Mis comparaciones")
	String myComparations();
	
	@DefaultStringValue("Elija una comparaci\u00F3n")
	String chooseComparation();
	
	@DefaultStringValue("Guardar comparaci\u00F3n")
	String saveComparation();
	
	@DefaultStringValue("Nombre de comparaci\u00F3n")
	String comparationName();
	
	@DefaultStringValue("Descripci\u00F3n")
	String description();
	
	@DefaultStringValue("Ya tiene grabada una comparacion con ese nombre. Desea sobreescribirla?")
	String askForAnotherName();
	
	@DefaultStringValue("El campo Nombre de comparaci\u00F3n es obligatorio")
	String mandatoryFieldName();
	
	@DefaultStringValue("Grabar la comparaci\u00F3n")
	String saveTip();
	
	@DefaultStringValue("Utilice el comparador de olas para realizar comparaciones de entre dos y cinco de sus olas preferidas. Si usted es un usuario registrado " +
			"podr\u00E1 incluso guardar sus comparaciones para visualizarlas cuando lo desee.")
	String compSectionDescription();
	
	@DefaultStringValue("Guardando la comparaci\u00F3n...")
	String savingComparation();
	
	@DefaultStringValue("Eliminando la comparaci\u00F3n...")
	String deletingComparation();
	
	/**
	 * ComparationViewerPanel
	 */
	@DefaultStringValue("Seleccione el pronosticador de cada ola")
	String chooseForecasters();
	
	@DefaultStringValue("Actualizar")
	String refresh();
	
	@DefaultStringValue("Volver")
	String back();
	
	@DefaultStringValue("Tabla de pronosticos detallados")
	String detailedForecastsTable();
	
	@DefaultStringValue("Volver a la pantalla de definici\u00F3n de comparaciones")
	String compBackTip();
	
	@DefaultStringValue("Refrescar la comparaci\u00F3n con los pronosticadores seleccionados")
	String refreshTip();
	
	@DefaultStringValue("Altura de olas en las pr\u00F3ximas horas")
	String waveHeightNextHours();
	
	@DefaultStringValue("El siguiente gr\u00E1fico de barras muestra la altura de las distintas olas comparadas durante las pr\u00F3ximas horas del d\u00EDa. " +
			"Los datos de cada ola son pronosticados con el pronosticador seleccionado previamente.")
	String waveHeightNextHoursDescr();
	
	@DefaultStringValue("Gr\u00E1fico animado de altura de olas")
	String waveHeightAllForecast();
	
	@DefaultStringValue("El siguiente gr\u00E1fico animado permite visualizar como ir\u00E1n variando las alturas de las olas comparadas desde este momento hasta " +
			"la semana pr\u00F3xima. Los datos de cada ola son pronosticados con el pronosticador seleccionado previamente.")
	String waveHeightAllForecastDescr();
	
	@DefaultStringValue("Cargando pron\u00F3sticos detallados...")
	String loadingDetailedForecasts();
	
	@DefaultStringValue("Cargando gr\u00E1fico...")
	String loadingChart();
	
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
	
	@DefaultStringValue("WW3GridPoint")
	String justWW3GridPoint();
	
	@DefaultStringValue("Lat")
	String lat_abbr();
	
	@DefaultStringValue("Lon")
	String lon_abbr();
	
	/**
	 * ForecastPanel
	 */
	@DefaultStringValue("Cargando \u00FAltimos pron\u00F3sticos de la ola...")
	String loadingSpotForecast();
	
	/**
	 * ForecastTabPanel
	 */
	@DefaultStringValue("Descripci\u00F3n de ola")
	String spotDescription();
	
	@DefaultStringValue("Comparar olas")
	String spotComparer();
	
	@DefaultStringValue("Nueva ola")
	String newSpot();
	
	@DefaultStringValue("Mis olas")
	String mySpots();
	
	/**
	 * LinksLocalizationPanel
	 */
	@DefaultStringValue("Elegir area")
	String chooseArea();
	
	@DefaultStringValue("Elegir country")
	String chooseCountry();
	
	@DefaultStringValue("Elegir zona")
	String chooseZone();
	
	@DefaultStringValue("Elegir ola")
	String chooseSpot();
	
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
	@DefaultStringValue("Clickee en el mapa para ubicar geogr\u00E1ficamente la ola que desea pronosticar. Luego seleccione uno de los pronosticadores(WW3GridPoint) " +
			"que aparecen alrededor de la ola seleccionada.")
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
	
	@DefaultStringValue("Todav\u00EDa no a creado ninguna ola.")
	String noneSpotsCreated();
	
	@DefaultStringValue("Cargando informaci\u00F3n de la ola...")
	String loadingSpotData();
	
	@DefaultStringValue("Eliminando la ola...")
	String deletingSpot();
	
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
	
	@DefaultStringValue("Entrenar un pronosticador especializado (Opcional)")
	String trainClassifier();
	
	@DefaultStringValue("Observaciones visuales")
	String visualObservations();
	
	@DefaultStringValue("Formato del archivo")
	String observationsFormatSample();
	
	@DefaultStringValue("Horas de luz solar")
	String dayLightTime();
	
	@DefaultStringValue("De")
	String from();
	
	@DefaultStringValue("A")
	String to();
	
	@DefaultStringValue("m")
	String minutes_abbr();
	
	@DefaultStringValue("Elegir una zona")
	String chooseAZone();
	
	@DefaultStringValue("Crear una zona")
	String createAZone();
	
	@DefaultStringValue("Reemplazar observaciones")
	String replaceObservations();
	
	@DefaultStringValue("Agregar observaciones")
	String appendObservations();
	
	@DefaultStringValue("Esta secci\u00F3n le permite registrar una ola en cualquier lugar del mundo para que pueda empezar a recibir predicciones del pronosticador(WW3GridPoint)" +
			" m\u00E1s cercano a dicha ola. Adem\u00E1s, si dispone de algun historial de observaciones costeras diarias con la altura m\u00E1xima que alcanzaron las olas en esa ubicaci\u00F3n " +
			"(una observaci\u00F3n diaria de altura m\u00E1xima), podr\u00E1 entrenar un pronosticador especializado ubicado exactamente en las coordenadas de la ola registrada.")
	String registerNewSpotDesc();
	
	@DefaultStringValue("Excelente")
	String excelent();
	
	@DefaultStringValue("Muy bueno")
	String veryGood();
	
	@DefaultStringValue("Bueno")
	String good();
	
	@DefaultStringValue("Malo")
	String bad();
	
	@DefaultStringValue("Muy malo")
	String veryBad();
	
	@DefaultStringValue("En este momento se esta entrenando un pronosticador especializado, los resultados le ser\u00E1n informados una vez finalizado el entrenamiento. Esta operaci\u00F3n puede demorar unos minutos, sin embargo usted puede continuar utilizando el sistema.")
	String trainingClassifier();
	
	@DefaultStringValue("Guardando nueva ola...")
	String savingNewSpot();
	
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
	
	@DefaultStringValue("Por que registrarse?")
	String whyRegister();
	
	@DefaultStringValue("Puedes utilizar el sistema en forma an\u00F3nima sin tener una cuenta de usuario, pero de esta manera solo tendr\u00E1s acceso a olas " +
			"creadas por t\u00ED o creadas y declaradas como PUBLICAS por otros usuarios que tienen la intenci\u00F3n de compartir esas predicciones. Como usuario an\u00F3nimo " +
			"podr\u00E1s ver los pron\u00F3sticos y descripciones de olas PUBLICAS, como tambi\u00E9n realizar comparaciones entre dichas olas. Registrarse en el sistema " +
			"lleva apenas un instante, llenando un simple formulario el cual no requiere datos personales ni privados. Como usuario registrado podr\u00E1s " +
			"dar de alta tus olas preferidas en el sistema, de cualquier parte del mundo, para poder tener acceso a un pron\u00F3stico detallado cercano a esa " +
			"ubicaci\u00F3n, adem\u00E1s si dispones de informaci\u00F3n hist\u00F3rica con la altura de las olas que arribaron a esa costa, podr\u00E1s entrenar un pronosticador " +
			"especializado para obtener una predicci\u00F3n aun m\u00E1s certera ex\u00E1ctamente donde rompe esa ola. Por otra parte como usuario registrado podr\u00E1s " +
			"generar tus propias comparaciones entre olas y guardarlas en el sistema, para tenerlas disponibles en cualquier momento, evitando el trabajo " +
			"de tener que generarlas cont\u00EDnuamente. Registrate GRATIS ahora!.")
	String whyRegisterDesc();
	
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
	 * VisualObservationSampleBox
	 */
	@DefaultStringValue("Formato del archivo de observaciones visuales")
	String observationsFileFormat();

	@DefaultStringValue("Si desea obtener un pron\u00F3stico detallado ex\u00E1ctamente en la ubicaci\u00F3n de la ola marcada en el mapa, debe proveer al sistema de un archivo " +
			"indexado por fecha que contenga en cada l\u00EDnea la altura de la ola (METROS) m\u00E1s grande observada en dicha ubicaci\u00F3n en el d\u00EDa especificado, como se puede ver " +
			"en la imagen debajo, donde cada l\u00EDnea del archivo contiene cuatro columnas separadas por un ESPACIO que representan a\u00F1o, mes, d\u00EDa y altura de ola (METROS) " +
			"respectivamente. Si la altura de ola contiene valores decimales como se ve en la imagen, utilizar el punto (.) como separador decimal. Use archivos de texto con formato TXT.")
	String observationsFileFormatDesc();
	
	
	/**
	 * WekaEvaluationResultsPanel
	 */
	@DefaultStringValue("Esta ola ha sido entrenada con los siguientes pronosticadores")
	String wekaEvaluationPanelTitle();
	
	@DefaultStringValue("Correlaci\u00F3n")
	String correlation();
	
	@DefaultStringValue("Error absoluto medio")
	String meanAbsoluteError();
	
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
