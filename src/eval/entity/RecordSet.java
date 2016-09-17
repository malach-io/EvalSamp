package eval.entity;

import static java.util.Arrays.asList;

import eval.eval.Util;

public class RecordSet implements Util {
	
	protected int recordsInGroup, elementsInGroup;
	protected String type, power;
	
	public boolean powerIsNull() {
		return power == null;
	}
	
	public String getType() {
		return type;
	}
	
	protected void setType(String type) {
		this.type = type;
	}
	
	public int getRecordsInGroup() {
		return recordsInGroup;
	}

	public int getElementsInGroup() {
		return elementsInGroup;
	}
	
	public double getResults() {
		return 0.0;
	}
	
	public String averageToString() {
		return "AVG " + type + " = " + getAverage(asList(asDouble(recordsInGroup), asDouble(elementsInGroup)));
	}
	
	public String resultToString() {
		return "FORM " + type + " = " + getResults();
	}
}
