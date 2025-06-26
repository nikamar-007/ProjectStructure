package controller;

import model.User;
import view.MainView;
import javafx.stage.Stage;

public class MainController {
	private final Stage primaryStage;
	private final User currentUser;
	private final MainView mainView;
	
	public MainController(Stage primaryStage, User currentUser) {
		this.primaryStage = primaryStage;
		this.currentUser = currentUser;
		this.mainView = new MainView(this);
	}
	
	public boolean isAdmin() {return "Администратор".equalsIgnoreCase(currentUser.getRole());}
	public void showMainView() {mainView.showMainView(primaryStage);}
	
	public void showEmployeeManagementView() {
		EmployeeController employeeController = new EmployeeController(primaryStage, currentUser);
		employeeController.showProfileView();
	}
	
	public void showAllEmployeesView() {
		EmployeeController employeeController = new EmployeeController(primaryStage, currentUser);
		employeeController.showAllEmployeesView();
	}
	
	public void showWorkManagementView() {
		WorkController workController = new WorkController(primaryStage, currentUser);
		workController.showWorkManagementView();
	}
	
	public void showEmployeeWorkManagementView() {
		EmployeeWorkController employeeWorkController = new EmployeeWorkController(primaryStage, currentUser);
		employeeWorkController.showEmployeeWorkManagementView();
	}
	
	public void showResponsibleWorksView() {
		WorkController workController = new WorkController(primaryStage, currentUser);
		workController.showResponsibleWorksView();
	}
	
	public void logout() {
		String defaultRole = currentUser != null ? currentUser.getRole() : "Сотрудник";
		LoginController loginController = new LoginController(primaryStage, defaultRole);
		loginController.showLoginView();
	}
}