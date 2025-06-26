package model;

public class Employee {
	private int idEmployee;
	private String lastName;
	private String firstName;
	private String middleName;
	private String gender;
	private String position;
	private double salary;
	private int experience;
	private String email;
	
	public Employee(String lastName, String firstName, String middleName, String gender, String position, double salary, int experience, String email) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.gender = gender;
		this.position = position;
		this.salary = salary;
		this.experience = experience;
		this.email = email;
	}
	
	public int getIdEmployee() {return idEmployee;}
	public void setIdEmployee(int idEmployee) {this.idEmployee = idEmployee;}
	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	public String getFirstName() {return firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	public String getMiddleName() {return middleName;}
	public void setMiddleName(String middleName) {this.middleName = middleName;}
	public String getGender() {return gender;}
	public void setGender(String gender) {this.gender = gender;}
	public String getPosition() {return position;}
	public void setPosition(String position) {this.position = position;}
	public double getSalary() {return salary;}
	public void setSalary(double salary) {this.salary = salary;}
	public int getExperience() {return experience;}
	public void setExperience(int experience) {this.experience = experience;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	@Override
	public String toString() {
		return lastName + " " + firstName + (middleName != null ? " " + middleName : "");
	}
}