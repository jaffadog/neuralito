package edu.unicen.surfforecaster.gwt.client.panels;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

import edu.unicen.surfforecaster.common.exceptions.NeuralitoException;
import edu.unicen.surfforecaster.common.services.dto.Unit;
import edu.unicen.surfforecaster.gwt.client.dto.WekaForecasterEvaluationGwtDTO;
import edu.unicen.surfforecaster.gwt.client.utils.GWTUtils;
import edu.unicen.surfforecaster.gwt.client.utils.UnitConverter;
import edu.unicen.surfforecaster.gwt.client.utils.UnitTranslator;

public class WekaEvaluationResultsPanel extends FlexTable {
	
	public WekaEvaluationResultsPanel(List<WekaForecasterEvaluationGwtDTO> results) {
		Label lblTitle = new Label(GWTUtils.LOCALE_CONSTANTS.wekaEvaluationPanelTitle() + ": ");
		lblTitle.addStyleName("gwt-Label-WekaPanelTitle");
		this.setWidget(0, 0, lblTitle);
		Unit heightUnitTarget = Unit.Meters;
		int index = 1;
		Iterator<WekaForecasterEvaluationGwtDTO> i = results.iterator();
		while (i.hasNext()) {
			WekaForecasterEvaluationGwtDTO wekaForecasterEvaluationDTO = i.next();
			Label lblForecasterName = new Label(wekaForecasterEvaluationDTO.getClassifierName());
			lblForecasterName.addStyleName("gwt-Label-WekaPanel-ClassifierName");
			this.setWidget(index, 0, lblForecasterName);
			index++;
			Label lblCorrelation = new Label(GWTUtils.LOCALE_CONSTANTS.correlation() + ": " + NumberFormat.getFormat("###.##").format(wekaForecasterEvaluationDTO.getCorrelation() * 100) + "%");
			lblCorrelation.addStyleName("gwt-Label-WekaPanelLabel");
			this.setWidget(index, 0, lblCorrelation);
			index++;
			Label lblMeanError;
			try {
				lblMeanError = new Label(GWTUtils.LOCALE_CONSTANTS.meanAbsoluteError() + ": " + NumberFormat.getFormat("######.##").format(UnitConverter.convertValue(wekaForecasterEvaluationDTO.getMeanAbsoluteError(), Unit.Meters, heightUnitTarget)) + UnitTranslator.getUnitAbbrTranlation(heightUnitTarget));
				lblMeanError.addStyleName("gwt-Label-WekaPanelLabel");
				this.setWidget(index, 0, lblMeanError);
				index++;
			} catch (NeuralitoException e) {
				// TODO ver como manejar esta exvepcion de conversion de unidades
				e.printStackTrace();
			}
			//Insert a separator
			Label emptyLabel = new Label();
			emptyLabel.setHeight("10px");
			this.setWidget(index, 0, emptyLabel);
			index++;
		}
	}
}
