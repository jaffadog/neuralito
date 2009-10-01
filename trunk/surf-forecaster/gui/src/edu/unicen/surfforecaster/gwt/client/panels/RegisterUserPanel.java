package edu.unicen.surfforecaster.gwt.client.panels;


import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.gwt.client.ForecastCommonServices;
import edu.unicen.surfforecaster.gwt.client.utils.ClientI18NMessages;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;


public class RegisterUserPanel extends VerticalPanel {
	
	private Label errorlabel = null;
	private TextBox nameTxt;
	private TextBox lastNameTxt;
	private TextBox emailTxt;
	private TextBox userTxt;
	private PasswordTextBox passTxt;
	
	public RegisterUserPanel() {
		setSpacing(10);
		this.setWidth(GWTUtils.APLICATION_WIDTH);
		
		final MessagePanel errorPanel = new ErrorMsgPanel();
		errorPanel.setVisible(false);
		this.add(errorPanel);
		
		Vector<String> message = new Vector<String>();
		message.add(ClientI18NMessages.getInstance().getErrorMessage("CHANGES_SAVED_SUCCESFULLY"));
		final MessagePanel successPanel = new SuccessMsgPanel(message);
		successPanel.setVisible(false);
		this.add(successPanel);
		
		final FlexTable flexTable = new FlexTable();
		Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.registerSectionTitle());
		lblTitle.addStyleName("gwt-Label-SectionTitle");
		this.add(lblTitle);
		
		Label lblRegisterdescription = new Label("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's " +
				"standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has " +
				"survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s " +
				"with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including " +
				"versions of Lorem Ipsum.");
		lblRegisterdescription.addStyleName("gwt-Label-RegisterSectionDescription");
		add(lblRegisterdescription);
		
		this.add(flexTable);
		flexTable.setCellSpacing(5);
		flexTable.setSize("450", "300");

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
		flexTable.setWidget(1, 2, nameTxt);
		nameTxt.setWidth("300px");

		lastNameTxt = new TextBox();
		flexTable.setWidget(2, 2, lastNameTxt);
		lastNameTxt.setWidth("300px");
		
		emailTxt = new TextBox();
		flexTable.setWidget(3, 2, emailTxt);
		emailTxt.setWidth("300px");

		userTxt = new TextBox();
		flexTable.setWidget(4, 2, userTxt);
		userTxt.setWidth("300px");

		passTxt = new PasswordTextBox();
		flexTable.setWidget(5, 2, passTxt);
		passTxt.setWidth("300px");
		
//		final Label adminLabel = new Label(GWTUtils.LOCALE_CONSTANTS.administrator() + ":");
//		flexTable.setWidget(6, 0, adminLabel);
//		adminLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
//
//		final CheckBox adminCheck = new CheckBox();
//		flexTable.setWidget(6, 2, adminCheck);
//		
//		flexTable.getCellFormatter().setVisible(6, 0, false);
//		flexTable.getCellFormatter().setVisible(6, 2, false);

		final HorizontalPanel btnsPanel = new HorizontalPanel();
		flexTable.setWidget(6, 0, btnsPanel);
		btnsPanel.setSpacing(5);
		flexTable.getCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(6, 0, 3);

		final PushButton registerBtn = new PushButton();
		btnsPanel.add(registerBtn);
		registerBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				final Vector<String> messages = new Vector<String>();
				errorPanel.setVisible(false);
				successPanel.setVisible(false);
				
				if (nameTxt.getText().trim() != "" && lastNameTxt.getText().trim() != "" && emailTxt.getText().trim() != "" 
					&& userTxt.getText().trim() != "" && passTxt.getText().trim() != ""){
					
					ForecastCommonServices.Util.getInstance().addUser(nameTxt.getText().trim(), lastNameTxt.getText().trim(), 
							emailTxt.getText().trim(), userTxt.getText().trim(), passTxt.getText().trim(), 2, new AsyncCallback<Integer>(){
						public void onSuccess(Integer result){
							clearFields();		
							successPanel.setVisible(true);
			            }
			            public void onFailure(Throwable caught){
			            	messages.add(ClientI18NMessages.getInstance().getErrorMessage((NeuralitoException)caught));
							errorPanel.setMessages(messages);
							errorPanel.setVisible(true);
			            }
						});
				}
				else{
					messages.add(GWTUtils.LOCALE_CONSTANTS.MANDATORY_FIELDS());
					errorPanel.setMessages(messages);
					errorPanel.setVisible(true);
				}
			}
		});
		registerBtn.setHeight(GWTUtils.PUSHBUTTON_HEIGHT);
		registerBtn.setText(GWTUtils.LOCALE_CONSTANTS.register());

		final PushButton cancelBtn = new PushButton();
		cancelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem(GWTUtils.DEFAULT_HISTORY_TOKEN);
				//The previos statement call the history change event to reload the view
			}
		});
		btnsPanel.add(cancelBtn);
//		cancelBtn.addClickListener(new ClickListener() {
//			public void onClick(final Widget sender) {
//				hide();
//			}
//		});
		cancelBtn.setHeight(GWTUtils.PUSHBUTTON_HEIGHT);
		cancelBtn.setText(GWTUtils.LOCALE_CONSTANTS.goBack());
		flexTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
	}
	
	private void clearFields() {
		this.emailTxt.setText("");
		this.nameTxt.setText("");
		this.lastNameTxt.setText("");
		this.userTxt.setText("");
		this.passTxt.setText("");
	}
		
}
