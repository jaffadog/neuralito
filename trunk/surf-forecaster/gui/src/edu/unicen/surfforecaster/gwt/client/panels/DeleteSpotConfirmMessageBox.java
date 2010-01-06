package edu.unicen.surfforecaster.gwt.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.widgets.HTMLButtonGrayGrad;

public class DeleteSpotConfirmMessageBox extends MessageBox implements ISurfForecasterBasePanel {
	
	private Widget baseParentPanel = null;
	
	public DeleteSpotConfirmMessageBox(String message, IconType iconType, final Integer spotId, final Integer rowIndex) {
		super(message, iconType);
		
		//accept btn
		final HTMLButtonGrayGrad acceptBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.accept(), "ConfirmBox-accept", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
		acceptBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				((MySpotsPanel)baseParentPanel).deleteSpot(spotId, rowIndex);
				hide();
			}
		});
		super.addBtn(acceptBtn);
		
		//cancel btn
		final HTMLButtonGrayGrad cancelBtn = new HTMLButtonGrayGrad(GWTUtils.LOCALE_CONSTANTS.cancel(), "ConfirmBox-cancel", HTMLButtonGrayGrad.BUTTON_GRAY_GRAD_90PX);
		cancelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		super.addBtn(cancelBtn);
	}

	@Override
	public Widget getBasePanel() {
		return baseParentPanel;
	}

	@Override
	public void setBasePanel(Widget basePanel) {
		baseParentPanel = basePanel;
	}
}
