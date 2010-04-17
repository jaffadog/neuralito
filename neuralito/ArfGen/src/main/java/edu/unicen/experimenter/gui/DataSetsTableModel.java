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

	private final String[] columnNames = new String[] { "Selected", "DsetId",
			"Beach", "StrategyName", "StrategyOptions", "#Instances",
			"attributes" };

	private List<DataSet> dataSets;
	private boolean[] selected;

	public DataSetsTableModel() {

	}

	public DataSetsTableModel(final List<DataSet> dataSets) {
		setData(dataSets);
	}

	public void setData(final List<DataSet> dataSets) {
		this.dataSets = dataSets;
		selected = new boolean[dataSets.size()];
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
				return selected[col];
			case 1:
				return dataSet.getId();
			case 2:
				return dataSet.getBeach();
			case 3:
				return dataSet.getGenerationStrategy().getName();
			case 4:
				return dataSet.getStrategyOptions();
			case 5:
				return dataSet.getNumberOfInstances();
			case 6:
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
			selected[row] = ((Boolean) value).booleanValue();
			break;

		}
		fireTableRowsUpdated(0, selected.length);
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
			return Integer.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return String.class;
		case 5:
			return String.class;
		case 6:
			return String.class;
		default:
			return Object.class;
		}
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		if (col == 0)
			return true;
		return false;
	}

	public List<DataSet> getSelectedDataSets() {

		final List<DataSet> selectedDataSets = new ArrayList<DataSet>();
		for (int i = 0; i < selected.length; i++)
			if (selected[i]) {
				selectedDataSets.add(dataSets.get(i));
			}
		return selectedDataSets;
	}

	/**
	 * @param dsName
	 */
	public void selectByName(final String dsName) {
		selected = new boolean[dataSets.size()];
		for (int i = 0; i < dataSets.size(); i++) {
			if (dataSets.get(i).getDataSetGroup().equals(dsName)) {
				selected[i] = true;
			}

		}
		fireTableDataChanged();

	}

}
