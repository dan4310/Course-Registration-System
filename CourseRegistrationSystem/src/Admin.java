import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Admin extends User implements adminControl {
	
	@Override
	public void deleteCourse(ArrayList<Course> courseList, Scanner in) {
		System.out.print("Enter course ID: ");
		String ID = in.next();
		System.out.print("Enter course section: ");
		int sec = Integer.parseInt(in.next());
		Course c;
		// find course with given ID
		for (int i = 0; i < courseList.size(); i++) {
			c = courseList.get(i);
			if (c.getID().equals(ID) && c.getSection() == sec) {
				// course found
				Student s;
				for (int k = 0; k < c.getStudentList().size(); k++) {
					// remove all students from course first
					s = c.getStudentList().get(k);
					s.removeCourse(c);
					c.removeStudent(s);
				}
				System.out.println(courseList.get(i).getCourseName()+" deleted!");
				// remove course from list of courses
				courseList.remove(i);
				break;
			}
		}
	}

	@Override
	public void editCourse(ArrayList<Course> courses, Scanner in) {
		System.out.print("Enter ID of course you want to edit: ");
		String ID = in.next(); 
		System.out.print("Enter course sec: ");
		int sec = Integer.parseInt(in.next());
		Course c = new Course();
		// find course with given ID first
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getID().equals(ID) && courses.get(i).getSection() == sec) {
				c = courses.get(i); break;
			}
		}
		
		while (true) {
			System.out.println();
			System.out.println("Editing: " + c.getCourseName());
			System.out.println("-----------------------");
			System.out.println("1) Change location");
			System.out.println("2) Change instructor");
			System.out.println("3) Change section");
			System.out.println("4) Increase capacity");
			System.out.println("0) Back");
			System.out.println();
			// make sure valid input
			boolean notValid = true;
			int choice = 0;
			while (notValid) {
				System.out.print("Enter an option: ");
				try { 
					choice = Integer.parseInt(in.next()); 
					checkEditOptions(choice); notValid = false;
					}
				catch (Exception e) {
					System.out.println("Not an option!");
				}
			}
			if (choice == 1) {
				System.out.print("Enter new location: ");
				c.setLocation(in.next());
			} else if (choice == 2) {
				in.nextLine();
				System.out.print("Enter new instructor: ");
				c.setInstructor(in.nextLine());
			} else if (choice == 3) {
				System.out.print("Enter new section: ");
				c.setSection(in.nextInt());
			} else if (choice == 4) {
				System.out.print("Enter new capacity: ");
				c.setMaxStudents(in.nextInt());
			} else if (choice == 0) { break; }
		}
		
	}

	@Override
	public void displayInfo(ArrayList<Course> courses, Scanner in) {
		System.out.print("Enter ID of course you want to see: ");
		String ID = in.next();
		System.out.print("Enter course sec: ");
		int sec = Integer.parseInt(in.next());
		Course c = new Course();
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getID().equals(ID) && courses.get(i).getSection() == sec) {
				c = courses.get(i); break;
			}
		}
		c.showInfo();
	}

	@Override
	public void registerStudent(ArrayList<Student> students, Scanner in) {
		// initially username and password set by admin
		// student and admins can change username and password once they login
		Student s = new Student();
		System.out.print("Enter student's first name: ");
		s.setFirstName(in.next());
		System.out.print("Enter student's last name: ");
		s.setLastName(in.next());
		System.out.print("Enter student's ID: ");
		s.setID(in.next());
		System.out.print("Enter student's username: ");
		s.setUsername(in.next());
		System.out.print("Enter student's password: ");
		s.setPassword(in.next());
		students.add(s);
		System.out.println("Student " + s.getFirstName()+" "+s.getLastName()+" registered!");
		System.out.println("Username: " + s.getUsername() + " | Password: " + s.getPassword());
		System.out.println("ID: " + s.getID());
	}

	@Override
	public void viewFull(ArrayList<Course> courses) {
		System.out.println();
		int count = 0;
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getMaxStudents() == courses.get(i).getCurrentStudents() ) {
				courses.get(i).showInfo(); count++;
			}
		}
		if (count == 0) { System.out.println("No full courses"); }
	}

	@Override
	public void saveFullCourses(ArrayList<Course> courses) {
		System.out.println();
		FileWriter fw;
		try {
			fw = new FileWriter("data/fullCourses.txt");
		
		
		for (int i = 0; i < courses.size(); i++) {
			Course c = courses.get(i);
			// if course full
			if (c.getMaxStudents() == c.getCurrentStudents() ) {
				fw.write(c.getCourseName()+","+c.getID()+","+c.getMaxStudents()+","+c.getCurrentStudents()+",");
				Student s;
				// write all info like the inital course txt file
				for (int k = 0; k < c.getStudentList().size(); k++) {
					s = c.getStudentList().get(k);
					fw.write(s.getFirstName()+" "+s.getLastName()+",");
				}
				fw.write(c.getInstructor()+","+c.getSection()+","+c.getLocation()+"\n");
			}
		}
		fw.close();
		} catch (Exception e) { e.printStackTrace(); }
		
	}

	@Override
	public void sortCourses(ArrayList<Course> courses) {
		// sorts based on current students
		// see course class for use of comparable interface 
		Collections.sort(courses);
	}

	
	@Override
	public void createCourse(ArrayList<Course> courseList, Scanner in) {
		System.out.print("Enter course name: ");
		String name = in.next();
		System.out.print("Enter course ID: ");
		String ID = in.next();
		System.out.print("Enter maximum students: ");
		int maxStudents = Integer.parseInt(in.next());
		System.out.print("Enter course location: ");
		String loc = in.next();
		System.out.print("Enter course section: ");
		int sec = Integer.parseInt(in.next());
		in.nextLine();
		System.out.print("Enter course instructor's name: ");
		String instruc = in.nextLine();
		System.out.println("Course Created!");
		Course c = new Course(name, ID, maxStudents, loc, sec, instruc);
		courseList.add(c);
	}
	
	public void checkEditOptions(int choice) throws Exception {
		boolean notValid = true;
		int[] options = {0,1,2,3,4};
		for (int option : options) {
			if (choice == option) {
				notValid = false; break;
			}
		}
		if (notValid) { throw new Exception(); } 
	}

	@Override
	public void showStudents(ArrayList<Course> courses, Scanner in) {
		System.out.println();
		System.out.print("Enter a course ID: ");
		String ID = in.next();
		System.out.print("Enter course section: ");
		int sec = Integer.parseInt(in.next());
		System.out.println();
		Course c;
		for (int i = 0; i < courses.size(); i++) {
			c = courses.get(i);
			if (c.getID().equals(ID) && c.getSection() == sec) {
				System.out.println(c.getCourseName()+" - "+c.getID());
				if (c.getCurrentStudents() != 0) {
					ArrayList<Student> students = c.getStudentList();
					Student s;
					for (int k = 0; k < students.size(); k++) {
						s = students.get(k);
						System.out.print(s.getFirstName()+" "+s.getLastName());
						if (k != students.size()) { System.out.print(","); }
					}
					break;
				} else {
					System.out.println("No students are currently enrolled.");
				}
			}
		}
		System.out.println();
	}

	@Override
	public void showStudentCourses(ArrayList<Student> students, Scanner in) {
		System.out.println();
		System.out.print("Enter a student's first name: ");
		String name1 = in.next();
		System.out.print("Enter the student's last name: ");
		String name2 = in.next();
		System.out.println("Enter the student's ID: ");
		String ID = in.next();
		Student s;
		for (int i = 0; i < students.size(); i++) {
			s = students.get(i);
			if (s.getFirstName().equals(name1) 
				&& s.getLastName().equals(name2)
				&& s.getID().equals(ID)) {
				ArrayList<Course> courses = s.getCourses();
				System.out.println();
				System.out.println(s.getFirstName() + " " + s.getLastName());
				if ( courses.size() != 0) {
					System.out.println(courses.get(i).getCourseName()+" - "+courses.get(i).getID());
				} else { System.out.println(s.getFirstName()+ " has no registerd courses."); }
			}
		}
	}

	@Override
	public void addAdmin(ArrayList<Admin> admins, Scanner in) {
		Admin a = new Admin();
		System.out.println();
		String name1, name2, user, pass;
		System.out.print("Enter new admin's first name: ");
		name1 = in.next();
		a.setFirstName(name1);
		System.out.print("Enter admin's last name: ");
		name2 = in.next();
		a.setLastName(name2);
		System.out.print("Enter admin's username: ");
		user = in.next();
		a.setUsername(user);
		System.out.print("Enter admin's password: ");
		pass = in.next();
		a.setPassword(pass);
		admins.add(a);
		System.out.println("Admin " + name1 + " " + name2 + "created!");
		System.out.print("Username: " + user + " | Password: " + pass);
		System.out.println();
	}

	@Override
	public void enrollStudent(ArrayList<Student> students, ArrayList<Course> courses, Scanner in) {
		System.out.println();
		System.out.print("Enter a student's first name: ");
		String name1 = in.next();
		System.out.print("Enter the student's last name: ");
		String name2 = in.next();
		System.out.print("Enter the student's ID: ");
		String ID = in.next();
		Student s = new Student(); boolean found = false;
		for (int i = 0; i < students.size(); i++) {
			s = students.get(i);
			if (s.getFirstName().equals(name1) 
				&& s.getLastName().equals(name2)
				&& s.getID().equals(ID)) {
				found = true; break;
			}
		}
		
		if (found) {
			System.out.print("Enter course ID: ");
			String courseID = in.next();
			System.out.print("Enter course section: ");
			int sec = Integer.parseInt(in.next());
			
			Course c;
			for (int i = 0; i < courses.size(); i++) {
				c = courses.get(i);
				if (c.getID().contentEquals(courseID) && c.getSection() == sec) {
					c.registerStudent(s); break;
				}
			}
		}
	}
	

}
