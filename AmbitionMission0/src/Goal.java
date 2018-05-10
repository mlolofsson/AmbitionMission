import java.util.*; 

public class Goal {
	private String goalName;
	private double goalTime; 
	private double hourProgress = 0;
	private List<String> comments = new ArrayList<String>(); 


	public Goal(String goalName, double goalTime){
		this.goalName = goalName;
		this.goalTime = goalTime;
		
	}

	public String getGoal(){
		return goalName;
	}

	public double getTime(){
		return goalTime;
	}

	public void setTime(double newTime) { // if user wants to change time 
		goalTime = newTime; 
	}
	public void recordProgress(double progress){
		hourProgress = hourProgress + progress;
	}
	
	public void addComments(String currentComment) {
		comments.add(currentComment); 
	}

}
