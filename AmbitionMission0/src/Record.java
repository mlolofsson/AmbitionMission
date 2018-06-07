import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Record {
	
	private ArrayList<Goal> GList;
	private ArrayList<Habit> HList;
	private ArrayList<String> MList;
	private Habit currentHabit;
	private Goal currentGoal; 
	private Record userRecord;
	private String recordName;

	public Record(){
		GList = new ArrayList<Goal>();
		HList = new ArrayList<Habit>();
		MList = new ArrayList<String>();
	}
	
	public Record(ArrayList<Goal> GList, ArrayList<Habit> HList, ArrayList<String> MList, String recordName) { // constructor if record already exists
		this.GList = GList; 
		this.HList = HList; 
		this.MList = MList; 
		this.recordName = recordName;
		this.currentHabit = HList.get(0);
		//System.out.print(currentHabit.getHabitName());
		this.currentGoal = GList.get(0);
		
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
		for(Habit h: HList) {
			h.printHabit();
			System.out.print("\n");
		}
		System.out.println("Goal Data");
		System.out.println("GList");
		for(Goal g: GList) {
			g.printGoal();
			System.out.print("\n");
		}
	}
	
	public void setGoal(Goal currentGoal) {
		this.currentGoal = currentGoal;
	}
	
	public Goal getGoal() {
		return currentGoal; 
	}
	
	public void saveToTextFile() {
		String pathname = recordName ;
		File userListFile = new File(pathname);
		PrintWriter output = null;
		Scanner inputOfFile = null;
		try {
			inputOfFile = new Scanner(userListFile);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + pathname);
			System.exit(1);
		}
		int lineIndex = 1;
		boolean printGoals = false; 
		boolean printHabits = false; 
		boolean printMoods = false; 
		while (inputOfFile.hasNextLine()) { // file should contain one name followed by its record file name on each
			if(lineIndex==1) {
				output.print("goals");
				printGoals = true;
			}
			else if(lineIndex == (GList.size() + 1)) {
				output.print("habits");
				printHabits = true; 
				printGoals = false; 
			}
			else if(lineIndex == (GList.size() + 1 + HList.size())){
				output.print("moods");
				printHabits = false; 
				printGoals = false; 
				printMoods = true;
			}
			else if(printGoals==true) {
				for(Goal g : GList) {
					output.println(g.getGoal() + ", " + g.getTime() + ", " + g.getProgress());
				}
			}
			else if(printHabits==true) {
				for(Habit h : HList) {
					output.println(h.getHabitName() + ", " + h.getGoalDays() + ", " + h.getCompleted());
				}
			}
			else if(printMoods=true) {
				for(String m : MList) {
					output.println(m);
				}
			}
		}
	}

	

}