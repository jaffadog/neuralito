package edu.unicen.surfforecaster.server.domain.download;

import java.util.HashMap;
import java.util.Map;

import com.enterprisedt.net.ftp.EventListener;
import com.enterprisedt.util.debug.Logger;

/**
 * 
 */

/**
 * Custom listener to register with the FTP file downloader library(edtFTPj).
 * 
 * @author esteban
 * 
 */
public class DownloaderListener implements EventListener {

	private final Map<String, Long> map = new HashMap<String, Long>();
	private Logger log = Logger.getLogger(DownloaderListener.class);
	/**
	 * @see com.enterprisedt.net.ftp.EventListener#bytesTransferred(java.lang.String,
	 *      java.lang.String, long)
	 */
	@Override
	public void bytesTransferred(final String connId,
			final String remoteFileName, final long transfered) {
		log.info("Transfered(Kb)  : " + transfered / 1024 + "/"
				+ map.get(remoteFileName) / 1024);

	}

	/**
	 * @see com.enterprisedt.net.ftp.EventListener#commandSent(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void commandSent(final String arg0, final String arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.enterprisedt.net.ftp.EventListener#downloadCompleted(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void downloadCompleted(final String arg0, final String arg1) {

	}

	/**
	 * @see com.enterprisedt.net.ftp.EventListener#downloadStarted(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void downloadStarted(final String arg0, final String arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.enterprisedt.net.ftp.EventListener#replyReceived(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void replyReceived(final String arg0, final String arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.enterprisedt.net.ftp.EventListener#uploadCompleted(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void uploadCompleted(final String arg0, final String arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.enterprisedt.net.ftp.EventListener#uploadStarted(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void uploadStarted(final String arg0, final String arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param filename
	 * @param size
	 */
	public void setFileSize(final String filename, final long size) {
		map.put(filename, size);

	}

}
