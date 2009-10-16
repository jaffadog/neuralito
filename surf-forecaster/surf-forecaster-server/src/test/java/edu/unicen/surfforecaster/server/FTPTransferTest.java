/**
 * 
 */
package edu.unicen.surfforecaster.server;

import org.junit.Ignore;
import org.junit.Test;

import com.enterprisedt.net.ftp.EventListener;
import com.enterprisedt.net.ftp.FileTransferClient;
import com.enterprisedt.util.debug.Level;
import com.enterprisedt.util.debug.Logger;

import edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.downloader.DownloaderListener;

/**
 * @author esteban
 * 
 */

public class FTPTransferTest {
	@Test
	@Ignore
	public void transferNOAAFile() {
		// Server parameters
		final String host = "polar.ncep.noaa.gov";
		final String username = "anonymous";
		final String password = "a@.com";

		final String filename = "/pub/waves/develop/multi_1.latest_run/multi_1.glo_30m.DIRPW.grb2";

		// set up logger so that we get some output
		final Logger log = Logger.getLogger(this.getClass());
		Logger.setLevel(Level.INFO);

		FileTransferClient ftp = null;

		try {
			// create client
			log.info("Creating FTP client");
			ftp = new FileTransferClient();
			final EventListener listener = new DownloaderListener();
			ftp.setEventListener(listener);

			// set remote host
			ftp.setRemoteHost(host);
			ftp.setUserName(username);
			ftp.setPassword(password);

			// connect to the server
			log.info("Connecting to server " + host);
			ftp.connect();
			log.info("Connected and logged in to server " + host);
			// Download file
			log.info("Downloading file");
			((DownloaderListener) listener).setFileSize(filename, ftp
					.getSize(filename));
			ftp.downloadFile("e.txt", filename);
			log.info("File downloaded");

			// Shut down client
			log.info("Quitting client");
			ftp.disconnect();

			log.info("Example complete");

		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

}
