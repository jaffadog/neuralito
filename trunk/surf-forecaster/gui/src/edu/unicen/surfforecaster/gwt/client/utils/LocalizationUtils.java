package edu.unicen.surfforecaster.gwt.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unicen.surfforecaster.gwt.client.SpotServices;
import edu.unicen.surfforecaster.gwt.client.dto.AreaGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.CountryGwtDTO;

public class LocalizationUtils extends Observable{
	
	private static LocalizationUtils instance = null;
	private List<AreaGwtDTO> areas = null;
	private List<CountryGwtDTO> countries = null;
	private AsyncCallsQueue callsQueue = null;
	
	public AsyncCallsQueue getCallsQueue() {
		return callsQueue;
	}

	public void setCallsQueue(AsyncCallsQueue callsQueue) {
		this.callsQueue = callsQueue;
	}

	public static LocalizationUtils getInstance() {
        if (instance == null) {
            instance = new LocalizationUtils();
        }
        return instance;
    }
	
	private LocalizationUtils() {
		this.callsQueue = new AsyncCallsQueue();
	}
	
	public void initialize() {
		this.callsQueue.clearCalls();
		this.setAreas();
		this.setCountries();
	}
	
	/**
	 * A wrapper name for initializa method
	 */
	public void refresh() {
		this.initialize();
	}

	public List<AreaGwtDTO> getAreas() {
		List<AreaGwtDTO> result = new ArrayList<AreaGwtDTO>();
		if (this.callsQueue.allFinished()) {
			result = areas;
		}
		return result;
	}

	public void setAreas(List<AreaGwtDTO> areas) {
		this.areas = areas;
	}
	
	public void setAreas() {
		this.callsQueue.addCall("getAreas");
		SpotServices.Util.getInstance().getAreas(GWTUtils.getCurrentLocaleCode(), new AsyncCallback<List<AreaGwtDTO>>(){
			public void onSuccess(List<AreaGwtDTO> result) {
				if (result != null) {
					areas = result;
				}
				callsQueue.markAsFinished("getAreas");
				checkCallsAndNotify();
			}
				
			public void onFailure(Throwable caught) {
				//TODO maybe want to inform something when the setAreas method fails
				callsQueue.markAsFinished("getAreas");
				checkCallsAndNotify();
			}
		});
	}

	public List<CountryGwtDTO> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryGwtDTO> countries) {
		this.countries = countries;
	}
	
	public void setCountries() {
		this.callsQueue.addCall("getCountries");
		SpotServices.Util.getInstance().getCountries(GWTUtils.getCurrentLocaleCode(), new AsyncCallback<List<CountryGwtDTO>>(){
			public void onSuccess(List<CountryGwtDTO> result) {
				if (result != null) {
					countries = result;
				}
				callsQueue.markAsFinished("getCountries");
				checkCallsAndNotify();
			}
				
			public void onFailure(Throwable caught) {
				//TODO maybe want to inform something when the setCountries method fails
				callsQueue.markAsFinished("getCountries");
				checkCallsAndNotify();
			}
		});
	}
	
	public List<CountryGwtDTO> getCountries(Integer areaId) {
		List<CountryGwtDTO> countries = new ArrayList<CountryGwtDTO>();
		if (this.callsQueue.allFinished()) {
			Iterator<CountryGwtDTO> i = this.countries.iterator();
			while (i.hasNext()) {
				CountryGwtDTO country = i.next();
				if (country.getAreaDTO().getId().equals(areaId)) {
					countries.add(country);
				}
			}
		}
		return countries;
	}
	
	public void checkCallsAndNotify() {
		if (this.callsQueue.allFinished()) {
			setChanged();
			notifyObservers();
		}
	}

}
