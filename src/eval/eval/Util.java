package eval.eval;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public interface Util {
	
	public double pi = 3.14;
	public double psi = 6.39485;
	public double zeta = 3.2;
	
	public default double asDouble(double i) {
    	return Double.parseDouble(String.valueOf(i));
    }
    
    public default <T> int asInt(T i) {
    	return parseInt(String.valueOf(i), -1);
    }
    
    public default int parseInt(final String s, final int valueIfInvalid) {
        try {
            if (s == null) {
                return valueIfInvalid;
            } else {
                return Integer.parseInt(s);
            }
        } catch (final NumberFormatException ex) {
            return valueIfInvalid;
        }
    }
	
//	public default double getAverage(double ... a) {
//		double sum = DoubleStream.of(a).boxed().map(this::asDouble).mapToDouble(Double::doubleValue).sum();
//		return sum / a.length;
//	}
	
	public default double getAverage(List<Double> a) {
		double sum = a.stream().reduce(0.0, Double::sum);
		return sum / a.size();
	}
	
	public default String[] splitAndTrim(String line) {
		return Arrays.stream(line.split(" ")).map(String::trim).filter(c -> c != " " && !c.isEmpty()).toArray(String[]::new);
	}
	
	public default <T> void printArray(T[] array) {
		System.out.println(Arrays.toString(array));
	}
	
	public default Runnable runBoolean(boolean bool, Runnable runTrue, Runnable runFalse) {
		HashMap<Boolean, Runnable> map = new HashMap<Boolean, Runnable>();
		map.put(true, runTrue);
		map.put(false, runFalse);
		return map.get(bool);
	}

}
