package filter;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import util.WaveData;

public class MonthFilter extends Filter {
	
	private int minMonth;
	private int maxMonth;
	
	public MonthFilter(int minMonth, int maxMonth) {
		this.minMonth = minMonth;
		this.maxMonth = maxMonth;
	}

	@Override
	public Vector<WaveData> executeFilter(Vector<?> dataSet) {
		Vector<WaveData> dataset = (Vector<WaveData>) dataSet;
		Vector<WaveData> dataFiltered = new Vector<WaveData>();
		
		if (this.minMonth <= this.maxMonth){
			for (Enumeration<WaveData> e = dataset.elements(); e.hasMoreElements();) {
				WaveData data = e.nextElement();
				if (data.getDate().get(Calendar.MONTH) + 1  >= this.minMonth && data.getDate().get(Calendar.MONTH) + 1 <= this.maxMonth)
					dataFiltered.add(data);
			}
		}
		else{
			for (Enumeration<WaveData> e = dataset.elements(); e.hasMoreElements();) {
				WaveData data = e.nextElement();
				if (data.getDate().get(Calendar.MONTH) + 1  >= this.minMonth || data.getDate().get(Calendar.MONTH) + 1 <= this.maxMonth)
					dataFiltered.add(data);
			}
		}
		
		return dataFiltered;
	}
	
	public String toString(){
		String text = "";
		text = "\tMonthFilter\n\t\tDesde -> " + this.minMonth + "\n\t\tHasta -> " + this.maxMonth + "\n";
		return text;
	}
}
