package edu.unicen.surfforecaster.gwt.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.unicen.surfforecaster.common.services.ForecastService;
import edu.unicen.surfforecaster.common.services.dto.VisualObservationDTO;
import edu.unicen.surfforecaster.common.services.dto.WekaForecasterEvaluationDTO;
import edu.unicen.surfforecaster.gwt.server.util.VisualObsDTOsLoader;
import edu.unicen.surfforecaster.server.services.ForecastServiceImplementation;

/**
 * servlet to handle file upload requests
 * 
 * @author maxi
 * 
 */
public class FileUploadServicesImpl extends ServicesImpl {

	private static final long serialVersionUID = 1L;
	private ForecastService forecastService;
	/**
	 * Logger.
	 */
	Logger logger = Logger.getLogger(FileUploadServicesImpl.class);

	/**
	 * @param service the service to set
	 */
	public void setForecastService(final ForecastService service) {
		forecastService = service;
	}

	/**
	 * @return the forecast service
	 */
	public ForecastService getForecastService() {
		return forecastService;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {

		Integer spotId = null;
		Integer hour = null;
		Integer hour2 = null;
		Integer minutes = null;
		Integer minutes2 = null;
		Float latitudeGridPoint = null;
		Float longitudeGridPoint = null;
		Vector<VisualObservationDTO> obsData = null;
		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			try {
				logger.log(Level.INFO, "FileUploadServicesImpl - doPost - Parsing visual observations data...");
				List<FileItem> items = upload.parseRequest(req);
				for (FileItem item : items) {
					if (item.isFormField()) {
						if (item.getFieldName().trim().equals("spotIdFormElement"))
							spotId = new Integer(item.getString());
						if (item.getFieldName().trim().equals("hourFormElement"))
							hour = new Integer(item.getString());
						if (item.getFieldName().trim().equals("hour2FormElement"))
							hour2 = new Integer(item.getString());
						if (item.getFieldName().trim().equals("minutesFormElement"))
							minutes = new Integer(item.getString());
						if (item.getFieldName().trim().equals("minutes2FormElement"))
							minutes2 = new Integer(item.getString());
						if (item.getFieldName().trim().equals("latitudeGridPointFormElement"))
							latitudeGridPoint = new Float(item.getString());
						if (item.getFieldName().trim().equals("longitudeGridPointFormElement"))
							longitudeGridPoint = new Float(item.getString());
					}

					if (!item.isFormField()&& item.getFieldName().trim().equals("uploadFormElement")) {
						String fileName = item.getName();
						// get only the file name not whole path
						if (fileName != null) {
							fileName = FilenameUtils.getName(fileName);
						}
						InputStream is = item.getInputStream();

						logger.log(Level.INFO, "FileUploadServicesImpl - doPost - Starting parse observations file: " + fileName + "...");
						if (!fileName.trim().equals("")) {
							obsData = VisualObsDTOsLoader.getInstance().loadObsData(is);
						}
					}
				}

				if (obsData != null && spotId != null && hour != null && minutes != null && hour2 != null && minutes2 != null && latitudeGridPoint != null && longitudeGridPoint != null) {
					String trainningResp = this.sendData(spotId, obsData, hour, hour2, minutes, minutes2, latitudeGridPoint, longitudeGridPoint);
					sendResponseToClient(resp, HttpServletResponse.SC_OK, "OK", trainningResp);
				} else
					logger.log(Level.INFO, "FileUploadServicesImpl - doPost - Couldn't send observation data. Some form data or the obsData are null...");

			} catch (Exception e) {
				logger.log(Level.INFO, "FileUploadServicesImpl - doPost - An error occurred while parsing the file : " + e.getMessage());
				sendResponseToClient(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ERROR", "An error occurred while parsing the observations file");
				//resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while parsing the observations file");
			}

		} else {
			logger.log(Level.INFO, "Request contents type is not supported by the servlet.");
			sendResponseToClient(resp, HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "ERROR", "Request contents type is not supported by the servlet.");
			//resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Request contents type is not supported by the servlet.");
		}
	}

	private String sendData(Integer spotId, Vector<VisualObservationDTO> obsData,
			Integer hour, Integer hour2, Integer minutes, Integer minutes2, Float latitudeGridPoint, Float longitudeGridPoint) {
		logger.log(Level.INFO,"FileUploadServicesImpl - sendData - Sending observation data to forecastServices from spot: " + spotId + "...");
		// TODO send data to forecast service
		HashMap<String, Serializable> options = new HashMap<String, Serializable>();
		options.put("latitudeGridPoint1", latitudeGridPoint);
		options.put("longitudeGridPoint1", longitudeGridPoint);
		options.put("utcSunriseHour", hour);
		options.put("utcSunriseMinute", minutes);
		options.put("utcSunsetHour", hour2);
		options.put("utcSunsetMinute", minutes2);
		
		logger.log(Level.INFO, "FileUploadServicesImpl - sendData - " + spotId + " >> " + hour + " >> " + minutes + " >> " + hour2 + " >> " + minutes2 + " >> " + obsData + " >> " + latitudeGridPoint + " >> " + longitudeGridPoint);
		
		WekaForecasterEvaluationDTO result = forecastService.createWekaForecaster(obsData, spotId, options);
		
		logger.log(Level.INFO, "FileUploadServicesImpl - sendData - WekaForecaster created!.");
		
		return "Correlation: " + result.getCorrelation() + "|Mean absolute error: " + result.getMeanAbsoluteError();

	}
	
	private void sendResponseToClient(HttpServletResponse resp, int statusCode, String status, String message) {
		try {
			resp.setStatus(statusCode);
			resp.getWriter().print(status + "|" + message);
			resp.flushBuffer();
		} catch (IOException e) {
			logger.log(Level.INFO,"FileUploadServicesImpl - sendResponseToClient - Couldn't send response to client.");
			e.printStackTrace();
		}
		
	}
}