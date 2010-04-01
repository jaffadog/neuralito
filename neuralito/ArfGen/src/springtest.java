import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 */

/**
 * @author esteban
 * 
 */
public class springtest {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		final ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "dao.xml", });
	}

}
