/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.Point;

/**
 * @author esteban
 * 
 */
@Entity
public class ValidGridPoints {

	/**
	 * The id for ORM pupose.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 11)
	private Integer id;

	public Integer getId() {
		return id;
	}

	/**
	 * 
	 */
	public ValidGridPoints() {
		// ORM purpose
	}

	@OneToMany(fetch = FetchType.EAGER)
	private Collection<Point> validPoints = new ArrayList<Point>();

	/**
	 * @return
	 */
	public Collection<Point> getValidGridPoints() {
		return validPoints;
	}

	/**
	 * @param validPoints2
	 */
	public void setValidPoints(final Collection<Point> validPoints2) {
		validPoints = validPoints2;
	}

	/**
	 * @param point1
	 * @return
	 */
	public boolean isValid(final Point point1) {
		for (final Iterator iterator = validPoints.iterator(); iterator
				.hasNext();) {
			final Point point = (Point) iterator.next();
			if (point.equals(point1))
				return true;
		}
		return false;
	}

}
