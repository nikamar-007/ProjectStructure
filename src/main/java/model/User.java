package model;

public class User {
	private int idUser;
	private int idEmployee;
	private String email;
	private String password;
	private String role;
	private String lastName;
	private String firstName;
	private String middleName;
	private String gender;
	private String position;
	private double salary;
	private int experience;
	
	public User(String email, String password, String role, String lastName, String firstName, String middleName,
	            String gender, String position, double salary, int experience) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.gender = gender;
		this.position = position;
		this.salary = salary;
		this.experience = experience;
	}
	
	public int getIdUser() { return idUser; }
	public void setIdUser(int idUser) { this.idUser = idUser; }
	public int getIdEmployee() { return idEmployee; }
	public void setIdEmployee(int idEmployee) { this.idEmployee = idEmployee; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPasswordHash(String password) { this.password = password; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public String getMiddleName() { return middleName; }
	public void setMiddleName(String middleName) { this.middleName = middleName; }
	public String getGender() { return gender; }
	public void setGender(String gender) { this.gender = gender; }
	public String getPosition() { return position; }
	public void setPosition(String position) { this.position = position; }
	public double getSalary() { return salary; }
	public void setSalary(double salary) { this.salary = salary; }
	public int getExperience() { return experience; }
	public void setExperience(int experience) { this.experience = experience; }
	
	@Override
	public String toString() {
		return lastName + " " + firstName + (middleName != null ? " " + middleName : "") + " (" + email + ")";
	}
}