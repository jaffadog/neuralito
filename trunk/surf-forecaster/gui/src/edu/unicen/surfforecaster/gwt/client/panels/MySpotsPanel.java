package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class MySpotsPanel extends FlexTable{

	FlexTable mySpotsTable = null;
	
	public MySpotsPanel() {
		mySpotsTable = new FlexTable();
		this.setWidget(0, 0, mySpotsTable);
		this.setSpotsTableTitles();
		this.retrieveMySpots();
	}

	/**
	 * Sets mySpots table titles row
	 */
	private void setSpotsTableTitles() {
		Label lblSpot = new Label(GWTUtils.LOCALE_CONSTANTS.spot());
		mySpotsTable.setWidget(0, 0, lblSpot);
		
		Label lblArea = new Label(GWTUtils.LOCALE_CONSTANTS.area());
		mySpotsTable.setWidget(0, 1, lblArea);
		
		Label lblCountry = new Label(GWTUtils.LOCALE_CONSTANTS.country());
		mySpotsTable.setWidget(0, 2, lblCountry);
		
		Label lblZone = new Label(GWTUtils.LOCALE_CONSTANTS.zone());
		mySpotsTable.setWidget(0, 3, lblZone);
		
		Label lblVisibility = new Label(GWTUtils.LOCALE_CONSTANTS.spotVisibility());
		mySpotsTable.setWidget(0, 4, lblVisibility);
		
		Label lblEdit = new Label(GWTUtils.LOCALE_CONSTANTS.edit());
		mySpotsTable.setWidget(0, 5, lblEdit);
		
		Label lblDelete = new Label(GWTUtils.LOCALE_CONSTANTS.delete());
		mySpotsTable.setWidget(0, 6, lblDelete);
	}
	
	/**
	 * Calls to spots service to get the logged in user spots
	 */
	private void retrieveMySpots() {
		SpotServices.Util.getInstance().getSpotsCreatedBy(new AsyncCallback<List<SpotGwtDTO>>(){
			public void onSuccess(List<SpotGwtDTO> result){
				fillSpotsTable(result);
            }
            
			public void onFailure(Throwable caught){
            	if (((NeuralitoException)caught).getErrorCode().equals(ErrorCode.USER_SESSION_EMPTY_OR_EXPIRED) && 
						Cookies.getCookie("surfForecaster-Username") != null) {
					GWTUtils.showSessionExpiredLoginBox();
				} else {
//					messages.add(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
//					errorPanel.setMessages(messages);
//					errorPanel.setVisible(true);
				}
            }
		});
		
	}
	
	private void fillSpotsTable(List<SpotGwtDTO> spots) {
		int index = 1;
		Iterator<SpotGwtDTO> it = spots.iterator();
		while (it.hasNext()) {
			final SpotGwtDTO spot = it.next();
			
			Image editIcon = new Image(GWTUtils.EDIT_ICON_URL);
			editIcon.setSize("30px", "30px");
			editIcon.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					editSpot(spot.getId());	
				}
			});
			
			Image deleteIcon = new Image(GWTUtils.DELETE_ICON_URL);
			deleteIcon.setSize("30px", "30px");
			deleteIcon.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					deleteSpot(spot.getId());	
				}
			});
			
			mySpotsTable.setWidget(index, 0, new Label(spot.getName()));
			mySpotsTable.setWidget(index, 1, new Label(spot.getArea().getNames().get(GWTUtils.getCurrentLocaleCode())));
			mySpotsTable.setWidget(index, 2, new Label(spot.getCountry().getNames().get(GWTUtils.getCurrentLocaleCode())));
			mySpotsTable.setWidget(index, 3, new Label(spot.getZone().getName()));
			mySpotsTable.setWidget(index, 4, new Label(spot.isPublik() ? GWTUtils.LOCALE_CONSTANTS.public_() : GWTUtils.LOCALE_CONSTANTS.private_()));
			mySpotsTable.setWidget(index, 5, editIcon);
			mySpotsTable.setWidget(index, 6, deleteIcon);
			
			index++;
		}
		
	}
	
	private void editSpot(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	private void deleteSpot(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
