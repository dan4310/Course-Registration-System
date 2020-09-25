import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User implements studentControl {
	private ArrayList<Course> registeredCourses = new ArrayList<>();
	private String sID;
	
	@Override
	public void viewAvailable(ArrayList<Course> courses) {
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getMaxStudents() > courses.get(i).getCurrentStudents()) {
				courses.get(i).showInfo();
			}
		}
		
	}

	@Override
	public void registerCourse(ArrayList<Course> courses, Scanner in) {
		System.out.println();
		in.nextLine();
		System.out.print("Enter course ID: ");
		String courseID = in.next();
		System.out.print("Enter course section: ");
		int sec = Integer.parseInt(in.next());
		in.nextLine();
		// hw asks me to get full name and add it to course. instead the student instance is added
		// this way more info is stored (including full name) in the course and i can still
		// retrieve the full name anytime from the course instance (see showInfo() method in Course)
		Course c;
		for (int i = 0; i < courses.size(); i++) {
			c = courses.get(i);
			if (c.getID().equals(courseID)
				&& c.getSection() == sec) {
				if (c.getCurrentStudents() < c.getMaxStudents()) {
					c.registerStudent(this);
					registeredCourses.add(c);
					System.out.println("Registerd for " + c.getCourseName());
				} else { System.out.println("Course is full!"); }
			}
		}
	}

	@Override
	public void withdrawCourse(Scanner in) {
		System.out.println();
		in.nextLine();
		System.out.print("Enter course ID: ");
		String ID = in.next();
		System.out.print("Enter course section: ");
		int sec = Integer.parseInt(in.next());
		Course c = new Course();
		for (int i = 0; i < registeredCourses.size(); i++) {
			if (registeredCourses.get(i).getID().equals(ID) 
				&& registeredCourses.get(i).getSection() == sec) {
				c = registeredCourses.get(i);
			}
		}
		try {
			registeredCourses.remove(c);
			c.removeStudent(this);
			System.out.println("You are no longer enrolled in "+c.getCourseName());
		} catch (Exception e) {
			System.out.println("You are not enrolled in this course!");
		}
	}
	
	public void removeCourse(Course c) {
		registeredCourses.remove(c);
	}

	@Override
	public void viewMyCourses() {
		if (registeredCourses.size() != 0) {
			for (int i = 0; i < registeredCourses.size(); i++) {
				registeredCourses.get(i).showInfo();
			}
		} else { 
			System.out.println("You are not registered for any courses");
		}
	}
	
	public ArrayList<Course> getCourses() {
		return registeredCourses;
	}
	
	public String getID() {
		return sID;
	}
	public void setID(String ID) {
		sID = ID;
	}
}
