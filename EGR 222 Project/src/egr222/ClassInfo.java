package egr222;

import java.io.Serializable;
import java.util.Comparator;

public class ClassInfo implements Serializable {
	
	private static final long serialVersionUID = -7830607127840985313L;
	
	String fullName = "LOL420";
	String division = fullName.substring(0,3);
	String section = fullName.substring(3,6);
	String instructor;
	String instructorID;
	String roomNumber;
	int credits;
	int maxCapacity;
	int year;
	int semester;
	
	// The first part of the array checks for the day.
	// The second holds the starting and ending time of class for that specific day. 
	String [][] classtime = new String[5][2];
	
	public ClassInfo(String fullName, String instructor, String instructorID, int credits, int maxCapacity, int year, int semester){
		this.fullName = fullName;
		this.division = fullName.substring(0,3);
		this.section = fullName.substring(3,6);
		this.instructor = instructor;
		this.instructorID = instructorID;
		this.credits = credits;
		this.maxCapacity = maxCapacity;
		this.year = year;
		this.semester = semester;
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 2; j++){
				this.classtime[i][j] = " ";
			}
		}
	}
	
	public int getCredits(){
		return this.credits;
	}

	@Override
	public String toString() {
		return new StringBuffer(" Class : ")
		.append(this.division + this.section)
		.append(" Room Number : ")
		.append(this.roomNumber)
		.append(" Credits : ")
		.append(this.credits)
 	   	.append(" Max Capacity : ")
 	   	.append(this.maxCapacity).toString();
	}
	
	public class CourseComparator implements Comparator<ClassInfo> {

		@Override
		public int compare(ClassInfo course1, ClassInfo course2) {
			Integer comparison1 = course1.getCredits();
			Integer comparison2 = course2.getCredits();
	        return comparison1.compareTo(comparison2);
	    }
		
	}
	
}