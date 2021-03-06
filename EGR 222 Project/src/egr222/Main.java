package egr222;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {

		Boolean quit = false;
		String task = "0";
		
		do {
			task = Menu();
			Serialization.ReadObjects();
			
			switch (task) {
				case "1": AddCourse(Serialization.RoomInfo, Serialization.ClassInfo, Serialization.InstructorInfo);
					break;
				case "2": AddRoom(Serialization.RoomInfo);
					break;
				case "3":	AddInstructor(Serialization.InstructorInfo);
					break;
				case "4": Schedule(Serialization.RoomInfo, Serialization.ClassInfo, Serialization.InstructorInfo, Serialization.NotScheduled);
					break;
				case "5":quit = true;
					break;
				default: System.out.println("Sorry that's an invalid input. Please try again.");
			}
			Serialization.FileReset();
			Serialization.WriteObjects();
		} while (!quit);
		
		System.out.println("Have a good day!");
		System.exit(0);
	}
	
	static String Menu() {
		System.out.println(" ");
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
		
		String task = "0";
		task = input.nextLine();
		System.out.println(" ");
		
		return task;
	}

	static void AddCourse(ArrayList<RoomInfo> RoomInfo,	ArrayList<ClassInfo> ClassInfo,	ArrayList<InstructorInfo> InstructorInfo) {
		String fullName = "LOL420";
		String instructor = " ";
		String instructorID= " ";
		String sect = "A";
		int credits=0;
		int schedulingType = 0;
		int maxCapacity = 0;
		int year=0;
		int semester=0;
		String prereqs = " ";
		String description = " ";
		Boolean pass = true;
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner( System.in );
		
		System.out.println("ADD COURSE");
		System.out.print("Let's start off by entering the 6 letter/number Course ID (e.g. 'EGR101', 'MAT245', 'ENG123', etc.) or enter 'e' at any time to return to the main menu: ");
		
		do {
			pass = true;
			
			fullName = input.nextLine().toUpperCase();
			if(fullName.substring(0).matches("E"))
			{
				System.out.println("Going back to the main menu.");
				return;
			}
			if (fullName.length() != 6 || !fullName.substring(0,3).matches("[a-zA-Z]+") || !fullName.substring(3).matches("[-+]?\\d*\\.?\\d+")) {
				System.out.print("Oh no, this is an invalid entry! Try entering the Course ID (e.g. 'EGR101', 'MAT245', 'ENG123', etc.) again: ");
				pass = false;
			}
			if(pass) {
				System.out.println("ADD SECTION");
				System.out.print("Now let's add the section letter to the course: ");
				
				do{
					pass = true;
					
					sect = input.nextLine().toUpperCase();
					if(sect.substring(0).matches("E"))
					{
						System.out.println("Going back to the main menu.");
						return;
					}
					if (sect.length() != 1 || !sect.matches("[a-zA-Z]+")){
						System.out.print("Oh no, this is an invalid entry! Please enter in a letter: ");
						pass = false;
					}
					if(pass) {
						System.out.println(fullName + sect);
						for (int i = 0; i < Serialization.ClassInfo.size(); i++) {
							if((fullName + sect).equals(Serialization.ClassInfo.get(i).fullName + Serialization.ClassInfo.get(i).sect)){
								System.out.print("Whoops! This class name and section is already registered. Try entering in a different section letter:");
								pass = false;
							}
						}
					}
				} while (!pass);
			}
		}while (!pass);
		
		System.out.print("Great! Now let's add the course's instructor. \nEnter in the instructor's unique 6-digit ID or type in \"R\" to register a new instructor: ");
		
		do {
			pass = true;
			instructorID = input.nextLine().toUpperCase();
			if(instructorID.substring(0).matches("E"))
			{				
				System.out.println("Going back to the main menu.");
				return;
			} else if (instructorID.matches("R")) {
				AddInstructor(Serialization.InstructorInfo);
				pass = false; 
				System.out.print("Welcome back to course registration! Let's try adding the course's instructor again. \nEnter in the instructor's unique 6-digit ID or type in \"R\" to register a new instructor: ");
			} else if (instructorID.length() != 6 || !instructorID.matches("[-+]?\\d*\\.?\\d+")) {
				System.out.print("Oh no, this is an invalid entry! \nTry entering in the instructors unique 6-digit ID again: ");
				pass = false;
			} else {
				pass = false;
				for (int i = 0; i < Serialization.InstructorInfo.size(); i++) {
					if(instructorID.matches(Serialization.InstructorInfo.get(i).instructorID.toString())){
						pass = true;
					}
				}
				if (!pass)
					System.out.print("Uh oh, this instructor is not registered! \nTry entering in a different instructor's unique 6-digit ID or type in \"R\" to register a new instructor: ");
			}
		} while (!pass);
		String creditS;				
		System.out.print("Awesome! Now enter in the number of credits for this course: ");
		//Validating amount of credits per course input
		do{
			pass=false;
			creditS = input.nextLine();
			if(creditS.matches("e"))
			{
				System.out.println("Going back to the main menu.");
				return;
			}
			credits = Integer.parseInt(creditS);
			if(credits >= 1 && credits <= 15)
			{
				pass=true;
			}else{
				System.out.println("Invalid input. Please input an integer from 1-15: ");
				pass = false;
			}
		}while(!pass);
		
		System.out.println("What kind of scheduling would you prefer?");
		System.out.println("");
		System.out.println("1. One class meeting per week");
		System.out.println("2. Two class meetings per week");
		System.out.println("3. Three class meetings per week");
		System.out.println("4. Hybrid scheduling (Half of the hours in class, the other half online).");
		System.out.println("");
		System.out.print("Type in the number corresponding to your choice: ");
		String schedulingT;
		do {				
			pass = false;
			schedulingT = input.nextLine();
			if(schedulingT.substring(0).matches("e"))
				{
					System.out.println("Going back to the main menu.");
					return;
			}
			if(schedulingT.matches("[-+]?\\d*\\.?\\d+"))
			{ 
				schedulingType = Integer.parseInt(schedulingT);
				if (schedulingType < 1 || schedulingType > 4){
					System.out.print("Whoops, that entry is invalid! Try typing in the number corresponding to your choice again: ");
				}else{
					pass = true;
				}					
			}else{
				System.out.println("Invalid input. Please enter a valid input: ");
			}			
		} while(!pass);

		
		System.out.print("Fantastic, only a few more details! Enter in the maximum capacity of the class: ");
		String stringToInt;
		do {
			pass = true;
			stringToInt = input.nextLine();
			if(stringToInt.substring(0).matches("e"))
			{
				System.out.println("Going back to the main menu.");
				return;
			}
			if (stringToInt.length() > 0) {
				if (!stringToInt.matches("[-+]?\\d*\\.?\\d+") || (Integer.parseInt(stringToInt) % 1 != 0)) {
					System.out.print("Uh oh, Did you enter in an integer for the maximum capacity of the class? Try again: ");
					pass = false;
				} else
					maxCapacity = Integer.parseInt(stringToInt);
			} else
				pass = false;
		} while(!pass);
		
		System.out.print("Good. Now enter in the year this class will start: ");
		String yeaR;
		do{
			pass=false;
			yeaR = input.nextLine();
			if(yeaR.matches("e"))
			{
				System.out.println("Going back to the main menu.");
				return;
			}			
			if(yeaR.matches("[a-zA-Z]+"))
			{
				System.out.println("Invalid input. Please input a valid year:");
			}else{
				year = Integer.parseInt(yeaR);
				if(year<2015)
				{
					System.out.println("This is an invalid year, plese enter a valid year:");
				}else{
					pass=true;
				}				
			}
		}while(!pass);	
		
		System.out.println("What semester will the course take place?");
		System.out.println(" ");
		System.out.println("1. Spring");
		System.out.println("2. Summer");
		System.out.println("3. Fall");
		System.out.println(" ");
		System.out.print("Select the number associated with the desired semester: ");
		String semesteR;
		do {
			pass = true;
			semesteR = input.nextLine();			
			if(semesteR.matches("e"))
			{
				System.out.println("Going back to the main menu.");
				return;				
			}
			if(semesteR.matches("[a-zA-Z]+")||!(semesteR.matches("[-+]?\\d*\\.?\\d+")))
			{
				System.out.println("I am sorry but you have entered an invalid number. Please input a valid number:");
				pass = false;
			}else{
				semester = Integer.parseInt(semesteR);
				if (semester < 1 || semester > 3) {
					pass = false;
					System.out.println(" ");
					System.out.print("You entered an invalid number. Try selecting the number associated with the desired semester again: ");
				}
			}				
		} while(!pass);
		
		System.out.print("So close! Enter in any prerequisites to this course, seperated by a space, or type in 'NONE': ");
		prereqs = input.nextLine();
		if (prereqs.length() < 6)
			prereqs = "NONE";
		
		System.out.print("\nFinally, why don't you add a description for this course or type 'NONE': ");
		description = input.nextLine();
		if (description.length() <= 4)
			description = "NONE";
		
		// Add the things to the other things.
		for (int i = 0; i < InstructorInfo.size(); i++) {
			if (instructorID.equals(InstructorInfo.get(i).instructorID)){
				InstructorInfo.get(i).classes.add(fullName);
				instructor = InstructorInfo.get(i).name;
			}
		}
		Serialization.ClassInfo.add(new ClassInfo(fullName, instructor, instructorID, sect, credits, schedulingType, maxCapacity, year, semester, prereqs, description));
		System.out.println(fullName + sect + " added!");
		System.out.println(" ");

	}
	
	static void AddRoom(ArrayList<RoomInfo> RoomInfo) {
		String roomNumber;
		int maxCapacity = 0;
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner( System.in );
		
		System.out.println("ADD CLASSROOM");
		System.out.print("Let's start by entering in the room number (You may also enter 'e' at any time to return to the main menu): ");
		
		Boolean pass = true;
		
		do {
			pass = true;
			roomNumber = input.nextLine();
			if(roomNumber.matches("e"))
			{
				System.out.println("Going back to the main menu.");
				return;
			
			}
			
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
			if(stringToInt.matches("e"))
			{
				System.out.println("Going back to the main menu.");
				return;
			}
			if (!stringToInt.matches("[-+]?\\d*\\.?\\d+") || (Integer.parseInt(stringToInt) % 1 != 0)) {
				System.out.print("Uh oh, did you not enter in an integer for the maximum capacity of the room? Try again: ");
				pass = false;
			} else{
				maxCapacity = Integer.parseInt(stringToInt);
				if(maxCapacity<1)
				{
					System.out.println("This is an invalid input. Please enter a valid capacity: ");
					pass = false;
				}
			}				
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
		System.out.print("Let's start by entering in the unique 6-digit ID of the instructor (you may also enter 'e' at any time to return to the main menu): ");
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner( System.in );
		Boolean pass = true;
		
		do {
			pass = true;
			instructorID = input.nextLine().toUpperCase();
			if(instructorID.matches("E"))
			{
				System.out.println("Going back to the main menu.");
				return;
			}
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
		name = input.nextLine().toUpperCase();
		if(name.matches("E")){
			System.out.println("Going back to the main menu.");
			return;
		}
			
		// Add the things to the other things.
		Serialization.InstructorInfo.add(new InstructorInfo(name, instructorID));
		System.out.println("Instructor added!");
		System.out.println(" ");
	}
	
	static void Schedule(ArrayList<RoomInfo> RoomInfo,	ArrayList<ClassInfo> ClassInfo,	ArrayList<InstructorInfo> InstructorInfo, ArrayList <String> NotScheduled) {	
		
		ScheduleGenerator.Generate(Serialization.RoomInfo, Serialization.ClassInfo, Serialization.InstructorInfo, Serialization.NotScheduled);
		
		System.out.println("FULL SCHEDULE:");
		System.out.println(" ");
		
		String days = "";
		String semester = null;
		String startTime = null;
		String endTime = null;
		
		for (int i = 0; i < Serialization.ClassInfo.size(); i++) {
			days = "";
			
			switch (ClassInfo.get(i).semester) {
			case 1: semester = "SPRING";
				break;
			case 2: semester = "SUMMER";
				break;
			default: semester = "FALL";
				break;
			}
			
			for (int j = 0; j < 5; j++){
				for (int k = 0; k < 2; k++){
					if (k == 0 && ClassInfo.get(i).classtime[j][k] != null && ClassInfo.get(i).roomNumber != "Not Assigned") {
						
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
				System.out.println("Name: " + Serialization.ClassInfo.get(i).fullName + Serialization.ClassInfo.get(i).sect + " Instructor: " + Serialization.ClassInfo.get(i).instructor + " Room Number: " + Serialization.ClassInfo.get(i).roomNumber 
						+ " Credits: " + Serialization.ClassInfo.get(i).credits + " Max Capacity: " + Serialization.ClassInfo.get(i).maxCapacity + " Year: " + Serialization.ClassInfo.get(i).year 
						+ " Semester: " + semester + " Classtime: " + days + " " + startTime + "-" + endTime + "\nPrerequisites: " + ClassInfo.get(i).prereqs + "\nDescription: " + ClassInfo.get(i).description);
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
			System.out.print("Enter a Course ID, Instructor ID or name, or Room Number to search, or enter 'e' to exit: ");
			search = input.nextLine().toUpperCase();
			
			if(search.equals("e") || search.equals("E")){
				return;
			}
			
			System.out.println(" ");
			System.out.println("SEARCH RESULTS:");
			System.out.println(" ");
			
			SearchResults.clear();
			
			pass = false;
			days = "";
			
			// Users are able to search by course name, course number, room number, instructor, year, or semester.
			for (int i = 0; i < ClassInfo.size(); i ++) {
				if(search.length() >= 3) {
					// This narrows searches to the number part.
					if (search.matches("[-+]?\\d*\\.?\\d+") && search.equals(ClassInfo.get(i).fullName.substring(3,6))) {
						SearchResults.add(new ClassInfo(ClassInfo.get(i).fullName, ClassInfo.get(i).instructor, ClassInfo.get(i).instructorID, ClassInfo.get(i).sect, ClassInfo.get(i).credits, ClassInfo.get(i).schedulingType, ClassInfo.get(i).maxCapacity, ClassInfo.get(i).year, ClassInfo.get(i).semester, ClassInfo.get(i).prereqs, ClassInfo.get(i).description));
						for (int j = 0; j < SearchResults.size(); j++) {
							if ((ClassInfo.get(i).fullName + ClassInfo.get(i).sect).equals(SearchResults.get(j).fullName + SearchResults.get(j).sect)) {
								SearchResults.get(j).roomNumber = ClassInfo.get(i).roomNumber;
								for (int k = 0; k < 5; k++){
									for (int l = 0; l < 2; l++){
										SearchResults.get(j).classtime[k][l] = ClassInfo.get(i).classtime[k][l];
									}
								}
							}
						}
						pass = true;
						// This searches for string matches, such as the class name or the entire instructor ID or room number.
					} else if (search.equals(ClassInfo.get(i).fullName) || search.equals(ClassInfo.get(i).fullName + ClassInfo.get(i).sect) || (search.length() <= (ClassInfo.get(i).fullName + ClassInfo.get(i).sect).length() && search.equals((ClassInfo.get(i).fullName + ClassInfo.get(i).sect).substring(0,search.length()))) || search.equals(ClassInfo.get(i).instructor) || (search.length() <= ClassInfo.get(i).instructor.length() && search.equals(ClassInfo.get(i).instructor.substring(0,search.length()))) || search.equals(ClassInfo.get(i).instructorID) || search.equals(ClassInfo.get(i).roomNumber) || search.equals(Integer.toString(ClassInfo.get(i).year))){
						SearchResults.add(new ClassInfo(ClassInfo.get(i).fullName, ClassInfo.get(i).instructor, ClassInfo.get(i).instructorID, ClassInfo.get(i).sect, ClassInfo.get(i).credits, ClassInfo.get(i).schedulingType, ClassInfo.get(i).maxCapacity, ClassInfo.get(i).year, ClassInfo.get(i).semester, ClassInfo.get(i).prereqs, ClassInfo.get(i).description));
						for (int j = 0; j < SearchResults.size(); j++) {
							if ((ClassInfo.get(i).fullName + ClassInfo.get(i).sect).equals(SearchResults.get(j).fullName + SearchResults.get(j).sect)) {
								SearchResults.get(j).roomNumber = ClassInfo.get(i).roomNumber;
								for (int k = 0; k < 5; k++){
									for (int l = 0; l < 2; l++){
										SearchResults.get(j).classtime[k][l] = ClassInfo.get(i).classtime[k][l];
									}
								}
							}
						}
						pass = true;
						// Checks to see if a room was assigned before checking for a room number match.
					} else if(ClassInfo.get(i).roomNumber != null && search.length() <= ClassInfo.get(i).roomNumber.length()) {
						if (search.equals(ClassInfo.get(i).roomNumber.substring(0,search.length()))) {
							SearchResults.add(new ClassInfo(ClassInfo.get(i).fullName, ClassInfo.get(i).instructor, ClassInfo.get(i).instructorID, ClassInfo.get(i).sect, ClassInfo.get(i).credits, ClassInfo.get(i).schedulingType, ClassInfo.get(i).maxCapacity, ClassInfo.get(i).year, ClassInfo.get(i).semester, ClassInfo.get(i).prereqs, ClassInfo.get(i).description));
							for (int j = 0; j < SearchResults.size(); j++) {
								if ((ClassInfo.get(i).fullName + ClassInfo.get(i).sect).equals(SearchResults.get(j).fullName + SearchResults.get(j).sect)) {
									SearchResults.get(j).roomNumber = ClassInfo.get(i).roomNumber;
									for (int k = 0; k < 5; k++){
										for (int l = 0; l < 2; l++){
											SearchResults.get(j).classtime[k][l] = ClassInfo.get(i).classtime[k][l];
										}
									}
								}
							}
							pass = true;
						}
						// The last resort is to check by semester.
					} else {
						switch (ClassInfo.get(i).semester) {
						case 1: semester = "SPRING";
							break;
						case 2: semester = "SUMMER";
							break;
						default: semester = "FALL";
							break;
						}
						
						if(search.equals(semester)) {
							SearchResults.add(new ClassInfo(ClassInfo.get(i).fullName, ClassInfo.get(i).instructor, ClassInfo.get(i).instructorID, ClassInfo.get(i).sect, ClassInfo.get(i).credits, ClassInfo.get(i).schedulingType, ClassInfo.get(i).maxCapacity, ClassInfo.get(i).year, ClassInfo.get(i).semester, ClassInfo.get(i).prereqs, ClassInfo.get(i).description));
							for (int j = 0; j < SearchResults.size(); j++) {
								if ((ClassInfo.get(i).fullName + ClassInfo.get(i).sect).equals(SearchResults.get(j).fullName + SearchResults.get(j).sect)) {
									SearchResults.get(j).roomNumber = ClassInfo.get(i).roomNumber;
									for (int k = 0; k < 5; k++){
										for (int l = 0; l < 2; l++){
											SearchResults.get(j).classtime[k][l] = ClassInfo.get(i).classtime[k][l];
										}
									}
								}
							}
							pass = true;
						}
					}
				} 
			}
			if (pass) {
				
				for (int i = 0; i < SearchResults.size(); i++) {
					days = "";
					
					switch (SearchResults.get(i).semester) {
					case 1: semester = "SPRING";
						break;
					case 2: semester = "SUMMER";
						break;
					default: semester = "FALL";
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
					
					if(SearchResults.get(i).roomNumber != null){
						System.out.println("Name: " + SearchResults.get(i).fullName + SearchResults.get(i).sect + " Instructor: " + SearchResults.get(i).instructor + " Room Number: " + SearchResults.get(i).roomNumber 
								+ " Credits: " + SearchResults.get(i).credits + " Max Capacity: " + SearchResults.get(i).maxCapacity + " Year: " + SearchResults.get(i).year 
								+ " Semester: " + semester + " Classtime: " + days + " " + startTime + "-" + endTime+ "\nPrerequisites: " + SearchResults.get(i).prereqs + "\nDescription: " + SearchResults.get(i).description);
					} else if(SearchResults.get(i).roomNumber == null){
						System.out.println("Name: " + SearchResults.get(i).fullName + SearchResults.get(i).sect + " Instructor: " + SearchResults.get(i).instructor + " Room Number: " + "NOT ASSIGNED"  
								+ " Credits: " + SearchResults.get(i).credits + " Max Capacity: " + SearchResults.get(i).maxCapacity + " Year: " + SearchResults.get(i).year 
								+ " Semester: " + semester + " Classtime: " + "NOT ASSIGNED" + "\nPrerequisies: " + SearchResults.get(i).prereqs + "\nDescription: " + SearchResults.get(i).description);
					}

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