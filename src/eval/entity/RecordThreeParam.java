package eval.entity;

import java.util.HashMap;

public class RecordThreeParam extends RecordSet{
		
	public <T> RecordThreeParam(T recordsInGroup, T elementsInGroup, String power) {
		setType("3");
		this.recordsInGroup = asInt(recordsInGroup);
		this.elementsInGroup = asInt(elementsInGroup);
		this.power = power;
	}

	public double getResults() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 3);
		map.put("b", 4);
		map.put("c", 6);
		int power = map.getOrDefault(this.power, map.get("a"));
		return pi * recordsInGroup + psi * (Math.pow(elementsInGroup, 3)) + (Math.pow(zeta, power) / elementsInGroup);
	}
	
	public String toString() {
		return recordsInGroup + " " + elementsInGroup + " " + power;
	}
}
