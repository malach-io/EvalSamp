package eval.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import eval.eval.Util;

public class RecordGroup extends ArrayList<RecordSet> implements Util{
	
	private int controlOne, controlTwo;
	
	public <T> RecordGroup(T controlOne, T controlTwo) {
		this.controlOne = asInt(controlOne);
		this.controlTwo = asInt(controlTwo);
	}
	
	public String toString() {
		return controlOne + " " + controlTwo;
	}
	
	public List<String> recordsAsString() {
		return this.stream().map(RecordSet::toString).collect(Collectors.toList());
	}
}
