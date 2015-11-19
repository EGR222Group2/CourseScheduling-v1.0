package egr222;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScheduleGenerator {
	
	public static void Generate(ArrayList<RoomInfo> RoomInfo, ArrayList<ClassInfo> ClassInfo, ArrayList<InstructorInfo> InstructorInfo) {
		ResetSchedule(RoomInfo, ClassInfo, InstructorInfo);
		Organize(RoomInfo, ClassInfo);
		AssignCourses(RoomInfo, ClassInfo, InstructorInfo);
	}
	
	// Clears all object of their current schedules.
	public static void ResetSchedule(ArrayList<RoomInfo> RoomInfo, ArrayList<ClassInfo> ClassInfo, ArrayList<InstructorInfo> InstructorInfo) {
		for (int i = 0; i < RoomInfo.size(); i++) {
			for (int j = 0; j < 5; j++){
				for (int k = 0; k < 58; k++){
					RoomInfo.get(i).timesUsed[j][k] = false;
				}
			}
		}
		
		for (int i = 0; i < ClassInfo.size(); i++) {
			for (int j = 0; j < 5; j++){
				for (int k = 0; k < 2; k++){
					ClassInfo.get(i).classtime[j][k] = null;
				}
			}
		}
		
		for (int i = 0; i < InstructorInfo.size(); i++) {
			for (int j = 0; j < 5; j++){
				for (int k = 0; k < 58; k++){
					InstructorInfo.get(i).inClass[j][k] = false;
				}
			}
		}
	}
	
	// Sorts the courses by credits and rooms by maximum capacity(both high to low).
	public static void Organize(ArrayList<RoomInfo> RoomInfo, ArrayList<ClassInfo> ClassInfo) {
		Collections.sort(ClassInfo, new CourseComparator());
		Collections.sort(RoomInfo, new RoomComparator());
	}
	
	// Used for sorting.
	public static class CourseComparator implements Comparator<ClassInfo> {

		@Override
		public int compare(ClassInfo course1, ClassInfo course2) {
			Integer comparison1 = course1.getCredits();
			Integer comparison2 = course2.getCredits();
	        return comparison1.compareTo(comparison2);
	    }
		
	}
	
	// Used for sorting
	public static class RoomComparator implements Comparator<RoomInfo> {

		@Override
		public int compare(RoomInfo room1, RoomInfo room2) {
			return Integer.compare(room1.getMaxCapacity(), room2.getMaxCapacity());
		}
		
	}
	
	// Assumes that the courses have been arranged by credit count, highest to lowest.
	// In addition, it assumes the rooms have been arranged from highest max capacity to lowest. 
	public static void AssignCourses(ArrayList<RoomInfo> RoomInfo, ArrayList<ClassInfo> ClassInfo, ArrayList<InstructorInfo> InstructorInfo) {
		// Cycles through year.
		for (int year = 0; year < ClassInfo.size(); year++) {
			// 3 semesters (Spring, Summer, Fall)
			for (int semester = 1; semester <= 3; semester++) {
				// Cycles through courses.
				for (int course = 0; course < ClassInfo.size(); course++) {
					// Enters into a loop and will exit once a course has been found.
					courseLoop:
					if(ClassInfo.get(course).year == year + 2015 && ClassInfo.get(course).semester == semester) {
						for (int roomNum= 0; roomNum < RoomInfo.size(); roomNum++) {
							/*	Tries to find find the smallest possible room for the course so rooms with a large capacity
								will never be unavailable for large classes. However, the algorithm in it's current state won't
								try to get the least amount of rooms. Assuming, however, that CBU has a set standard for
								max capacity, this won't be much of an issue.*/
							if (ClassInfo.get(course).maxCapacity <= RoomInfo.get(roomNum).maxCapacity) {
								// If the class can't fit the schedule, the program will go to the room before it to find a spot.
								for (int room = roomNum; room >= 0; room--) {
									// The number of credits changes which days it tries to schedule first.
									// In addition, the times assigned changes if the course is a 500+ course.
									if (ClassInfo.get(course).credits <= 2) {
										if (Integer.parseInt(ClassInfo.get(course).section) < 500) {
											for (int day = 0; day < 5; day++) {
												for (int start = 0; start < (40 - (ClassInfo.get(course).credits * 4)); start++) {
													Boolean availible = true;
													for (int hour = 0; hour < ClassInfo.get(course).credits * 4; hour++) {
														if (RoomInfo.get(room).timesUsed[day][start + hour]) {
															availible = false;
														}
													}
													if (availible) {
														ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
														String startTime = Integer.toString((start / 4) + 7);
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														int end = start + ClassInfo.get(course).credits * 4;
														String endTime = Integer.toString(end/4 + 7);
														switch (end % 4) {
															case 1: endTime += ":15";
																break;
															case 2: endTime += ":30";
																break;
															case 3: endTime += ":45";
																break;
															default: endTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;
														ClassInfo.get(course).classtime[day][1] = endTime;
														for (int i = 0; i < ClassInfo.get(course).credits * 4; i++) {
															RoomInfo.get(course).timesUsed[day][start + i] = true;
														}
														break courseLoop;
													}
												}
											}
										} else {
											for (int day = 0; day < 5; day++) {
												for (int start = 40; start < (58 - (ClassInfo.get(course).credits * 4)); start++) {
													Boolean availible = true;
													for (int hour = 0; hour < ClassInfo.get(course).credits * 4; hour++) {
														if (RoomInfo.get(room).timesUsed[day][start + hour]) {
															availible = false;
														}
													}
													if (availible) {
														ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
														String startTime = Integer.toString((start / 4) + 7);
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														int end = start + ClassInfo.get(course).credits * 4;
														String endTime = Integer.toString(end/4 + 7);
														switch (end % 4) {
															case 1: endTime += ":15";
																break;
															case 2: endTime += ":30";
																break;
															case 3: endTime += ":45";
																break;
															default: endTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;
														ClassInfo.get(course).classtime[day][1] = endTime;
														for (int i = 0; i < ClassInfo.get(course).credits * 4; i++) {
															RoomInfo.get(course).timesUsed[day][start + i] = true;
														}
														break courseLoop;
													}
												}
											}
										}
									} else if (ClassInfo.get(course).credits % 2 == 0) {
										if (Integer.parseInt(ClassInfo.get(course).section) < 500) {
											// First, check to see if 2 days with the same times are available.
											for (int start = 0; start < (40 - (ClassInfo.get(course).credits * 2)); start++) {
												Boolean availible = true;
												for (int hour = 0; hour < ClassInfo.get(course).credits * 2; hour++) {
													if (RoomInfo.get(room).timesUsed[1][start + hour] == true) {
														availible = false;
													} else {
														for (int day = 1; day < 5; day += 2) {
															if (RoomInfo.get(room).timesUsed[day][start + hour])
																availible = false;
														}
													}
												}
												if (availible) {
													ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
													for (int day = 1; day < 5; day += 2) {
														String startTime = Integer.toString(start / 4) + 7;
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														int end = start + (ClassInfo.get(course).credits * 2);
														String endTime = Integer.toString(end/4 + 7);
														switch (end % 4) {
															case 1: endTime += ":15";
																break;
															case 2: endTime += ":30";
																break;
															case 3: endTime += ":45";
																break;
															default: endTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;
														ClassInfo.get(course).classtime[day][1] = endTime;
														for (int i = 0; i < (ClassInfo.get(course).credits * 2); i++) {
															RoomInfo.get(course).timesUsed[day][start + i] = true;
														}
													}
													break courseLoop;
												}
											}
											// If not, it then check if 3 days with the same times are available.
											int remainder = ClassInfo.get(course).credits % 3;
											for (int start = 0; start < (40 - ((Integer)(ClassInfo.get(course).credits * 4/3) + 4)); start++) {
												Boolean availible = true;
												for (int hour = 0; hour < ((Integer)(ClassInfo.get(course).credits * 4/3) + 4); hour++) {
													if (RoomInfo.get(room).timesUsed[0][start + hour]) {
														availible = false;
													} else {
														for (int day = 0; day < 5; day += 2) {
															if (RoomInfo.get(room).timesUsed[day][start + hour])
																availible = false;
														}
													}
												}
												if (availible) {
													ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
													for (int day = 0; day < 5; day += 2) {
														String startTime = Integer.toString(start / 4) + 7;
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;

														if (day > 3) {
															int end = start + (Integer)(ClassInfo.get(course).credits * 4/3);
															String endTime = Integer.toString(end/4 + 7);
															switch (end % 4) {
																case 1: endTime += ":15";
																	break;
																case 2: endTime += ":30";
																	break;
																case 3: endTime += ":45";
																	break;
																default: endTime += ":00"; break;
															}
															
															ClassInfo.get(course).classtime[day][1] = endTime;
														} else {
															int end = start + (Integer)(ClassInfo.get(course).credits * 4/3 + 4);
															String endTime = Integer.toString(end/4 + 7);
															switch (end % 4) {
																case 1: endTime += ":15";
																	break;
																case 2: endTime += ":30";
																	break;
																case 3: endTime += ":45";
																	break;
																default: endTime += ":00"; break;
															}
															
															ClassInfo.get(course).classtime[day][1] = endTime;
														}
														if(remainder > 0) {
															for (int i = 0; i < ((Integer)(ClassInfo.get(course).credits * 4/3) + 4); i++) {
																RoomInfo.get(course).timesUsed[day][start + i] = true;
															}
															remainder--;
														} else {
															for (int i = 0; i < (Integer)(ClassInfo.get(course).credits * 4/3); i++) {
																RoomInfo.get(course).timesUsed[day][start + i] = true;
															}
														}
													}
													break courseLoop;
												}
											}
											// If that doesn't work, check for a single time on a single day point.
											for (int day = 0; day < 5; day++) {
												for (int start = 0; start < (40 - (ClassInfo.get(course).credits * 4)); start++) {
													Boolean availible = true;
													for (int hour = 0; hour < ClassInfo.get(course).credits * 4; hour++) {
														if (RoomInfo.get(room).timesUsed[day][start + hour]) {
															availible = false;
														}
													}
													if (availible) {
														ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
														String startTime = Integer.toString((start / 4) + 7);
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														int end = start + ClassInfo.get(course).credits * 4;
														String endTime = Integer.toString(end/4 + 7);
														switch (end % 4) {
															case 1: endTime += ":15";
																break;
															case 2: endTime += ":30";
																break;
															case 3: endTime += ":45";
																break;
															default: endTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;
														ClassInfo.get(course).classtime[day][1] = endTime;
														for (int i = 0; i < ClassInfo.get(course).credits; i++) {
															RoomInfo.get(course).timesUsed[day][start + i] = true;
														}
														break courseLoop;
													}
												}
											}
										} else {
											// First, check to see if 2 days with the same times are available.
											for (int start = 40; start < (58 - (ClassInfo.get(course).credits * 2)); start++) {
												Boolean availible = true;
												for (int hour = 0; hour < ClassInfo.get(course).credits * 2; hour++) {
													if (RoomInfo.get(room).timesUsed[1][start + hour]) {
														availible = false;
													} else {
														for (int day = 1; day < 5; day += 2) {
															if (RoomInfo.get(room).timesUsed[day][start + hour])
																availible = false;
														}
													}
												}
												if (availible) {
													ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
													for (int day = 1; day < 5; day += 2) {
														String startTime = Integer.toString(start / 4) + 7;
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														int end = start + (ClassInfo.get(course).credits * 2);
														String endTime = Integer.toString(end/4 + 7);
														switch (end % 4) {
															case 1: endTime += ":15";
																break;
															case 2: endTime += ":30";
																break;
															case 3: endTime += ":45";
																break;
															default: endTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;
														ClassInfo.get(course).classtime[day][1] = endTime;
														for (int i = 0; i < (ClassInfo.get(course).credits * 2); i++) {
															RoomInfo.get(course).timesUsed[day][start + i] = true;
														}
													}
													break courseLoop;
												}
											}
											// If not, it then check if 3 days with the same times are available.
											int remainder = ClassInfo.get(course).credits % 3;
											for (int start = 40; start < (58 - ((Integer)(ClassInfo.get(course).credits * 4/3) + 4)); start++) {
												Boolean availible = true;
												for (int hour = 0; hour < ((Integer)(ClassInfo.get(course).credits * 4/3) + 4); hour++) {
													if (RoomInfo.get(room).timesUsed[0][start + hour]) {
														availible = false;
													} else {
														for (int day = 0; day < 5; day += 2) {
															if (RoomInfo.get(room).timesUsed[day][start + hour])
																availible = false;
														}
													}
												}
												if (availible) {
													ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
													for (int day = 0; day < 5; day += 2) {
														String startTime = Integer.toString(start / 4) + 7;
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;

														if (day > 3) {
															int end = start + (Integer)(ClassInfo.get(course).credits * 4/3);
															String endTime = Integer.toString(end/4 + 7);
															switch (end % 4) {
																case 1: endTime += ":15";
																	break;
																case 2: endTime += ":30";
																	break;
																case 3: endTime += ":45";
																	break;
																default: endTime += ":00"; break;
															}
															
															ClassInfo.get(course).classtime[day][1] = endTime;
														} else {
															int end = start + (Integer)(ClassInfo.get(course).credits * 4/3 + 4);
															String endTime = Integer.toString(end/4 + 7);
															switch (end % 4) {
																case 1: endTime += ":15";
																	break;
																case 2: endTime += ":30";
																	break;
																case 3: endTime += ":45";
																	break;
																default: endTime += ":00"; break;
															}
															
															ClassInfo.get(course).classtime[day][1] = endTime;
														}
														if(remainder > 0) {
															for (int i = 0; i < ((Integer)(ClassInfo.get(course).credits * 4/3) + 4); i++) {
																RoomInfo.get(course).timesUsed[day][start + i] = true;
															}
															remainder--;
														} else {
															for (int i = 0; i < (Integer)(ClassInfo.get(course).credits * 4/3); i++) {
																RoomInfo.get(course).timesUsed[day][start + i] = true;
															}
														}
													}
													break courseLoop;
												}
											}
											// If that doesn't work, check for a single time on a single day point.
											for (int day = 0; day < 5; day++) {
												for (int start = 40; start < (58 - (ClassInfo.get(course).credits * 4)); start++) {
													Boolean availible = true;
													for (int hour = 0; hour < ClassInfo.get(course).credits * 4; hour++) {
														if (RoomInfo.get(room).timesUsed[day][start + hour]) {
															availible = false;
														}
													}
													if (availible) {	
														ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
														String startTime = Integer.toString((start / 4) + 7);
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														int end = start + ClassInfo.get(course).credits * 4;
														String endTime = Integer.toString(end/4 + 7);
														switch (end % 4) {
															case 1: endTime += ":15";
																break;
															case 2: endTime += ":30";
																break;
															case 3: endTime += ":45";
																break;
															default: endTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;
														ClassInfo.get(course).classtime[day][1] = endTime;
														for (int i = 0; i < ClassInfo.get(course).credits; i++) {
															RoomInfo.get(course).timesUsed[day][start + i] = true;
														}
														break courseLoop;
													}
												}
											}
										}	
									} else {
										if (Integer.parseInt(ClassInfo.get(course).section) < 500) {
											// First, check if 3 days with the same times are available.
											int remainder = ClassInfo.get(course).credits % 3;
											for (int start = 0; start < (40 - ((Integer)(ClassInfo.get(course).credits * 4/3) + 4)); start++) {
												Boolean availible = true;
												for (int hour = 0; hour < ((Integer)(ClassInfo.get(course).credits * 4/3) + 4); hour++) {
													if (RoomInfo.get(room).timesUsed[0][start + hour]) {
														availible = false;
													} else {
														for (int day = 0; day < 5; day += 2) {
															if (RoomInfo.get(room).timesUsed[day][start + hour])
																availible = false;
														}
													}
												}
												if (availible) {	
													ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
													for (int day = 0; day < 5; day += 2) {
														String startTime = Integer.toString(start / 4) + 7;
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;

														if (day > 3) {
															int end = start + (Integer)(ClassInfo.get(course).credits * 4/3);
															String endTime = Integer.toString(end/4 + 7);
															switch (end % 4) {
																case 1: endTime += ":15";
																	break;
																case 2: endTime += ":30";
																	break;
																case 3: endTime += ":45";
																	break;
																default: endTime += ":00"; break;
															}
															
															ClassInfo.get(course).classtime[day][1] = endTime;
														} else {
															int end = start + (Integer)(ClassInfo.get(course).credits * 4/3 + 4);
															String endTime = Integer.toString(end/4 + 7);
															switch (end % 4) {
																case 1: endTime += ":15";
																	break;
																case 2: endTime += ":30";
																	break;
																case 3: endTime += ":45";
																	break;
																default: endTime += ":00"; break;
															}
															
															ClassInfo.get(course).classtime[day][1] = endTime;
														}
														if(remainder > 0) {
															for (int i = 0; i < ((Integer)(ClassInfo.get(course).credits * 4/3) + 4); i++) {
																RoomInfo.get(course).timesUsed[day][start + i] = true;
															}
															remainder--;
														} else {
															for (int i = 0; i < (Integer)(ClassInfo.get(course).credits * 4/3); i++) {
																RoomInfo.get(course).timesUsed[day][start + i] = true;
															}
														}
													}
													break courseLoop;
												}
											}
											// If not, check to see if 2 days with the same times are available.
											for (int start = 0; start < (40 - (ClassInfo.get(course).credits * 2)); start++) {
												Boolean availible = true;
												for (int hour = 0; hour < ClassInfo.get(course).credits * 2; hour++) {
													if (RoomInfo.get(room).timesUsed[1][start + hour]) {
														availible = false;
													} else {
														for (int day = 1; day < 5; day += 2) {
															if (RoomInfo.get(room).timesUsed[day][start + hour])
																availible = false;
														}
													}
												}
												if (availible) {
													ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
													for (int day = 1; day < 5; day += 2) {
														String startTime = Integer.toString(start / 4) + 7;
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														int end = start + (ClassInfo.get(course).credits * 2);
														String endTime = Integer.toString(end/4 + 7);
														switch (end % 4) {
															case 1: endTime += ":15";
																break;
															case 2: endTime += ":30";
																break;
															case 3: endTime += ":45";
																break;
															default: endTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;
														ClassInfo.get(course).classtime[day][1] = endTime;
														for (int i = 0; i < (ClassInfo.get(course).credits * 2); i++) {
															RoomInfo.get(course).timesUsed[day][start + i] = true;
														}
													}
													break courseLoop;
												}
											}
											// If that doesn't work, check for a single time on a single day point.
											for (int day = 0; day < 5; day++) {
												for (int start = 0; start < (40 - (ClassInfo.get(course).credits * 4)); start++) {
													Boolean availible = true;
													for (int hour = 0; hour < ClassInfo.get(course).credits * 4; hour++) {
														if (RoomInfo.get(room).timesUsed[day][start + hour]) {
															availible = false;
														}
													}
													if (availible) {
														ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
														String startTime = Integer.toString((start / 4) + 7);
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														int end = start + ClassInfo.get(course).credits * 4;
														String endTime = Integer.toString(end/4 + 7);
														switch (end % 4) {
															case 1: endTime += ":15";
																break;
															case 2: endTime += ":30";
																break;
															case 3: endTime += ":45";
																break;
															default: endTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;
														ClassInfo.get(course).classtime[day][1] = endTime;
														for (int i = 0; i < ClassInfo.get(course).credits; i++) {
															RoomInfo.get(course).timesUsed[day][start + i] = true;
														}
														break courseLoop;
													}
												}
											}
										} else {
											// First, check to see if 2 days with the same times are available.
											for (int start = 40; start < (58 - (ClassInfo.get(course).credits * 2)); start++) {
												Boolean availible = true;
												for (int hour = 0; hour < ClassInfo.get(course).credits * 2; hour++) {
													if (RoomInfo.get(room).timesUsed[1][start + hour]) {
														availible = false;
													} else {
														for (int day = 1; day < 5; day += 2) {
															if (RoomInfo.get(room).timesUsed[day][start + hour])
																availible = false;
														}
													}
												}
												if (availible) {
													ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
													for (int day = 1; day < 5; day += 2) {
														String startTime = Integer.toString(start / 4) + 7;
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														int end = start + (ClassInfo.get(course).credits * 2);
														String endTime = Integer.toString(end/4 + 7);
														switch (end % 4) {
															case 1: endTime += ":15";
																break;
															case 2: endTime += ":30";
																break;
															case 3: endTime += ":45";
																break;
															default: endTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;
														ClassInfo.get(course).classtime[day][1] = endTime;
														for (int i = 0; i < (ClassInfo.get(course).credits * 2); i++) {
															RoomInfo.get(course).timesUsed[day][start + i] = true;
														}
													}
													break courseLoop;
												}
											}
											// If not, it then check if 3 days with the same times are available.
											int remainder = ClassInfo.get(course).credits % 3;
											for (int start = 40; start < (58 - ((Integer)(ClassInfo.get(course).credits * 4/3) + 4)); start++) {
												Boolean availible = true;
												for (int hour = 0; hour < ((Integer)(ClassInfo.get(course).credits * 4/3) + 4); hour++) {
													if (RoomInfo.get(room).timesUsed[0][start + hour]) {
														availible = false;
													} else {
														for (int day = 0; day < 5; day += 2) {
															if (RoomInfo.get(room).timesUsed[day][start + hour])
																availible = false;
														}
													}
												}
												if (availible) {
													ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
													for (int day = 0; day < 5; day += 2) {
														String startTime = Integer.toString(start / 4) + 7;
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;

														if (day > 3) {
															int end = start + (Integer)(ClassInfo.get(course).credits * 4/3);
															String endTime = Integer.toString(end/4 + 7);
															switch (end % 4) {
																case 1: endTime += ":15";
																	break;
																case 2: endTime += ":30";
																	break;
																case 3: endTime += ":45";
																	break;
																default: endTime += ":00"; break;
															}
															
															ClassInfo.get(course).classtime[day][1] = endTime;
														} else {
															int end = start + (Integer)(ClassInfo.get(course).credits * 4/3 + 4);
															String endTime = Integer.toString(end/4 + 7);
															switch (end % 4) {
																case 1: endTime += ":15";
																	break;
																case 2: endTime += ":30";
																	break;
																case 3: endTime += ":45";
																	break;
																default: endTime += ":00"; break;
															}
															
															ClassInfo.get(course).classtime[day][1] = endTime;
														}
														if(remainder > 0) {
															for (int i = 0; i < ((Integer)(ClassInfo.get(course).credits * 4/3) + 4); i++) {
																RoomInfo.get(course).timesUsed[day][start + i] = true;
															}
															remainder--;
														} else {
															for (int i = 0; i < (Integer)(ClassInfo.get(course).credits * 4/3); i++) {
																RoomInfo.get(course).timesUsed[day][start + i] = true;
															}
														}
													}
													break courseLoop;
												}
											}
											// If that doesn't work, check for a single time on a single day point.
											for (int day = 0; day < 5; day++) {
												for (int start = 40; start < (58 - (ClassInfo.get(course).credits * 4)); start++) {
													Boolean availible = true;
													for (int hour = 0; hour < ClassInfo.get(course).credits * 4; hour++) {
														if (RoomInfo.get(room).timesUsed[day][start + hour]) {
															availible = false;
														}
													}
													if (availible) {
														ClassInfo.get(course).roomNumber = RoomInfo.get(room).roomNumber;
														String startTime = Integer.toString((start / 4) + 7);
														switch (start % 4) {
															case 1: startTime += ":15";
																break;
															case 2: startTime += ":30";
																break;
															case 3: startTime += ":45";
																break;
															default: startTime += ":00"; break;
														}
														int end = start + ClassInfo.get(course).credits * 4;
														String endTime = Integer.toString(end/4 + 7);
														switch (end % 4) {
															case 1: endTime += ":15";
																break;
															case 2: endTime += ":30";
																break;
															case 3: endTime += ":45";
																break;
															default: endTime += ":00"; break;
														}
														
														ClassInfo.get(course).classtime[day][0] = startTime;
														ClassInfo.get(course).classtime[day][1] = endTime;
														for (int i = 0; i < ClassInfo.get(course).credits; i++) {
															RoomInfo.get(course).timesUsed[day][start + i] = true;
														}
														break courseLoop;
													}
												}
											}
										}	
									}
								}
							}
						}
					}
				}
			}
		}
	}
}

// Holy balls that was a lot.