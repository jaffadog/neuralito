package edu.unicen.surfforecaster.gwt.client.panels;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.UserDTO;
import edu.unicen.surfforecaster.gwt.client.UserServices;
import edu.unicen.surfforecaster.gwt.client.utils.ClientI18NMessages;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class LoginBox extends DialogBox{
	
	private static LoginBox instance = null;
	private final Label label_loginMessage;
	private SimplePanel loadingPanel = null;
	private HorizontalPanel horizontalPanel = null;
	private final String crossIconHTML = "<div id=\"closeLoginBoxDiv\" ><a onclick=\"closeDialog()\">X</a></div>";
	private Hyperlink registerLink;
	
	public static LoginBox getInstance() {
        if (instance == null) {
            instance = new LoginBox();
        }
        return instance;
    }
	
	private LoginBox() {
		
		super(false, false);
		setAnimationEnabled(true);
		this.setHTML(this.crossIconHTML);
		this.redefineClose(this);
		this.addStyleName("gwt-LoginBox");
		
		//Loading panel
		loadingPanel = new SimplePanel();
		loadingPanel.setStylePrimaryName("gwt-loadingPanelLoginBox"); //to center the loading icon
		Image loadingImage = new Image(GWTUtils.IMAGE_BLUE_CIRCLE_LOADER);
		loadingPanel.setWidget(loadingImage);
		
		//Form panel
		horizontalPanel = new HorizontalPanel();
		this.setWidget(horizontalPanel);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		final VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		
		label_loginMessage = new Label();
		verticalPanel.add(label_loginMessage);
		label_loginMessage.addStyleName("gwt-Label-error");
		label_loginMessage.setVisible(false);
		label_loginMessage.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		label_loginMessage.setWidth("250");
		
		final FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setCellPadding(4);

		final Label userLabel = new Label(GWTUtils.LOCALE_CONSTANTS.userName() + ": ");
		flexTable.setWidget(0, 0, userLabel);

		final Label passLabel = new Label(GWTUtils.LOCALE_CONSTANTS.password() + ": ");
		flexTable.setWidget(1, 0, passLabel);

		final TextBox loginUserName = new TextBox();
		flexTable.setWidget(0, 1, loginUserName);
		loginUserName.setWidth("150");
		
		final PasswordTextBox loginPassword = new PasswordTextBox();
		flexTable.setWidget(1, 1, loginPassword);
		loginPassword.setWidth("150");
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(2, 0, 2);
	
		loginUserName.setText("admin");
		loginPassword.setText("admin");

		final HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		flexTable.setWidget(2, 0, horizontalPanel_1);
		horizontalPanel_1.setSpacing(5);
		
		final HTMLButtonGrayGrad signInBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.signIn(), "LoginBox-signIn", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_120PX);
		horizontalPanel_1.add(signInBtn);
		horizontalPanel_1.setCellHorizontalAlignment(signInBtn, HasHorizontalAlignment.ALIGN_CENTER);
		//signInBtn.setHeight(GWTUtils.PUSHBUTTON_HEIGHT);
		signInBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				label_loginMessage.setVisible(false);
				showLoadingPanel();
				if (loginUserName.getText().trim().equals("") || loginPassword.getText().trim().equals("")){
					label_loginMessage.setText(GWTUtils.LOCALE_CONSTANTS.INVALID_LOGIN());
					label_loginMessage.setVisible(true);
					showFormPanel();
				}
				else{
					UserServices.Util.getInstance().login(loginUserName.getText().trim(), loginPassword.getText().trim(), new AsyncCallback<UserDTO>(){
						public void onSuccess(UserDTO result) {
							//Set a cookie with the username to difference a null session from a expired session (to show the login box again).
							Cookies.setCookie("surfForecaster-Username", result.getUsername(), GWTUtils.getExpirityDate());
							Window.open(GWTUtils.getHostPageLocation(true, false), "_self", "");
						}
							
						public void onFailure(Throwable caught) {
							label_loginMessage.setText(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
							label_loginMessage.setVisible(true);
							showFormPanel();
						}
					});
				}
				
				
			}
		});

		registerLink = new Hyperlink(GWTUtils.LOCALE_CONSTANTS.register(), "registerNewUser");
		horizontalPanel_1.add(registerLink);
		horizontalPanel_1.setCellVerticalAlignment(registerLink, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		registerLink.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
				removeUsernameCookie();
			}
		});
		
		
	}
	
	private void showLoadingPanel() {
		this.setHTML(GWTUtils.LOCALE_CONSTANTS.waitPlease() + "...");
		this.setWidget(loadingPanel);
	}
	
	private void showFormPanel() {
		this.setHTML(this.crossIconHTML);
		this.setWidget(horizontalPanel);
	}
	
	public void loginFailedMsgState(boolean state){
		label_loginMessage.setVisible(state);
	}
	
	public void removeUsernameCookie() {
		Cookies.removeCookie("surfForecaster-Username");
	}
	
	// Define closeDialog using JSNI
	private native void redefineClose(LoginBox loginBox) /*-{
		$wnd['closeDialog'] = function () {
			loginBox.@edu.unicen.surfforecaster.gwt.client.panels.LoginBox::loginFailedMsgState(Z)(false);
			loginBox.@edu.unicen.surfforecaster.gwt.client.panels.LoginBox::hide()();
			loginBox.@edu.unicen.surfforecaster.gwt.client.panels.LoginBox::removeUsernameCookie()();
			loginBox.@edu.unicen.surfforecaster.gwt.client.panels.LoginBox::reloadSite()();
		}
	}-*/;
	
	public void showExpiredSessionState() {
		label_loginMessage.setText(GWTUtils.LOCALE_CONSTANTS.USER_SESSION_EXPIRED());
		label_loginMessage.setVisible(true);
		registerLink.setVisible(false);
		this.setModal(true);
	}
	
	/**
	 * This method must be called when the user close this panel after his session expired.
	 * If the label for the username in UserStatePanel displays a name, the site must be refreshed
	 * to change to the unregistered user view.
	 */
	public void reloadSite() {
		if (UserStatePanel.getInstance().getLblUserName() != null)
			Window.open(GWTUtils.getHostPageLocation(true, false), "_self", "");
		else {
			this.setModal(false);
			registerLink.setVisible(true);
			label_loginMessage.setText("");
			TransparentPanel.getInstance().hide();
		}
	}

}
