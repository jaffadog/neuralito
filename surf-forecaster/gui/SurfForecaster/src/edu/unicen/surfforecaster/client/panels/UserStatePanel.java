package edu.unicen.surfforecaster.client.panels;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import edu.unicen.surfforecaster.client.GWTUtils;
import edu.unicen.surfforecaster.client.SurfForecasterConstants;


public class UserStatePanel extends Composite {
	
	private SurfForecasterConstants localeConstants = null;
	LoginBox loginPanel = null;
	
	public UserStatePanel() {}
	
	/**
	 * @wbp.parser.constructor
	 */
	public UserStatePanel(SurfForecasterConstants localeConstants) {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel.setWidth(GWTUtils.APLICATION_WIDTH);
		initWidget(horizontalPanel);
		
		Label emptyLabel = new Label("");
		horizontalPanel.add(emptyLabel);
		emptyLabel.setWidth("700");
	
		loginPanel = new LoginBox(localeConstants);
		loginPanel.hide();
		
		final Hyperlink lnkLogin = new Hyperlink(localeConstants.signIn(), "signIn");
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
		horizontalPanel.add(lnkLogin);
	
		Label lblLanguage = new Label(" | " + localeConstants.language() + ": ");
		lblLanguage.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel.add(lblLanguage);
		//lblLanguage.setWidth("120");
	
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
	        Window.open(GWTUtils.getHostPageLocation() + "?locale=" + localeName, "_self",
	            "");
	      }

	    }); 
	    horizontalPanel.add(localeBox);
	
	}

}
