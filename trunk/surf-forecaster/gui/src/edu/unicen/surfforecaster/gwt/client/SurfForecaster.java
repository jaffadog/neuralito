package edu.unicen.surfforecaster.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.services.dto.HelloDTO;
import edu.unicen.surfforecaster.gwt.client.panels.ContentPanel;
import edu.unicen.surfforecaster.gwt.client.panels.LogoPanel;
import edu.unicen.surfforecaster.gwt.client.panels.UserStatePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SurfForecaster implements EntryPoint {
	
	//Create an instance of SurfForecasterConstants interface
	private SurfForecasterConstants localeConstants = GWT.create(SurfForecasterConstants.class);
	//Create an instance of SurfForecasterMessages interface
	private SurfForecasterMessages localeMessages = GWT.create(SurfForecasterMessages.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		this.testService();
		RootPanel rootPanel = RootPanel.get();
		rootPanel.addStyleName("gwt-RootPanel");
		
		VerticalPanel rootVPanel = new VerticalPanel();
		rootVPanel.setWidth(GWTUtils.APLICATION_WIDTH);
		
		//rootVPanel.setHeight("100%");
		rootVPanel.addStyleName("gwt-RootVPanel");
		
		
		UserStatePanel userStatePanel = new UserStatePanel(localeConstants);
		
		rootVPanel.add(userStatePanel);
		
		LogoPanel logoPanel = new LogoPanel();
		
		rootVPanel.add(logoPanel);
		
		ContentPanel contentPanel = ContentPanel.getInstance(localeConstants);
		rootVPanel.add(contentPanel);
		
		rootVPanel.setCellVerticalAlignment(userStatePanel, HasVerticalAlignment.ALIGN_TOP);
		rootVPanel.setCellVerticalAlignment(logoPanel, HasVerticalAlignment.ALIGN_TOP);
		rootVPanel.setCellVerticalAlignment(contentPanel, HasVerticalAlignment.ALIGN_TOP);
		
		rootPanel.add(rootVPanel);
		
		// finally, remove the splash screen/loading msg
		GWTUtils.removeElementFromDOM("loadingDiv");
	}
	  
	private void testService(){
		ForecastCommonServices.Util.getInstance().test2(new AsyncCallback<HelloDTO>(){
			public void onSuccess(HelloDTO result) {
				Window.alert(result.toString());
			}
				
			public void onFailure(Throwable caught) {
					
			}
		});
	}
}
