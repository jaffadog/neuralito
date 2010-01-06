package edu.unicen.surfforecaster.server.domain.wavewatch;

import java.io.File;

import org.apache.log4j.Logger;


public class FtpGribAccess implements GribAccess {

	private String host;
	private String userName;
	private String password;
	private String downloadFilePath;
	private String destinationFilePath;
	@Override
	public File getLastGrib() throws GribAccessException {
		return this.downloadFile();
	}
	public String getPassword() {
		return password;
	}



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
	 * @throws GribAccessException
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	protected File downloadFile() throws GribAccessException {
//		FileTransferClient ftp = null;
		try {
//			ftp = new FileTransferClient();
//			final EventListener listener = new FtpDownloaderListener();
//			ftp.setEventListener(listener);
//
//			// set remote host
//			ftp.setRemoteHost(this.getHost());
//			ftp.setUserName(this.getUserName());
//			ftp.setPassword(this.getPassword());
//			// connect to the server
//			log.info("Connecting to FTP server " + getHost());
//			ftp.connect();
//			log.info("Connected and logged in to server " + getHost());
//			// Download file
//			log.info("Downloading file: " + getDownloadFilePath());
//			((FtpDownloaderListener) listener).setFileSize(getDownloadFilePath(),
//					ftp.getSize(getDownloadFilePath()));
//			ftp.downloadFile(getDestinationFilePath(), getDownloadFilePath());
//			log.info("File downloaded");
//			// Put the downloaded file in the job context. So listeners can
//			// notify observers.
//			// Shut down client
//			ftp.disconnect();
			return new File(getDestinationFilePath());
		} catch (final Exception e) {
			throw new GribAccessException(e);
		}
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

}
