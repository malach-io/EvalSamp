package eval.eval;

import java.util.HashMap;

import eval.entity.RecordSet;
import eval.entity.RecordTwoParam;
import eval.entity.RecordThreeParam;

public class RecordSetFactory extends HashMap<String, RecordSet>{

	public <T> RecordSet makeRecordSet(T recordsInGroup, T elementsInGroup, String n){
		put(null, new RecordTwoParam(recordsInGroup, elementsInGroup));
		put("R", new RecordThreeParam(recordsInGroup, elementsInGroup, n));
		return get(powerMap(n));
	}
	
	public String powerMap(String n) {
		if(n != null) return "R";
		return null;
	}
}