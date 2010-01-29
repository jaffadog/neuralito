package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class AnonymousMessagePanel extends VerticalPanel {

	public AnonymousMessagePanel() {
		this.setWidth("100%");
		this.addStyleName("gwt-anonymousMessagePanel");
		this.setSpacing(5);
		HorizontalPanel sentenceContainer = new HorizontalPanel();
		
		Label lblMessagePart1 = new Label(GWTUtils.LOCALE_CONSTANTS.anonymousMessagePart1() + " ");
		lblMessagePart1.addStyleName("gwt-Label-error");
		lblMessagePart1.addStyleName("gwt-Label-anonymousMessagePanel");
		sentenceContainer.add(lblMessagePart1);
		//The link history token calls History.newItem that calls the history change event to reload the view
		Hyperlink lnkRegister = new Hyperlink(GWTUtils.LOCALE_CONSTANTS.register2(), "registerNewUser");
		lnkRegister.addStyleName("gwt-Hyperlink-Register-anonymousMessagePanel");
		sentenceContainer.add(lnkRegister);
		Label lblMessagePart2 = new Label(" " + GWTUtils.LOCALE_CONSTANTS.anonymousMessagePart2());
		lblMessagePart2.addStyleName("gwt-Label-error");
		lblMessagePart2.addStyleName("gwt-Label-anonymousMessagePanel");
		sentenceContainer.add(lblMessagePart2);
		
		this.add(sentenceContainer);
		
		DisclosurePanel whyRegister = new DisclosurePanel(GWTUtils.LOCALE_CONSTANTS.whyRegister(), false);
		whyRegister.setWidth("100%");
		whyRegister.setAnimationEnabled(true);
		Label lblWhyRegisterDesc = new Label(GWTUtils.LOCALE_CONSTANTS.whyRegisterDesc());
		lblWhyRegisterDesc.addStyleName("gwt-Label-SectionDescription");
		whyRegister.setContent(lblWhyRegisterDesc);
		
		this.add(whyRegister);
	}

}
