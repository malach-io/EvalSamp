package eval.entity;

public class RecordTwoParam extends RecordSet {
	
	public <T> RecordTwoParam(T recordsInGroup, T elementsInGroup) {
		setType("2");
		this.recordsInGroup = asInt(recordsInGroup);
		this.elementsInGroup = asInt(elementsInGroup);
	}

	public double getResults() {
		return pi * recordsInGroup + psi * (1.34 + (Math.pow(elementsInGroup, 3)));
	}
	
	public String toString() {
		return recordsInGroup + " " + elementsInGroup;
	}
}
