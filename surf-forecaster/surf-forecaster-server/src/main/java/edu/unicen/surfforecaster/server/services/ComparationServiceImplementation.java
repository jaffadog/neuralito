/**
 * 
 */
package edu.unicen.surfforecaster.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import edu.unicen.surfforecaster.common.exceptions.ErrorCode;
import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.ComparationService;
import edu.unicen.surfforecaster.common.services.dto.ComparationDTO;
import edu.unicen.surfforecaster.server.dao.ComparationDAO;
import edu.unicen.surfforecaster.server.dao.SpotDAO;
import edu.unicen.surfforecaster.server.dao.UserDAO;
import edu.unicen.surfforecaster.server.domain.entity.Comparation;
import edu.unicen.surfforecaster.server.domain.entity.Spot;
import edu.unicen.surfforecaster.server.domain.entity.User;

/**
 * @author esteban
 * 
 */
public class ComparationServiceImplementation implements ComparationService {

	private UserDAO userDAO;
	private SpotDAO spotDAO;
	private ComparationDAO comparationDAO;

	/**
	 * @see edu.unicen.surfforecaster.common.services.ComparationService#addComparation(java.lang.String,
	 *      java.lang.String, java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional
	public ComparationDTO addComparation(final String comparationName,
			final String comparationDescription, final Integer userId,
			final List<Integer> spotsIds) throws NeuralitoException {
		validateUserIdExists(userId);
		validateSpotsExists(spotsIds);
		final User user = userDAO.getUserByUserId(userId);
		final List<Spot> spots = new ArrayList<Spot>();
		for (final Integer spotId : spotsIds) {
			final Spot spot = spotDAO.getSpotById(spotId);
			spots.add(spot);
		}
		final Comparation comparation = new Comparation(comparationName,
				comparationDescription, user, spots);
		comparationDAO.saveComparation(comparation);
		return comparation.getDTO();
	}

	/**
	 * @param spotsIds
	 * @throws NeuralitoException
	 */
	private void validateSpotsExists(final List<Integer> spotsIds)
			throws NeuralitoException {
		for (final Integer integer : spotsIds) {
			if (spotDAO.getSpotById(integer) == null)
				throw new NeuralitoException(ErrorCode.SPOT_ID_DOES_NOT_EXISTS);
		}
	}

	/**
	 * @param userId
	 * @throws NeuralitoException
	 */
	private void validateUserIdExists(final Integer userId)
			throws NeuralitoException {
		if (userDAO.getUserByUserId(userId) == null)
			throw new NeuralitoException(ErrorCode.USER_ID_DOES_NOT_EXIST);

	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ComparationService#getComparationsForUserId(java.lang.Integer)
	 */
	@Override
	@Transactional
	public List<ComparationDTO> getComparationsForUserId(final Integer userId)
			throws NeuralitoException {
		validateUserIdExists(userId);
		final User user = userDAO.getUserByUserId(userId);
		final Set<Comparation> comparations = user.getComparations();
		final List<ComparationDTO> dtos = new ArrayList<ComparationDTO>();
		for (final Comparation comparation : comparations) {
			dtos.add(comparation.getDTO());
		}
		return dtos;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ComparationService#removeComparation(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void removeComparation(final Integer comparationId)
			throws NeuralitoException {
		final Comparation comparation = comparationDAO
				.getComparationById(comparationId);
		comparationDAO.remove(comparation);
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ComparationService#updateComparation(java.lang.Integer,
	 *      java.lang.String, java.util.List)
	 */
	@Override
	@Transactional
	public ComparationDTO updateComparation(final Integer comparationId,
			final String comparationDescription, final List<Integer> spotsIds)
			throws NeuralitoException {
		final List<Spot> spots = new ArrayList<Spot>();
		for (final Integer spotId : spotsIds) {
			final Spot spot = spotDAO.getSpotById(spotId);

			spots.add(spot);
		}
		final Comparation comparation = comparationDAO
				.getComparationById(comparationId);

		comparation.setDescription(comparationDescription);

		comparation.setSpots(spots);

		comparationDAO.saveComparation(comparation);
		return comparation.getDTO();
		// return null;

	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @param userDAO
	 *            the userDAO to set
	 */
	public void setUserDAO(final UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @param spotDAO
	 *            the spotDAO to set
	 */
	public void setSpotDAO(final SpotDAO spotDAO) {
		this.spotDAO = spotDAO;
	}

	/**
	 * @param comparationDAO
	 *            the comparationDAO to set
	 */
	public void setComparationDAO(final ComparationDAO comparationDAO) {
		this.comparationDAO = comparationDAO;
	}

	/**
	 * @see edu.unicen.surfforecaster.common.services.ComparationService#getComparationById(java.lang.Integer)
	 */
	@Override
	@Transactional
	public ComparationDTO getComparationById(final Integer comparationId) {
		final Comparation comparation = comparationDAO
				.getComparationById(comparationId);
		if (comparation != null)
			return comparation.getDTO();
		return null;

	}

}
