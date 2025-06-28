package view;

import controller.EmployeeController;
import controller.MainController;
import model.Employee;
import dao.UserDAO;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import javafx.application.Platform;
import model.User;

public class EmployeeView {
	private final EmployeeController controller;
	
	public EmployeeView(EmployeeController controller) {
		this.controller = controller;
	}
	
	public void showProfileView(Stage primaryStage, Employee employee) {
		showEmployeeForm(primaryStage, employee, false, true, () -> {
			User updatedUser = UserDAO.getInstance().getUserByEmail(employee.getEmail());
			MainController mainController = new MainController(primaryStage, updatedUser != null ? updatedUser : controller.getCurrentUser());
			mainController.showMainView();
		});
	}
	
	public void showAllEmployeesView(Stage primaryStage) {
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				              "-fx-background-radius: 20;" +
				              "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		HBox navPanel = new HBox(10);
		navPanel.setAlignment(Pos.CENTER_LEFT);
		
		Button backButton = new Button("Назад");
		styleButton(backButton);
		Tooltip backTooltip = new Tooltip("Вернуться на главную страницу");
		backTooltip.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12; -fx-background-color: #f5f5f5; -fx-text-fill: #3c2f5f; -fx-background-radius: 5; -fx-padding: 8;");
		backButton.setTooltip(backTooltip);
		backButton.setOnAction(e -> {
			MainController mainController = new MainController(primaryStage, controller.getCurrentUser());
			mainController.showMainView();
		});
		
		Label titleLabel = new Label("Список сотрудников");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		
		navPanel.getChildren().addAll(backButton, titleLabel);
		
		TableView<Employee> table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setStyle("-fx-background-color: rgba(255,255,255,0.95);" +
				               "-fx-background-radius: 15;" +
				               "-fx-border-color: #b7e4f7;" +
				               "-fx-border-width: 1.5;" +
				               "-fx-border-radius: 15;" +
				               "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.3), 8, 0.4, 0, 0);");
		
		TableColumn<Employee, String> lastNameCol = new TableColumn<>("Фамилия");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		
		TableColumn<Employee, String> firstNameCol = new TableColumn<>("Имя");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		
		TableColumn<Employee, String> middleNameCol = new TableColumn<>("Отчество");
		middleNameCol.setCellValueFactory(new PropertyValueFactory<>("middleName"));
		
		TableColumn<Employee, String> gender = new TableColumn<>("Пол");
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		
		TableColumn<Employee, String> positionCol = new TableColumn<>("Должность");
		positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
		
		TableColumn<Employee, String> salary = new TableColumn<>("Оклад (руб.)");
		salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
		
		TableColumn<Employee, String> experience = new TableColumn<>("Стаж (лет)");
		experience.setCellValueFactory(new PropertyValueFactory<>("experience"));
		
		TableColumn<Employee, String> email = new TableColumn<>("Почта");
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		table.getColumns().addAll(lastNameCol, firstNameCol, middleNameCol, gender, positionCol, salary, experience, email);
		
		List<Employee> employees = controller.getAllEmployees();
		table.setItems(FXCollections.observableArrayList(employees));
		
		Platform.runLater(() -> {
			table.lookupAll(".column-header-background .label").forEach(node ->
					                                                            node.setStyle("-fx-background-color: linear-gradient(to right, #c9a9a6, #d7b7b4);" +
							                                                                          "-fx-text-fill: #f5f5f5;" +
							                                                                          "-fx-font-family: 'Verdana';" +
							                                                                          "-fx-font-weight: bold;" +
							                                                                          "-fx-padding: 10;" +
							                                                                          "-fx-alignment: center;"));
		});
		
		table.setRowFactory(tv -> new TableRow<Employee>() {
			@Override
			protected void updateItem(Employee item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setStyle("");
				} else {
					if (getIndex() % 2 == 0) {
						setStyle("-fx-background-color: rgba(255,193,204,0.05); -fx-text-fill: #333333;");
					} else {
						setStyle("-fx-background-color: rgba(183,228,247,0.05); -fx-text-fill: #333333;");
					}
					updateSelected(isSelected());
					
					setOnMouseEntered(e -> {
						setStyle("");
						getChildren().forEach(node -> {
							if (node instanceof TableCell) {
								((TableCell<?, ?>) node).setStyle("-fx-background-color: rgba(255,193,204,0.2); -fx-text-fill: #000000;");
							}
						});
					});
					setOnMouseExited(e -> {
						getChildren().forEach(node -> {
							if (node instanceof TableCell) {
								((TableCell<?, ?>) node).setStyle("");
							}
						});
						if (isSelected()) {
							updateSelected(true);
						} else {
							setStyle(getIndex() % 2 == 0 ? "-fx-background-color: rgba(255,193,204,0.05); -fx-text-fill: #333333;" : "-fx-background-color: rgba(183,228,247,0.05); -fx-text-fill: #333333;");
						}
					});
				}
			}
			
			@Override
			public void updateSelected(boolean selected) {
				super.updateSelected(selected);
				if (selected) {
					getChildren().forEach(node -> {
						if (node instanceof TableCell) {
							((TableCell<?, ?>) node).setStyle("-fx-background-color: #fcb6f1; -fx-text-fill: #000000;");
						}
					});
				} else {
					getChildren().forEach(node -> {
						if (node instanceof TableCell) {
							((TableCell<?, ?>) node).setStyle("");
						}
					});
					setStyle(getIndex() % 2 == 0 ? "-fx-background-color: rgba(255,193,204,0.05); -fx-text-fill: #333333;" : "-fx-background-color: rgba(183,228,247,0.05); -fx-text-fill: #333333;");
				}
			}
		});
		
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(10));
		
		if (controller.isAdmin()) {
			Button addButton = new Button("Добавить");
			Button editButton = new Button("Редактировать");
			Button deleteButton = new Button("Удалить");
			styleButton(addButton);
			styleButton(editButton);
			styleButton(deleteButton);
			
			Tooltip addTooltip = new Tooltip("Добавить нового сотрудника");
			addTooltip.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12; -fx-background-color: #f5f5f5; -fx-text-fill: #3c2f5f; -fx-background-radius: 5; -fx-padding: 8;");
			addButton.setTooltip(addTooltip);
			
			Tooltip editTooltip = new Tooltip("Редактировать данные выбранного сотрудника");
			editTooltip.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12; -fx-background-color: #f5f5f5; -fx-text-fill: #3c2f5f; -fx-background-radius: 5; -fx-padding: 8;");
			editButton.setTooltip(editTooltip);
			
			Tooltip deleteTooltip = new Tooltip("Удалить выбранного сотрудника");
			deleteTooltip.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12; -fx-background-color: #f5f5f5; -fx-text-fill: #3c2f5f; -fx-background-radius: 5; -fx-padding: 8;");
			deleteButton.setTooltip(deleteTooltip);
			
			addButton.setOnAction(e -> showEmployeeForm(primaryStage, null, true, false, () -> showAllEmployeesView(primaryStage)));
			editButton.setOnAction(e -> {
				Employee selected = table.getSelectionModel().getSelectedItem();
				if (selected != null) {
					showEmployeeForm(primaryStage, selected, false, false, () -> showAllEmployeesView(primaryStage));
				} else {
					showAlert("Ошибка", "Выберите сотрудника для редактирования");
				}
			});
			deleteButton.setOnAction(e -> {
				Employee selected = table.getSelectionModel().getSelectedItem();
				if (selected == null) {
					showAlert("Ошибка", "Выберите сотрудника для удаления");
					return;
				}
				Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
				confirmAlert.setTitle("Подтверждение удаления");
				confirmAlert.setHeaderText(null);
				confirmAlert.setContentText("Вы уверены, что хотите удалить сотрудника " + selected.getFirstName() + " " + selected.getLastName() + "?");
				ButtonType yesButton = new ButtonType("Да");
				ButtonType noButton = new ButtonType("Нет");
				confirmAlert.getButtonTypes().setAll(yesButton, noButton);
				confirmAlert.showAndWait().ifPresent(response -> {
					if (response == yesButton) {
						try {
							controller.deleteEmployee(selected.getIdEmployee());
							Platform.runLater(() -> {
								table.setItems(FXCollections.observableArrayList(controller.getAllEmployees()));
								showAlert("Успех", "Сотрудник успешно удален");
							});
						} catch (Exception ex) {
							Platform.runLater(() -> showAlert("Ошибка", "Ошибка удаления сотрудника: " + ex.getMessage()));
						}
					}
				});
			});
			
			buttonBox.getChildren().addAll(addButton, editButton, deleteButton);
		}
		
		vbox.getChildren().addAll(navPanel, table, buttonBox);
		
		Scene scene = new Scene(vbox, 1000, 600);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), vbox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void showEmployeeForm(Stage primaryStage, Employee employee, boolean isNew, boolean isProfileView, Runnable returnAction) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(15, 25, 15, 25));
		grid.setStyle("-fx-background-color: rgba(255,255,255,0.3);" +
				              "-fx-background-radius: 15;" +
				              "-fx-border-color: #b7e4f7;" +
				              "-fx-border-width: 1.5;" +
				              "-fx-border-radius: 15;");
		
		ScrollPane scrollPane = new ScrollPane(grid);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		scrollPane.setStyle("-fx-background-color: transparent;" +
				                    "-fx-background: transparent;" +
				                    "-fx-border-color: #b7e4f7;" +
				                    "-fx-border-width: 1.5;" +
				                    "-fx-border-radius: 15;");
		
		Platform.runLater(() -> {
			scrollPane.lookup(".scroll-bar:vertical .thumb").setStyle("-fx-background-color: #b7e4f7;" +
					                                                          "-fx-background-radius: 10;");
		});
		
		Label titleLabel = new Label(isNew ? "Добавление сотрудника" : isProfileView ? "Мой профиль" : "Редактирование сотрудника");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		grid.add(titleLabel, 0, 0, 2, 1);
		
		TextField lastNameField = new TextField(isNew ? "" : employee.getLastName());
		styleTextField(lastNameField);
		grid.add(new Label("Фамилия:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 1);
		grid.add(lastNameField, 1, 1);
		
		TextField firstNameField = new TextField(isNew ? "" : employee.getFirstName());
		styleTextField(firstNameField);
		grid.add(new Label("Имя:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 2);
		grid.add(firstNameField, 1, 2);
		
		TextField middleNameField = new TextField(isNew ? "" : employee.getMiddleName());
		styleTextField(middleNameField);
		grid.add(new Label("Отчество:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 3);
		grid.add(middleNameField, 1, 3);
		
		ComboBox<String> genderCombo = new ComboBox<>();
		genderCombo.getItems().addAll("Мужской", "Женский");
		genderCombo.setValue(isNew ? "Выберите пол" : employee.getGender());
		styleComboBox(genderCombo);
		grid.add(new Label("Пол:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 4);
		grid.add(genderCombo, 1, 4);
		
		TextField positionField = new TextField(isNew ? "" : employee.getPosition());
		styleTextField(positionField);
		grid.add(new Label("Должность:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 5);
		grid.add(positionField, 1, 5);
		
		TextField salaryField = new TextField(isNew ? "" : String.valueOf(employee.getSalary()));
		styleTextField(salaryField);
		grid.add(new Label("Зарплата:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 6);
		grid.add(salaryField, 1, 6);
		
		TextField experienceField = new TextField(isNew ? "" : String.valueOf(employee.getExperience()));
		styleTextField(experienceField);
		grid.add(new Label("Стаж:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 7);
		grid.add(experienceField, 1, 7);
		
		TextField emailField = new TextField(isNew ? "" : employee.getEmail());
		styleTextField(emailField);
		grid.add(new Label("Email:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 8);
		grid.add(emailField, 1, 8);
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText(isNew ? "Пароль" : "Новый пароль");
		styleTextField(passwordField);
		grid.add(new Label("Пароль:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 9);
		grid.add(passwordField, 1, 9);
		
		ComboBox<String> roleCombo = new ComboBox<>();
		roleCombo.getItems().addAll("Администратор", "Сотрудник");
		String employeeRole = isNew ? "Сотрудник" : (UserDAO.getInstance().getUserByEmail(employee.getEmail()) != null ? UserDAO.getInstance().getUserByEmail(employee.getEmail()).getRole() : "Сотрудник");
		roleCombo.setValue(employeeRole);
		styleComboBox(roleCombo);
		if (isProfileView && !controller.isAdmin()) {
			roleCombo.setDisable(true);
		}
		grid.add(new Label("Роль:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 10);
		grid.add(roleCombo, 1, 10);
		
		Button saveButton = new Button("Сохранить");
		Button cancelButton = new Button("Отмена");
		styleButton(saveButton);
		styleButton(cancelButton);
		
		Tooltip saveTooltip = new Tooltip("Сохранить изменения");
		saveTooltip.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12; -fx-background-color: #f5f5f5; -fx-text-fill: #3c2f5f; -fx-background-radius: 5; -fx-padding: 8;");
		saveButton.setTooltip(saveTooltip);
		
		Tooltip cancelTooltip = new Tooltip("Отменить и вернуться назад");
		cancelTooltip.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12; -fx-background-color: #f5f5f5; -fx-text-fill: #3c2f5f; -fx-background-radius: 5; -fx-padding: 8;");
		cancelButton.setTooltip(cancelTooltip);
		
		HBox buttonBoxForm = new HBox(10, saveButton, cancelButton);
		buttonBoxForm.setAlignment(Pos.CENTER);
		grid.add(buttonBoxForm, 1, 11);
		
		final Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
		messageLabel.setStyle("-fx-text-fill: #ff9999;" +
				                      "-fx-padding: 10;");
		grid.add(messageLabel, 1, 12);
		
		saveButton.setOnAction(e -> {
			try {
				String lastName = lastNameField.getText();
				String firstName = firstNameField.getText();
				String middleName = middleNameField.getText();
				String gender = genderCombo.getValue();
				String position = positionField.getText();
				String salaryText = salaryField.getText();
				String experienceText = experienceField.getText();
				String email = emailField.getText();
				String password = passwordField.getText();
				String role = isProfileView && !controller.isAdmin() ? controller.getCurrentUser().getRole() : roleCombo.getValue();
				
				if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || (isNew && password.isEmpty()) || role == null) {
					throw new IllegalArgumentException("Заполните все обязательные поля, включая роль");
				}
				
				double salary = Double.parseDouble(salaryText);
				int experience = Integer.parseInt(experienceText);
				
				Employee emp = isNew ? new Employee(lastName, firstName, middleName, gender, position, salary, experience, email) : employee;
				if (!isNew) {
					emp.setLastName(lastName);
					emp.setFirstName(firstName);
					emp.setMiddleName(middleName);
					emp.setGender(gender);
					emp.setPosition(position);
					emp.setSalary(salary);
					emp.setExperience(experience);
					emp.setEmail(email);
				}
				
				if (isProfileView) {
					controller.updateEmployee(emp, password.isEmpty() ? null : password, role);
					messageLabel.setText("Данные сохранены");
					messageLabel.setStyle("-fx-text-fill: #00cc00;" +
							                      "-fx-font-family: 'Segoe UI';" +
							                      "-fx-font-weight: bold;" +
							                      "-fx-font-size: 14;" +
							                      "-fx-padding: 10;");
					User updatedUser = UserDAO.getInstance().getUserByEmail(emp.getEmail());
					MainController mainController = new MainController(primaryStage, updatedUser != null ? updatedUser : controller.getCurrentUser());
					mainController.showMainView();
				} else {
					if (isNew) {
						controller.createEmployee(emp, password, role);
						lastNameField.clear();
						firstNameField.clear();
						middleNameField.clear();
						genderCombo.setValue("Мужской");
						positionField.clear();
						salaryField.clear();
						experienceField.clear();
						emailField.clear();
						passwordField.clear();
						roleCombo.setValue("Сотрудник");
					} else {
						controller.updateEmployee(emp, password.isEmpty() ? null : password, role);
					}
					returnAction.run();
				}
			} catch (Exception ex) {
				messageLabel.setText("Ошибка: " + ex.getMessage());
				messageLabel.setStyle("-fx-text-fill: #ff9999;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
			}
		});
		
		cancelButton.setOnAction(e -> returnAction.run());
		
		VBox formVBox = new VBox(20, titleLabel, scrollPane);
		formVBox.setPadding(new Insets(20));
		formVBox.setAlignment(Pos.CENTER);
		formVBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				                  "-fx-background-radius: 20;" +
				                  "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Scene scene = new Scene(formVBox, 600, 700);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), formVBox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	private void styleButton(Button button) {
		button.setFont(Font.font("Roboto", FontWeight.BOLD, 16));
		button.setStyle("-fx-background-color: linear-gradient(to right, #c9a9a6, #d7b7b4);" +
				                "-fx-text-fill: #f5f5f5;" +
				                "-fx-background-radius: 20;" +
				                "-fx-padding: 12 25 12 25;" +
				                "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.3), 8, 0.4, 0, 0);" +
				                "-fx-border-color: #c9a9a6;" +
				                "-fx-border-width: 1.5;" +
				                "-fx-border-radius: 20;");
		button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: linear-gradient(to right, #d7b7b4, #ffc1cc);" +
				                                              "-fx-text-fill: #f5f5f5;" +
				                                              "-fx-background-radius: 20;" +
				                                              "-fx-padding: 12 25 12 25;" +
				                                              "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.5), 10, 0.6, 0, 0);" +
				                                              "-fx-border-color: #c9a9a6;" +
				                                              "-fx-border-width: 1.5;" +
				                                              "-fx-border-radius: 20;"));
		button.setOnMouseExited(e -> button.setStyle("-fx-background-color: linear-gradient(to right, #c9a9a6, #d7b7b4);" +
				                                             "-fx-text-fill: #f5f5f5;" +
				                                             "-fx-background-radius: 20;" +
				                                             "-fx-padding: 12 25 12 25;" +
				                                             "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.3), 8, 0.4, 0, 0);" +
				                                             "-fx-border-color: #c9a9a6;" +
				                                             "-fx-border-width: 1.5;" +
				                                             "-fx-border-radius: 20;"));
		button.setOnMousePressed(e -> button.setScaleX(0.95));
		button.setOnMouseReleased(e -> button.setScaleX(1.0));
	}
	
	private void styleTextField(TextField textField) {
		textField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		textField.setStyle("-fx-background-color: #f5f5f5;" +
				                   "-fx-background-radius: 10;" +
				                   "-fx-border-color: #b7e4f7;" +
				                   "-fx-border-width: 1.5;" +
				                   "-fx-border-radius: 10;" +
				                   "-fx-padding: 10;" +
				                   "-fx-text-fill: #3c2f5f;" +
				                   "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
	}
	
	private void styleComboBox(ComboBox<?> comboBox) {
		comboBox.setStyle("-fx-background-color: #f5f5f5;" +
				                  "-fx-background-radius: 10;" +
				                  "-fx-border-color: #b7e4f7;" +
				                  "-fx-border-width: 1.5;" +
				                  "-fx-border-radius: 10;" +
				                  "-fx-padding: 5;" +
				                  "-fx-text-fill: #3c2f5f;" +
				                  "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
	}
}