package edu.unicen.surfforecaster.server.domain.wavewatch.gribAccess;

import java.io.File;

public interface GribAccess {

	public File getLastGrib() throws GribAccessException;

}
