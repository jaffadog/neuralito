package edu.unicen.surfforecaster.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.client.ForecastCommonServices;
import edu.unicen.surfforecaster.client.GWTUtils;
import edu.unicen.surfforecaster.client.SurfForecasterConstants;
import edu.unicen.surfforecaster.client.User;

public class LoginBox extends DialogBox{
	
	private final Label label_loginMessage;
	
	public LoginBox() {
		label_loginMessage = null;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public LoginBox(SurfForecasterConstants localeConstants) {
		
		super(false, false);
		setAnimationEnabled(true);
		this.setHTML("<div id=\"closeLoginBoxDiv\" ><a onclick=\"closeDialog()\"><strong>X</strong></a></div>");
		this.redefineClose(this);
		
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		this.setWidget(horizontalPanel);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		final VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		
		label_loginMessage = new Label(localeConstants.invalidUserPass());
		verticalPanel.add(label_loginMessage);
		label_loginMessage.setStylePrimaryName("gwt-Label-error");
		label_loginMessage.setVisible(false);
		label_loginMessage.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		final FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setCellPadding(4);

		final Label usuarioLabel = new Label(localeConstants.userName() + ": ");
		flexTable.setWidget(0, 0, usuarioLabel);

		final Label contrasenaLabel = new Label(localeConstants.password() + ": ");
		flexTable.setWidget(1, 0, contrasenaLabel);

		final TextBox loginUserName = new TextBox();
		flexTable.setWidget(0, 1, loginUserName);
		loginUserName.setWidth("150");
		
		final PasswordTextBox loginPassword = new PasswordTextBox();
		flexTable.setWidget(1, 1, loginPassword);
		loginPassword.setWidth("150");
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(2, 0, 2);
		// TODO Auto-generated constructor stub
	
		loginUserName.setText("admin");
		loginPassword.setText("admin");

		final HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		flexTable.setWidget(2, 0, horizontalPanel_1);
		horizontalPanel_1.setSpacing(5);
		
		final PushButton ingresarPushButton = new PushButton(localeConstants.signIn(), localeConstants.signIn());
		horizontalPanel_1.add(ingresarPushButton);
		horizontalPanel_1.setCellHorizontalAlignment(ingresarPushButton, HasHorizontalAlignment.ALIGN_CENTER);
		ingresarPushButton.setHeight("20px");
		ingresarPushButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				label_loginMessage.setVisible(false);
				if (loginUserName.getText().trim().equals("") || loginPassword.getText().trim().equals("")){
					label_loginMessage.setVisible(true);
				}
				else{
					ForecastCommonServices.Util.getInstance().login(loginUserName.getText().trim(), loginPassword.getText().trim(), new AsyncCallback<User>(){
						public void onSuccess(User result) {
							if (result == null)
								label_loginMessage.setVisible(true);
							else{
								Window.open(GWTUtils.getHostPageLocation(true, true), "_self", "");
							}
						}
							
						public void onFailure(Throwable caught) {
							
						}
					});
				}
				
				
			}
		});

		final Hyperlink registerLink = new Hyperlink(localeConstants.register(), "registerNewUser");
		horizontalPanel_1.add(registerLink);
		horizontalPanel_1.setCellVerticalAlignment(registerLink, HasVerticalAlignment.ALIGN_MIDDLE);
		registerLink.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//new RegisterUI();
			}
		});
		
		
	}
	
	public void loginFailedMsgState(boolean state){
		label_loginMessage.setVisible(state);
	}
	
	// Define closeDialog using JSNI
	private native void redefineClose(LoginBox loginBox) /*-{
		$wnd['closeDialog'] = function () {
			loginBox.@edu.unicen.surfforecaster.client.panels.LoginBox::loginFailedMsgState(Z)(false);
			loginBox.@edu.unicen.surfforecaster.client.panels.LoginBox::hide()();
		}
	}-*/;

}
