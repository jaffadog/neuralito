package edu.unicen.surfforecaster.gwt.client.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.gwt.client.SurfForecasterConstants;
import edu.unicen.surfforecaster.gwt.client.SurfForecasterMessages;
import edu.unicen.surfforecaster.gwt.client.dto.ForecastGwtDTO;
import edu.unicen.surfforecaster.gwt.client.panels.LoginBox;
import edu.unicen.surfforecaster.gwt.client.panels.TransparentPanel;

/**
 * GWTUtils is a set of helper classes to make it easier to work with GWT
 */
public final class GWTUtils {
	
	//WIDGETS MEASURES
	public final static String APLICATION_WIDTH = "1000px";
	
	//IMAGES URLS
	public final static String FLAGS_PATH = "images/flags/";
	public final static String IMAGE_LOGO = "images/logo2.PNG";
	public final static String IMAGE_SPOT = "images/wave.png";
	public final static String IMAGE_BUOY_DISABLED = "images/red-ball.gif";
	public final static String IMAGE_BUOY_SELECTED = "images/green-ball.gif";
	public final static String IMAGE_BLUE_BAR_LOADER = "images/blue-bar-loader.gif";
	public final static String IMAGE_BLUE_SMALL_BAR_LOADER = "images/blue-small-bar-loader.gif";
	public final static String IMAGE_ERROR_ICON = "images/Error.png";
	public final static String IMAGE_GOOD_ICON = "images/Good.png";
	public final static String IMAGE_WARNING_ICON = "images/Warning.png";
	public final static String IMAGE_INFO_ICON = "images/Info.png";
	public final static String IMAGE_HELP_ICON = "images/Help.png";
	public final static String IMAGE_EDIT_ICON = "images/edit.png";
	public final static String IMAGE_DELETE_ICON = "images/delete.png";
	
	//Translators
	public static SurfForecasterConstants LOCALE_CONSTANTS;
	public static SurfForecasterMessages LOCALE_MESSAGES;
	
	//Valid history tokens
	public static Vector<String> VALID_HISTORY_TOKENS = new Vector<String>();
	public final static String DEFAULT_HISTORY_TOKEN = "forecastTab";
	
	//Default language
	private static String DEFAULT_LOCALE = "es";
	
	//WW3 Forecaster name
	public final static String WW3_FORECASTER_NAME = "WW3 Noaa Forecaster";
	
	//Utils regex
	//Only digits
	public final static String REGEX_DIGITS = "[\\d]*";
	//Alphanumeric with spaces and not starts with numbers
	public final static String ALPHANUM_SPACES_NOT_START_WITH_NUM = "[^\\d][\\w ]*";
	//Alphanumeric with spaces and dashes and not starts with numbers
	public final static String ALPHANUM_SPACES_DASHES_NOT_START_WITH_NUM = "[^\\d][\\w -]*";
	//Alphanumeric with spaces
	public final static String ALPHANUM_SPACES = "[\\w ]*";
	//Alphanumeric, no spaces and not starts with numbers
	public final static String ALPHANUM_NOT_SPACES_NOT_STARTS_WITH_NUM = "[^\\d][\\w]*";
	//Alphanumeric, no spaces
	public final static String ALPHANUM_NOT_SPACES = "[\\w]*";
	//Email pattern
	public final static String REGEX_EMAIL = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
	
	public static String IMAGE_OBS_SAMPLE() {
		return "images/obsSample_" + GWTUtils.getCurrentLocaleCode() + ".JPG";
	}
	
	public static boolean isNumeric(String value){
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	public static void showSessionExpiredLoginBox() {
		TransparentPanel.getInstance().show();
		LoginBox.getInstance().showExpiredSessionState();
		LoginBox.getInstance().center();
	}
	
	//Cookie expiration date
	public static Date getExpirityDate() {
		Date now = new Date();
		long nowLong = now.getTime();
		nowLong = nowLong + (1000 * 60 * 60 * 24 * 1);//one day
		now.setTime(nowLong);
		
		return now;
	}
	
	public static String getCurrentLocaleCode() {
		String locale = LocaleInfo.getCurrentLocale().getLocaleName();
		if (locale.equals("default"))
			locale = DEFAULT_LOCALE;
		return locale;
	}
	
	
	/**
	 * Set the valid history tokens values in the valid tokens vector
	 */
	public static void setValidHistoryTokens() {
		VALID_HISTORY_TOKENS.add("forecastTab");
		VALID_HISTORY_TOKENS.add("descriptionTab");
		VALID_HISTORY_TOKENS.add("comparatorTab");
		VALID_HISTORY_TOKENS.add("newSpotTab");
		VALID_HISTORY_TOKENS.add("mySpotsTab");
		VALID_HISTORY_TOKENS.add("registerNewUser");
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
	 * Returns the client clock hours
	 * @return String datetime as YYYY-MM-DD hh:mm
	 */
	private static native String getJSClientDate() /*-{
		var now = new Date();
		return now.getFullYear() + "-" + now.getMonth() + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes();
	}-*/;
	
	private static Map<String, Integer> getClientDate() {
		HashMap<String, Integer> date = new HashMap<String, Integer>();
		String sDate = getJSClientDate();
		date.put("year", new Integer(sDate.substring(0, 4)));
		date.put("month", new Integer(sDate.substring(5, sDate.indexOf("-", 5))));
		date.put("day", new Integer(sDate.substring(sDate.indexOf("-", 5) + 1, sDate.indexOf(" "))));
		date.put("hours", new Integer(sDate.substring(sDate.indexOf(" ") + 1, sDate.indexOf(":"))));
		date.put("minutes", new Integer(sDate.substring(sDate.indexOf(":") + 1)));
		return date;
	}
	
	/**
	 * Returns true if the difference between the first hour and the second is less than one
	 * @param clientHour The client system hours in 24h format
	 * @param foreastHour Forecast hours in 24h format
	 * @returns boolean
	 */
	private static boolean isCurrentHour(int clientHour, int forecastHour) {
		clientHour = clientHour == 23 ? 21 : clientHour; //This is an exception because if clienthour is 23, this method always return false (due midnight is 0 and not 24) 
		int diff = clientHour - forecastHour;
		diff = diff < 0 ? diff * (-1) : diff; //get diff absolute value
		return diff <= 1 ? true : false;
	}
	
	/**
	 * Returns the index of the forecasts list whose datetime is closest to the current (+- one hour) or -1 if nothing found
	 * @return int index 
	 */
	public static int getCurrentForecastIndex(List<ForecastGwtDTO> forecasts) {
		Map<String, Integer> date = getClientDate();
		for (int j = 0; j < forecasts.size(); j++) {
			ForecastGwtDTO forecastDTO = forecasts.get(j);
			long miliDate = forecastDTO.getBaseDate().getTime() + (forecastDTO.getForecastTime() * 3600000);
			Date realDate = new Date(miliDate);
			if (realDate.getYear() + 1900 == date.get("year").intValue() && realDate.getMonth() == date.get("month").intValue() && 
					realDate.getDate() == date.get("day").intValue()){
				if (isCurrentHour(new Integer(date.get("hours").intValue()), realDate.getHours()))
					return j;
			}
		}
		
		return -1;
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

	public static String getDayAbbr(int day) {
		switch (day) {
		case 0:
			return GWTUtils.LOCALE_CONSTANTS.sunday_abbr();
		case 1:
			return GWTUtils.LOCALE_CONSTANTS.monday_abbr();
		case 2:
			return GWTUtils.LOCALE_CONSTANTS.tuesday_abbr();
		case 3:
			return GWTUtils.LOCALE_CONSTANTS.wednesday_abbr();
		case 4:
			return GWTUtils.LOCALE_CONSTANTS.thursday_abbr();
		case 5:
			return GWTUtils.LOCALE_CONSTANTS.friday_abbr();
		case 6:
			return GWTUtils.LOCALE_CONSTANTS.saturday_abbr();

		default:
			return "";
		}
	}

}//end class GWTUtils
