import java.util.ArrayList;

public class User {

	private String name;
	private Habit currentHabit;
	private Goal currentGoal; 
	private ArrayList<String> mood = new ArrayList<>();

	public User(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMood(String dailyMood){
		mood.add(dailyMood);	
	}

	public void changeMood(String newMood, int day){
		mood.set(day, newMood);
	}

	public void setHabit(Habit h){
		this.currentHabit = h;
	}

	public Habit getHabit(){
		return currentHabit; 
	}
	
	public void setGoal(Goal currentGoal) {
		this.currentGoal = currentGoal;
	}
	
	public Goal getGoal() {
		return currentGoal; 
	}
}


