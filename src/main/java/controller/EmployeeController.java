package controller;

import dao.EmployeeDAO;
import dao.UserDAO;
import model.Employee;
import model.User;
import view.EmployeeView;
import javafx.stage.Stage;
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
	public List<Employee> getAllEmployees() {return employeeDAO.getAllEmployees();}
	
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
	
	public void createEmployee(Employee employee, String password, String role) {
		int idEmployee = employeeDAO.addEmployee(employee);
		if (idEmployee > 0) {
			User user = new User(
					employee.getEmail(),
					password,
					role,
					employee.getLastName(),
					employee.getFirstName(),
					employee.getMiddleName(),
					employee.getGender(),
					employee.getPosition(),
					employee.getSalary(),
					employee.getExperience()
			);
			user.setIdEmployee(idEmployee);
			userDAO.registerUser(user);
		} else {
			throw new RuntimeException("Не удалось создать сотрудника");
		}
	}
	
	public void deleteEmployee(int idEmployee) {
		if (idEmployee <= 0) {
			throw new IllegalArgumentException("Некорректный ID сотрудника");
		}
		if (idEmployee == currentUser.getIdEmployee()) {
			throw new IllegalStateException("Нельзя удалить текущего администратора");
		}
		employeeDAO.deleteEmployee(idEmployee);
	}
	
	public boolean isAdmin() {
		return "Администратор".equalsIgnoreCase(currentUser.getRole());
	}
}