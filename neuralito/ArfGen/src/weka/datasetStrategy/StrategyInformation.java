package weka.datasetStrategy;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import filter.Filter;

public class StrategyInformation {
	
	private String name;
	private String description;
	private Hashtable<String, String> strategyParameters = new Hashtable<String, String>();
	private Vector<Filter> buoyFilters = new Vector<Filter>();
	private Vector<Filter> ww3Filters = new Vector<Filter>();
	private String[] strategyAttributes = null;
	private String classAttribute = "";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Hashtable<String, String> getStrategyParameters() {
		return strategyParameters;
	}
	public void setStrategyParameters(Hashtable<String, String> strategyParameters) {
		this.strategyParameters = strategyParameters;
	}
	public Vector<Filter> getBuoyFilters() {
		return buoyFilters;
	}
	public void setBuoyFilters(Vector<Filter> buoyFilters) {
		this.buoyFilters = buoyFilters;
	}
	public Vector<Filter> getWw3Filters() {
		return ww3Filters;
	}
	public void setWw3Filters(Vector<Filter> ww3Filters) {
		this.ww3Filters = ww3Filters;
	}
	public String[] getStrategyAttributes() {
		return strategyAttributes;
	}
	public void setStrategyAttributes(String[] strategyAttributes) {
		this.strategyAttributes = strategyAttributes;
	}
	public String getClassAttribute() {
		return classAttribute;
	}
	public void setClassAttribute(String classAttribute) {
		this.classAttribute = classAttribute;
	}
	
	public String toString(){
		String text = "";
		text = this.name + "\n" + this.description + "\n\n";
		
		if (this.strategyParameters.size() > 0){
			text += "STRATEGY PARAMETERS: \n\n";
			for (Enumeration<String> e = this.strategyParameters.keys(); e.hasMoreElements();){
				String key = e.nextElement();
				text += key + " -> " + this.strategyParameters.get(key) + "\n";
			}
		}
		
		if (this.buoyFilters.size() > 0){
			text += "BUOY FILTERS: \n\n";
			for (Enumeration<Filter> e = this.buoyFilters.elements(); e.hasMoreElements();){
				Filter filter = e.nextElement();
				text += filter.toString();
			}
		}
		
		if (this.ww3Filters.size() > 0){
			text += "WW3 FILTERS: \n\n";
			for (Enumeration<Filter> e = this.ww3Filters.elements(); e.hasMoreElements();){
				Filter filter = e.nextElement();
				text += filter.toString();
			}
		}
		
		if (this.strategyAttributes.length > 0){
			text += "INSTANCE ATTRIBUTES: \n\n";
			for (int i = 0; i < this.strategyAttributes.length; i++)
				text += this.strategyAttributes[i] + ", ";
			text += "\nCLASS ATTRIBUTE: " + this.classAttribute + "\n\n";
		}
			
		return text;
		
	}
}
