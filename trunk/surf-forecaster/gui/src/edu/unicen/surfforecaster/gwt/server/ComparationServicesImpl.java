package edu.unicen.surfforecaster.gwt.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ComparationService;
import edu.unicen.surfforecaster.common.services.dto.ComparationDTO;
import edu.unicen.surfforecaster.common.services.dto.SpotDTO;
import edu.unicen.surfforecaster.gwt.client.ComparationServices;
import edu.unicen.surfforecaster.gwt.client.dto.ComparationGwtDTO;
import edu.unicen.surfforecaster.gwt.client.dto.SpotGwtDTO;

@SuppressWarnings("serial")
public class ComparationServicesImpl extends ServicesImpl implements ComparationServices {
	
	private ComparationService comparationService;

	/**
	 * @param service the service to set
	 */
	public void setComparationService(final ComparationService service) {
		comparationService = service;
	}

	/**
	 * @return the comparation service
	 */
	public ComparationService getComparationService() {
		return comparationService;
	}

	@Override
	public Integer addComparation(String comparationName, String comparationDescription, List<Integer> spotsIds) throws NeuralitoException {
		logger.log(Level.INFO,"ComparationServicesImpl - addComparation - Saving comparation: " + comparationName + "'...");
		
		if (super.hasAccessTo("saveComparation")){
			final Integer userId = super.getLoggedUser().getId();
			
			logger.log(Level.INFO,"ComparationServicesImpl - addComparation - Comparation saved successfully.");
			ComparationDTO comparation = comparationService.addComparation(comparationName, comparationDescription, userId, spotsIds);
			return comparation.getId();
		}
		logger.log(Level.INFO,"ComparationServicesImpl - addComparation - Permissions denied to the current user to perform this action.");
		return null;
	}

	@Override
	public ComparationGwtDTO getComparationById(Integer comparationId) {
		logger.log(Level.INFO,"ComparationServicesImpl - getComparationsById - Retrieving comparation: " + comparationId + "...");
		return this.getComparationGwtDTO(comparationService.getComparationById(comparationId));
	}
	

	@Override
	public List<ComparationGwtDTO> getComparationsForUserId() throws NeuralitoException {
		logger.log(Level.INFO,"ComparationServicesImpl - getComparationsForUserId - Retrieving spots comparations'...");
		
		if (super.hasAccessTo("getComparations")){
			final Integer userId = super.getLoggedUser().getId();
			List<ComparationGwtDTO> result = new ArrayList<ComparationGwtDTO>();
			Iterator<ComparationDTO> i = comparationService.getComparationsForUserId(userId).iterator();
			while (i.hasNext()) {
				ComparationDTO comparationDTO = i.next();
				result.add(this.getComparationGwtDTO(comparationDTO));
			}
			logger.log(Level.INFO,"ComparationServicesImpl - getComparationsForUserId - " + result.size() + " Comparations retrieved");
			return result;
		}
		logger.log(Level.INFO,"ComparationServicesImpl - getComparationsForUserId - Permissions denied to the current user to perform this action.");
		return null;
	}

	@Override
	public boolean removeComparation(Integer comparationId) throws NeuralitoException {
		logger.log(Level.INFO,"ComparationServicesImpl - removeComparation - Deleting comparation: " + comparationId + "'...");
		
		if (super.hasAccessTo("deleteComparation")){
			final Integer userId = super.getLoggedUser().getId();
			if (userId.intValue() == this.getComparationById(comparationId).getUserId().intValue()) {
				comparationService.removeComparation(comparationId);
				logger.log(Level.INFO,"ComparationServicesImpl - removeComparation - Comparation deleted successfully.");
				return true;
			} else {
				logger.log(Level.INFO,"ComparationServicesImpl - removeComparation - The current user is not the owner of this comparation.");
				return false;
			}
		}
		logger.log(Level.INFO,"ComparationServicesImpl - removeComparation - Permissions denied to the current user to perform this action.");
		return false;
	}

	@Override
	public Integer updateComparation(Integer comparationId, String comparationDescription, List<Integer> spotsIds) throws NeuralitoException {
		logger.log(Level.INFO,"ComparationServicesImpl - updateComparation - Updating comparation: " + comparationId + "'...");
		if (super.hasAccessTo("saveComparation")){
			final Integer userId = super.getLoggedUser().getId();
			if (userId.intValue() == this.getComparationById(comparationId).getUserId().intValue()) {
				ComparationDTO comparation = comparationService.updateComparation(comparationId, comparationDescription, spotsIds);
				logger.log(Level.INFO,"ComparationServicesImpl - updateComparation - Comparation saved successfully.");
				return comparation.getId();
			} else {
				logger.log(Level.INFO,"ComparationServicesImpl - updateComparation - The current user is not the owner of this comparation.");
				return null;
			}
		}
		logger.log(Level.INFO,"ComparationServicesImpl - updateComparation - Permissions denied to the current user to perform this action.");
		return null;
	}
	
	private ComparationGwtDTO getComparationGwtDTO(ComparationDTO comparationDTO) {
		List<SpotGwtDTO> spotGwtDTOs = new ArrayList<SpotGwtDTO>();
		List<SpotDTO> spotDTOs = comparationDTO.getSpots();
		Iterator<SpotDTO> i = spotDTOs.iterator();
		while (i.hasNext()) {
			SpotDTO spotDTO = i.next();
			spotGwtDTOs.add(super.getSpotGwtDTO(spotDTO));
		}
		return new ComparationGwtDTO(comparationDTO.getId(), comparationDTO.getName(), spotGwtDTOs, comparationDTO.getDescription(), comparationDTO.getUserId());
	}
	
}
