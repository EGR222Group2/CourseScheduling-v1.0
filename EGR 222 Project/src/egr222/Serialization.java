package egr222;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serialization {
	static ArrayList<RoomInfo> RoomInfo = new ArrayList<>();
	static ArrayList<ClassInfo> ClassInfo = new ArrayList<>();
	static ArrayList<InstructorInfo> InstructorInfo = new ArrayList<>();
	static ArrayList<String> NotScheduled = new ArrayList<String>();
	
	public static void ReadObjects() {
		// Clear the arrays before reading the information back in.
		RoomInfo.clear();
		ClassInfo.clear();
		InstructorInfo.clear();
		
		// Reads objects from the RoomInfo.dat file into the RoomInfo ArrayList.
		File roomFile = new File("RoomInfo.txt");
		try {
			if(!roomFile.exists()){ // create new file if needed
				roomFile.createNewFile();
			}
			
			FileInputStream fi = new FileInputStream(roomFile);
			
			if(fi.available() > 0){
				ObjectInputStream os = new ObjectInputStream(fi);
				
				while (fi.available() > 0) {
					RoomInfo.add((RoomInfo)os.readObject());
				}
				os.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Reads objects from the CourseInfo.dat file into the ClassInfo ArrayList.
		File courseFile = new File("ClassInfo.txt");
		try  {
			if(!courseFile.exists()){ // create new file if needed
				courseFile.createNewFile();
			}
			
			FileInputStream fi = new FileInputStream(courseFile);
			
			if(fi.available() > 0){
				ObjectInputStream os = new ObjectInputStream(fi);
				
				while (fi.available() > 0) {
					ClassInfo.add((ClassInfo)os.readObject());
				}
				os.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Reads objects from the InstructorInfo.dat file into the InstructorInfo ArrayList.
		File instructorFile = new File("InstructorInfo.txt");
		try{
			if(!instructorFile.exists()){ // create new file if needed
				instructorFile.createNewFile();
			}
			
			FileInputStream fi = new FileInputStream(instructorFile);
			
			if(fi.available() > 0){
				ObjectInputStream os = new ObjectInputStream(fi);
			
				while (fi.available() > 0) {
					InstructorInfo.add((InstructorInfo)os.readObject());
				}
				os.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void NewRoom(String number, int capacity) {
		RoomInfo.add(new RoomInfo(number, capacity));
	}
	
	public void NewClass(String fullName, String instructor, String instructorID, int credits, int schedulingType, int maxCapacity, int year, int semester, float[][] classtime) {
		System.out.println("new class2");
		ClassInfo.add(new ClassInfo(fullName, instructor, instructorID, credits, schedulingType, maxCapacity, year, semester));
	}
	
	public void NewInstructor(String name, String instructorID) {
		InstructorInfo.add(new InstructorInfo(name, instructorID));
	}
		
	public static void WriteObjects() {
		// Writes Room Info to file.
		try (FileOutputStream fs = new FileOutputStream("RoomInfo.txt")) {			
			ObjectOutputStream os = new ObjectOutputStream(fs);
			for (int i = 0; i < RoomInfo.size(); i++) {
				os.writeObject(RoomInfo.get(i));
			}
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Writes Class Info to file.
		try (FileOutputStream fs = new FileOutputStream("ClassInfo.txt")) {			
			ObjectOutputStream os = new ObjectOutputStream(fs);
			for (int i = 0; i < ClassInfo.size(); i++) {
				os.writeObject(ClassInfo.get(i));
			}
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Writes Instructor Info to file.
		try (FileOutputStream fs = new FileOutputStream("InstructorInfo.txt")) {			
			ObjectOutputStream os = new ObjectOutputStream(fs);
			for (int i = 0; i < InstructorInfo.size(); i++) {
				os.writeObject(InstructorInfo.get(i));
			}
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void FileReset() {
		File f = null;
		
		f = new File("ClassInfo.txt");
		f.delete();
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f = new File("RoomInfo.txt");
		f.delete();
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f = new File("InstructorInfo.txt");
		f.delete();
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
