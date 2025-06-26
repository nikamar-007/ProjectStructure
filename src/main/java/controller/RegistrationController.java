package controller;

import dao.EmployeeDAO;
import dao.UserDAO;
import model.Employee;
import model.User;
import view.LoginView;
import view.RegistrationView;
import view.RoleView;
import javafx.stage.Stage;

public class RegistrationController {
	private final Stage primaryStage;
	private final LoginView loginView;
	private final RegistrationView registrationView;
	private final UserDAO userDAO;
	private final String role;
	
	public RegistrationController(Stage primaryStage, String role) {
		this.primaryStage = primaryStage;
		this.role = role;
		this.loginView = new LoginView(this);
		this.registrationView = new RegistrationView(this);
		this.userDAO = UserDAO.getInstance();
	}
	
	public void showLoginView() {loginView.showLoginView(primaryStage, role);}
	public void showRegistrationView() {registrationView.showRegistrationView(primaryStage, role);}
	public void backToRoleSelection() {new RoleView().showRoleSelectionView(primaryStage);}
	
	public User authenticate(String email, String password) {
		User user = userDAO.getUserByEmail(email);
		if (user != null && password.equals(user.getPassword())) {
			return user;
		}
		return null;
	}
	
	public void navigateToMainView(User user) {
		MainController mainController = new MainController(primaryStage, user);
		mainController.showMainView();
	}
	
	public boolean registerUser(User user) {
		if (userDAO.getUserByEmail(user.getEmail()) != null) {
			return false;
		}
		Employee employee = new Employee(
				user.getLastName(),
				user.getFirstName(),
				user.getMiddleName(),
				user.getGender(),
				user.getPosition(),
				user.getSalary(),
				user.getExperience(),
				user.getEmail()
		);
		int idEmployee = EmployeeDAO.getInstance().addEmployee(employee);
		user.setIdEmployee(idEmployee);
		return userDAO.registerUser(user);
	}
}