package edu.unicen.surfforecaster.client.panels;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import edu.unicen.surfforecaster.client.ForecastCommonServices;
import edu.unicen.surfforecaster.client.GWTUtils;
import edu.unicen.surfforecaster.client.SessionData;
import edu.unicen.surfforecaster.client.SurfForecasterConstants;


public class UserStatePanel extends Composite {
	
	private SurfForecasterConstants localeConstants = null;
	private LoginBox loginPanel = null;
	
	//LoggedIn username label
	private Label lblUserName = null;
	//Link to LoginBox
	private Hyperlink lnkLogin = null;
	//Link to sign out
	private Hyperlink lnkSignOut = null;
	//Link to settings
	private Hyperlink lnkSettings = null;
	//Root items structure
	private HorizontalPanel horizontalPanel = null;
	
	public UserStatePanel() {
		/**
		 * should never enter here
		 */
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public UserStatePanel(SurfForecasterConstants localeConstants) {
		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(2);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel.setWidth(GWTUtils.APLICATION_WIDTH);
		initWidget(horizontalPanel);
		
		Label emptyLabel = new Label("");
		horizontalPanel.add(emptyLabel);
		emptyLabel.setWidth("500");
	
		loginPanel = new LoginBox(localeConstants);
		loginPanel.hide();
		
		
		//Link to LoginBox
		lnkLogin = new Hyperlink(localeConstants.signIn(), "signIn");
		lnkLogin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//Window.alert(((Boolean)loginPanel.isShowing()).toString());
				if (loginPanel.isShowing()){
					loginPanel.loginFailedMsgState(false);
					loginPanel.hide();
					
				}
				else{
					loginPanel.showRelativeTo(lnkLogin);
				}
			}
		});
		
		final Label lblSeparator = new Label("|");
		horizontalPanel.add(lblSeparator);
		
		Label lblLanguage = new Label(localeConstants.language() + ": ");
		lblLanguage.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel.add(lblLanguage);
	
		// Add the option to change the locale
	    final ListBox localeBox = new ListBox();
	    String currentLocale = LocaleInfo.getCurrentLocale().getLocaleName();
	    if (currentLocale.equals("default")) {
	      currentLocale = "es";
	    }
	    String[] localeNames = LocaleInfo.getAvailableLocaleNames();
	    for (String localeName : localeNames) {
	      if (!localeName.equals("default")) {
	        String nativeName = LocaleInfo.getLocaleNativeDisplayName(localeName);
	        localeBox.addItem(nativeName, localeName);
	        if (localeName.equals(currentLocale)) {
	          localeBox.setSelectedIndex(localeBox.getItemCount() - 1);
	        }
	      }
	    }
	    localeBox.addChangeHandler(new ChangeHandler() {
	      public void onChange(ChangeEvent event) {
	        String localeName = localeBox.getValue(localeBox.getSelectedIndex());
	        Window.open(GWTUtils.getHostPageLocation(true, true) + "?locale=" + localeName, "_self",
	            "");
	      }

	    }); 
	    horizontalPanel.add(localeBox);
	    
	    final Label lblSeparator2 = new Label("|");
		horizontalPanel.add(lblSeparator2);
		
		Hyperlink lnkHelp = new Hyperlink(localeConstants.help(), "help");
		horizontalPanel.add(lnkHelp);
		
		lnkSettings = new Hyperlink(localeConstants.settings(), "settings");
		
		lnkSignOut = new Hyperlink(localeConstants.signOut(), "");
		lnkSignOut.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ForecastCommonServices.Util.getInstance().closeSession(new AsyncCallback<?>(){
					
					public void onSuccess(Object result){
						Window.open(GWTUtils.getHostPageLocation(true, true), "_self", "");
					}
					
					public void onFailure(Throwable caught) {
						
					}
				});
			}
		});
		//Check if exist any opened session
		this.getSessionData();
	}
	
	private void getSessionData(){
		ForecastCommonServices.Util.getInstance().getSessionData(new AsyncCallback<SessionData>(){
			public void onSuccess(SessionData result) {
				if (result == null)
					horizontalPanel.insert(lnkLogin, 1);
				else{
					lblUserName = new Label(result.getUserName());
					horizontalPanel.insert(lblUserName, 1);
					
					final Label lblSeparator3 = new Label("|");
					horizontalPanel.add(lblSeparator3);
					
					horizontalPanel.add(lnkSettings);
					
					final Label lblSeparator4 = new Label("|");
					horizontalPanel.add(lblSeparator4);
					
					horizontalPanel.add(lnkSignOut);
				}
			}

			public void onFailure(Throwable caught) {
				
			}
		});
	}

}
