public class Habit {	

	private String habitName;
	private int goalDays;
	private int completeDays=0;

	public Habit(String habitName, int goalDays){
		this.habitName = habitName;
		this.goalDays = goalDays;
		
	}
	
	public Habit(String habitName, int goalDays, int completeDays) {
		this.habitName = habitName; 
		this.goalDays = goalDays; 
		this.completeDays = completeDays; 
	}

	public void changeDays(int goalDays){
		this.goalDays= goalDays;
		System.out.print("sdfghj");
		int l =0;
	}

	public void recordProgress(){
		this.completeDays++;
	}

	public boolean finishHabit(){
		if(this.completeDays == this.goalDays)
			return true;
		return false;
	}

	public String getHabitName(){
		return this.habitName;
	}

	public int getGoalDays(){
		return this.goalDays;
	}
	
	public int getCompleted() {
		return this.completeDays;
	}
	public void printHabit() {
		System.out.print("habitName = " + habitName);
		System.out.print(", goalDays = " + goalDays);
		System.out.print(", completeDays = " + completeDays);
	}
	

}

