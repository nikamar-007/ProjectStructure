package controller;

import dao.EmployeeDAO;
import dao.EmployeeWorkDAO;
import dao.UserDAO;
import dao.WorkDAO;
import model.Employee;
import model.EmployeeWork;
import model.User;
import model.Work;
import view.WorkView;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class WorkController {
	private final Stage primaryStage;
	public final User currentUser;
	private final WorkView workView;
	private final WorkDAO workDAO;
	private final EmployeeDAO employeeDAO;
	public final EmployeeWorkDAO employeeWorkDAO;
	
	public WorkController(Stage primaryStage, User currentUser) {
		this.primaryStage = primaryStage;
		this.currentUser = currentUser;
		this.workView = new WorkView(this);
		this.workDAO = WorkDAO.getInstance();
		this.employeeDAO = EmployeeDAO.getInstance();
		this.employeeWorkDAO = EmployeeWorkDAO.getInstance();
	}
	
	public boolean isAdmin() {
		return "Администратор".equalsIgnoreCase(currentUser.getRole());
	}
	
	public void showWorkManagementView() {workView.showWorkManagementView(primaryStage);}
	public void showResponsibleWorksView() {workView.showResponsibleWorksView(primaryStage);}
	public List<Work> getAllWorks() {return workDAO.getAllWorks();}
	public Employee getEmployeeById(int id) {return employeeDAO.getEmployeeById(id);}
	public void addWork(Work work) {workDAO.addWork(work);}
	public void updateWork(Work work) {workDAO.updateWork(work);}
	public void deleteWork(int idWork) {workDAO.deleteWork(idWork);}
	public void assignWork(EmployeeWork employeeWork) {employeeWorkDAO.addEmployeeWork(employeeWork);}
	
	public List<Work> getUserWorks() {
		List<Work> works = new ArrayList<>();
		List<EmployeeWork> assignments = employeeWorkDAO.getEmployeeWorksByEmployeeId(currentUser.getIdEmployee());
		
		for (EmployeeWork assignment : assignments) {
			Work work = workDAO.getWorkById(assignment.getIdWork());
			if (work != null)
				works.add(work);
		}
		return works;
	}
	
	public List<Work> getResponsibleWorks() {
		List<Work> works = new ArrayList<>();
		List<Work> allWorks = workDAO.getAllWorks();
		for (Work work : allWorks) {
			if (work.getIdResponsible() == currentUser.getIdEmployee())
				works.add(work);
		}
		return works;
	}
	
	public List<Employee> getAllEmployees() {
		List<Employee> allEmployees = employeeDAO.getAllEmployees();
		List<Employee> filteredEmployees = new ArrayList<>();
		UserDAO userDAO = UserDAO.getInstance();
		for (Employee employee : allEmployees) {
			User user = userDAO.getUserByEmail(employee.getEmail());
			if (user != null && !"Администратор".equalsIgnoreCase(user.getRole()))
				filteredEmployees.add(employee);
		}
		return filteredEmployees;
	}
	
	public void backToMainView() {
		MainController mainController = new MainController(primaryStage, currentUser);
		mainController.showMainView();
	}
	
	public List<Employee> getAssignedEmployees(int idWork) {
		List<Employee> employees = new ArrayList<>();
		List<EmployeeWork> assignments = employeeWorkDAO.getEmployeeWorksByWorkId(idWork);
		
		for (EmployeeWork assignment : assignments) {
			Employee employee = employeeDAO.getEmployeeById(assignment.getIdEmployee());
			if (employee != null)
				employees.add(employee);
		}
		return employees;
	}
	
	public void removeAllAssignmentsForWork(int idWork) {
		List<EmployeeWork> assignments = employeeWorkDAO.getEmployeeWorksByWorkId(idWork);
		for (EmployeeWork assignment : assignments) {
			employeeWorkDAO.deleteEmployeeWork(
					assignment.getIdEmployee(),
					assignment.getIdWork(),
					assignment.getStartDate()
			);
		}
	}
}