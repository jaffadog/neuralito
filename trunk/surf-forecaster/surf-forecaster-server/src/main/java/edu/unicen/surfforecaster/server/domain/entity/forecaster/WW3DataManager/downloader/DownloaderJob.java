/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.entity.forecaster.WW3DataManager.downloader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.enterprisedt.net.ftp.FileTransferClient;

/**
 * Download a file from an FTP server.
 * 
 * @author esteban
 * 
 */
public class DownloaderJob extends QuartzJobBean {
	public static final String DownloadedFiles = "files";
	Logger log = Logger.getLogger(this.getClass());

	/**
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(final JobExecutionContext context)
			throws JobExecutionException {
		// System.out.println("THE DOWNLOADER HAS BEEN EXECUTED AND FAILED : ");
		// System.out.println(context.getMergedJobDataMap().get("esteban"));
		// System.out.println("refire: " + context.getRefireCount());
		FileTransferClient ftp = null;
		try {
			log.info("Creating FTP client");
			ftp = new FileTransferClient();
			// final EventListener listener = new DownloaderListener();
			// ftp.setEventListener(listener);

			// set remote host
			ftp.setRemoteHost(getHost());
			ftp.setUserName(getUsername());
			ftp.setPassword(getPassword());
			// connect to the server
			log.info("Connecting to server " + getHost());
			ftp.connect();
			log.info("Connected and logged in to server " + getHost());
			// Download file
			log.info("Downloading file: " + getFilePath());
			// ((DownloaderListener) listener).setFileSize(getFilePath(), ftp
			// .getSize(getFilePath()));
			ftp.downloadFile(getDestinationFileName(), getFilePath());
			log.info("File downloaded");
			final Collection<File> files = new ArrayList<File>();
			files.add(new File(getDestinationFileName()));
			// Put the downloaded files in the job context. So listeners can
			// notify observers.
			context.put(DownloadedFiles, files);
			// Shut down client
			log.info("Quitting FTPclient");
			ftp.disconnect();
		} catch (final Exception e) {
			log.error(e);
			throw new JobExecutionException();
		}
	}

	/**
	 * @return
	 */
	private String getDestinationFileName() {
		return "src/main/resources/waves/latestforecast/grib2/latestForecast.grb2";
	}

	private String getHost() {
		return "polar.ncep.noaa.gov";
	}

	private String getFilePath() {
		return "/pub/waves/develop/multi_1.latest_run/multi_1.glo_30m.DIRPW.grb2";
	}

	private String getUsername() {
		return "anonymous";
	}

	private String getPassword() {
		return "a@d";
	}

}
