import java.util.*;
import java.text.SimpleDateFormat;

public class Menu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		ArrayList<User> users = new ArrayList<>();
		System.out.print("Hello, have you used this program before? (y/n) ");
		String response = in.nextLine();
		String username = "";
		User temp = null;
		if (response.equals("y")) {
			for (User t : users) {
				if (t.getName().equals(username)) {
					temp = t;
					System.out.print("Enter Username: ");
					username = in.nextLine();
				} else {
					System.out.print("What is your name? ");
					setUpNewUser(in, temp);
					username = in.nextLine();
				}
				temp = new User(username);
				users.add(temp);
				recordToday(in, temp);
			}
		}

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
			System.out.print("\nHow are you feeling today? ( :) or :| or :( )");
			String currentMood = in.nextLine(); 
			temp.setMood(currentMood); 
			break; 

		case 2:
			System.out.println("Have you completed " + temp.getHabit().getHabitName() + " ? (Y/N)");
			String answer = in.nextLine();
			if (answer.equals("Y")) {
				temp.getHabit().recordProgress();
				if (temp.getHabit().finishHabit() == true) {
					System.out.println("You completed your habit! Good job! ");
					System.out.println("What habit would you like to work on next?");
					String habitChoice = in.nextLine();
					System.out.println("How long do you want to work on your habit?(days)");
					int habitTime = in.nextInt();
					Habit currentHabit = new Habit(habitChoice, habitTime);
					temp.setHabit(currentHabit);
				}
			}
			else if(answer.equals("N")){
				System.out.println("Bad dog! Go work on your habit!");
			}
			break; 
		case 3:
			System.out.println("Have you made progress towards your goal (" + temp.getGoal() + ")?");
			String response = in.nextLine(); 
			if(response.equals("Y")) {
				System.out.println("How many hours did you complete? ");
				double hours = in.nextDouble(); 
				temp.getGoal().recordProgress(hours);
				System.out.print("Any comments on your progress? (Y/N)");
				String ans = in.nextLine(); 
				if(ans.equals("Y")) {
					System.out.print("Enter your comments: ");
					String comments = in.nextLine(); 
					temp.getGoal().addComments(comments);
				}
			}
			if(response.equals("N")) {
				System.out.println("Put more effort please!!!");
			}
			break; 
		}

	}

	public static void setUpNewUser(Scanner in, User temp) {
		// SET UP HABIT
		System.out.println("What habit would you like to work on?");
		String habitChoice = in.nextLine();
		System.out.println("How long do you want to work on your habit?(days)");
		int habitTime = in.nextInt();
		Habit currentHabit = new Habit(habitChoice, habitTime);
		temp.setHabit(currentHabit);
		
		// SET UP GOAL 
		System.out.println("What goal would you like to work on?");
		String goalChoice = in.nextLine(); 
		System.out.println("How many hours do you want to work on your goal?");
		int goalTime = in.nextInt(); 
		Goal currentGoal = new Goal(goalChoice, goalTime);
		temp.setGoal(currentGoal);
		
	}
}
