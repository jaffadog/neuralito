/**
 * 
 */
package incubator;

import java.io.File;
import java.util.Collection;

/**
 * @author esteban
 * 
 */
public interface ResultsConverter {

	/**
	 * Convert the given results into another representation. Could be an excel
	 * spreadsheet, xml file.
	 * 
	 * @param results
	 * @return
	 */
	public File convertResults(Collection<Result> results);
}
