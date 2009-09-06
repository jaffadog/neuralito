package edu.unicen.surfforecaster.server.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import edu.unicen.surfforecaster.server.dao.SurfSpotDAO;
import edu.unicen.surfforecaster.server.domain.entity.SurfSpot;

public class SurfSpotDAOHibernateImplTest extends TestCase
{
  private SurfSpotDAO surfSpotDAO;
  
  public void setUp() throws Exception
  {
    super.setUp();
    ApplicationContext context = new ClassPathXmlApplicationContext("/spring-hibernate-jpa-config.xml");
    surfSpotDAO = (SurfSpotDAO)context.getBean("surfSpotDAO");
  }

  /**
   * Simple tests excersing the various methods of MusicDAO
   */
  public void test()
  {
    SurfSpot surfSpot = new SurfSpot();
    double latitude=2L;
	surfSpot.setLatitude(latitude);
    double longitude=1L;
	surfSpot.setLongitude(longitude);
    String name="biologia";
	surfSpot.setName(name);
    surfSpotDAO.saveSurfSpot(surfSpot);
    assertNotNull(surfSpot.getId());
    
    //Test "loadAll"
    Collection<SurfSpot> loadedArtists = surfSpotDAO.getAllSurfSpots();
    assertEquals(1, loadedArtists.size());
  }    
}