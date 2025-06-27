package controller;

import dao.EmployeeDAO;
import dao.EmployeeWorkDAO;
import dao.WorkDAO;
import model.Employee;
import model.EmployeeWork;
import model.User;
import model.Work;
import view.EmployeeWorkView;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;

public class EmployeeWorkController {
	private final Stage primaryStage;
	private final User currentUser;
	private final EmployeeWorkView employeeWorkView;
	private final EmployeeWorkDAO employeeWorkDAO;
	private final EmployeeDAO employeeDAO;
	private final WorkDAO workDAO;
	
	public EmployeeWorkController(Stage primaryStage, User currentUser) {
		this.primaryStage = primaryStage;
		this.currentUser = currentUser;
		this.employeeWorkView = new EmployeeWorkView(this);
		this.employeeWorkDAO = EmployeeWorkDAO.getInstance();
		this.employeeDAO = EmployeeDAO.getInstance();
		this.workDAO = WorkDAO.getInstance();
	}
	
	public boolean isAdmin() {return "Администратор".equalsIgnoreCase(currentUser.getRole());}
	public void showEmployeeWorkManagementView() {employeeWorkView.showEmployeeWorkManagementView(primaryStage);}
	public List<EmployeeWork> getAllEmployeeWorks() {return employeeWorkDAO.getAllEmployeeWorks();}
	public List<EmployeeWork> getUserEmployeeWorks() {return employeeWorkDAO.getEmployeeWorksByEmployeeId(currentUser.getIdEmployee());}
	public List<Employee> getAllEmployees() {return employeeDAO.getAllEmployees();}
	public List<Work> getAllWorks() {return workDAO.getAllWorks();}
	public Employee getEmployeeById(int id) {return employeeDAO.getEmployeeById(id);}
	public Work getWorkById(int id) {return workDAO.getWorkById(id);}
	public void addEmployeeWork(EmployeeWork employeeWork) {employeeWorkDAO.addEmployeeWork(employeeWork);}
	public void updateEmployeeWork(EmployeeWork employeeWork) {employeeWorkDAO.updateEmployeeWork(employeeWork);}
	public void deleteEmployeeWork(EmployeeWork employeeWork) {employeeWorkDAO.deleteEmployeeWork(employeeWork.getIdEmployee(), employeeWork.getIdWork(), employeeWork.getStartDate());}
	
	public double calculateAdditionalPayment(Employee employee, Work work, LocalDate endDate) {
		if (endDate == null) {
			return 0.0;
		}
		double fixedPayment = work.getFixedPayment() != null ? work.getFixedPayment() : 0;
		double salary = employee.getSalary();
		int experience = employee.getExperience();
		return fixedPayment * (1 + experience / 10.0) + (salary * 0.01);
	}
	
	public void backToMainView() {
		MainController mainController = new MainController(primaryStage, currentUser);
		mainController.showMainView();
	}
}