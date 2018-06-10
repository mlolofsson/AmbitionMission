import java.util.ArrayList;

public class User {

	private String name;
	private Record currentRecord; 
	private ArrayList<Record> allRecords;
	
	public User(String name, ArrayList<Record> x) { // constructor if user already exists 
		this.name = name; 
		this.allRecords = x; 
	}

	public String getName(){
		return name;
	}
	
	public Record getRecord() {
		return currentRecord; 
	}
	
	public ArrayList<Record> getAllRecords(){
		return allRecords;
	}
	
	public void setCurrentRec(int month) {
		currentRecord = allRecords.get(month);
	}
	
	public String getRecordName(int month) {
		return name + "Record" + month + ".txt";
	}
}


