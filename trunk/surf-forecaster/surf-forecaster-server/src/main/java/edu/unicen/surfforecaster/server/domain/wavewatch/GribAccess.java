package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.File;

public interface GribAccess {

	public File getLastGrib() throws GribAccessException;

}
