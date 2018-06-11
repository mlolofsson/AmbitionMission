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

		// SET DATE
		Date curDate = new Date();
		int month = curDate.getMonth(); // month is 1 less than should be

		Scanner in = new Scanner(System.in);
		// CREATE FILE THAT STORES USERS
		ArrayList<User> users = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> recordFileNames = new ArrayList<>();
		String pathname = "UserList.txt";
		File userListFile = new File(pathname);
		PrintWriter output = null;
		Scanner inputOfFile = null;
		User temp = null;
		try {
			inputOfFile = new Scanner(userListFile);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + pathname);
			System.exit(1);
		}
		while (inputOfFile.hasNextLine()) { // file should contain one name followed by its record file name on each
			// line
			String name = inputOfFile.nextLine();
			name = name.trim();
			ArrayList<String> recordNames = new ArrayList<String>();
			ArrayList<Record> records = new ArrayList<Record>();
			for (int i = 0; i < 12; i++) {
				String recordFile = name + "Record" + i + ".txt";
				recordNames.add(recordFile);
				
				records.add(readInRecord(recordFile, name)); // set record to proper text file
			}
			temp = new User(name, records);
			users.add(temp);
			names.add(name);
		}

		// GET USER INFORMATION
		System.out.print("Hello, have you used this program before? (Y/N) ");
		String response = in.nextLine();
		String username = "";

		if (response.equals("Y")) {
			System.out.print("What is your name? ");
			username = in.nextLine();
			int indexOfUser = 0;
			boolean flag = false; // true if found
			for (int t = 0; t < names.size(); t++) {
				if (names.get(t).equals(username)) {
					indexOfUser = t;
					flag = true;
				}
			}
			if (!flag) {
				System.out.print(username + " is not an existing username.");
				System.exit(1);
			}
		} else {
			System.out.print("What is your name? ");
			username = in.nextLine();
			names.add(username);
			ArrayList<String> recNames = new ArrayList<String>();
			ArrayList<Record> recs = new ArrayList<Record>();
			String temp2 = null;
			for (int i = 0; i < 12; i++) { // set record names
				temp2 = username + "Record" + i + ".txt";
				recNames.add(i, temp2);
				recs.add(new Record(temp2));
			}
			// recordFileNames.add(recName);
			temp = new User(username, recs);
			setUpNewUser(in, temp);

		}

		// temp = new User(username);
		saveUserList(names, users);
		temp.setCurrentRec(month); // set the current record to the record of the current month
		//printUserRecord(temp);
		options(in, temp);

	}

	public static void options(Scanner in, User temp) {
		System.out.println("\nProgram options: ");
		System.out.println("1. Record today's data");
		System.out.println("2. View month's progress");
		System.out.print("3. Quit");

		int choice = 0;
		do {
			System.out.print("\nPick a number: ");
			choice = in.nextInt();
			switch (choice) {
			case 1:
				recordToday(in, temp);
				break;
			case 2:
				displayMonthProgress(in, temp);
				break; 
			}
		} while (choice != 3);
	}

	public static void saveUserList(ArrayList<String> names, ArrayList<User> users) {
		String pathname = "UserList.txt";
		File userListFile = new File(pathname);
		PrintWriter output = null;
		Scanner inputOfFile = null;
		/*
		 * try { inputOfFile = new Scanner(userListFile); } catch (FileNotFoundException
		 * ex) { System.out.println("Cannot create " + pathname); System.exit(1); }
		 */
		try {
			output = new PrintWriter(userListFile);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + pathname);
			System.exit(1);
		}
		int i = 0;
		while (i < names.size()) {
			output.print(names.get(i));
			i++;
		}
		output.close();
	}

	public static void recordToday(Scanner in, User temp) {
		Date curDate = new Date();
		int month = curDate.getMonth(); // month is 1 less than should be
		SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
		String date = sdf.format(curDate);
		System.out.println("\n" + date);
		System.out.print("Good day! How are you? ");
		System.out.println("\nOptions: \n1.Mood\n2.Habit\n3.Goal\n4.Quit");

		temp.setCurrentRec(month); // set the current record to the record of the current month
		if (temp.getRecord().getGoal() == null || temp.getRecord().getHabit() == null) { // if it is a new month
			rollOver(month, temp);
		}
		int choice = 0;
		do {

			System.out.print("\nWhat would you like to record? ");
			choice = in.nextInt();
			switch (choice) {
			case 1:
				System.out.print("How are you feeling today? ( :) or :| or :( ) ");
				String l = in.nextLine();
				String currentMood = in.nextLine();
				temp.getRecord().setMood(currentMood);
				break;

			case 2:
				System.out.print("Have you completed " + temp.getRecord().getHabit().getHabitName() + " ? (Y/N) ");
				String a = in.nextLine();
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
				System.out.print("Have you made progress towards your goal (" + temp.getRecord().getGoal().getGoal()
						+ ")? (Y/N) ");
				String r = in.nextLine();
				String response = in.nextLine();
				if (response.equals("Y")) {
					System.out.println("How many hours did you complete? ");
					double hours = in.nextDouble();
					temp.getRecord().getGoal().recordProgress(hours);
					if (temp.getRecord().getGoal().goalSuccess() == true) {
						System.out.print("\nGood job! You completed your goal!");
						System.out.print("You now have to choose a new goal to work on. What'll it be? ");
						String t = in.nextLine();
						String goalChoice = in.nextLine();
						System.out.print("How many hours do you want to work on your goal? ");
						int goalTime = in.nextInt();
						Goal currentGoal = new Goal(goalChoice, goalTime);
						temp.getRecord().setGoal(currentGoal);
					}
					// System.out.print("Any comments on your progress? (Y/N)");
					/*
					 * String ans = in.nextLine(); if (ans.equals("Y")) {
					 * System.out.print("Enter your comments: "); String comments = in.nextLine();
					 * temp.getRecord().getGoal().addComments(comments); }
					 */
				}
				if (response.equals("N")) {
					System.out.println("Put more effort please!!!");
				}
				break;
			}
		} while (choice != 4);
		temp.getRecord().saveToTextFile();

	}

	public static void displayMonthProgress(Scanner in, User temp) {
		System.out.print("\nWhich month would you like to see (enter the integer)? "); 
		int month = in.nextInt(); 
		System.out.println("\nMonth " + month + " summary: ");
		String pathname = temp.getAllRecords().get(month-1).getName();
		File recordFile = new File(pathname);
		Scanner inputOfFile = null;
		try {
			inputOfFile = new Scanner(recordFile);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + pathname);
			System.exit(1);
		}
	
		while (inputOfFile.hasNextLine()) {
			String line = inputOfFile.nextLine();
			System.out.print(line + "\n");
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

		// SET UP RECORD
		temp.getRecord().setRecordName(temp.getName() + "Record.txt"); // recordName = nameOfUserRecord.txt

	}

	public static void rollOver(int currentMonth, User user) { // puts current goal and habit of previous month into
																// current month
		Habit lastMonthH = user.getAllRecords().get(currentMonth - 1).getHabit();
		user.getRecord().setHabit(lastMonthH); // set the user's first habit of this month as the last habit of the last
												// month
		Goal lastMonthG = user.getAllRecords().get(currentMonth - 1).getGoal();
		user.getRecord().setGoal(lastMonthG); // set the user's first goal of this month as the last goal of the last
												// month
	}

	public static Record readInRecord(String nameOfFile, String username) {
		//System.out.print(nameOfFile);
		ArrayList<Goal> goals = new ArrayList<Goal>();
		ArrayList<Habit> habits = new ArrayList<Habit>();
		ArrayList<String> moods = new ArrayList<String>();

		String pathname = nameOfFile;
		File recordFile = new File(pathname);
		PrintWriter output = null;
		Scanner inputOfFile = null;
		try {
			inputOfFile = new Scanner(recordFile);
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot create " + pathname);
			System.exit(1);
		}
		int lineNum = 0;
		boolean readGoals = false;
		boolean readHabits = false;
		boolean readMoods = false;
		Record rec; 
		while (inputOfFile.hasNextLine()) { // file should contain one name followed by its record file name on each
											// line
			String line = inputOfFile.nextLine();
			//System.out.print(line + " line num = " + lineNum);
			if(!line.isEmpty()) {
				lineNum++; 
			}
			// System.out.println("line = " + line);
			if (line.equals("goals")) {
				readGoals = true;
			} else if (line.equals("habits")) {
				// System.out.print("helloooo");
				readHabits = true;
				readGoals = false;
				readMoods = false;
			} else if (line.equals("moods")) {
				readMoods = true;
				readHabits = false;
				readGoals = false;
			} else if (readGoals == true) {
				// System.out.println("readGoals == true!");
				int commaIndex = line.indexOf(",");
				String goalName = line.substring(0, commaIndex);
				int commaIndex2 = commaIndex + 1 + line.substring(commaIndex + 1).indexOf(","); // index of , after
																								// first ,
				String goalTime1 = line.substring(commaIndex + 2, commaIndex2);
				double goalTime = Double.parseDouble(goalTime1);
				// int commaIndex3 = line.substring(commaIndex2 + 1).indexOf(",");
				String progress1 = line.substring(commaIndex2 + 2);
				double progress = Double.parseDouble(progress1);
				// String time = line.substring(commaIndex2+1, )
				Goal tempG = new Goal(goalName, goalTime, progress);
				goals.add(tempG);

			} else if (readHabits == true) {
				int commaIndex = line.indexOf(",");
				String habitName = line.substring(0, commaIndex);
				int commaIndex2 = commaIndex + 1 + line.substring(commaIndex + 1).indexOf(","); // index of , after
																								// first ,
				String goalDays1 = line.substring(commaIndex + 2, commaIndex2);
				int goalDays = Integer.parseInt(goalDays1);
				// int commaIndex3 = line.substring(commaIndex2 + 1).indexOf(",");
				// int spaceIndex = line.substring(commaIndex2).indexOf(" ");
				String completedDays1 = line.substring(commaIndex2 + 2);
				int completedDays = Integer.parseInt(completedDays1);
				Habit tempH = new Habit(habitName, goalDays, completedDays);
				habits.add(tempH);
			} else if (readMoods = true) {
				String mood = line;
				moods.add(mood);
			}
		}
		//System.out.print(" " + lineNum);
		if(lineNum==0) { // if file is empty 
			rec = new Record(nameOfFile);
		}
		else {
		rec = new Record(goals, habits, moods, nameOfFile);
		}

		// rec.printRecord();

		return rec;

	}

	public static void printUserRecord(User temp) {
		temp.getRecord().printRecord();
	}
}
