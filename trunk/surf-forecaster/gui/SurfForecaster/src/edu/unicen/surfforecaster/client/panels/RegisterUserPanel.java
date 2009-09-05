package edu.unicen.surfforecaster.client.panels;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.client.GWTUtils;
import edu.unicen.surfforecaster.client.SurfForecasterConstants;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class RegisterUserPanel extends VerticalPanel {
	
	private Label errorlabel = null;
	
	public RegisterUserPanel(){}
	
	/**
	 * @wbp.parser.constructor
	 */
	public RegisterUserPanel(final SurfForecasterConstants localeConstants) {
		setSpacing(10);
		this.setWidth(GWTUtils.APLICATION_WIDTH);
		
		final FlexTable flexTable = new FlexTable();
		Label lblTitle = new Label(localeConstants.registerSectionTitle());
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

		errorlabel = new Label(localeConstants.starFields());
		errorlabel.setStylePrimaryName("gwt-Label-error");
		flexTable.setWidget(0, 0, errorlabel);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 3);
		flexTable.getCellFormatter().setVisible(0, 0, false);

		final Label namelabel = new Label("* " + localeConstants.name() + ":");
		flexTable.setWidget(1, 0, namelabel);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label lnameLabel = new Label("* " + localeConstants.lastName() + ":");
		flexTable.setWidget(2, 0, lnameLabel);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		final Label emailLabel = new Label("* " + localeConstants.email() + ":");
		flexTable.setWidget(3, 0, emailLabel);

		final Label userLabel = new Label("* " + localeConstants.userName() + ":");
		flexTable.setWidget(4, 0, userLabel);
		flexTable.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final Label passLabel = new Label("* " + localeConstants.password() + ":");
		flexTable.setWidget(5, 0, passLabel);
		flexTable.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		final TextBox nameTxt = new TextBox();
		flexTable.setWidget(1, 2, nameTxt);
		nameTxt.setWidth("300px");

		final TextBox lastNameTxt = new TextBox();
		flexTable.setWidget(2, 2, lastNameTxt);
		lastNameTxt.setWidth("300px");
		
		final TextBox emailTxt = new TextBox();
		flexTable.setWidget(3, 2, emailTxt);
		emailTxt.setWidth("300px");

		final TextBox userTxt = new TextBox();
		flexTable.setWidget(4, 2, userTxt);
		userTxt.setWidth("300px");

		final PasswordTextBox passTxt = new PasswordTextBox();
		flexTable.setWidget(5, 2, passTxt);
		passTxt.setWidth("300px");
		
		final Label adminLabel = new Label(localeConstants.administrator() + ":");
		flexTable.setWidget(6, 0, adminLabel);
		adminLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		final CheckBox adminCheck = new CheckBox();
		flexTable.setWidget(6, 2, adminCheck);
		
//		if (user == null){
//			flexTable.getCellFormatter().setVisible(5, 0, false);
//			flexTable.getCellFormatter().setVisible(5, 2, false);
//		}

		final HorizontalPanel btnsPanel = new HorizontalPanel();
		flexTable.setWidget(7, 0, btnsPanel);
		btnsPanel.setSpacing(5);
		flexTable.getCellFormatter().setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(7, 0, 3);

		final PushButton registerBtn = new PushButton();
		btnsPanel.add(registerBtn);
//		registerBtn.addClickListener(new ClickListener() {
//			public void onClick(final Widget sender) {
//				if (nameTxt.getText().trim() != "" && lastNameTxt.getText().trim() != "" && userTxt.getText().trim() != "" && passTxt.getText().trim() != ""){
//					int userPermissions = 2;
//					if (user != null && user.getIdUserType() == 1 && adminCheck.isChecked())
//						userPermissions = 1;
//					DBService.Util.getInstance().addNewUser(nameTxt.getText().trim(), lastNameTxt.getText().trim(), 
//							userTxt.getText().trim(), passTxt.getText().trim(), userPermissions, new AsyncCallback<Integer>(){
//						public void onSuccess(Integer result){
//							if (result == null){
//								//System.out.println("No se pudo salvar el usuario");
//								flexTable.getCellFormatter().setVisible(0, 0, false);
//							}
//							else
//								if (result > 0){
//									//System.out.println("El usuario fue grabado con exito.");
//									if (user != null && user.getIdUserType() == 1)
//										new MessageBoxUI("Aceptar", "Usuario registrado exitosamente!!!");
//									else
//										new MessageBoxUI("Aceptar", "Bienvenido al sistema!!!, usted ya ha sido registrado");
//									flexTable.getCellFormatter().setVisible(0, 0, false);
//									hide();
//								}
//								else{
//									//System.out.println("Ya existe un usuario con ese userName.");
//									errorlabel.setText("Ya existe ese nombre de usuario en el sistema, ingrese otro por favor.");
//									flexTable.getCellFormatter().setVisible(0, 0, true);
//								}
//			            }
//			            public void onFailure(Throwable caught){
//			            	
//			            	//System.out.println("fault adding a new user");
//			            	caught.printStackTrace();
//			            }
//						});
//				}
//				else{
//					errorlabel.setText("Los campos marcados con (*) son obligatorios.");
//					flexTable.getCellFormatter().setVisible(0, 0, true);
//				}
//			}
//		});
		registerBtn.setHeight(GWTUtils.PUSHBUTTON_HEIGHT);
		registerBtn.setText(localeConstants.register());

		final PushButton cancelBtn = new PushButton();
		cancelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ContentPanel.getInstance(localeConstants).showMainVerticalPanel();
			}
		});
		btnsPanel.add(cancelBtn);
//		cancelBtn.addClickListener(new ClickListener() {
//			public void onClick(final Widget sender) {
//				hide();
//			}
//		});
		cancelBtn.setHeight(GWTUtils.PUSHBUTTON_HEIGHT);
		cancelBtn.setText(localeConstants.goBack());
		flexTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
	}
		
}
