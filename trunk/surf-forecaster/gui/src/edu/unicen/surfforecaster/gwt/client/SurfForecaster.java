package edu.unicen.surfforecaster.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.panels.ContentPanel;
import edu.unicen.surfforecaster.gwt.client.panels.LogoPanel;
import edu.unicen.surfforecaster.gwt.client.panels.UserStatePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SurfForecaster implements EntryPoint {

	// Create an instance of SurfForecasterConstants interface
	private final SurfForecasterConstants localeConstants = GWT
			.create(SurfForecasterConstants.class);
	// Create an instance of SurfForecasterMessages interface
	private final SurfForecasterMessages localeMessages = GWT
			.create(SurfForecasterMessages.class);
	// history old token
	private String oldToken = null;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		GWTUtils.LOCALE_CONSTANTS = localeConstants;
		GWTUtils.LOCALE_MESSAGES = localeMessages;
		// this.testService();
		final RootPanel rootPanel = RootPanel.get();
		rootPanel.addStyleName("gwt-RootPanel");

		final VerticalPanel rootVPanel = new VerticalPanel();
		rootVPanel.setWidth(GWTUtils.APLICATION_WIDTH);
		rootVPanel.addStyleName("gwt-RootVPanel");

		final UserStatePanel userStatePanel = new UserStatePanel();
		rootVPanel.add(userStatePanel);

		final LogoPanel logoPanel = new LogoPanel();
		rootVPanel.add(logoPanel);

		final ContentPanel contentPanel = ContentPanel.getInstance();
		rootVPanel.add(contentPanel);

		rootPanel.add(rootVPanel);

		// finally, remove the splash screen/loading msg
		GWTUtils.removeElementFromDOM("loadingDiv");

		final ValueChangeHandler<String> historyHandler = new ValueChangeHandler<String>() {
			public void onValueChange(final ValueChangeEvent<String> event) {
				
				if (oldToken != null && event.getValue().equals(oldToken))
					System.out.println("SurfForecaster->oldToken es igual a actual(Corta el change event de history):" + event.getValue());
				// If they are the same, no need to do anything
				if (oldToken != null && event.getValue().equals(oldToken))
					return;
				
				oldToken = event.getValue();

				ContentPanel.getInstance().setPanelState(event.getValue());
			}
		};
		History.addValueChangeHandler(historyHandler);

		if (History.getToken().length() > 0) {
			if (GWTUtils.VALID_HISTORY_TOKENS.contains(History.getToken())) {
				System.out.println("token-valido: " + History.getToken());
				History.fireCurrentHistoryState();
			} else {
				ContentPanel.getInstance().setPanelState(
						GWTUtils.DEFAULT_HISTORY_TOKEN);
			}
		} 
//		else {
//			// Use the first token available
//			ContentPanel.getInstance().setPanelState(
//					GWTUtils.DEFAULT_HISTORY_TOKEN);
//		}

	}

}
