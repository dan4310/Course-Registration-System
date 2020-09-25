import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable, Comparable<Course> {
	private ArrayList<Student> list = new ArrayList<>();
	private int sec,maxStudents;
	private String loc,courseInstructor,name,ID;
	
	public Course() {
		
	}
	
	public Course (String name, String ID, int maxStudents, String loc, int sec, String instruc) {
		this.name = name; this.ID = ID; this.maxStudents = maxStudents;
		this.loc = loc; this.sec = sec; this.courseInstructor = instruc;
	}
	
	public void setSection(int sec) {
		this.sec = sec;
	}
	public int getSection() {
		return sec;
	}
	
	public int getNumberStudents() {
		if (list != null) {
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null) {
					count++;
				}
				
			}
			return count;
		} else {return 0;}
	}
	
	public void setMaxStudents(int num) {
		maxStudents = num;
	}
	public int getMaxStudents() {
		return maxStudents;
	}
	
	public void setLocation(String loc) {
		this.loc = loc;
	}
	public String getLocation() {
		return loc;
	}
	
	public void setInstructor(String fullName) {
		courseInstructor = fullName;
	}
	public String getInstructor() {
		return courseInstructor;
	}
	
	public void setCourseName(String name) {
		this.name = name;
	}
	public String getCourseName() {
		return name;
	}
	
	public void setID(String sequence) {
		ID = sequence;
	}
	public String getID() {
		return ID;
	}
	
	public ArrayList<Student> getStudentList() {
		return list;
	}
	
	public void showInfo() {
		System.out.println();
		System.out.println(name);
		System.out.println(ID + " | Sec: " + sec);
		System.out.println(courseInstructor);
		System.out.println("Location: "+loc);
		System.out.println("Max students: "+maxStudents);
		System.out.println("Students: " + list.size());
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getFirstName()+ " "+ list.get(i).getLastName());
			}
		}
		System.out.println();
	}
	
	public int getCurrentStudents() {
		return list.size();
	}
	
	public void registerStudent(Student s) {
		list.add(s);
	}
	public void removeStudent(Student s) {
		list.remove(s);
	}

	@Override
	public int compareTo(Course c) {
		if (this.getCurrentStudents() > c.getCurrentStudents()) {
			return 1;
		} else if (this.getCurrentStudents() < c.getCurrentStudents()) {
			return -1;
		}
		return 0;
	}
	
}
