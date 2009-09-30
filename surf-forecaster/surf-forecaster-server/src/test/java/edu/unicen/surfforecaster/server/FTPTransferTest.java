/**
 * 
 */
package edu.unicen.surfforecaster.server;

import org.junit.Test;

import com.enterprisedt.net.ftp.EventListener;
import com.enterprisedt.net.ftp.FileTransferClient;
import com.enterprisedt.util.debug.Level;
import com.enterprisedt.util.debug.Logger;

/**
 * @author esteban
 * 
 */

public class FTPTransferTest {
	@Test
	public void transferNOAAFile() {
		// extract command-line arguments
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
			final EventListener listener = new MyEventLister();
			ftp.setEventListener(listener);

			// set remote host
			ftp.setRemoteHost(host);
			ftp.setUserName(username);
			ftp.setPassword(password);

			// connect to the server
			log.info("Connecting to server " + host);
			ftp.connect();
			log.info("Connected and logged in to server " + host);

			// log.info("Uploading file");
			// ftp.uploadFile(filename, filename);
			// log.info("File uploaded");

			log.info("Downloading file");
			((MyEventLister) listener).setFileSize(filename, ftp
					.getSize(filename));
			ftp.downloadFile("e.txt", filename);
			log.info("File downloaded");
			//
			// log.info("Deleting remote file");
			// ftp.deleteFile(filename);
			// log.info("Deleted remote file");
			//
			// final File file = new File(filename + ".copy");
			// file.delete();
			// log.info("Deleted local file copy");

			// Shut down client
			log.info("Quitting client");
			ftp.disconnect();

			log.info("Example complete");

		} catch (final Exception e) {
			e.printStackTrace();
		}
		// try {
		//
		// final FTPClient client = new FTPClient("polar.ncep.noaa.gov");
		//
		// client.login("anonymous", "a@.com");
		// final String[] dir = client.dir();
		//
		// for (int i = 0; i < dir.length; i++) {
		// System.out.println(dir[i]);
		// }
		// client
		// .get("",
		// "/pub/waves/develop/multi_1p.latest_run/multi_1p.glo_30mext.DIRPW.grb2");
		// } catch (final Exception e) {
		// e.printStackTrace();
		// }
	}
	// @Test
	// public void transferNOAAFile() {
	// final String server;
	// final FtpClient client = new FtpClient();
	// final File file = new File("e2.txt");
	// OutputStream out;
	// try {
	// out = new FileOutputStream(file);
	// try {
	// client.setPassive(true);
	// client.open("polar.ncep.noaa.gov");
	// client.user("anonymous");
	// client.pass("la@as.com");
	//
	// // final Vector<String> nameList = client
	// // .getNameList("/pub/waves/develop/multi_1p.latest_run");
	// //
	// // for (final Iterator iterator = nameList.iterator(); iterator
	// // .hasNext();) {
	// // final String string = (String) iterator.next();
	// // System.out.println(string);
	// // }
	// // // client.mode(FtpClientProtocol.STRU_FILE);
	// // client.setSocketTimeout(10000);
	// client.noop();
	// client.type(FtpClientProtocol.TYPE_IMAGE);
	// client.mode(FtpClientProtocol.MODE_STREAM);
	// client.noop();
	// client
	// .get(
	// "/pub/waves/develop/multi_1p.latest_run/multi_1p.glo_30mext.DIRPW.grb2",
	// out);
	// client.noop();
	// } catch (final IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (final ServerResponseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// } catch (final FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
}
