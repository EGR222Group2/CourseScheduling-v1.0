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
	
	public static void ReadObjects() {
		// Clear the arrays before reading the information back in.
		RoomInfo.clear();
		ClassInfo.clear();
		InstructorInfo.clear();
		
		// Reads objects from the RoomInfo.dat file into the RoomInfo ArrayList.
		try (FileInputStream fi = new FileInputStream("RoomInfo.dat")) {
			ObjectInputStream os = new ObjectInputStream(fi);
			while (fi.available() > 0) {
				RoomInfo.add((RoomInfo)os.readObject());
			}
			os.close();
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
		
		// Reads objects from the ClassInfo.dat file into the ClassInfo ArrayList.
		try (FileInputStream fi = new FileInputStream("ClassInfo.dat")) {
			ObjectInputStream os = new ObjectInputStream(fi);
			while (fi.available() > 0) {
				ClassInfo.add((ClassInfo)os.readObject());
			}
			os.close();
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
		try (FileInputStream fi = new FileInputStream("InstructorInfo.dat")) {
			ObjectInputStream os = new ObjectInputStream(fi);
			while (fi.available() > 0) {
				InstructorInfo.add((InstructorInfo)os.readObject());
			}
			os.close();
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
	
	public void NewClass(String fullName, String instructor, String instructorID, int credits, int maxCapacity, int year, int semester, float[][] classtime) {
		System.out.println("new class2");
		ClassInfo.add(new ClassInfo(fullName, instructor, instructorID, credits, maxCapacity, year, semester));
	}
	
	public void NewInstructor(String name, String instructorID) {
		InstructorInfo.add(new InstructorInfo(name, instructorID));
	}
		
	public static void WriteObjects() {
		// Writes Room Info to file.
		try (FileOutputStream fs = new FileOutputStream("RoomInfo.dat")) {			
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
		try (FileOutputStream fs = new FileOutputStream("ClassInfo.dat")) {			
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
		try (FileOutputStream fs = new FileOutputStream("InstructorInfo.dat")) {			
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
		
		f = new File("ClassInfo.dat");
		f.delete();
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f = new File("RoomInfo.dat");
		f.delete();
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f = new File("InstructorInfo.dat");
		f.delete();
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
