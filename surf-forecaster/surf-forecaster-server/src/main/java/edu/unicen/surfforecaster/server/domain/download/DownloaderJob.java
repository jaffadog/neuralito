/**
 * 
 */
package edu.unicen.surfforecaster.server.domain.download;

import java.io.File;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.enterprisedt.net.ftp.EventListener;
import com.enterprisedt.net.ftp.FileTransferClient;

/**
 * Download a file from an FTP server.
 * 
 * @author esteban
 * 
 */
public class DownloaderJob extends QuartzJobBean {
	private String host;
	private String userName;
	private String password;
	public String getPassword() {
		return password;
	}

	private String downloadFilePath;
	private String destinationFilePath;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}

	public String getDestinationFilePath() {
		return destinationFilePath;
	}

	public void setDestinationFilePath(String destinationFilePath) {
		this.destinationFilePath = destinationFilePath;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static final String DownloadedFile = "file";
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
			ftp = new FileTransferClient();
			 final EventListener listener = new DownloaderListener();
			 ftp.setEventListener(listener);

			// set remote host
			ftp.setRemoteHost(this.getHost());
			ftp.setUserName(this.getUserName());
			ftp.setPassword(this.getPassword());
			// connect to the server
			log.info("Connecting to FTP server " + getHost());
			ftp.connect();
			log.info("Connected and logged in to server " + getHost());
			// Download file
			log.info("Downloading file: " + getDownloadFilePath());
			 ((DownloaderListener) listener).setFileSize(getDownloadFilePath(), ftp
			 .getSize(getDownloadFilePath()));
			ftp.downloadFile(getDestinationFilePath(), getDownloadFilePath());
			log.info("File downloaded");
			// Put the downloaded file in the job context. So listeners can
			// notify observers.
			context.put(DownloadedFile, new File(getDestinationFilePath()));
			// Shut down client
			ftp.disconnect();
		} catch (final Exception e) {
			log.error(e);
			throw new JobExecutionException();
		}
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	

}
