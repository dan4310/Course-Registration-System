import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements Serializable {
	protected String user,pass,name1,name2;
	
	
	public String getUsername() {
		return user;
	}
	public void setUsername(String username) {
		this.user = username;
	}
	
	public String getPassword() {
		return pass;
	}
	public void setPassword(String password) {
		this.pass = password;
	}
	
	public String getFirstName() {
		return name1;
	}
	public void setFirstName(String name) {
		this.name1 = name;
	}
	
	public String getLastName() {
		return name2;
	}
	public void setLastName(String name) {
		this.name2 = name;
	}
	
	public void viewCourses(ArrayList<Course> courses) {
		for (int i = 0; i < courses.size(); i++) {
			courses.get(i).showInfo();
		}
		
	}
	
	public void changeUsername(Scanner in) {
		System.out.print("Enter new username: ");
		String s = in.next();
		setUsername(s);
		System.out.println("Username changed to " + s);
		System.out.println();
	}
	public void changePassword(Scanner in) {
		System.out.print("Enter new password: ");
		String s = in.next();
		setPassword(s);
		System.out.println("Password changed to " + s);
		System.out.println();
	}
}
