import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.server.dao.ForecastDAOHibernateImpl;
import edu.unicen.surfforecaster.server.dao.SpotDAOHibernateImpl;
import edu.unicen.surfforecaster.server.domain.WaveWatchSystem;
import edu.unicen.surfforecaster.server.domain.entity.Forecaster;
import edu.unicen.surfforecaster.server.domain.entity.Point;
import edu.unicen.surfforecaster.server.domain.entity.VisualObservation;
import edu.unicen.surfforecaster.server.domain.entity.WekaForecaster;
import edu.unicen.surfforecaster.server.domain.weka.strategy.DataSetGenerator;
import edu.unicen.surfforecaster.server.domain.weka.strategy.NoBuoyStrategy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/services.xml" })
public class DraftTests {
	@Autowired
	ForecastDAOHibernateImpl forecastDAO;

	@Autowired
	SpotDAOHibernateImpl spotDAO;

@Test
	public void timeZones(){
	System.out.println(TimeZone.getAvailableIDs());
	
	TimeZone tz = TimeZone.getTimeZone("UTC");	
	System.out.println(tz.getDisplayName());
	}
@Test
public void ser(){
Classifier cl = new LinearRegression();
VisualObservation o = new VisualObservation(2D,new Date(),Unit.Meters);
List a = new ArrayList();
a.add(o);
Point point = new Point(2F,2F);
DataSetGenerator st = new NoBuoyStrategy( forecastDAO.getModels().get("GlobalModel"), point, a);
WekaForecaster forecaster = new WekaForecaster(cl, st);
spotDAO.save(point);
forecastDAO.save(st);
forecastDAO.save(forecaster);

Forecaster forecasterById = forecastDAO.getForecasterById(forecaster.getId());
System.out.println(((WekaForecaster)forecasterById).getStrategy().getName());


}
}
