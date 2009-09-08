package edu.unicen.surfforecaster.gwt.client;

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
	
	//PATH
	public final static String IMAGE_LOGO = "images/logo2.PNG";
	public final static String IMAGE_BLUE_BAR_LOADER = "images/blue-bar-loader.gif";
	public final static String IMAGE_BLUE_CIRCLE_LOADER = "images/blue-circle-loader.gif";
	
	//Translators
	public static SurfForecasterConstants LOCALE_CONSTANTS;
	public static SurfForecasterMessages LOCALE_MESSAGES;
	
	//Valid history tokens
	public static Vector<String> VALID_HISTORY_TOKENS = new Vector<String>();
	public final static String DEFAULT_HISTORY_TOKEN = "forecastTab";
	
	
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
