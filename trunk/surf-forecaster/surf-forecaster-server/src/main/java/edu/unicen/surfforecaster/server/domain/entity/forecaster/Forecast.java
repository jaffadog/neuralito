/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.apache.commons.lang.Validate;

import edu.unicen.surfforecaster.common.services.dto.ForecastAttributeDTO;
import edu.unicen.surfforecaster.common.services.dto.ForecastDTO;

/**
 * @author esteban
 * 
 */
public class Forecast {
	/**
	 * Map with <attributeName, attributeValue> info. Attributes may be:
	 * waveHeight, wavePeriod, waveDirection, windSpeed.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKey(name = "attributeName")
	private final Map<String, ForecastAttribute> attributes;

	/**
	 * @param baseDate
	 * @param runDate
	 * @param forecastDate
	 * @param attributes
	 * @param ww3Forecaster
	 */
	public Forecast(final Date baseDate, final Date runDate,
			final Date forecastDate,
			final Map<String, ForecastAttribute> attributes,
			final Forecaster forecaster) {
		Validate.notNull(baseDate);
		Validate.notNull(runDate);
		Validate.notNull(forecastDate);
		Validate.notNull(attributes);
		Validate.notNull(forecaster);
		this.baseDate = baseDate;
		this.runDate = runDate;
		this.forecastDate = forecastDate;
		this.attributes = attributes;
		this.forecaster = forecaster;
	}

	/**
	 * The date this forecast has been run.
	 */
	private final Date runDate;
	/**
	 * The date from which this forecast is valid.
	 */
	private final Date baseDate;
	/**
	 * The date to which this forecast applies.
	 */
	private final Date forecastDate;

	/**
	 * Forecaster used to obtain this forecast.
	 */
	private final Forecaster forecaster;

	/**
	 * @return the attributes
	 */
	public ForecastAttribute getAttribute(final String attributeName) {
		return attributes.get(attributeName);
	}

	/**
	 * @return the runDate
	 */
	public Date getRunDate() {
		return runDate;
	}

	/**
	 * @return the baseDate
	 */
	public Date getBaseDate() {
		return baseDate;
	}

	/**
	 * @return the forecastDate
	 */
	public Date getForecastDate() {
		return forecastDate;
	}

	/**
	 * @return the forecaster
	 */
	public Forecaster getForecaster() {
		return forecaster;
	}

	/**
	 * @return
	 */
	public ForecastDTO getDTO() {
		final Collection<ForecastAttribute> values = attributes.values();
		final Map<String, ForecastAttributeDTO> map = new HashMap<String, ForecastAttributeDTO>();
		for (final Iterator iterator = values.iterator(); iterator.hasNext();) {
			final ForecastAttribute forecastAttribute = (ForecastAttribute) iterator
					.next();
			map.put(forecastAttribute.getAttributeName(), forecastAttribute
					.getDTO());
		}
		return new ForecastDTO(getBaseDate(), getRunDate(), getForecastDate(),
				map);
	}

}
