import java.util.ArrayList;
import java.util.Scanner;

public interface studentControl {
	
	public void viewAvailable(ArrayList<Course> courses);
	public void registerCourse(ArrayList<Course> courses, Scanner in);
	public void withdrawCourse(Scanner in);
	public void viewMyCourses();
}
