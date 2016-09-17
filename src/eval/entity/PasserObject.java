package eval.entity;

import java.util.Arrays;

import eval.eval.Util;

public class PasserObject implements Util{
	
	private boolean isControlRecord = false, isCorrupt = false, lengthError = false;
	private String[] content;
	
	public PasserObject(String line) {
		setControlRecord(isControlRecord(line));
		content = splitAndTrim(line.replace("*", ""));
		setCorrupt(hasLengthError(content));
		if(!hasLengthError()) {
			setCorrupt(numberFormatException(asInt(content[0])));
			setCorrupt(numberFormatException(asInt(content[1])));
			content = Arrays.copyOf(content, 3);
		} 
	}
	
	public String[] getContent() {
		return content;
	}
	
	public boolean isControlRecord() {
		return isControlRecord;
	}

	private void setControlRecord(boolean isControlRecord) {
		this.isControlRecord = isControlRecord;
	}

	private boolean isControlRecord(String line) {
		return line.startsWith("*");
	}
	
	public boolean isCorrupt() {
		return isCorrupt;
	}

	public void setCorrupt(boolean isCorrupt) {
		this.isCorrupt = isCorrupt;
	}

	private <T> boolean hasLengthError(T[] array) {
		if(array.length < 2) {
			this.lengthError = true;
			return true;
		}
		return false;
	}
	
	public boolean hasLengthError() {
		return lengthError;
	}
	
	private boolean numberFormatException(int i) {
		return (i == -1);
	}
	
	public String toString() {
		return Arrays.toString(content);
	}
	
	public boolean invalidLength() {
		return lengthError;
	}
	
	public boolean invalidControlRecord() {
		return (isControlRecord() && isCorrupt() && !hasLengthError());
	}
	
	public boolean invalidRecordSet() {
		return (!isControlRecord() && isCorrupt() && !hasLengthError());
	}
	
    public boolean validControlRecord() {
    	return (isControlRecord() && !isCorrupt());
    }
    
    public boolean validRecordSet() {
    	return (!isControlRecord() && !isCorrupt());
    }
	
	
}
