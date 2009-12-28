package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.ClientI18NMessages;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;

public class MySpotsPanel extends FlexTable{

	private FlexTable mySpotsTable = null;
	private MessagePanel errorPanel;
	private MessagePanel successPanel;
	
	//Static properties
	private final static String ACTION_ICON_HEIGHT = "20px";
	private final static String ACTION_ICON_WIDTH = "20px";
	
	
	public MySpotsPanel() {
		this.setWidth("100%");
		
		errorPanel = new ErrorMsgPanel();
		errorPanel.setVisible(false);
		this.setWidget(1, 0, errorPanel);
		this.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setColSpan(1, 0, 3);
		
		Vector<String> message = new Vector<String>();
		message.add(ClientI18NMessages.getInstance().getMessage("CHANGES_SAVED_SUCCESFULLY"));
		successPanel = new SuccessMsgPanel(message);
		successPanel.setVisible(false);
		this.setWidget(2, 0, successPanel);
		this.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		this.getFlexCellFormatter().setColSpan(2, 0, 3);
		
		mySpotsTable = new FlexTable();
		this.setWidget(3, 0, mySpotsTable);
		//First row style
		mySpotsTable.getRowFormatter().addStyleName(0, "gwt-FlexTable-MySpotsTable-Titles");
		
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
					errorPanel.setMessage(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
					errorPanel.setVisible(true);
				}
            }
		});
		
	}
	
	private void fillSpotsTable(List<SpotGwtDTO> spots) {
		int index = 1;
		if (spots.size() > 0) {
			Iterator<SpotGwtDTO> it = spots.iterator();
			while (it.hasNext()) {
				final SpotGwtDTO spot = it.next();
				final int rowIndex = index;
				Image editIcon = new Image(GWTUtils.EDIT_ICON_URL);
				editIcon.setSize(MySpotsPanel.ACTION_ICON_WIDTH, MySpotsPanel.ACTION_ICON_HEIGHT);
				editIcon.addStyleName("gwt-Image-ActionIcon");
				editIcon.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						editSpot(spot);	
					}
				});
				
				Image deleteIcon = new Image(GWTUtils.DELETE_ICON_URL);
				deleteIcon.setSize(MySpotsPanel.ACTION_ICON_WIDTH, MySpotsPanel.ACTION_ICON_HEIGHT);
				deleteIcon.addStyleName("gwt-Image-ActionIcon");
				deleteIcon.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						deleteSpot(spot.getId(), rowIndex);	
					}
				});
				
				mySpotsTable.setWidget(index, 0, new Label(spot.getName()));
				mySpotsTable.setWidget(index, 1, new Label(spot.getArea().getNames().get(GWTUtils.getCurrentLocaleCode())));
				mySpotsTable.setWidget(index, 2, new Label(spot.getCountry().getNames().get(GWTUtils.getCurrentLocaleCode())));
				mySpotsTable.setWidget(index, 3, new Label(spot.getZone().getName()));
				mySpotsTable.setWidget(index, 4, new Label(spot.isPublik() ? GWTUtils.LOCALE_CONSTANTS.public_() : GWTUtils.LOCALE_CONSTANTS.private_()));
				mySpotsTable.setWidget(index, 5, editIcon);
				mySpotsTable.setWidget(index, 6, deleteIcon);
				
				//Cells align
				mySpotsTable.getFlexCellFormatter().setHorizontalAlignment(index, 4, HasHorizontalAlignment.ALIGN_CENTER);
				mySpotsTable.getFlexCellFormatter().setHorizontalAlignment(index, 5, HasHorizontalAlignment.ALIGN_CENTER);
				mySpotsTable.getFlexCellFormatter().setHorizontalAlignment(index, 6, HasHorizontalAlignment.ALIGN_CENTER);
				
				index++;
			}
		} else {
			mySpotsTable.setWidget(index, 0, new Label(GWTUtils.LOCALE_CONSTANTS.noneSpotsCreated()));
			mySpotsTable.getFlexCellFormatter().setHorizontalAlignment(index, 0, HasHorizontalAlignment.ALIGN_CENTER);
			mySpotsTable.getFlexCellFormatter().setColSpan(index, 0, 7);
		}
		
		//Columns format
		mySpotsTable.getColumnFormatter().setWidth(0, "200px");
		mySpotsTable.getColumnFormatter().setWidth(1, "150px");
		mySpotsTable.getColumnFormatter().setWidth(2, "150px");
		mySpotsTable.getColumnFormatter().setWidth(3, "200px");
	}
	
	private void editSpot(SpotGwtDTO spot) {
		errorPanel.setVisible(false);
		successPanel.setVisible(false);
		this.setWidget(4, 0, new NewSpotPanel(spot));
	}
	
	private void deleteSpot(Integer spotId, final int rowIndex) {
		errorPanel.setVisible(false);
		successPanel.setVisible(false);
		SpotServices.Util.getInstance().deleteSpot(spotId, new AsyncCallback<Boolean>(){
			public void onSuccess(Boolean result){
				if (result) {
					successPanel.setVisible(true);
					deleteMySpotsTableRow(rowIndex);
				} else {
					errorPanel.setMessage(GWTUtils.LOCALE_CONSTANTS.ERROR_DELETING_SPOT());
					errorPanel.setVisible(true);
				}
            }

			public void onFailure(Throwable caught){
            	if (((NeuralitoException)caught).getErrorCode().equals(ErrorCode.USER_SESSION_EMPTY_OR_EXPIRED) && 
						Cookies.getCookie("surfForecaster-Username") != null) {
					GWTUtils.showSessionExpiredLoginBox();
				} else {
					errorPanel.setMessage(ClientI18NMessages.getInstance().getMessage((NeuralitoException)caught));
					errorPanel.setVisible(true);
				}
            }
		});
	}
	
	/**
	 * Delete the row as parameter from the myspots table logically, not phisically to mantain the sames rowindex for the others row.
	 * After a page refresh the deleted spots are not be loaded.
	 * @param rowIndex
	 */
	private void deleteMySpotsTableRow(int rowIndex) {
		for (int i = 0; i < mySpotsTable.getCellCount(rowIndex); i++) {
			mySpotsTable.clearCell(rowIndex, i);
		}
		mySpotsTable.getRowFormatter().setVisible(rowIndex, false);
		//TODO usar la estructura de observer observable para actualizar los combos de spots de todo el sistema
	}
}
