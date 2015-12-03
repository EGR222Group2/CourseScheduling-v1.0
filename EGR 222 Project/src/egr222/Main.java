package egr222;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {

		Boolean quit = false;
		int task = 0;
		
		do {			
			task = Menu();
			Serialization.ReadObjects();
			
			switch (task) {
				case 1: AddCourse(Serialization.RoomInfo, Serialization.ClassInfo, Serialization.InstructorInfo);
					break;
				case 2: AddRoom(Serialization.RoomInfo);
					break;
				case 3:	AddInstructor(Serialization.InstructorInfo);
					break;
				case 4: Schedule(Serialization.RoomInfo, Serialization.ClassInfo, Serialization.InstructorInfo, Serialization.NotScheduled);
					break;
				default: quit = true;
					break;
			}
			Serialization.FileReset();
			Serialization.WriteObjects();
		} while (!quit);
		
		System.out.println("Have a good day!");
		System.exit(0);
	}
	
	static int Menu() {
		System.out.println("Welcome to the California Baptist University catalog creator!");
		System.out.println("What would you like to do today?");
		System.out.println(" ");
		System.out.println("1. Add a new course");
		System.out.println("2. Add a new classroom");
		System.out.println("3. Add a new Instructor");
		System.out.println("4. Generate, view, and search the catalog");
		System.out.println("5. Quit");
		System.out.println(" ");
		System.out.print("Please enter in the number associated with the desired task: ");
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner( System.in );
		
		int task = 0;
		do {
			task = input.nextInt();
		} while (task <= 0 && task > 5);
		System.out.println(" ");
		
		return task;
	}

	static void AddCourse(ArrayList<RoomInfo> RoomInfo,	ArrayList<ClassInfo> ClassInfo,	ArrayList<InstructorInfo> InstructorInfo) {
		String fullName = "LOL420";
		String instructor = " ";
		String instructorID;
		int credits;
		int schedulingType = 0;
		int maxCapacity = 0;
		int year;
		int semester;
		Boolean pass = true;
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner( System.in );
		
		System.out.println("ADD COURSE");
		System.out.print("Let's start off by entering the 6 letter/number Course ID (e.g. 'EGR101', 'MAT245', 'ENG123', etc.):");
		
		do {
			pass = true;
			fullName = input.nextLine();
			if (fullName.length() != 6 || !fullName.substring(0,3).matches("[a-zA-Z]+") || !fullName.substring(3).matches("[-+]?\\d*\\.?\\d+")) {
				System.out.print("Oh no, this is an invalid entry! Try entering the Course ID (e.g. 'EGR101', 'MAT245', 'ENG123', etc.) again: ");
				pass = false;
			}
			if(pass) {
				System.out.println(fullName);
				for (int i = 0; i < Serialization.ClassInfo.size(); i++) {
					if(fullName.equals(Serialization.ClassInfo.get(i).fullName)){
						System.out.print("Whoops! This class name is already registered. Try entering the Course ID (e.g. 'EGR101', 'MAT245', 'ENG123', etc.) again: ");
						pass = false;

					}
				}
			}
		} while (!pass);
		
		System.out.print("Great! Now let's add the course's instructor. Enter in the instructors unique 6-digit ID:");
		
		do {
			pass = true;
			instructorID = input.nextLine();
			if (instructorID.length() != 6 || !instructorID.matches("[-+]?\\d*\\.?\\d+")) {
				System.out.print("Oh no, this is an invalid entry! Try entering in the instructors unique 6-digit ID again: ");
				pass = false;
			} else {
				pass = false;
				for (int i = 0; i < Serialization.InstructorInfo.size(); i++) {
					if(instructorID.equals(InstructorInfo.get(i).instructorID));
						pass = true;
				}
				if (!pass)
					System.out.print("Uh oh, this instructor is not registered! Try entering in a different instructors unique 6-digit ID:");
			}
		} while (!pass);
						
		System.out.print("Awesome! Now enter in the number of credits for this course:");
		credits = input.nextInt();
		
		System.out.println("What kind of scheduling would you prefer?");
		System.out.println("");
		System.out.println("1. One class meeting per week");
		System.out.println("2. Two class meetings per week");
		System.out.println("3. Three class meetings per week");
		System.out.println("4. Hybrid scheduling (Half of the hours in class, the other half online).");
		System.out.println("");
		System.out.print("Type in the number corresponding to your choice: ");
		do {
			schedulingType = input.nextInt();
			if (schedulingType < 1 || schedulingType > 4)
				System.out.print("Whoops, that entry is invalid! Try typing in the number corresponding to your choice again: ");
		} while(schedulingType < 1 && schedulingType > 4);

		
		System.out.print("Fantastic, only a few more details! Enter in the maximum capacity of the class:");
		String stringToInt;
		do {
			pass = true;
			stringToInt = input.nextLine();
			if (stringToInt.length() > 0) {
				if (!stringToInt.matches("[-+]?\\d*\\.?\\d+") || (Integer.parseInt(stringToInt) % 1 != 0)) {
					System.out.print("Uh oh, Did you enter in an integer for the maximum capacity of the class? Try again: ");
					pass = false;
				} else
					maxCapacity = Integer.parseInt(stringToInt);
			} else
				pass = false;
		} while(!pass);
		
		System.out.print("Good. Now enter in the year this class will start:");
		year = input.nextInt();
		
		System.out.println("Finally, we'll select the semester of the course");
		System.out.println(" ");
		System.out.println("1. Spring");
		System.out.println("2. Summer");
		System.out.println("3. Fall");
		System.out.println(" ");
		System.out.print("Select the number associated with the desired semester: ");
		do {
			pass = true;
			semester = input.nextInt();
			
			if (semester < 1 || semester > 3) {
				pass = false;
				System.out.println(" ");
				System.out.print("You entered an invalid number. Try selecting the number associated with the desired semester again: ");
			}	
		} while(!pass);
		
		// Add the things to the other things.
		for (int i = 0; i < InstructorInfo.size(); i++) {
			if (instructorID.equals(InstructorInfo.get(i).instructorID));
				InstructorInfo.get(i).classes.add(fullName);
				instructor = InstructorInfo.get(i).name;
		}
		Serialization.ClassInfo.add(new ClassInfo(fullName, instructor, instructorID, credits, schedulingType, maxCapacity, year, semester));
		System.out.println(fullName + " added!");
		System.out.println(" ");

	}
	
	static void AddRoom(ArrayList<RoomInfo> RoomInfo) {
		String roomNumber;
		int maxCapacity = 0;
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner( System.in );
		
		System.out.println("ADD CLASSROOM");
		System.out.print("Let's start by entering in the room number: ");
		
		Boolean pass = true;
		
		do {
			pass = true;
			roomNumber = input.nextLine();
			for (int i = 0; i < Serialization.RoomInfo.size(); i++) {
				if(roomNumber.equals(RoomInfo.get(i).roomNumber)) {
					System.out.print("Uh oh! This room already exists! Try entering in a unique room number: ");
					pass = false;
					break;
				}
			}
		} while (!pass);
		
		System.out.print("Great! Now enter in the maximum capacity of the room: ");
		
		String stringToInt;
		
		do {
			pass = true;
			stringToInt = input.nextLine();
			if (!stringToInt.matches("[-+]?\\d*\\.?\\d+") || (Integer.parseInt(stringToInt) % 1 != 0)) {
				System.out.print("Uh oh, Did you enter in an integer for the maximum capacity of the room? Try again: ");
				pass = false;
			} else
				maxCapacity = Integer.parseInt(stringToInt);
		} while(!pass);
		
		// Add the things to the other things.
		Serialization.RoomInfo.add(new RoomInfo(roomNumber, maxCapacity));
		System.out.println("Room added!");
		System.out.println(" ");

	}
	
	static void AddInstructor(ArrayList<InstructorInfo> InstructorInfo) {
		String name = null;
		String instructorID;
		
		System.out.println("ADD INSTRUCTOR");
		System.out.print("Let's start by entering in the unique 6-digit ID of the instructor: ");
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner( System.in );
		Boolean pass = true;
		
		do {
			pass = true;
			instructorID = input.nextLine();
			if (instructorID.length() != 6 || !instructorID.matches("[-+]?\\d*\\.?\\d+")) {
				System.out.print("Oh no, this is an invalid entry! Try entering in the instructors unique 6-digit ID again: ");
				pass = false;
			} else {
				for (int i = 0; i < Serialization.InstructorInfo.size(); i++) {
					if(instructorID.equals(InstructorInfo.get(i).instructorID)) {
						System.out.print("Uh oh! This instructor already exists! Try entering in a different 6-digit ID: ");
						pass = false;
						break;
					}
				}
			}
		} while (!pass);
		
		System.out.print("Now just enter in the instructor's name: ");
		name = input.nextLine();
		
		// Add the things to the other things.
		Serialization.InstructorInfo.add(new InstructorInfo(name, instructorID));
		System.out.println("Instructor added!");
		System.out.println(" ");
	}
	
	static void Schedule(ArrayList<RoomInfo> RoomInfo,	ArrayList<ClassInfo> ClassInfo,	ArrayList<InstructorInfo> InstructorInfo, ArrayList <String> NotScheduled) {	
		
		ScheduleGenerator.Generate(Serialization.RoomInfo, Serialization.ClassInfo, Serialization.InstructorInfo, Serialization.NotScheduled);
		
		System.out.println(" ");
		System.out.println("FULL SCHEDULE:");
		System.out.println(" ");
		
		String days = "";
		String semester = null;
		String startTime = null;
		String endTime = null;
		
		for (int i = 0; i < Serialization.ClassInfo.size(); i++) {
			days = "";
			
			switch (ClassInfo.get(i).semester) {
			case 1: semester = "Spring";
				break;
			case 2: semester = "Summer";
				break;
			default: semester = "Fall";
				break;
			}
			
			for (int j = 0; j < 5; j++){
				for (int k = 0; k < 2; k++){
					if (k == 0 && ClassInfo.get(i).classtime[j][k] != null) {
						
						switch (j) {
						case 0: days += "M"; break;
						case 1: days += "T"; break;
						case 2: days += "W"; break;
						case 3: days += "R"; break;
						case 4: days += "F"; break;
						}
						
						startTime = ClassInfo.get(i).classtime[j][0];
						endTime = ClassInfo.get(i).classtime[j][1];
					}
				}
			}
			if(Serialization.ClassInfo.get(i).roomNumber != null){
				System.out.println("Name: " + Serialization.ClassInfo.get(i).fullName + " Instructor: " + Serialization.ClassInfo.get(i).instructor + " Room Number: " + Serialization.ClassInfo.get(i).roomNumber 
						+ " Credits: " + Serialization.ClassInfo.get(i).credits + " Max Capacity: " + Serialization.ClassInfo.get(i).maxCapacity + " Year: " + Serialization.ClassInfo.get(i).year 
						+ " Semester: " + semester + " Classtime: " + days + " " + startTime + "-" + endTime);
				System.out.println(" ");
			}
		}
		
		if(!NotScheduled.isEmpty()){
			System.out.println("UNABLE TO SCHEDULE: ");
			System.out.println(" ");
			for(int i = 0; i < NotScheduled.size(); i++){
				System.out.println("Name: " + Serialization.NotScheduled.get(i));
				System.out.println(" ");
			}
		}
		
		Search(RoomInfo, ClassInfo, InstructorInfo);
		
	}
	
	static void Search(ArrayList<RoomInfo> RoomInfo, ArrayList<ClassInfo> ClassInfo,	ArrayList<InstructorInfo> InstructorInfo) { 
		String days = "";
		String semester = null;
		String startTime = null;
		String endTime = null;
		String search = null;
		Boolean pass = false;
		Boolean again = false;
		ArrayList<ClassInfo> SearchResults = new ArrayList<>();
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner( System.in );
		
		do {		
			System.out.print("Enter a Course ID, Instructor ID, or Room Number to search: ");
			search = input.nextLine();
			
			System.out.println(" ");
			System.out.println("SEARCH RESULTS:");
			System.out.println(" ");
			
			SearchResults.clear();
			
			pass = false;
			days = "";
			
			/* There are 5 possible ways to match a the user search with a course:
				1. If the entry is only the course number (e.g. 222, 201, 255).
				2. If the entry is only the 3 letters before the course number (e.g. 'EGR', 'PHY', 'MAT').
				3. If the entry contains both the 3 letters and the course number (e.g. 'EGR222', 'PHY 201', 'MAT245'). 
				4. If the entry contains the instructor's name or the instructor's ID.
				5. If the entry contains the room number of the class. */
			for (int i = 0; i < ClassInfo.size(); i ++) {
				if(search.length() >= 3) {
					// This narrows searches to the number part.
					if (search.matches("[-+]?\\d*\\.?\\d+") && search.equals(ClassInfo.get(i).fullName.substring(3,6))) {
						SearchResults.add(new ClassInfo(ClassInfo.get(i).fullName, ClassInfo.get(i).instructor, ClassInfo.get(i).instructorID, ClassInfo.get(i).credits, ClassInfo.get(i).schedulingType, ClassInfo.get(i).maxCapacity, ClassInfo.get(i).year, ClassInfo.get(i).semester));
						for (int j = 0; j < SearchResults.size(); j++) {
							if (ClassInfo.get(i).fullName.equals(SearchResults.get(j).fullName)) {
								SearchResults.get(j).roomNumber = ClassInfo.get(i).roomNumber;
								for (int k = 0; k < 5; k++){
									for (int l = 0; l < 2; l++){
										SearchResults.get(j).classtime[k][l] = ClassInfo.get(j).classtime[k][l];
									}
								}
							}
						}
						pass = true;
						// This narrows searches to the first 3 letters.
					} else if (search.length() == 3 && search.equals(ClassInfo.get(i).fullName.substring(0,3))) { 						
						SearchResults.add(new ClassInfo(ClassInfo.get(i).fullName, ClassInfo.get(i).instructor, ClassInfo.get(i).instructorID, ClassInfo.get(i).credits, ClassInfo.get(i).schedulingType, ClassInfo.get(i).maxCapacity, ClassInfo.get(i).year, ClassInfo.get(i).semester));
						for (int j = 0; j < SearchResults.size(); j++) {
							if (ClassInfo.get(i).fullName.equals(SearchResults.get(j).fullName)) {
								SearchResults.get(j).roomNumber = ClassInfo.get(i).roomNumber;
								for (int k = 0; k < 5; k++){
									for (int l = 0; l < 2; l++){
										SearchResults.get(j).classtime[k][l] = ClassInfo.get(j).classtime[k][l];
									}
								}
							}
						}
						pass = true;
						// This searches for an entire string, such as the entire class name or the entire instructor ID.
					} else if (search.equals(ClassInfo.get(i).fullName) || search.equals(ClassInfo.get(i).instructor) || search.equals(ClassInfo.get(i).instructorID) || search.equals(ClassInfo.get(i).roomNumber)){
						SearchResults.add(new ClassInfo(ClassInfo.get(i).fullName, ClassInfo.get(i).instructor, ClassInfo.get(i).instructorID, ClassInfo.get(i).credits, ClassInfo.get(i).schedulingType, ClassInfo.get(i).maxCapacity, ClassInfo.get(i).year, ClassInfo.get(i).semester));
						for (int j = 0; j < SearchResults.size(); j++) {
							if (ClassInfo.get(i).fullName.equals(SearchResults.get(j).fullName)) {
								SearchResults.get(j).roomNumber = ClassInfo.get(i).roomNumber;
								for (int k = 0; k < 5; k++){
									for (int l = 0; l < 2; l++){
										SearchResults.get(j).classtime[k][l] = ClassInfo.get(j).classtime[k][l];
									}
								}
							}
						}
						pass = true;
					}
				} 
			}
			if (pass) {
				
				for (int i = 0; i < SearchResults.size(); i++) {
					
					switch (SearchResults.get(i).semester) {
					case 1: semester = "Spring";
						break;
					case 2: semester = "Summer";
						break;
					default: semester = "Fall";
						break;
					}
					
					for (int j = 0; j < 5; j++){
						for (int k = 0; k < 2; k++){
							if (k == 0 && SearchResults.get(i).classtime[j][k] != null) {
								
								switch (j) {
								case 0: days += "M"; break;
								case 1: days += "T"; break;
								case 2: days += "W"; break;
								case 3: days += "R"; break;
								case 4: days += "F"; break;
								}
								
								startTime = SearchResults.get(i).classtime[j][0];
								endTime = SearchResults.get(i).classtime[j][1];
							}
						}
					}
					
					System.out.println("Name: " + Serialization.ClassInfo.get(i).fullName + " Instructor: " + Serialization.ClassInfo.get(i).instructor + " Room Number: " + Serialization.ClassInfo.get(i).roomNumber 
							+ " Credits: " + Serialization.ClassInfo.get(i).credits + " Max Capacity: " + Serialization.ClassInfo.get(i).maxCapacity + " Year: " + Serialization.ClassInfo.get(i).year 
							+ " Semester: " + semester + " Classtime: " + days + " " + startTime + "-" + endTime);
					System.out.println(" ");
				}
				
			} else {
				System.out.print("Uh oh! We couldn't find a match. ");
			}
			
			System.out.print("Search again? (y/n): ");
			
			do {
				pass = false;
				search = input.nextLine();
				
				if (search.equals("y") || search.equals("Y")) {
					pass= true;
					again = true;
				} else if (search.equals("n") || search.equals("N")) {
					pass= true;
					again = false;
				} else {
					System.out.print("Whoops! That's an invalid response. Search again? (Type either 'y' for yes or 'n' for no): ");
				}
				
			} while(!pass);
			
		} while(pass && again);
	}
}