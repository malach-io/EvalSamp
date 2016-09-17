package eval.eval;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import eval.entity.PasserObject;
import eval.entity.RecordGroup;
import eval.entity.RecordSet;

public class Records extends ArrayList<RecordGroup> implements Util{
	
	private int countIndicator, lineCounter = 0;
	private String fileName;
	private boolean hasCorruptControl;
	private ArrayList<String> errorList = new ArrayList<String>();
	
	public Records(String fileName) {
		this.fileName = fileName;
		setCountIndicator();
	}
		
	public int getCountIndicator() {
		return countIndicator;
	}
	
	private void setCountIndicator() {
		int num = asInt(readFileLine(0));
		if(num > 0) {
			countIndicator = num;
		}
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public ArrayList<String> getErrorList() {
		return errorList;
	}

	public boolean isControlRecord(String line) {
		return line.startsWith("*");
	}
	
	public List<String> readFile() {
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			list = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private String readFileLine(int lineNum) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
		    return stream.skip(lineNum).findFirst().get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void readFileToList() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.skip(1)
			.limit(countIndicator)
			.forEachOrdered(c -> {
				lineCounter++;
				hasCorruptControl = setRecord(new PasserObject(c));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean setRecord(PasserObject passer) {
		if(passer.hasLengthError()) errorList.add("file line: " + lineCounter + " length error: " + passer.toString());
		if(passer.invalidControlRecord()) {
			errorList.add("file line: " + lineCounter + " control record error: " + passer.toString());
			return true;
		}
		if(passer.invalidRecordSet()) errorList.add("file line: " + lineCounter + " record set error: " + passer.toString() + " data group: " + getLast().toString());
		
		if(passer.validControlRecord())	add(new RecordGroup(passer.getContent()[0], passer.getContent()[1]));
		if(passer.validRecordSet() && !hasCorruptControl) {
			getLast().add(recordSetFactory(passer.getContent()[0], passer.getContent()[1], passer.getContent()[2]));
		}
		return false;
	}
	
	public List<String> readFileSkip(String fileName, int lineNum) {
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
		    list = stream.skip(lineNum).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public RecordGroup getLast() {
        return this != null && !isEmpty() ? get(size() - 1) : null;
    }
	
	public <T> RecordSet recordSetFactory(T recordsInGroup, T elementsInGroup, String n) {
		RecordSetFactory factory = new RecordSetFactory();
		return factory.makeRecordSet(recordsInGroup, elementsInGroup, n);
	}
	
	public List<Double> allResults() {
		return this.stream().map(i -> i.stream().map(c -> c.getResults()).collect(Collectors.toList())
			).flatMap(List::stream).collect(Collectors.toList());
	}
	
	public int totalRecordSets() {
		return this.stream().flatMap(List::stream).collect(Collectors.toList()).size();
	}
	
	public List<String> allToString() {
		return this.stream().map(i -> i.stream().map(RecordSet::toString).collect(Collectors.toList())
			).flatMap(List::stream).collect(Collectors.toList());
	}

	public void displayTotals() {
		System.out.printf("average of all results : %.3f\n", getAverage(allResults()));
		System.out.println("total Record Groups reads: " + this.size());
		System.out.println("total Record Sets reads: " + totalRecordSets());
		System.out.println("total Records reads: " + (this.size() + totalRecordSets()));
	}

	public static void main(String[] args) {
		String fileName = "d://documents/test_record.txt";
		Records record = new Records(fileName);

		record.readFileToList();
		
		record.allToString().forEach(System.out::println);
		
		record.getErrorList().forEach(System.out::println);

		record.stream().forEachOrdered(c -> {
			System.out.println("group: " + c.toString());
			c.forEach(i -> {
				System.out.println(i.toString());
				System.out.println(i.averageToString());
				System.out.println(i.resultToString());
			});
		});
		System.out.println(record.allResults());
		record.displayTotals();
	}
}
