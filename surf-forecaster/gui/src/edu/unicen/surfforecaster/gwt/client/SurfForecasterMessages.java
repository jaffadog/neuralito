package edu.unicen.surfforecaster.gwt.client;

import com.google.gwt.i18n.client.Messages;

/**
 * This interface set translations GWT methods organizated by panel name.
 * Mantain this file ordered alphabeticaly by panel class name 
 * @author MAXI
 *
 */
public interface SurfForecasterMessages extends Messages {
	
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
	
	@DefaultMessage("El campo \"{0}\" es obligatorio.")
	String MANDATORY_FIELD(String fieldName);
	
	@DefaultMessage("El campo \"{0}\" solo puede contener letras, n\u00FAmeros, guiones bajos y espacios y no puede comenzar con un n\u00FAmero.")
	String ALPHANUM_SPACES_NOT_START_WITH_NUM(String fieldName);
	
	@DefaultMessage("El campo \"{0}\" solo puede contener letras, n\u00FAmeros, guiones bajos y medios y espacios y no puede comenzar con un n\u00FAmero.")
	String ALPHANUM_SPACES_DASHES_NOT_START_WITH_NUM(String fieldName);
	
	@DefaultMessage("El campo \"{0}\" solo puede contener letras, n\u00FAmeros, guiones bajos y no puede comenzar con un n\u00FAmero.")
	String ALPHANUM_NOT_SPACES_NOT_STARTS_WITH_NUM(String fieldName);
	
	@DefaultMessage("El campo \"{0}\" ingresado no tiene un formato v\u00E1lido.")
	String REGEX_EMAIL(String fieldName);
	
	/**
	 * MySpotsPanel
	 */
	
	@DefaultMessage("Confirma que desea eliminar la ola \"{0}\"?")
	String askForDeleteSpot(String spotName);
	
	/**
	 * newSpotPanel
	 */
	
	@DefaultMessage("El entrenamiento del pronosticador a finalizado. El resultado del entrenamiento en contraste con los datos suministrados fue: {0}")
	String wekaTrainingResults(String correlationExpresion);
}
