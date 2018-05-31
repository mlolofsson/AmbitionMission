import java.util.ArrayList;

public class Record {
	
	private ArrayList<Goal> GList;
	private ArrayList<Habit> HList;
	private ArrayList<String> MList;
	private Habit currentHabit;
	private Goal currentGoal; 
	private Record userRecord; 

	public Record(){
		GList = new ArrayList<Goal>();
		HList = new ArrayList<Habit>();
		MList = new ArrayList<String>();
	}
	
	public Record(ArrayList<Goal> GList, ArrayList<Habit> HList, ArrayList<String> MList) { // constructor if record already exists
		this.GList = GList; 
		this.HList = HList; 
		this.MList = MList; 
	}

	public void setMood(String dailyMood){
		MList.add(dailyMood);	
	}

	public void changeMood(String newMood, int day){
		MList.set(day, newMood);
	}

	public void setHabit(Habit h){
		this.currentHabit = h;
	}

	public Habit getHabit(){
		return currentHabit; 
	}

	public ArrayList<Goal> getGoalList(){
		return GList;
	}

	public void storeGoal(Goal g){
		GList.add(g);

	}

	public void storeMood(String mood){
		MList.add(mood);
	}

	public void storeHabit(Habit h){
		HList.add(h);
	}

	public void printRecord(){
		System.out.println("Mood Data:");
		System.out.println(MList);
		System.out.println("Habit Data");
		System.out.println("HList");
		System.out.println("Goal Data");
		System.out.println("GList");
	}
	
	public void setGoal(Goal currentGoal) {
		this.currentGoal = currentGoal;
	}
	
	public Goal getGoal() {
		return currentGoal; 
	}

	

}