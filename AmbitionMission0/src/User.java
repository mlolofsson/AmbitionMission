import java.util.ArrayList;

public class User {

	private String name;
	private Record userRecord; 
	

	public User(String name){
		this.name = name;
		this.userRecord = new Record(); 
	}
	
	public User(String name, Record x) { // constructor if user already exists 
		this.name = name; 
		this.userRecord = x; 
	}

	public String getName(){
		return name;
	}
	
	public Record getRecord() {
		return userRecord; 
	}
	
}


