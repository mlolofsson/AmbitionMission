public class Goal {
	private String goalName;
	private int goalTime;
	private int hourProgress = 0;


	public Goal(String goalName, int goalTime){
		this.goalName = goalName;
		this.goalTime = goalTime;
		int i = 4;
		
	}

	public String getGoal(){
		return goalName;
	}

	public int getTime(){
		return goalTime;
	}

	public void recordProgress(){
		hourProgress++;
	}

}
