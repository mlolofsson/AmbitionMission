public class Habit {	

	private String habitName;
	private int goalDays;
	private int completeDays=0;

	public Habit(String habitName, int goalDays){
		this.habitName = habitName;
		this.goalDays = goalDays;
		
	}

	public void changeDays(int goalDays){
		this.goalDays= goalDays;
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

}

