package edu.unicen.surfforecaster.gwt.client.panels;


import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.UserType;
import edu.unicen.surfforecaster.gwt.client.UserServices;
import edu.unicen.surfforecaster.gwt.client.utils.ClientI18NMessages;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;


public class RegisterUserPanel extends VerticalPanel {
	
	private Label errorlabel = null;
	private TextBox nameTxt;
	private TextBox lastNameTxt;
	private TextBox emailTxt;
	private TextBox userTxt;
	private PasswordTextBox passTxt;
	
	private static final String INPUT_WIDTH = "300px";
	
	public RegisterUserPanel() {
		this.setWidth(GWTUtils.APLICATION_WIDTH);
		
		final FlexTable flexTable = new FlexTable();
		Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.registerSectionTitle());
		lblTitle.addStyleName("gwt-Label-SectionTitle");
		this.add(lblTitle);
		
		final MessagePanel errorPanel = new ErrorMsgPanel();
		errorPanel.setVisible(false);
		this.add(errorPanel);
		
		Vector<String> message = new Vector<String>();
		message.add(ClientI18NMessages.getInstance().getMessage("CHANGES_SAVED_SUCCESFULLY"));
		final MessagePanel successPanel = new SuccessMsgPanel(message);
		successPanel.setVisible(false);
		this.add(successPanel);
		
		Label lblRegisterdescription = new Label("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's " +
				"standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has " +
				"survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s " +
				"with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including " +
				"versions of Lorem Ipsum.");
		lblRegisterdescription.addStyleName("gwt-Label-SectionDescription");
		add(lblRegisterdescription);
		
		this.add(flexTable);

		errorlabel = new Label();
		errorlabel.setStylePrimaryName("gwt-Label-error");
		flexTable.setWidget(0, 0, errorlabel);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 3);
		flexTable.getCellFormatter().setVisible(0, 0, false);

		final Label namelabel = new Label("* " + GWTUtils.LOCALE_CONSTANTS.name() + ":");
		flexTable.setWidget(1, 0, namelabel);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label lnameLabel = new Label("* " + GWTUtils.LOCALE_CONSTANTS.lastName() + ":");
		flexTable.setWidget(2, 0, lnameLabel);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		final Label emailLabel = new Label("* " + GWTUtils.LOCALE_CONSTANTS.email() + ":");
		flexTable.setWidget(3, 0, emailLabel);

		final Label userLabel = new Label("* " + GWTUtils.LOCALE_CONSTANTS.userName() + ":");
		flexTable.setWidget(4, 0, userLabel);
		flexTable.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label passLabel = new Label("* " + GWTUtils.LOCALE_CONSTANTS.password() + ":");
		flexTable.setWidget(5, 0, passLabel);
		flexTable.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		nameTxt = new TextBox();
		nameTxt.setMaxLength(100);
		flexTable.setWidget(1, 2, nameTxt);
		nameTxt.setWidth(RegisterUserPanel.INPUT_WIDTH);

		lastNameTxt = new TextBox();
		lastNameTxt.setMaxLength(100);
		flexTable.setWidget(2, 2, lastNameTxt);
		lastNameTxt.setWidth(RegisterUserPanel.INPUT_WIDTH);
		
		emailTxt = new TextBox();
		emailTxt.setMaxLength(100);
		flexTable.setWidget(3, 2, emailTxt);
		emailTxt.setWidth(RegisterUserPanel.INPUT_WIDTH);

		userTxt = new TextBox();
		userTxt.setMaxLength(50);
		flexTable.setWidget(4, 2, userTxt);
		userTxt.setWidth(RegisterUserPanel.INPUT_WIDTH);

		passTxt = new PasswordTextBox();
		passTxt.setMaxLength(50);
		flexTable.setWidget(5, 2, passTxt);
		passTxt.setWidth(RegisterUserPanel.INPUT_WIDTH);

		final HorizontalPanel btnsPanel = new HorizontalPanel();
		flexTable.setWidget(6, 0, btnsPanel);
		btnsPanel.setSpacing(5);
		flexTable.getCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(6, 0, 3);
		
		final HTMLButtonGrayGrad registerBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.register(), "RegisterUserPanel-register", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
		btnsPanel.add(registerBtn);
		registerBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				final Vector<String> messages = new Vector<String>();
				errorPanel.setVisible(false);
				successPanel.setVisible(false);
				
				messages.addAll(validateForm());
				if (messages.isEmpty()){
					
					UserServices.Util.getInstance().addUser(nameTxt.getText().trim(), lastNameTxt.getText().trim(), 
							emailTxt.getText().trim(), userTxt.getText().trim(), passTxt.getText().trim(), UserType.REGISTERED_USER, new AsyncCallback<Integer>(){
						public void onSuccess(Integer result){
							clearFields();		
							successPanel.setVisible(true);
			            }
			            public void onFailure(Throwable caught){
			            	messages.add(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
							errorPanel.setMessages(messages);
							errorPanel.setVisible(true);
			            }
						});
				}
				else{
					errorPanel.setMessages(messages);
					errorPanel.setVisible(true);
					Window.scrollTo(0, 0);
				}
			}
		});

		final HTMLButtonGrayGrad backBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.goBack(), "RegisterUserPanel-goBack", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
		backBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem(GWTUtils.DEFAULT_HISTORY_TOKEN);
				//The previos statement call the history change event to reload the view
			}
		});
		btnsPanel.add(backBtn);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
	}
	
	private void clearFields() {
		this.emailTxt.setText("");
		this.nameTxt.setText("");
		this.lastNameTxt.setText("");
		this.userTxt.setText("");
		this.passTxt.setText("");
	}
	
	private Vector<String> validateForm() {
		Vector<String> messages = new Vector<String>();
		
		if (this.nameTxt.getText().trim().equals(""))
			messages.add(GWTUtils.LOCALE_MESSAGES.MANDATORY_FIELD(GWTUtils.LOCALE_CONSTANTS.name()));
		
		if (!this.nameTxt.getText().trim().equals("") && !this.nameTxt.getText().trim().matches(GWTUtils.ALPHANUM_SPACES_NOT_START_WITH_NUM))
			messages.add(GWTUtils.LOCALE_MESSAGES.ALPHANUM_SPACES_NOT_START_WITH_NUM(GWTUtils.LOCALE_CONSTANTS.name()));
		
		if (this.lastNameTxt.getText().trim().equals(""))
			messages.add(GWTUtils.LOCALE_MESSAGES.MANDATORY_FIELD(GWTUtils.LOCALE_CONSTANTS.lastName()));
		
		if (!this.lastNameTxt.getText().trim().equals("") && !this.lastNameTxt.getText().trim().matches(GWTUtils.ALPHANUM_SPACES_NOT_START_WITH_NUM))
			messages.add(GWTUtils.LOCALE_MESSAGES.ALPHANUM_SPACES_NOT_START_WITH_NUM(GWTUtils.LOCALE_CONSTANTS.lastName()));
		
		if (this.emailTxt.getText().trim().equals(""))
			messages.add(GWTUtils.LOCALE_MESSAGES.MANDATORY_FIELD(GWTUtils.LOCALE_CONSTANTS.email()));
		
		if (!this.emailTxt.getText().trim().equals("") && !this.emailTxt.getText().trim().matches(GWTUtils.REGEX_EMAIL))
			messages.add(GWTUtils.LOCALE_MESSAGES.REGEX_EMAIL(GWTUtils.LOCALE_CONSTANTS.email()));
		
		if (this.userTxt.getText().trim().equals(""))
			messages.add(GWTUtils.LOCALE_MESSAGES.MANDATORY_FIELD(GWTUtils.LOCALE_CONSTANTS.userName()));
		
		if (!this.userTxt.getText().trim().equals("") && !this.userTxt.getText().trim().matches(GWTUtils.ALPHANUM_NOT_SPACES_NOT_STARTS_WITH_NUM))
			messages.add(GWTUtils.LOCALE_MESSAGES.ALPHANUM_NOT_SPACES_NOT_STARTS_WITH_NUM(GWTUtils.LOCALE_CONSTANTS.userName()));
		
		if (this.passTxt.getText().trim().equals(""))
			messages.add(GWTUtils.LOCALE_MESSAGES.MANDATORY_FIELD(GWTUtils.LOCALE_CONSTANTS.password()));
		
		if (!this.passTxt.getText().trim().equals("") && !this.passTxt.getText().trim().matches(GWTUtils.ALPHANUM_NOT_SPACES_NOT_STARTS_WITH_NUM))
			messages.add(GWTUtils.LOCALE_MESSAGES.ALPHANUM_NOT_SPACES_NOT_STARTS_WITH_NUM(GWTUtils.LOCALE_CONSTANTS.password()));
		
		return messages;
	}
		
}
