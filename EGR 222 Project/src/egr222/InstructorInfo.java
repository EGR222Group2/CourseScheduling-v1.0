package egr222;

import java.io.Serializable;
import java.util.ArrayList;

public class InstructorInfo implements Serializable {
	
	private static final long serialVersionUID = -4650757226471996580L;
	
	String name;
	String instructorID;
	ArrayList<String> classes = new ArrayList<>();
	
	// The first part of the array checks for the day.
	// The second checks for 58 points in the day set at every 15 minute point from 7am to 9:30pm.
	Boolean[][] inClass = new Boolean[5][58];
	
	public InstructorInfo(String name, String instructorID) {
		this.name = name;
		this.instructorID = instructorID;
		
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 58; j++){
				this.inClass[i][j] = false;
			}
		}
	}

	@Override
	public String toString() {
		String classList = "";
		for(int i = 1; i < classes.size(); i++) {
			classList += classes.get(i) + " ";
		}
		return new StringBuffer(" Instructor Name : ")
		.append(this.name)
 	   	.append(" Classes Taught : ")
 	   	.append(classList).toString();
	}
	   
}