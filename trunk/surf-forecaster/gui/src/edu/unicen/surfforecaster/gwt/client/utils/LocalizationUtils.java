package edu.unicen.surfforecaster.gwt.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unicen.surfforecaster.common.services.dto.AreaDTO;
import edu.unicen.surfforecaster.common.services.dto.CountryDTO;
import edu.unicen.surfforecaster.gwt.client.SpotServices;

public class LocalizationUtils extends Observable{
	
	private static LocalizationUtils instance = null;
	private List<AreaDTO> areas = null;
	private List<CountryDTO> countries = null;
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

	public List<AreaDTO> getAreas() {
		List<AreaDTO> result = new ArrayList<AreaDTO>();
		if (this.callsQueue.allFinished()) {
			result = areas;
		}
		return result;
	}

	public void setAreas(List<AreaDTO> areas) {
		this.areas = areas;
	}
	
	public void setAreas() {
		this.callsQueue.addCall("getAreas");
		SpotServices.Util.getInstance().getAreas(new AsyncCallback<List<AreaDTO>>(){
			public void onSuccess(List<AreaDTO> result) {
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

	public List<CountryDTO> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryDTO> countries) {
		this.countries = countries;
	}
	
	public void setCountries() {
		this.callsQueue.addCall("getCountries");
		SpotServices.Util.getInstance().getCountries(new AsyncCallback<List<CountryDTO>>(){
			public void onSuccess(List<CountryDTO> result) {
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
	
	public List<CountryDTO> getCountries(Integer areaId) {
		List<CountryDTO> countries = new ArrayList<CountryDTO>();
		if (this.callsQueue.allFinished()) {
			Iterator<CountryDTO> i = this.countries.iterator();
			while (i.hasNext()) {
				CountryDTO country = i.next();
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
