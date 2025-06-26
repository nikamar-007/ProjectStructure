package controller;

import dao.EmployeeDAO;
import dao.UserDAO;
import model.Employee;
import model.User;
import view.EmployeeView;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
	private final Stage primaryStage;
	private final User currentUser;
	private final EmployeeView employeeView;
	private final EmployeeDAO employeeDAO;
	private final UserDAO userDAO;
	
	public EmployeeController(Stage primaryStage, User currentUser) {
		this.primaryStage = primaryStage;
		this.currentUser = currentUser;
		this.employeeView = new EmployeeView(this);
		this.employeeDAO = EmployeeDAO.getInstance();
		this.userDAO = UserDAO.getInstance();
	}
	
	public User getCurrentUser() {return currentUser;}
	public void showProfileView() {employeeView.showProfileView(primaryStage, getEmployeeById(currentUser.getIdEmployee()));}
	public void showAllEmployeesView() {employeeView.showAllEmployeesView(primaryStage);}
	public Employee getEmployeeById(int id) {return employeeDAO.getEmployeeById(id);}
	
	public List<Employee> getAllEmployees() {
		List<Employee> allEmployees = employeeDAO.getAllEmployees();
		List<Employee> filteredEmployees = new ArrayList<>();
		for (Employee employee : allEmployees) {
			User user = userDAO.getUserByEmail(employee.getEmail());
			if (user != null && !"Администратор".equalsIgnoreCase(user.getRole()))
				filteredEmployees.add(employee);
		}
		return filteredEmployees;
	}
	
	public void updateEmployee(Employee employee, String password, String role) {
		employeeDAO.updateEmployee(employee);
		User user = userDAO.getUserByEmail(employee.getEmail());
		if (user != null) {
			if (password != null && !password.isEmpty())
				user.setPasswordHash(password);
			if (role != null)
				user.setRole(role);
			user.setLastName(employee.getLastName());
			user.setFirstName(employee.getFirstName());
			user.setMiddleName(employee.getMiddleName());
			user.setGender(employee.getGender());
			user.setPosition(employee.getPosition());
			user.setSalary(employee.getSalary());
			user.setExperience(employee.getExperience());
			user.setEmail(employee.getEmail());
			userDAO.updateUser(user);
		}
	}
}