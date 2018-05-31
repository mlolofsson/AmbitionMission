import java.util.*;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		// CREATE FILE THAT STORES USERS
		ArrayList<User> users = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> recordFileNames = new ArrayList<>();
		String pathname = "UserList.txt";
		File userListFile = new File(pathname);
		PrintWriter output = null;
		Scanner inputOfFile = null;
		try {
			output = new PrintWriter(userListFile);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + pathname);
			System.exit(1);
		}
		while (inputOfFile.hasNextLine()) { // file should contain one name followed by its record file name on each
											// line
			String line = inputOfFile.nextLine();
			int quoteIndex = line.indexOf("\"");
			String name = line.substring(0, quoteIndex);
			int quoteIndex2 = line.substring(quoteIndex + 1).indexOf("\"");
			String record = line.substring(quoteIndex + 1, quoteIndex2);
			names.add(name);
			recordFileNames.add(record);
		}
		System.out.print("Hello, have you used this program before? (y/n) ");
		String response = in.nextLine();
		String username = "";
		User temp = null;
		if (response.equals("y")) {
			System.out.print("\nWhat is your name? ");
			username = in.nextLine();
			int indexOfUser = 0;
			for (int t = 0; t < names.size(); t++) {
				if (names.get(t).equals(username)) {
					// temp = new User(username);
					indexOfUser = t;
					// System.out.print("found user ");
					// username = in.nextLine();
				}
			}
			String recordName = recordFileNames.get(indexOfUser);
			Record rec = readInRecord(recordName, username);
			temp = new User(username, rec);
		} else {
			System.out.print("What is your name? ");
			username = in.nextLine();
			temp = new User(username);
			setUpNewUser(in, temp);

		}

		temp = new User(username);
		users.add(temp);
		recordToday(in, temp);

	}

	public static void recordToday(Scanner in, User temp) {
		SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
		String date = sdf.format(new Date());
		System.out.println(date);
		System.out.println("Hello, what would you like to record today?");
		System.out.println("Options: \n1.Mood\n2.Habit\n3.Goal");
		int choice = in.nextInt();

		switch (choice) {
		case 1:
			System.out.print("How are you feeling today? ( :) or :| or :( )");
			String l = in.nextLine();
			String currentMood = in.nextLine();
			temp.getRecord().setMood(currentMood);
			break;

		case 2:
			System.out.println("Have you completed " + temp.getRecord().getHabit().getHabitName() + " ? (Y/N)");
			String answer = in.nextLine();
			if (answer.equals("Y")) {
				temp.getRecord().getHabit().recordProgress();
				if (temp.getRecord().getHabit().finishHabit() == true) {
					System.out.println("You completed your habit! Good job! ");
					System.out.println("What habit would you like to work on next?");
					String habitChoice = in.nextLine();
					System.out.println("How long do you want to work on your habit?(days)");
					int habitTime = in.nextInt();
					Habit currentHabit = new Habit(habitChoice, habitTime);
					temp.getRecord().setHabit(currentHabit);
				}
			} else if (answer.equals("N")) {
				System.out.println("Bad dog! Go work on your habit!");
			}
			break;
		case 3:
			System.out.println("Have you made progress towards your goal (" + temp.getRecord().getGoal() + ")?");
			String response = in.nextLine();
			if (response.equals("Y")) {
				System.out.println("How many hours did you complete? ");
				double hours = in.nextDouble();
				temp.getRecord().getGoal().recordProgress(hours);
				System.out.print("Any comments on your progress? (Y/N)");
				String ans = in.nextLine();
				if (ans.equals("Y")) {
					System.out.print("Enter your comments: ");
					String comments = in.nextLine();
					temp.getRecord().getGoal().addComments(comments);
				}
			}
			if (response.equals("N")) {
				System.out.println("Put more effort please!!!");
			}
			break;
		}

	}

	public static void displayMonthProgress(User temp, int firstDay, int lastDay) {
		// DISPLAY MOOD

		// DISPLAY HABIT

		// DISPLAY GOAL
		ArrayList<Goal> tempGoal = temp.getRecord().getGoalList();
		for (int i = firstDay; i < lastDay; i++) {
			double timeProgress = tempGoal.get(i).getProgress();
			System.out.println("\nGoal (" + tempGoal.get(i).getGoal() + ") : ");
			System.out.println(timeProgress + " hours out of " + tempGoal.get(i).getTime() + " completed.");
		}
	}

	public static void setUpNewUser(Scanner in, User temp) {
		// SET UP HABIT
		System.out.println("What habit would you like to work on?");
		String habitChoice = in.nextLine();
		System.out.println("How long do you want to work on your habit?(days)");
		int habitTime = in.nextInt();
		Habit currentHabit = new Habit(habitChoice, habitTime);
		temp.getRecord().setHabit(currentHabit);

		// SET UP GOAL
		System.out.println("What goal would you like to work on?");
		String t = in.nextLine();
		String goalChoice = in.nextLine();
		System.out.println("How many hours do you want to work on your goal?");
		int goalTime = in.nextInt();
		Goal currentGoal = new Goal(goalChoice, goalTime);
		temp.getRecord().setGoal(currentGoal);

	}

	public static Record readInRecord(String nameOfFile, String username) {
		ArrayList<Goal> goals = new ArrayList<Goal>();
		ArrayList<Habit> habits = new ArrayList<Habit>();
		ArrayList<String> moods = new ArrayList<String>();

		String pathname = nameOfFile;
		File recordFile = new File(pathname);
		PrintWriter output = null;
		Scanner inputOfFile = null;
		try {
			output = new PrintWriter(recordFile);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + pathname);
			System.exit(1);
		}
		int lineNum = 0;
		boolean readGoals = false;
		boolean readHabits = false;
		boolean readMoods = false;
		while (inputOfFile.hasNextLine()) { // file should contain one name followed by its record file name on each
											// line
			String line = inputOfFile.nextLine();
			if (line.equals("goals")) {
				readGoals = true;
			} else if (line.equals("habits")) {
				readHabits = true;
			} else if (line.equals("moods")) {
				readMoods = true;
			} else if (readGoals == true) {
				int commaIndex = line.indexOf(",");
				String goalName = line.substring(0, commaIndex);
				int commaIndex2 = line.substring(commaIndex + 1).indexOf(","); // index of , after first ,
				String goalTime1 = line.substring(commaIndex + 1, commaIndex2);
				double goalTime = Double.parseDouble(goalTime1);
				int commaIndex3 = line.substring(commaIndex2 + 1).indexOf(",");
				String progress1 = line.substring(commaIndex2 + 1, commaIndex3);
				double progress = Double.parseDouble(progress1);
				// String time = line.substring(commaIndex2+1, )
				Goal tempG = new Goal(goalName, goalTime, progress);
				goals.add(tempG);

			} else if (readHabits == true) {
				int commaIndex = line.indexOf(",");
				String habitName = line.substring(0, commaIndex);
				int commaIndex2 = line.substring(commaIndex + 1).indexOf(","); // index of , after first ,
				String goalDays1 = line.substring(commaIndex + 1, commaIndex2);
				int goalDays = Integer.parseInt(goalDays1);
				int commaIndex3 = line.substring(commaIndex2 + 1).indexOf(",");
				String completedDays1 = line.substring(commaIndex2 + 1, commaIndex3);
				int completedDays = Integer.parseInt(completedDays1);
				Habit tempH = new Habit(habitName, goalDays, completedDays);
				habits.add(tempH);
			} else if (readMoods = true) {
				String mood = line;
				moods.add(mood);
			}
		}
		
		Record rec = new Record(goals, habits, moods);
		
		return rec; 
		
	}
}
