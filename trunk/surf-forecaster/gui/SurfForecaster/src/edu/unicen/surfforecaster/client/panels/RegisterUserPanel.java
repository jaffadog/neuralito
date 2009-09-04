package edu.unicen.surfforecaster.client.panels;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.client.SurfForecasterConstants;

public class RegisterUserPanel extends VerticalPanel {
	
	public RegisterUserPanel(){}
	
	/**
	 * @wbp.parser.constructor
	 */
	public RegisterUserPanel(SurfForecasterConstants localeConstants) {
		final FlexTable flexTable = new FlexTable();
		this.add(flexTable);
		flexTable.setCellPadding(4);

		final Label userLabel = new Label(localeConstants.userName() + ": ");
		flexTable.setWidget(0, 0, userLabel);

		final Label passLabel = new Label(localeConstants.password() + ": ");
		flexTable.setWidget(1, 0, passLabel);

		final TextBox loginUserName = new TextBox();
		flexTable.setWidget(0, 1, loginUserName);
		loginUserName.setWidth("150");
		
		final PasswordTextBox loginPassword = new PasswordTextBox();
		flexTable.setWidget(1, 1, loginPassword);
		loginPassword.setWidth("150");
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getFlexCellFormatter().setColSpan(2, 0, 2);
	}
		
}
