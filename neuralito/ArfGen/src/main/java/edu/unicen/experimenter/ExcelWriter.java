/**
 * 
 */
package edu.unicen.experimenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author esteban
 * 
 */
public class ExcelWriter {

	public static void generateExcel(final String fileOutput,
			final List<Result> results, final Map columnNames)
			throws IOException, WriteException {
		final WritableWorkbook workbook = Workbook.createWorkbook(new File(
				fileOutput));
		final WritableSheet sheet = workbook.createSheet("Results Sheet", 0);

		writeColumnHeaders(sheet, columnNames);
		writeResults(sheet, results, columnNames);
		workbook.write();
		workbook.close();
	}

	/**
	 * @param sheet
	 * @param results
	 *            collection of results. Each result is a map with
	 *            <attributename, value> pairs.
	 * @param columnNames
	 *            Map between excel output column names and the result
	 *            attributename.
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private static void writeResults(final WritableSheet sheet,
			final List<Result> results, final Map columnNames)
			throws RowsExceededException, WriteException {
		int currentRow = 1;
		int currentCol = 0;
		final List<String> columns = sort(columnNames.keySet());
		// MaxWidth per column.To autoadjust the columns to the largest cell.
		final int[] columnWidth = new int[columnNames.size()];
		initArray(columnWidth, columns);
		for (final Result result : results) {
			for (final String columnName : columns) {
				final String data = result.getResult((String) columnNames
						.get(columnName));
				if (columnWidth[currentCol] < data.length()) {
					columnWidth[currentCol] = data.length();
				}
				if (NumberUtils.isNumber(data)) {
					final jxl.write.Number number = new jxl.write.Number(
							currentCol, currentRow, Double.parseDouble(data));
					sheet.addCell(number);
				} else {
					final Label label = new Label(currentCol, currentRow, data);
					sheet.addCell(label);
				}

				currentCol++;
			}
			currentCol = 0;
			currentRow++;
		}
		adjustColumnSize(sheet, columnWidth);
	}

	/**
	 * @param sheet
	 * @param columnWidth
	 */
	private static void adjustColumnSize(final WritableSheet sheet,
			final int[] columnWidth) {
		for (int i = 0; i < columnWidth.length; i++) {
			sheet.setColumnView(i, columnWidth[i]);
		}
	}

	/**
	 * @param columnWidth
	 * @param i
	 */
	private static void initArray(final int[] columnWidth,
			final List<String> columns) {
		for (int j = 0; j < columnWidth.length; j++) {
			columnWidth[j] = columns.get(j).length();
		}

	}

	/**
	 * @param sheet
	 * @param columnNames
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private static void writeColumnHeaders(final WritableSheet sheet,
			final Map columnNames) throws RowsExceededException, WriteException {
		final List<String> keySet = sort(columnNames.keySet());
		Collections.sort(keySet);
		final int currentRow = 0;
		int currentColumn = 0;
		for (final String columnName : keySet) {
			final Label label = new Label(currentColumn, currentRow, columnName);
			sheet.addCell(label);
			currentColumn++;
		}
	}

	private static List<String> sort(final Collection<String> columns) {
		final List<String> keySet = new ArrayList(columns);
		Collections.sort(keySet);
		return keySet;
	}

}
