/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.unicen.experimenter.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import weka.core.Instances;
import edu.unicen.experimenter.datasetgenerator.DataSet;

/**
 * 
 * @author esteban
 */
public class DataSetsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private static final int DESCRIPTION = 1;

	private final String[] columnNames = new String[] { "Selected", "DsetId",
			"Beach", "StrategyName", "StrategyOptions", "#Instances",
			"attributes" };

	private Instances data;
	private Instances preprocessed;

	private boolean[] labeled;
	private boolean[] unlabeled;
	private boolean[] test;

	private List<DataSet> dataSets;
	private boolean[] selected;

	public DataSetsTableModel() {

		data = null;
		labeled = null;
		unlabeled = null;
		test = null;

	}

	public DataSetsTableModel(final List<DataSet> dataSets) {
		setData(dataSets);
	}

	public void setData(final List<DataSet> dataSets) {
		this.dataSets = dataSets;
		selected = new boolean[dataSets.size()];

	}

	public int getColumnCount() {
		System.out.println(columnNames.length + "miedaaaaaaaa");
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
		System.out.println(columnNames[col].toString() + "miedaaaaaaaa");
		return columnNames[col].toString();
	}

	// {"Selected","DsetId",
	// "StrategyName","StrategyOptions","#Instances","Beach"};
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

		final List<DataSet> selectedDataSets = new ArrayList();
		for (int i = 0; i < selected.length; i++)
			if (selected[i]) {
				selectedDataSets.add(dataSets.get(i));
			}
		return selectedDataSets;
		//
		// List<Integer> labeledRows = new Vector<Integer>();
		// for (int i = 0; i < this.labeled.length; i++)
		// if (this.labeled[i])
		// labeledRows.add(new Integer(i));
		// return labeledRows;

	}

	// public void setSelectedRows(int[] selectedRows){
	// for(int i = 0; i < selectedRows.length; i++){
	// this.labeled[selectedRows[i]] = true;
	// //this.unlabeled[selectedRows[i]] = false;
	// //this.test[selectedRows[i]] = false;
	// }
	// fireTableRowsUpdated(0, this.labeled.length);
	// }
	//
	// public void setUnlabeledRows(int[] selectedRows){
	// for(int i = 0; i < selectedRows.length; i++){
	// //this.labeled[selectedRows[i]] = false;
	// this.unlabeled[selectedRows[i]] = true;
	// //this.test[selectedRows[i]] = false;
	// }
	// fireTableRowsUpdated(0, this.labeled.length);
	// }
	//
	// public void setTestRows(int[] selectedRows){
	// for(int i = 0; i < selectedRows.length; i++){
	// // this.labeled[selectedRows[i]] = false;
	// // this.unlabeled[selectedRows[i]] = false;
	// this.test[selectedRows[i]] = true;
	//
	// }
	// fireTableRowsUpdated(0, this.labeled.length);
	// }
	// public void setAllTestRows(){
	// for(int i = 0; i < data.numInstances(); i++){
	// this.test[i] = true;
	// }
	// fireTableRowsUpdated(0, this.labeled.length);
	// }
	// public void selectNoneRows(){
	// for (int i = 0; i < this.labeled.length; i++){
	// this.labeled[i] = false;
	// this.unlabeled[i] = false;
	// this.test[i] = false;
	// }
	// fireTableRowsUpdated(0, this.labeled.length);
	// }
	//
	// public void completeSelection(String value){
	// if (value.equals("E")){
	// for (int i = 0; i < this.labeled.length; i++){
	// if (this.labeled[i] == false && this.unlabeled[i] == false &&
	// this.test[i] == false)
	// this.labeled[i] = true;
	// }
	// }
	// else if (value.equals("NE")){
	// for (int i = 0; i < this.labeled.length; i++){
	// if (this.labeled[i] == false && this.unlabeled[i] == false &&
	// this.test[i] == false)
	// this.unlabeled[i] = true;
	// }
	// }
	// else if (value.equals("P")){
	// for (int i = 0; i < this.labeled.length; i++){
	// if (this.labeled[i] == false && this.unlabeled[i] == false &&
	// this.test[i] == false)
	// this.test[i] = true;
	// }
	// }
	// fireTableRowsUpdated(0, this.labeled.length);
	// }
	// //class: the class to be used.
	// //labeled: porcentage of instances to be added to the set of labeled
	// instances
	// //unlabeled: porcentage of instances of the given class to be added to
	// the unlabeled instances
	//
	// public void selectClass(String clase, int labeled, int unlabeled) {
	// int labeledCount = 0;
	// int unlabeledCount = 0;
	//
	// //Get the total number of instances of the given class
	// int classCount = getCount(clase);
	// //Quantity of instances of this class to be added to the labeled set
	// labeled = classCount * labeled / 100;
	// //Quantity of instances of this class to be added to the unlabeled set
	// unlabeled = classCount * unlabeled / 100;
	//
	// //From the all instances filter only the class given and add the
	// corresponding quantity to each sset.
	// int[] labeledIndex = new int[labeled];
	// int[] unlabeledIndex = new int[unlabeled];
	// for (int i = 0; i < data.numInstances(); i++) {
	// if (data.instance(i).stringValue(
	// data.instance(i).classIndex())
	// .equals(clase)) {
	// if (labeledCount < labeled){
	// labeledIndex[labeledCount]= i;
	// labeledCount++;
	// }
	// else if (unlabeledCount < unlabeled){
	// unlabeledIndex[unlabeledCount]= i;
	// unlabeledCount++;
	// }
	//
	// }
	//
	//
	// }
	// setLabeledRows(labeledIndex);
	// setUnlabeledRows(unlabeledIndex);
	// setAllTestRows();
	// }

	private int getCount(final String clase) {
		int count;
		count = 0;
		for (int i = 0; i < data.numInstances(); i++) {
			if (data.instance(i).stringValue(data.instance(i).classIndex())
					.equals(clase)) {
				count++;

			}
		}
		return count;
	}

}
