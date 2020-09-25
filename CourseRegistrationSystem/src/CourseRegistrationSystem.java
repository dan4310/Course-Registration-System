
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseRegistrationSystem {
	public static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		
		try {
			// check if first time running program
			FileInputStream fileIn = new FileInputStream("CourseList.ser");
		} catch (IOException ex1) {
			// exception if CourseList.ser doesnt exist (first time running program)
			// reading initial data
			String dataFileName = "data/MyUniversityCourses.txt";
			String line = null;
			ArrayList<String[]> allData = new ArrayList<>();
			
			try {
				FileReader fr = new FileReader(dataFileName);
				
				BufferedReader br = new BufferedReader(fr);
				 
				// allData arrayList of String arrays
				while ((line = br.readLine()) != null) {
					allData.add(line.split(","));
				}
				
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to open file - " + dataFileName);
				ex.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// from allData store course information separately
			Course course;
			ArrayList<Course> courseList = new ArrayList<>();
			for (int i = 1; i < allData.size(); i++) {
				course = new Course();
				course.setCourseName(allData.get(i)[0]);
				course.setID(allData.get(i)[1]);
				course.setMaxStudents(Integer.parseInt(allData.get(i)[2]));
				course.setInstructor(allData.get(i)[5]);
				course.setSection(Integer.parseInt(allData.get(i)[6]));
				course.setLocation(allData.get(i)[7]);
				courseList.add(course);
			}
			
			// Serialize courseList
			try {
				FileOutputStream fos;
				ObjectOutputStream oos;
				
				fos = new FileOutputStream("CourseList.ser");
				oos = new ObjectOutputStream(fos);
				oos.writeObject(courseList);
				oos.close();
				fos.close();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
			// Serialize adminList with base admin
			ArrayList<Admin> adminList = new ArrayList<>();
			Admin a = new Admin();
			a.setUsername("Admin");
			a.setPassword("Admin001");
			a.setFirstName("John");
			a.setLastName("Smith");
			adminList.add(a);
						
			try {
				FileOutputStream fos1;
				ObjectOutputStream oos1;
				
				fos1 = new FileOutputStream("AdminList.ser");
				oos1 = new ObjectOutputStream(fos1);
				oos1.writeObject(adminList);
				oos1.close();
				fos1.close();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
			
			
			// Serialize empty studentList (no students enrolled yet)
			ArrayList<Student> studentList = new ArrayList<>();
			try {
				FileOutputStream fos;
				ObjectOutputStream oos;
				
				
				fos = new FileOutputStream("StudentList.ser");
				oos = new ObjectOutputStream(fos);
				oos.writeObject(studentList);
				oos.close();
				fos.close();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
				
	
		
		// Deserialize CourseList
		ArrayList<Course> courses;
		
		try {
			FileInputStream fis;
			ObjectInputStream ois;
			fis = new FileInputStream("CourseList.ser");
		     ois = new ObjectInputStream(fis);
		      
		     courses = (ArrayList<Course>) ois.readObject();
		     ois.close();
		     fis.close();
		} catch(IOException ioe) {
		      ioe.printStackTrace();
		      return;
		}
		 catch(ClassNotFoundException cnfe) {
		      cnfe.printStackTrace();
		      return;
		}
		
		// Deserialize adminList
		ArrayList<Admin> admins = new ArrayList<>();
		try {
			FileInputStream fis1;
			ObjectInputStream ois1;
			fis1 = new FileInputStream("AdminList.ser");
		      
		     ois1 = new ObjectInputStream(fis1);
		      
		     admins = (ArrayList<Admin>) ois1.readObject();
		     ois1.close();
		     fis1.close();
		     
		} catch(IOException ioe) {
		      ioe.printStackTrace();
		      return;
		}
		 catch(ClassNotFoundException cnfe) {
		      cnfe.printStackTrace();
		      return;
		}

		// Deserialize Student List
		ArrayList<Student> students;
		try {
			FileInputStream fis;
			ObjectInputStream ois;
			fis = new FileInputStream("StudentList.ser");
		      
		     ois = new ObjectInputStream(fis);
		      
		     students = (ArrayList<Student>) ois.readObject();
		     ois.close();
		     fis.close();
		} catch(IOException ioe) {
		      ioe.printStackTrace();
		      return;
		    }
		 catch(ClassNotFoundException cnfe) {
		      cnfe.printStackTrace();
		      return;
		 }
				
		// login
		String username,password;
				
		// Login Screen
		User user;
		int userIndex = 0;
		int userIndicator = 0;
		while (true) {
			System.out.print("Enter username: ");
			username = in.next();
			System.out.print("Enter password: ");
			password = in.next();
			System.out.println("-----------------------");
			// determine if login info fits any admins
			for (int i = 0; i < admins.size(); i++) {
				if (admins.get(i).getPassword().equals(password)
					&& admins.get(i).getUsername().equals(username)) {
					userIndicator = 1; userIndex = i;
					break;
				}
			}
			// if not admin, determine if login info fits any student login
			if (userIndicator == 0) {
				for (int i = 0; i < students.size(); i++) {
					if (students.get(i).getPassword().equals(password)
						&& students.get(i).getUsername().equals(username)) {
						userIndicator = 2; userIndex = i;
						break;
					}
				}
			}
			
			if (userIndicator != 0) { break; }
		}
		
		if (userIndicator == 1) {
			// Admin user options
			user = admins.get(userIndex);
			System.out.println("Admin "+user.getFirstName()+" "+user.getLastName()+" logged in!");
			while (true) {
				printAdminMenu();
				// make sure valid input choice
				boolean inputInvalid = true;
				int choice = 0;
				String input;
				while (inputInvalid) {
					System.out.print("Enter an option: ");
					input = in.next();
					try { 
						// make sure input is int
						choice = Integer.parseInt(input); 
						// make sure input is an option
						checkAdminOptons(choice);
						inputInvalid = false; 
					} 
					catch (Exception e) { 
						System.out.println("Not an option!");
					}
				}
				
				if (choice == 0) { break; }
				else if (choice == 1) {
					// Create new course
					((Admin) user).createCourse(courses, in);
				} else if (choice == 2) {
					// Delete course
					((Admin) user).deleteCourse(courses, in);
				} else if (choice == 3) {
					// Edit course
					((Admin) user).editCourse(courses, in);
				} else if (choice == 4) {
					// Display info for a course
					((Admin) user).displayInfo(courses, in);
				} else if (choice == 5) {
					// Register a student
					((Admin) user).registerStudent(students, in);
				} else if (choice == 6) {
					// enroll student
					((Admin) user).enrollStudent(students, courses, in);
				} else if (choice == 7) {
					// view all course info
					((Admin) user).viewCourses(courses);
				} else if (choice == 8) {
					// view full courses
					((Admin) user).viewFull(courses);
				} else if (choice == 9) {
					// Save full courses to txt file
					((Admin) user).saveFullCourses(courses);
				} else if (choice == 10) {
					((Admin) user).showStudents(courses, in);
				} else if (choice == 11) {
					// view courses a student is registered in
					((Admin) user).showStudentCourses(students, in);
				} else if (choice == 12) {
					// sort courses based on number of current students
					((Admin) user).sortCourses(courses);
				} else if (choice == 13) {
					// change username
					((Admin) user).changeUsername(in);
				} else if (choice == 14) {
					// change password
					((Admin) user).changePassword(in);
				} else if (choice == 15) {
					// add new admin
					((Admin) user).addAdmin(admins, in);
				}
				
			}
			
			
			
			
		} else {
			// Student user options
			user = students.get(userIndex);
			System.out.println("Student "+user.getFirstName()+" "+user.getLastName()+" logged in!");
			while (true) {
				printStudentMenu();
				
				// make sure valid input choice
				boolean inputInvalid = true;
				int choice = 0;
				while (inputInvalid) {
					System.out.print("Enter an option: ");
					String input = in.next();
					try { 
						// make sure input is int
						choice = Integer.parseInt(input); 
						// make sure input is an option
						checkStudentOptons(choice);
						inputInvalid = false; 
					} 
					catch (Exception e) { 
						System.out.println("Not an option!");
					}
				}
				
				if (choice == 0) { break; }
				else if (choice == 1) {
					// view all courses
					((Student) user).viewCourses(courses);
				} else if (choice == 2) {
					// view all course that are not full
					((Student) user).viewAvailable(courses);
				} else if (choice == 3) {
					// Register in a course
					((Student) user).registerCourse(courses, in);
				} else if (choice == 4) {
					// Withdraw from a course
					((Student) user).withdrawCourse(in);
				} else if (choice == 5) {
					// view all registered courses
					((Student) user).viewMyCourses();
				} else if (choice == 6) {
					// change username
					((Student) user).changeUsername(in);
				} else if (choice == 7) {
					// change password
					((Student) user).changePassword(in);
				}
 				
			}
		}
		
		in.close();
		// Serialize to save changes
		try {
			FileOutputStream fos;
			ObjectOutputStream oos;
			
			fos = new FileOutputStream("CourseList.ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(courses);
			oos.close();
			fos.close();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		try {
			FileOutputStream fos1;
			ObjectOutputStream oos1;
			
			fos1 = new FileOutputStream("AdminList.ser");
			oos1 = new ObjectOutputStream(fos1);
			oos1.writeObject(admins);
			oos1.close();
			fos1.close();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		try {
			FileOutputStream fos;
			ObjectOutputStream oos;
			
			
			fos = new FileOutputStream("StudentList.ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(students);
			oos.close();
			fos.close();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	
	}
	
	public static void printAdminMenu() {
		System.out.println();
		System.out.println("Course Management");
		System.out.println("-----------------------");
		System.out.println("1) Create new course");
		System.out.println("2) Delete a course");
		System.out.println("3) Edit a course");
		System.out.println("4) Display info for a course");
		System.out.println("5) Register Student");
		System.out.println("6) Enroll Student");
		System.out.println("0) Exit");
		System.out.println();
		System.out.println("Reports");
		System.out.println("-----------------------");
		System.out.println("7) View all courses");
		System.out.println("8) View all full courses");
		System.out.println("9) Save full courses");
		System.out.println("10) View students of a course");
		System.out.println("11) View courses of a student");
		System.out.println("12) Sort based on current students");
		System.out.println("0) Exit");
		System.out.println();
		System.out.println("User Management");
		System.out.println("-----------------------");
		System.out.println("13) Change username");
		System.out.println("14) Change password");
		System.out.println("15) Add new admin");
		System.out.println("0) Exit");
		System.out.println();
	}
	
	public static void printStudentMenu() {
		System.out.println();
		System.out.println("Course Management");
		System.out.println("-----------------------");
		System.out.println("1) View all courses");
		System.out.println("2) View available classes");
		System.out.println("3) Register for a course");
		System.out.println("4) Withdraw from a course");
		System.out.println("5) View your registered courses");
		System.out.println();
		System.out.println("User Management");
		System.out.println("-----------------------");
		System.out.println("6) Change username");
		System.out.println("7) Change password");
		System.out.println("0) Exit");
		System.out.println();
	}
	
	public static void checkAdminOptons(int choice) throws Exception {
		boolean notValid = true;
		int[] options = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		for (int option : options) {
			if (option == choice) {
				notValid = false; break;
			}
		}
		if (notValid) {throw new Exception(); }
	}
	
	public static void checkStudentOptons(int choice) throws Exception {
		boolean notValid = true;
		int[] options = {0,1,2,3,4,5,6,7};
		for (int option : options) {
			if (option == choice) {
				notValid = false; break;
			}
		}
		if (notValid) {throw new Exception(); }
	}
		
}
