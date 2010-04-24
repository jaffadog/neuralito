/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.unicen.experimenter.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import edu.unicen.experimenter.datasetgenerator.DataSet;

/**
 * 
 * @author esteban
 */
public class DataSetsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final String[] columnNames = new String[] { "SelectedTrain",
			"SelectedTest", "DsetId", "Beach", "DataSetGroupName",
			"StrategyName", "StrategyOptions", "#Instances", "attributes" };

	private List<DataSet> dataSets;
	private boolean[] trainSelected;

	private boolean[] testSelected;

	public DataSetsTableModel() {

	}

	public DataSetsTableModel(final List<DataSet> dataSets) {
		setData(dataSets);

	}

	public void setData(final List<DataSet> dataSets) {
		this.dataSets = dataSets;
		trainSelected = new boolean[dataSets.size()];
		testSelected = new boolean[dataSets.size()];
		fireTableDataChanged();

	}

	public int getColumnCount() {
		if (columnNames != null)
			return columnNames.length;
		else
			return 0;
	}

	public int getRowCount() {
		if (dataSets != null)
			return dataSets.size();
		else
			return 0;
	}

	@Override
	public String getColumnName(final int col) {
		return columnNames[col].toString();
	}

	public Object getValueAt(final int col, final int row) {
		// tengo que invertir filas por columnas no se porque razon
		if (dataSets == null)
			return null;
		else {
			final DataSet dataSet = dataSets.get(col);
			switch (row) {
			case 0:
				return trainSelected[col];
			case 1:
				return testSelected[col];
			case 2:
				return dataSet.getId();
			case 3:
				return dataSet.getBeach();
			case 4:
				return dataSet.getDataSetGroup();
			case 5:
				return dataSet.getGenerationStrategy().getName();
			case 6:
				return dataSet.getStrategyOptionString();
			case 7:
				return dataSet.getNumberOfInstances();
			case 8:
				return dataSet.getAttributes_String();
			default:
				return new Object();
			}
		}
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		switch (col) {
		case 0:
			trainSelected[row] = ((Boolean) value).booleanValue();

			break;
		case 1:
			testSelected[row] = ((Boolean) value).booleanValue();
			break;
		}
		// fireTableRowsUpdated(0, trainSelected.length);
		fireTableDataChanged();

	}

	/*
	 * public Class<?> getColumnClass(int col) { return getValueAt(0,
	 * col).getClass(); }
	 */

	@Override
	public Class<?> getColumnClass(final int column) {
		switch (column) {
		case 0:
			return Boolean.class;
		case 1:
			return Boolean.class;
		case 2:
			return Integer.class;
		case 3:
			return String.class;
		case 4:
			return String.class;
		case 5:
			return String.class;
		case 6:
			return String.class;
		case 7:
			return Integer.class;
		case 8:
			return String.class;
		default:
			return Object.class;
		}
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		if (col == 0 || col == 1)
			return true;
		return false;
	}

	public List<DataSet> getSelectedTrainDataSets() {

		final List<DataSet> selectedDataSets = new ArrayList<DataSet>();
		for (int i = 0; i < trainSelected.length; i++)
			if (trainSelected[i]) {
				selectedDataSets.add(dataSets.get(i));
			}
		return selectedDataSets;
	}

	public List<DataSet> getSelectedTestDataSets() {

		final List<DataSet> selectedDataSets = new ArrayList<DataSet>();
		for (int i = 0; i < testSelected.length; i++)
			if (testSelected[i]) {
				selectedDataSets.add(dataSets.get(i));
			}
		return selectedDataSets;
	}

	/**
	 * @param dsName
	 */
	public void selectByName(final String dsName) {
		trainSelected = new boolean[dataSets.size()];
		for (int i = 0; i < dataSets.size(); i++) {
			if (dataSets.get(i).getDataSetGroup().equals(dsName)) {
				trainSelected[i] = true;
			}

		}
		fireTableDataChanged();

	}

	/**
	 * @param dsName
	 */
	public void selectTrainSetsByName(final String dsName) {
		trainSelected = new boolean[dataSets.size()];
		for (int i = 0; i < dataSets.size(); i++) {
			if (dataSets.get(i).getDataSetGroup().equals(dsName)) {
				trainSelected[i] = true;
			}

		}
		fireTableDataChanged();

	}

	/**
	 * @param dsName
	 */
	public void selectTestSetsByName(final String dsName) {
		testSelected = new boolean[dataSets.size()];
		for (int i = 0; i < dataSets.size(); i++) {
			if (dataSets.get(i).getDataSetGroup().equals(dsName)) {
				testSelected[i] = true;
			}

		}
		fireTableDataChanged();

	}

}
