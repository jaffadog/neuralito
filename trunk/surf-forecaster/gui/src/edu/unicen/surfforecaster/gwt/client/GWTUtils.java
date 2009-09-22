package edu.unicen.surfforecaster.gwt.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * GWTUtils is a set of helper classes to make it easier to work with GWT
 */
public final class GWTUtils {
	
	//WIDGETS SETTINGS
	public final static String APLICATION_WIDTH = "1000";
	public final static String PUSHBUTTON_HEIGHT = "20";
	
	//PATH TO IMAGES
	public final static String IMAGE_LOGO = "images/logo2.PNG";
	public final static String IMAGE_BLUE_BAR_LOADER = "images/blue-bar-loader.gif";
	public final static String IMAGE_BLUE_CIRCLE_LOADER = "images/blue-circle-loader.gif";
	public final static String IMAGE_ERROR_ICON = "images/Error.png";
	
	//Translators
	public static SurfForecasterConstants LOCALE_CONSTANTS;
	public static SurfForecasterMessages LOCALE_MESSAGES;
	
	//Valid history tokens
	public static Vector<String> VALID_HISTORY_TOKENS = new Vector<String>();
	public final static String DEFAULT_HISTORY_TOKEN = "forecastTab";
	
	//Timezones hash
	private static HashMap<String, Object> timeZones = null;
	
	
	/**
	 * 
	 * @return a hashMap with the timezones values.
	 */
	public static HashMap<String, Object> getTimeZones() {
		if (timeZones != null) {
			return timeZones;
		}
		else {
			timeZones = new HashMap<String, Object>();
			timeZones.put("[UTC-12] UTC-12", "-12");
			timeZones.put("[UTC-11] Pacific/Apia, WST", "-11");
			timeZones.put("[UTC-11] Pacific/Midway, SST", "-11");
			timeZones.put("[UTC-11] Pacific/Niue, NUT", "-11");
			timeZones.put("[UTC-11] Pacific/Pago_Pago, SST", "-11");
			timeZones.put("[UTC-11] Pacific/Samoa, SST", "-11");
			timeZones.put("[UTC-11] US/Samoa, SST", "-11");
			timeZones.put("[UTC-11] UTC-11", "-11");
			timeZones.put("[UTC-10] America/Adak, HAST (HADT)", "-10");
			timeZones.put("[UTC-10] America/Atka, HAST (HADT)", "-10");
			timeZones.put("[UTC-10] Pacific/Fakaofo, TKT", "-10");
			timeZones.put("[UTC-10] Pacific/Honolulu, HST", "-10");
			timeZones.put("[UTC-10] Pacific/Johnston, HST", "-10");
			timeZones.put("[UTC-10] Pacific/Rarotonga, CKT", "-10");
			timeZones.put("[UTC-10] Pacific/Tahiti, TAHT", "-10");
			timeZones.put("[UTC-10] US/Aleutian, HAST (HADT)", "-10");
			timeZones.put("[UTC-10] US/Hawaii, HST", "-10");
			timeZones.put("[UTC-10] UTC-10", "-10");
			timeZones.put("[UTC] Pacific/Marquesas, MART", "0");
			timeZones.put("[UTC-9] America/Anchorage, AKST (AKDT)", "-9");
			timeZones.put("[UTC-9] America/Juneau, AKST (AKDT)", "-9");
			timeZones.put("[UTC-9] America/Nome, AKST (AKDT)", "-9");
			timeZones.put("[UTC-9] America/Yakutat, AKST (AKDT)", "-9");
			timeZones.put("[UTC-9] Pacific/Gambier, GAMT", "-9");
			timeZones.put("[UTC-9] US/Alaska, AKST (AKDT)", "-9");
			timeZones.put("[UTC-9] UTC-9", "-9");
			timeZones.put("[UTC-8] America/Dawson, PST (PDT)", "-8");
			timeZones.put("[UTC-8] America/Ensenada, PST (PDT)", "-8");
			timeZones.put("[UTC-8] America/Los_Angeles, PST (PDT)", "-8");
			timeZones.put("[UTC-8] America/Tijuana, PST (PDT)", "-8");
			timeZones.put("[UTC-8] America/Vancouver, PST (PDT)", "-8");
			timeZones.put("[UTC-8] America/Whitehorse, PST (PDT)", "-8");
			timeZones.put("[UTC-8] Canada/Pacific, PST (PDT)", "-8");
			timeZones.put("[UTC-8] Canada/Yukon, PST (PDT)", "-8");
			timeZones.put("[UTC-8] Mexico/BajaNorte, PST (PDT)", "-8");
			timeZones.put("[UTC-8] Pacific/Pitcairn, PST", "-8");
			timeZones.put("[UTC-8] US/Pacific-New, PST (PDT)", "-8");
			timeZones.put("[UTC-8] US/Pacific, PST (PDT)", "-8");
			timeZones.put("[UTC-8] UTC-8", "-8");
			timeZones.put("[UTC-7] America/Boise, MST (MDT)", "-7");
			timeZones.put("[UTC-7] America/Cambridge_Bay, MST (MDT)", "-7");
			timeZones.put("[UTC-7] America/Chihuahua, MST (MDT)", "-7");
			timeZones.put("[UTC-7] America/Dawson_Creek, MST", "-7");
			timeZones.put("[UTC-7] America/Denver, MST (MDT)", "-7");
			timeZones.put("[UTC-7] America/Edmonton, MST (MDT)", "-7");
			timeZones.put("[UTC-7] America/Hermosillo, MST", "-7");
			timeZones.put("[UTC-7] America/Inuvik, MST (MDT)", "-7");
			timeZones.put("[UTC-7] America/Mazatlan, MST (MDT)", "-7");
			timeZones.put("[UTC-7] America/Phoenix, MST", "-7");
			timeZones.put("[UTC-7] America/Shiprock, MST (MDT)", "-7");
			timeZones.put("[UTC-7] America/Yellowknife, MST (MDT)", "-7");
			timeZones.put("[UTC-7] Canada/Mountain, MST (MDT)", "-7");
			timeZones.put("[UTC-7] Mexico/BajaSur, MST (MDT)", "-7");
			timeZones.put("[UTC-7] Navajo, MST (MDT)", "-7");
			timeZones.put("[UTC-7] US/Arizona, MST", "-7");
			timeZones.put("[UTC-7] US/Mountain, MST (MDT)", "-7");
			timeZones.put("[UTC-7] UTC-7", "-7");
			timeZones.put("[UTC-6] America/Belize, CST", "-6");
			timeZones.put("[UTC-6] America/Cancun, CST (CDT)", "-6");
			timeZones.put("[UTC-6] America/Chicago, CST (CDT)", "-6");
			timeZones.put("[UTC-6] America/Costa_Rica, CST", "-6");
			timeZones.put("[UTC-6] America/El_Salvador, CST", "-6");
			timeZones.put("[UTC-6] America/Guatemala, CST", "-6");
			timeZones.put("[UTC-6] America/Indiana/Knox, EST (CDT)", "-6");
			timeZones.put("[UTC-6] America/Indiana/Tell_City, CST (CDT)", "-6");
			timeZones.put("[UTC-6] America/Knox_IN, EST (CDT)", "-6");
			timeZones.put("[UTC-6] America/Managua, CST", "-6");
			timeZones.put("[UTC-6] America/Menominee, CST (CDT)", "-6");
			timeZones.put("[UTC-6] America/Merida, CST (CDT)", "-6");
			timeZones.put("[UTC-6] America/Mexico_City, CST (CDT)", "-6");
			timeZones.put("[UTC-6] America/Monterrey, CST (CDT)", "-6");
			timeZones.put("[UTC-6] America/North_Dakota/Center, CST (CDT)", "-6");
			timeZones.put("[UTC-6] America/North_Dakota/New_Salem, CST (CDT)", "-6");
			timeZones.put("[UTC-6] America/Rainy_River, CST (CDT)", "-6");
			timeZones.put("[UTC-6] America/Rankin_Inlet, EST (EDT)", "-6");
			timeZones.put("[UTC-6] America/Regina, CST", "-6");
			timeZones.put("[UTC-6] America/Swift_Current, CST", "-6");
			timeZones.put("[UTC-6] America/Tegucigalpa, CST ()", "-6");
			timeZones.put("[UTC-6] America/Winnipeg, CST (CDT)", "-6");
			timeZones.put("[UTC-6] Canada/Central, CST (CDT)", "-6");
			timeZones.put("[UTC-6] Canada/East-Saskatchewan, CST", "-6");
			timeZones.put("[UTC-6] Canada/Saskatchewan, CST", "-6");
			timeZones.put("[UTC-6] Chile/EasterIsland, EAST (EASST)", "-6");
			timeZones.put("[UTC-6] Mexico/General, CST (CDT)", "-6");
			timeZones.put("[UTC-6] Pacific/Easter, EAST (EASST)", "-6");
			timeZones.put("[UTC-6] Pacific/Galapagos, GALT", "-6");
			timeZones.put("[UTC-6] US/Central, CST (CDT)", "-6");
			timeZones.put("[UTC-6] US/Indiana-Starke, EST (CDT)", "-6");
			timeZones.put("[UTC-6] UTC-6", "-6");
			timeZones.put("[UTC-5] America/Atikokan, EST ()", "-5");
			timeZones.put("[UTC-5] America/Bogota, COT", "-5");
			timeZones.put("[UTC-5] America/Cayman, EST", "-5");
			timeZones.put("[UTC-5] America/Coral_Harbour, EST ()", "-5");
			timeZones.put("[UTC-5] America/Detroit, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Eirunepe, ACT", "-5");
			timeZones.put("[UTC-5] America/Fort_Wayne, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Grand_Turk, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Guayaquil, ECT", "-5");
			timeZones.put("[UTC-5] America/Havana, CST (CDT)", "-5");
			timeZones.put("[UTC-5] America/Indiana/Indianapolis, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Indiana/Marengo, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Indiana/Petersburg, CST (CDT)", "-5");
			timeZones.put("[UTC-5] America/Indianapolis, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Indiana/Vevay, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Indiana/Vincennes, CST (CDT)", "-5");
			timeZones.put("[UTC-5] America/Indiana/Winamac, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Iqaluit, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Jamaica, EST", "-5");
			timeZones.put("[UTC-5] America/Kentucky/Louisville, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Kentucky/Monticello, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Lima, PET", "-5");
			timeZones.put("[UTC-5] America/Louisville, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Montreal, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Nassau, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/New_York, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Nipigon, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Panama, EST", "-5");
			timeZones.put("[UTC-5] America/Pangnirtung, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Port-au-Prince, EST ()", "-5");
			timeZones.put("[UTC-5] America/Porto_Acre, ACT", "-5");
			timeZones.put("[UTC-5] America/Resolute, EST ()", "-5");
			timeZones.put("[UTC-5] America/Rio_Branco, ACT", "-5");
			timeZones.put("[UTC-5] America/Thunder_Bay, EST (EDT)", "-5");
			timeZones.put("[UTC-5] America/Toronto, EST (EDT)", "-5");
			timeZones.put("[UTC-5] Brazil/Acre, ACT", "-5");
			timeZones.put("[UTC-5] Canada/Eastern, EST (EDT)", "-5");
			timeZones.put("[UTC-5] Cuba, CST (CDT)", "-5");
			timeZones.put("[UTC-5] Jamaica, EST", "-5");
			timeZones.put("[UTC-5] US/Eastern, EST (EDT)", "-5");
			timeZones.put("[UTC-5] US/East-Indiana, EST (EDT)", "-5");
			timeZones.put("[UTC-5] US/Michigan, EST (EDT)", "-5");
			timeZones.put("[UTC-5] UTC-5", "-5");
			timeZones.put("[UTC] America/Caracas, VET", "0");
			timeZones.put("[UTC-4] America/Anguilla, AST", "-4");
			timeZones.put("[UTC-4] America/Antigua, AST", "-4");
			timeZones.put("[UTC-4] America/Aruba, AST", "-4");
			timeZones.put("[UTC-4] America/Asuncion, PYT (PYST)", "-4");
			timeZones.put("[UTC-4] America/Barbados, AST", "-4");
			timeZones.put("[UTC-4] America/Blanc-Sablon, AST ()", "-4");
			timeZones.put("[UTC-4] America/Boa_Vista, AMT", "-4");
			timeZones.put("[UTC-4] America/Campo_Grande, AMT (AMST)", "-4");
			timeZones.put("[UTC-4] America/Cuiaba, AMT (AMST)", "-4");
			timeZones.put("[UTC-4] America/Curacao, AST", "-4");
			timeZones.put("[UTC-4] America/Dominica, AST", "-4");
			timeZones.put("[UTC-4] America/Glace_Bay, AST (ADT)", "-4");
			timeZones.put("[UTC-4] America/Goose_Bay, AST (ADT)", "-4");
			timeZones.put("[UTC-4] America/Grenada, AST", "-4");
			timeZones.put("[UTC-4] America/Guadeloupe, AST", "-4");
			timeZones.put("[UTC-4] America/Guyana, GYT", "-4");
			timeZones.put("[UTC-4] America/Halifax, AST (ADT)", "-4");
			timeZones.put("[UTC-4] America/La_Paz, BOT", "-4");
			timeZones.put("[UTC-4] America/Manaus, AMT", "-4");
			timeZones.put("[UTC-4] America/Marigot, AST ()", "-4");
			timeZones.put("[UTC-4] America/Martinique, AST", "-4");
			timeZones.put("[UTC-4] America/Moncton, AST (ADT)", "-4");
			timeZones.put("[UTC-4] America/Montserrat, AST", "-4");
			timeZones.put("[UTC-4] America/Port_of_Spain, AST", "-4");
			timeZones.put("[UTC-4] America/Porto_Velho, AMT", "-4");
			timeZones.put("[UTC-4] America/Puerto_Rico, AST", "-4");
			timeZones.put("[UTC-4] America/Santiago, CLT (CLST)", "-4");
			timeZones.put("[UTC-4] America/Santo_Domingo, AST", "-4");
			timeZones.put("[UTC-4] America/St_Barthelemy, AST ()", "-4");
			timeZones.put("[UTC-4] America/St_Kitts, AST", "-4");
			timeZones.put("[UTC-4] America/St_Lucia, AST", "-4");
			timeZones.put("[UTC-4] America/St_Thomas, AST", "-4");
			timeZones.put("[UTC-4] America/St_Vincent, AST", "-4");
			timeZones.put("[UTC-4] America/Thule, AST (ADT)", "-4");
			timeZones.put("[UTC-4] America/Tortola, AST", "-4");
			timeZones.put("[UTC-4] America/Virgin, AST", "-4");
			timeZones.put("[UTC-4] Antarctica/Palmer, CLT (CLST)", "-4");
			timeZones.put("[UTC-4] Atlantic/Bermuda, AST (ADT)", "-4");
			timeZones.put("[UTC-4] Atlantic/Stanley, FKT (FKST)", "-4");
			timeZones.put("[UTC-4] Brazil/West, AMT", "-4");
			timeZones.put("[UTC-4] Canada/Atlantic, AST (ADT)", "-4");
			timeZones.put("[UTC-4] Chile/Continental, CLT (CLST)", "-4");
			timeZones.put("[UTC-4] UTC-4", "-4");
			timeZones.put("[UTC] America/St_Johns, NST (NDT)", "0");
			timeZones.put("[UTC] Canada/Newfoundland, NST (NDT)", "0");
			timeZones.put("[UTC-3] America/Araguaina, BRT ()", "-3");
			timeZones.put("[UTC-3] America/Argentina/Buenos_Aires, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Argentina/Catamarca, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Argentina/ComodRivadavia, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Argentina/Cordoba, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Argentina/Jujuy, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Argentina/La_Rioja, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Argentina/Mendoza, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Argentina/Rio_Gallegos, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Argentina/San_Juan, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Argentina/Tucuman, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Argentina/Ushuaia, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Bahia, BRT ()", "-3");
			timeZones.put("[UTC-3] America/Belem, BRT", "-3");
			timeZones.put("[UTC-3] America/Buenos_Aires, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Catamarca, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Cayenne, GFT", "-3");
			timeZones.put("[UTC-3] America/Cordoba, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Fortaleza, BRT ()", "-3");
			timeZones.put("[UTC-3] America/Godthab, WGT (WGST)", "-3");
			timeZones.put("[UTC-3] America/Jujuy, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Maceio, BRT ()", "-3");
			timeZones.put("[UTC-3] America/Mendoza, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Miquelon, PMST (PMDT)", "-3");
			timeZones.put("[UTC-3] America/Montevideo, UYT (UYST)", "-3");
			timeZones.put("[UTC-3] America/Paramaribo, SRT", "-3");
			timeZones.put("[UTC-3] America/Recife, BRT ()", "-3");
			timeZones.put("[UTC-3] America/Rosario, ART (ARST)", "-3");
			timeZones.put("[UTC-3] America/Sao_Paulo, BRT (BRST)", "-3");
			timeZones.put("[UTC-3] Antarctica/Rothera, ROTT ()", "-3");
			timeZones.put("[UTC-3] Brazil/East, BRT (BRST)", "-3");
			timeZones.put("[UTC-3] UTC-3", "-3");
			timeZones.put("[UTC-2] America/Noronha, FNT", "-2");
			timeZones.put("[UTC-2] Atlantic/South_Georgia, GST", "-2");
			timeZones.put("[UTC-2] Brazil/DeNoronha, FNT", "-2");
			timeZones.put("[UTC-2] UTC-2", "-2");
			timeZones.put("[UTC-1] America/Scoresbysund, EGT (EGST)", "-1");
			timeZones.put("[UTC-1] Atlantic/Azores, AZOT (AZOST)", "-1");
			timeZones.put("[UTC-1] Atlantic/Cape_Verde, CVT", "-1");
			timeZones.put("[UTC-1] UTC-1", "-1");
			timeZones.put("[UTC] Africa/Abidjan, GMT", "0");
			timeZones.put("[UTC] Africa/Accra, GMT", "0");
			timeZones.put("[UTC] Africa/Bamako, GMT", "0");
			timeZones.put("[UTC] Africa/Banjul, GMT", "0");
			timeZones.put("[UTC] Africa/Bissau, GMT", "0");
			timeZones.put("[UTC] Africa/Casablanca, WET", "0");
			timeZones.put("[UTC] Africa/Conakry, GMT", "0");
			timeZones.put("[UTC] Africa/Dakar, GMT", "0");
			timeZones.put("[UTC] Africa/El_Aaiun, WET", "0");
			timeZones.put("[UTC] Africa/Freetown, GMT", "0");
			timeZones.put("[UTC] Africa/Lome, GMT", "0");
			timeZones.put("[UTC] Africa/Monrovia, GMT", "0");
			timeZones.put("[UTC] Africa/Nouakchott, GMT", "0");
			timeZones.put("[UTC] Africa/Ouagadougou, GMT", "0");
			timeZones.put("[UTC] Africa/Sao_Tome, GMT", "0");
			timeZones.put("[UTC] Africa/Timbuktu, GMT", "0");
			timeZones.put("[UTC] America/Danmarkshavn, GMT", "0");
			timeZones.put("[UTC] Atlantic/Canary, WET (WEST)", "0");
			timeZones.put("[UTC] Atlantic/Faeroe, WET (WEST)", "0");
			timeZones.put("[UTC] Atlantic/Faroe, WET (WEST)", "0");
			timeZones.put("[UTC] Atlantic/Madeira, WET (WEST)", "0");
			timeZones.put("[UTC] Atlantic/Reykjavik, GMT", "0");
			timeZones.put("[UTC] Atlantic/St_Helena, GMT", "0");
			timeZones.put("[UTC] Europe/Belfast, GMT (BST)", "0");
			timeZones.put("[UTC] Europe/Dublin, GMT (IST)", "0");
			timeZones.put("[UTC] Europe/Guernsey, GMT (BST)", "0");
			timeZones.put("[UTC] Europe/Isle_of_Man, GMT (BST)", "0");
			timeZones.put("[UTC] Europe/Jersey, GMT (BST)", "0");
			timeZones.put("[UTC] Europe/Lisbon, WET (WEST)", "0");
			timeZones.put("[UTC] Europe/London, GMT (BST)", "0");
			timeZones.put("[UTC] GB-Eire, GMT (BST)", "0");
			timeZones.put("[UTC] Iceland, GMT", "0");
			timeZones.put("[UTC] Portugal, WET (WEST)", "0");
			timeZones.put("[UTC] UTC+0", "0");
			timeZones.put("[UTC+1] Africa/Algiers, CET", "+1");
			timeZones.put("[UTC+1] Africa/Bangui, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Brazzaville, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Ceuta, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Africa/Douala, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Kinshasa, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Lagos, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Libreville, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Luanda, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Malabo, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Ndjamena, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Niamey, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Porto-Novo, WAT", "+1");
			timeZones.put("[UTC+1] Africa/Tunis, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Africa/Windhoek, WAT (WAST)", "+1");
			timeZones.put("[UTC+1] Arctic/Longyearbyen, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Atlantic/Jan_Mayen, EGT (EGST)", "+1");
			timeZones.put("[UTC+1] Europe/Amsterdam, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Andorra, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Belgrade, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Berlin, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Bratislava, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Brussels, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Budapest, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Copenhagen, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Gibraltar, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Ljubljana, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Luxembourg, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Madrid, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Malta, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Monaco, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Oslo, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Paris, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Podgorica, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Prague, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Rome, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/San_Marino, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Sarajevo, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Skopje, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Stockholm, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Tirane, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Vaduz, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Vatican, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Vienna, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Warsaw, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Zagreb, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Europe/Zurich, CET (CEST)", "+1");
			timeZones.put("[UTC+1] Poland, CET (CEST)", "+1");
			timeZones.put("[UTC+1] UTC+1", "+1");
			timeZones.put("[UTC+2] Africa/Blantyre, CAT", "+2");
			timeZones.put("[UTC+2] Africa/Bujumbura, CAT", "+2");
			timeZones.put("[UTC+2] Africa/Cairo, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Africa/Gaborone, CAT", "+2");
			timeZones.put("[UTC+2] Africa/Harare, CAT", "+2");
			timeZones.put("[UTC+2] Africa/Johannesburg, SAST", "+2");
			timeZones.put("[UTC+2] Africa/Kigali, CAT", "+2");
			timeZones.put("[UTC+2] Africa/Lubumbashi, CAT", "+2");
			timeZones.put("[UTC+2] Africa/Lusaka, CAT", "+2");
			timeZones.put("[UTC+2] Africa/Maputo, CAT", "+2");
			timeZones.put("[UTC+2] Africa/Maseru, SAST", "+2");
			timeZones.put("[UTC+2] Africa/Mbabane, SAST", "+2");
			timeZones.put("[UTC+2] Africa/Tripoli, EET", "+2");
			timeZones.put("[UTC+2] Asia/Amman, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Asia/Beirut, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Asia/Damascus, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Asia/Gaza, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Asia/Istanbul, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Asia/Jerusalem, IST (IDT)", "+2");
			timeZones.put("[UTC+2] Asia/Nicosia, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Asia/Tel_Aviv, IST (IDT)", "+2");
			timeZones.put("[UTC+2] Egypt, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Athens, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Bucharest, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Chisinau, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Helsinki, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Istanbul, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Kaliningrad, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Kiev, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Mariehamn, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Minsk, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Nicosia, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Riga, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Simferopol, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Sofia, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Tallinn, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Tiraspol, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Uzhgorod, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Vilnius, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Europe/Zaporozhye, EET (EEST)", "+2");
			timeZones.put("[UTC+2] Israel, IST (IDT)", "+2");
			timeZones.put("[UTC+2] Libya, EET", "+2");
			timeZones.put("[UTC+2] Turkey, EET (EEST)", "+2");
			timeZones.put("[UTC+2] UTC+2", "+2");
			timeZones.put("[UTC+3] Africa/Addis_Ababa, EAT", "+3");
			timeZones.put("[UTC+3] Africa/Asmara, EAT ()", "+3");
			timeZones.put("[UTC+3] Africa/Asmera, EAT", "+3");
			timeZones.put("[UTC+3] Africa/Dar_es_Salaam, EAT", "+3");
			timeZones.put("[UTC+3] Africa/Djibouti, EAT", "+3");
			timeZones.put("[UTC+3] Africa/Kampala, EAT", "+3");
			timeZones.put("[UTC+3] Africa/Khartoum, EAT", "+3");
			timeZones.put("[UTC+3] Africa/Mogadishu, EAT", "+3");
			timeZones.put("[UTC+3] Africa/Nairobi, EAT", "+3");
			timeZones.put("[UTC+3] Antarctica/Syowa, SYOT", "+3");
			timeZones.put("[UTC+3] Asia/Aden, AST", "+3");
			timeZones.put("[UTC+3] Asia/Baghdad, AST (ADT)", "+3");
			timeZones.put("[UTC+3] Asia/Bahrain, AST", "+3");
			timeZones.put("[UTC+3] Asia/Kuwait, AST", "+3");
			timeZones.put("[UTC+3] Asia/Qatar, AST", "+3");
			timeZones.put("[UTC+3] Asia/Riyadh, AST", "+3");
			timeZones.put("[UTC+3] Europe/Moscow, MSK (MSD)", "+3");
			timeZones.put("[UTC+3] Europe/Volgograd, VOLT (VOLST)", "+3");
			timeZones.put("[UTC+3] Indian/Antananarivo, EAT", "+3");
			timeZones.put("[UTC+3] Indian/Comoro, EAT", "+3");
			timeZones.put("[UTC+3] Indian/Mayotte, EAT", "+3");
			timeZones.put("[UTC+3] UTC+3", "+3");
			timeZones.put("[UTC+3.5] Asia/Tehran, IRT (IRDT)", "+3.5");
			timeZones.put("[UTC+3.5] Iran, IRT (IRDT)", "+3.5");
			timeZones.put("[UTC+4] Asia/Baku, AZT (AZST)", "+4");
			timeZones.put("[UTC+4] Asia/Dubai, GST", "+4");
			timeZones.put("[UTC+4] Asia/Muscat, GST", "+4");
			timeZones.put("[UTC+4] Asia/Tbilisi, GET ()", "+4");
			timeZones.put("[UTC+4] Asia/Yerevan, AMT (AMST)", "+4");
			timeZones.put("[UTC+4] Europe/Samara, SAMT (SAMST)", "+4");
			timeZones.put("[UTC+4] Indian/Mahe, SCT", "+4");
			timeZones.put("[UTC+4] Indian/Mauritius, MUT", "+4");
			timeZones.put("[UTC+4] Indian/Reunion, RET", "+4");
			timeZones.put("[UTC+4] UTC+4", "+4");
			timeZones.put("[UTC+4.5] Asia/Kabul, AFT", "+4.5");
			timeZones.put("[UTC+5] Asia/Aqtau, AQTT ()", "+5");
			timeZones.put("[UTC+5] Asia/Aqtobe, AQTT ()", "+5");
			timeZones.put("[UTC+5] Asia/Ashgabat, TMT", "+5");
			timeZones.put("[UTC+5] Asia/Ashkhabad, TMT", "+5");
			timeZones.put("[UTC+5] Asia/Dushanbe, TJT", "+5");
			timeZones.put("[UTC+5] Asia/Karachi, PKT", "+5");
			timeZones.put("[UTC+5] Asia/Oral, ORAT ()", "+5");
			timeZones.put("[UTC+5] Asia/Samarkand, TMT", "+5");
			timeZones.put("[UTC+5] Asia/Tashkent, UZT", "+5");
			timeZones.put("[UTC+5] Asia/Yekaterinburg, YEKT (YEKST)", "+5");
			timeZones.put("[UTC+5] Indian/Kerguelen, TFT", "+5");
			timeZones.put("[UTC+5] Indian/Maldives, MVT", "+5");
			timeZones.put("[UTC+5] UTC+5", "+5");
			timeZones.put("[UTC+5.5] Asia/Calcutta, IST", "+5.5");
			timeZones.put("[UTC+5.5] Asia/Colombo, LKT", "+5.5");
			timeZones.put("[UTC+5.8] Asia/Katmandu, NPT", "+5.8");
			timeZones.put("[UTC+6] Antarctica/Mawson, MAWT", "+6");
			timeZones.put("[UTC+6] Antarctica/Vostok, VOST", "+6");
			timeZones.put("[UTC+6] Asia/Almaty, ALMT ()", "+6");
			timeZones.put("[UTC+6] Asia/Bishkek, KGT ()", "+6");
			timeZones.put("[UTC+6] Asia/Dacca, BDT", "+6");
			timeZones.put("[UTC+6] Asia/Dhaka, BDT", "+6");
			timeZones.put("[UTC+6] Asia/Novosibirsk, NOVT (NOVST)", "+6");
			timeZones.put("[UTC+6] Asia/Omsk, OMST (OMSST)", "+6");
			timeZones.put("[UTC+6] Asia/Qyzylorda, QYZT ()", "+6");
			timeZones.put("[UTC+6] Asia/Thimbu, BTT", "+6");
			timeZones.put("[UTC+6] Asia/Thimphu, BTT", "+6");
			timeZones.put("[UTC+6] Indian/Chagos, IOT", "+6");
			timeZones.put("[UTC+6] UTC+6", "+6");
			timeZones.put("[UTC+6.5] Asia/Rangoon, MMT", "+6.5");
			timeZones.put("[UTC+6.5] Indian/Cocos, CCT", "+6.5");
			timeZones.put("[UTC+7] Antarctica/Davis, DAVT", "+7");
			timeZones.put("[UTC+7] Asia/Bangkok, ICT", "+7");
			timeZones.put("[UTC+7] Asia/Hovd, HOVT ()", "+7");
			timeZones.put("[UTC+7] Asia/Jakarta, WIT", "+7");
			timeZones.put("[UTC+7] Asia/Krasnoyarsk, KRAT (KRAST)", "+7");
			timeZones.put("[UTC+7] Asia/Phnom_Penh, ICT", "+7");
			timeZones.put("[UTC+7] Asia/Pontianak, WIT", "+7");
			timeZones.put("[UTC+7] Asia/Saigon, ICT", "+7");
			timeZones.put("[UTC+7] Asia/Vientiane, ICT", "+7");
			timeZones.put("[UTC+7] Indian/Christmas, CXT", "+7");
			timeZones.put("[UTC+7] UTC+7", "+7");
			timeZones.put("[UTC+8] Antarctica/Casey, WST", "+8");
			timeZones.put("[UTC+8] Asia/Brunei, BNT", "+8");
			timeZones.put("[UTC+8] Asia/Chongqing, CST", "+8");
			timeZones.put("[UTC+8] Asia/Chungking, CST", "+8");
			timeZones.put("[UTC+8] Asia/Harbin, CST", "+8");
			timeZones.put("[UTC+8] Asia/Hong_Kong, HKT", "+8");
			timeZones.put("[UTC+8] Asia/Irkutsk, IRKT (IRKST)", "+8");
			timeZones.put("[UTC+8] Asia/Kashgar, CST", "+8");
			timeZones.put("[UTC+8] Asia/Kuala_Lumpur, MYT", "+8");
			timeZones.put("[UTC+8] Asia/Kuching, MYT", "+8");
			timeZones.put("[UTC+8] Asia/Macao, CST", "+8");
			timeZones.put("[UTC+8] Asia/Macau, CST ()", "+8");
			timeZones.put("[UTC+8] Asia/Makassar, CIT ()", "+8");
			timeZones.put("[UTC+8] Asia/Manila, PHT", "+8");
			timeZones.put("[UTC+8] Asia/Shanghai, CST", "+8");
			timeZones.put("[UTC+8] Asia/Singapore, SGT", "+8");
			timeZones.put("[UTC+8] Asia/Taipei, CST", "+8");
			timeZones.put("[UTC+8] Asia/Ujung_Pandang, CIT", "+8");
			timeZones.put("[UTC+8] Asia/Ulaanbaatar, ULAT ()", "+8");
			timeZones.put("[UTC+8] Asia/Ulan_Bator, ULAT ()", "+8");
			timeZones.put("[UTC+8] Asia/Urumqi, CST", "+8");
			timeZones.put("[UTC+8] Australia/Perth, WST (WST)", "+8");
			timeZones.put("[UTC+8] Australia/West, WST (WST)", "+8");
			timeZones.put("[UTC+8] Hongkong, HKT", "+8");
			timeZones.put("[UTC+8] Singapore, SGT", "+8");
			timeZones.put("[UTC+8] UTC+8", "+8");
			timeZones.put("[UTC+8.8] Australia/Eucla, CWST (CWST)", "+8.8");
			timeZones.put("[UTC+9] Asia/Choibalsan, CHOT ()", "+9");
			timeZones.put("[UTC+9] Asia/Dili, TPT", "+9");
			timeZones.put("[UTC+9] Asia/Jayapura, EIT", "+9");
			timeZones.put("[UTC+9] Asia/Pyongyang, KST", "+9");
			timeZones.put("[UTC+9] Asia/Seoul, KST", "+9");
			timeZones.put("[UTC+9] Asia/Tokyo, JST", "+9");
			timeZones.put("[UTC+9] Asia/Yakutsk, YAKT (YAKST)", "+9");
			timeZones.put("[UTC+9] Japan, JST", "+9");
			timeZones.put("[UTC+9] Pacific/Palau, PWT", "+9");
			timeZones.put("[UTC+9] UTC+9", "+9");
			timeZones.put("[UTC+9.5] Australia/Adelaide, CST (CST)", "+9.5");
			timeZones.put("[UTC+9.5] Australia/Broken_Hill, CST (CST)", "+9.5");
			timeZones.put("[UTC+9.5] Australia/Darwin, CST", "+9.5");
			timeZones.put("[UTC+9.5] Australia/North, CST", "+9.5");
			timeZones.put("[UTC+9.5] Australia/South, CST (CST)", "+9.5");
			timeZones.put("[UTC+9.5] Australia/Yancowinna, CST (CST)", "+9.5");
			timeZones.put("[UTC+10] Antarctica/DumontDUrville, DDUT", "+10");
			timeZones.put("[UTC+10] Asia/Sakhalin, SAKT (SAKST)", "+10");
			timeZones.put("[UTC+10] Asia/Vladivostok, VLAT (VLAST)", "+10");
			timeZones.put("[UTC+10] Australia/ACT, EST (EST)", "+10");
			timeZones.put("[UTC+10] Australia/Brisbane, EST", "+10");
			timeZones.put("[UTC+10] Australia/Canberra, EST (EST)", "+10");
			timeZones.put("[UTC+10] Australia/Currie, EST (EST)", "+10");
			timeZones.put("[UTC+10] Australia/Hobart, EST (EST)", "+10");
			timeZones.put("[UTC+10] Australia/Lindeman, EST", "+10");
			timeZones.put("[UTC+10] Australia/Melbourne, EST (EST)", "+10");
			timeZones.put("[UTC+10] Australia/NSW, EST (EST)", "+10");
			timeZones.put("[UTC+10] Australia/Queensland, EST", "+10");
			timeZones.put("[UTC+10] Australia/Sydney, EST (EST)", "+10");
			timeZones.put("[UTC+10] Australia/Tasmania, EST (EST)", "+10");
			timeZones.put("[UTC+10] Australia/Victoria, EST (EST)", "+10");
			timeZones.put("[UTC+10] Pacific/Guam, ChST", "+10");
			timeZones.put("[UTC+10] Pacific/Port_Moresby, PGT", "+10");
			timeZones.put("[UTC+10] Pacific/Saipan, ChST", "+10");
			timeZones.put("[UTC+10] Pacific/Truk, TRUT", "+10");
			timeZones.put("[UTC+10] Pacific/Yap, YAPT", "+10");
			timeZones.put("[UTC+10] UTC+10", "+10");
			timeZones.put("[UTC+10.5] Australia/LHI, LHST (LHST)", "+10.5");
			timeZones.put("[UTC+10.5] Australia/Lord_Howe, LHST (LHST)", "+10.5");
			timeZones.put("[UTC+11] Asia/Magadan, MAGT (MAGST)", "+11");
			timeZones.put("[UTC+11] Pacific/Efate, VUT", "+11");
			timeZones.put("[UTC+11] Pacific/Guadalcanal, SBT", "+11");
			timeZones.put("[UTC+11] Pacific/Kosrae, KOST", "+11");
			timeZones.put("[UTC+11] Pacific/Noumea, NCT", "+11");
			timeZones.put("[UTC+11] Pacific/Ponape, PONT", "+11");
			timeZones.put("[UTC+11] UTC+11", "+11");
			timeZones.put("[UTC+11.5] Pacific/Norfolk, NFT", "+11.5");
			timeZones.put("[UTC+12] Antarctica/McMurdo, NZST (NZDT)", "+12");
			timeZones.put("[UTC+12] Antarctica/South_Pole, NZST (NZDT)", "+12");
			timeZones.put("[UTC+12] Asia/Anadyr, ANAT (ANAST)", "+12");
			timeZones.put("[UTC+12] Asia/Kamchatka, PETT (PETST)", "+12");
			timeZones.put("[UTC+12] Pacific/Auckland, NZST (NZDT)", "+12");
			timeZones.put("[UTC+12] Pacific/Fiji, FJT", "+12");
			timeZones.put("[UTC+12] Pacific/Funafuti, TVT", "+12");
			timeZones.put("[UTC+12] Pacific/Kwajalein, MHT", "+12");
			timeZones.put("[UTC+12] Pacific/Majuro, MHT", "+12");
			timeZones.put("[UTC+12] Pacific/Nauru, NRT", "+12");
			timeZones.put("[UTC+12] Pacific/Tarawa, GILT", "+12");
			timeZones.put("[UTC+12] Pacific/Wake, WAKT", "+12");
			timeZones.put("[UTC+12] Pacific/Wallis, WFT", "+12");
			timeZones.put("[UTC+12] UTC+12", "+12");
			timeZones.put("[UTC+12.8] Pacific/Chatham, CHAST (CHADT)", "+12.8");
			timeZones.put("[UTC+13] Pacific/Enderbury, PHOT", "+13");
			timeZones.put("[UTC+13] Pacific/Tongatapu, TOT", "+13");
			timeZones.put("[UTC+13] UTC+13", "+13");
			timeZones.put("[UTC+14] Pacific/Kiritimati, LINT", "+14");
			timeZones.put("[UTC+14] UTC+14", "+14");
			
			//Add an item with and array of keys ordered by key name
			Object[] keys = timeZones.keySet().toArray();
			Arrays.sort(keys);
			timeZones.put("orderedKeys", keys);
			
			return timeZones;
		}
	}
	
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// native js helpers
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	public static native String getParamString() /*-{
	        return $wnd.location.search;
	}-*/;
	
	public static HashMap parseParamString(String string) {
	  String[] ray = string.substring(1, string.length()).split("&");
	  HashMap map = new HashMap();
	
	  for (int i = 0; i < ray.length; i++) {
	    GWT.log("ray[" + i + "]=" + ray[i], null);
	    String[] substrRay = ray[i].split("=");
	    map.put(substrRay[0], substrRay[1]);
	  }
	
	  return map;
	}
	
	/**
	 * Get the URL of the page, without an hash of query string.
	 * @param Boolean pullOffHash, indicates if cut or not the #reference or the url
	 * @param Boolean pullOffQueryString, indicates if cut or not the get parameters of the url
	 * @return String the location of the page
	 */
	public static native String getHostPageLocation(boolean pullOffHash, boolean pullOffQueryString) /*-{
	    var s = $doc.location.href;
		
	    var hashIndex = s.indexOf('#');
	    var queryIndex = s.indexOf('?');
	    var s1 = '';
	    var s2 = '';
	    if (hashIndex != -1 && queryIndex != -1) {
	    	if (hashIndex > queryIndex) {
	    		if (pullOffQueryString){
	    			s1 = s.substring(0, queryIndex);
	    			s2 = s.substring(hashIndex);
	    			s = s1.concat(s2);
	    			hashIndex = s.indexOf('#');
	    		}
	    		if (pullOffHash){
	    			s = s.substring(0, hashIndex);
	    		}
	    	} else {
	    		if (pullOffQueryString){
	    			s = s.substring(0,queryIndex);
	    		}
	    		if (pullOffHash){
	    			s1 = s.substring(0, hashIndex);
	    			s2 = s.substring(queryIndex);
	    			s = s1.concat(s2);
	    			queryIndex = s.indexOf('?');
	    		}
	    		if (!pullOffHash && !pullOffQueryString){
	    			s1 = s.substring(0, hashIndex);
	    			s2 = s.substring(queryIndex);
	    			var s3 = s.substring(hashIndex,queryIndex);
	    			s = s1.concat(s2,s3);
	    			
	    		}
	    		
	    	}
	    } else if (hashIndex != -1){
	    	if (pullOffHash)
	    		s = s.substring(0, hashIndex);
	    } else if (queryIndex != -1) {
	    	if (pullOffQueryString)
	    		s = s.substring(0, queryIndex);
	    }
		    
	    //alert(s);
	    
	    return s;
  	}-*/;
	
	public static String hostPageForLocale(String locale) {
		String s = getHostPageLocation(false, true);
		int hashIndex = s.indexOf("#");
		if ( hashIndex != -1) {
			String s1 = s.substring(0,hashIndex);
			String s2 = s.substring(hashIndex);
			return s1 + locale + s2;
		} 
		return s + locale;
		
	}
	
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// DOM and RootPanel helpers
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	
	/** This method removes the element identified by s from the browser's DOM */
	public static void removeElementFromDOM(String s) {
	  notempty(s);
	  com.google.gwt.user.client.Element loading = DOM.getElementById(s);
	  DOM.removeChild(RootPanel.getBodyElement(), loading);
	}
	
	public static void addWidgetToDOM(String s, Widget widget) {
	  notempty(s);
	  notnull(widget);
	  RootPanel.get(s).add(widget);
	}
	
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// assertions, validations
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	
	/** asserts that the given string is not null or empty */
	private static void notempty(String s) throws IllegalArgumentException {
	  if (s == null) throw new IllegalArgumentException("String is null");
	
	  if (s.trim().equals("")) throw new IllegalArgumentException("String is empty");
	}
	
	/** asserts that the given widget is not null */
	private static void notnull(Widget widget) throws IllegalArgumentException {
	  if (widget == null) throw new IllegalArgumentException("Widget can not be null");
	}

}//end class GWTUtils
