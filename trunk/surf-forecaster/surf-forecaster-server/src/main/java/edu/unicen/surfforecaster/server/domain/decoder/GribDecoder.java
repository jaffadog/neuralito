/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.decoder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import edu.unicen.surfforecaster.server.domain.entity.Forecast;
import edu.unicen.surfforecaster.server.domain.wavewatch.WaveWatchParameter;

/**
 * @author esteban
 * 
 */
public interface GribDecoder extends Serializable{


	public List<String> listParameters(File file) throws IOException;

	public Collection<Forecast> decodeForecastForTime(File file,
			List<WaveWatchParameter> parameters, int time) throws IOException;

	public Collection<Forecast> decodeForecastForTime(Collection<File> files,
			List<WaveWatchParameter> parameters, int time) throws IOException;

	public int getTimes(File file) throws IOException;
}
