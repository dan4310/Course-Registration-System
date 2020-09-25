import java.util.ArrayList;
import java.util.Scanner;

public interface adminControl {
	
	// Course Management
	public void createCourse(ArrayList<Course> courseList, Scanner in);
	public void deleteCourse(ArrayList<Course> courseList, Scanner in);
	public void editCourse(ArrayList<Course> courseList, Scanner in);
	public void displayInfo(ArrayList<Course> courseList, Scanner in);
	public void registerStudent(ArrayList<Student> studentList, Scanner in);
	public void enrollStudent(ArrayList<Student> studentList, ArrayList<Course> courseList, Scanner in);
	
	// Reports
	public void viewFull(ArrayList<Course> courseList);
	public void saveFullCourses(ArrayList<Course> courseList);
	public void sortCourses(ArrayList<Course> courseList);
	public void showStudents(ArrayList<Course> courseList, Scanner in);
	public void showStudentCourses(ArrayList<Student> studentList, Scanner in);
	
	// user management
	public void addAdmin(ArrayList<Admin> admins, Scanner in);
}
