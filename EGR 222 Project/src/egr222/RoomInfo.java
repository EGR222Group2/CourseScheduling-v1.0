package egr222;

import java.io.Serializable;
import java.util.Comparator;

public class RoomInfo implements Serializable {

	private static final long serialVersionUID = -3469985829551709092L;
	
	String roomNumber;
	int maxCapacity;
	
	// The first part of the array checks for the day.
	// The second checks for 58 points in the day set at every 15 minute point of every hour from 7am to 9:30pm.
	Boolean[][] timesUsed = new Boolean[5][58];
	
	public RoomInfo(String number, int capacity) {
		this.roomNumber = number;
		this.maxCapacity = capacity;
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 58; j++){
				this.timesUsed[i][j] = false;
			}
		}
	}
	   
	public int getMaxCapacity(){
		return this.maxCapacity;
	}
	
	@Override
	public String toString() {
		return new StringBuffer(" Room Number : ")
		.append(this.roomNumber)
 	   	.append(" Max Capacity : ")
 	   	.append(this.maxCapacity).toString();
	}
	
	public class RoomComparator implements Comparator<RoomInfo> {

		@Override
		public int compare(RoomInfo room1, RoomInfo room2) {
			return Integer.compare(room1.getMaxCapacity(), room2.getMaxCapacity());
		}
		
	}
}
