package view;

import controller.EmployeeController;
import controller.MainController;
import model.Employee;
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

public class EmployeeView {
	private final EmployeeController controller;
	
	public EmployeeView(EmployeeController controller) {
		this.controller = controller;
	}
	
	public void showProfileView(Stage primaryStage, Employee employee) {
		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(30));
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				              "-fx-background-radius: 20;" +
				              "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
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
		
		Label titleLabel = new Label("Мой профиль");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		grid.add(titleLabel, 0, 0, 2, 1);
		
		TextField lastNameField = new TextField(employee.getLastName());
		lastNameField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		lastNameField.setStyle("-fx-background-color: #f5f5f5;" +
				                       "-fx-background-radius: 10;" +
				                       "-fx-border-color: #b7e4f7;" +
				                       "-fx-border-width: 1.5;" +
				                       "-fx-border-radius: 10;" +
				                       "-fx-padding: 10;" +
				                       "-fx-text-fill: #3c2f5f;" +
				                       "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		grid.add(new Label("Фамилия:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 1);
		grid.add(lastNameField, 1, 1);
		
		TextField firstNameField = new TextField(employee.getFirstName());
		firstNameField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		firstNameField.setStyle("-fx-background-color: #f5f5f5;" +
				                        "-fx-background-radius: 10;" +
				                        "-fx-border-color: #b7e4f7;" +
				                        "-fx-border-width: 1.5;" +
				                        "-fx-border-radius: 10;" +
				                        "-fx-padding: 10;" +
				                        "-fx-text-fill: #3c2f5f;" +
				                        "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		grid.add(new Label("Имя:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 2);
		grid.add(firstNameField, 1, 2);
		
		TextField middleNameField = new TextField(employee.getMiddleName());
		middleNameField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		middleNameField.setStyle("-fx-background-color: #f5f5f5;" +
				                         "-fx-background-radius: 10;" +
				                         "-fx-border-color: #b7e4f7;" +
				                         "-fx-border-width: 1.5;" +
				                         "-fx-border-radius: 10;" +
				                         "-fx-padding: 10;" +
				                         "-fx-text-fill: #3c2f5f;" +
				                         "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		grid.add(new Label("Отчество:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 3);
		grid.add(middleNameField, 1, 3);
		
		ComboBox<String> genderCombo = new ComboBox<>();
		genderCombo.getItems().addAll("Мужской", "Женский");
		genderCombo.setValue(employee.getGender());
		genderCombo.setStyle("-fx-background-color: #f5f5f5;" +
				                     "-fx-background-radius: 10;" +
				                     "-fx-border-color: #b7e4f7;" +
				                     "-fx-border-width: 1.5;" +
				                     "-fx-border-radius: 10;" +
				                     "-fx-padding: 10;" +
				                     "-fx-text-fill: #3c2f5f;" +
				                     "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		grid.add(new Label("Пол:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 4);
		grid.add(genderCombo, 1, 4);
		
		TextField positionField = new TextField(employee.getPosition());
		positionField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		positionField.setStyle("-fx-background-color: #f5f5f5;" +
				                       "-fx-background-radius: 10;" +
				                       "-fx-border-color: #b7e4f7;" +
				                       "-fx-border-width: 1.5;" +
				                       "-fx-border-radius: 10;" +
				                       "-fx-padding: 10;" +
				                       "-fx-text-fill: #3c2f5f;" +
				                       "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		grid.add(new Label("Должность:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 5);
		grid.add(positionField, 1, 5);
		
		TextField salaryField = new TextField(String.valueOf(employee.getSalary()));
		salaryField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		salaryField.setStyle("-fx-background-color: #f5f5f5;" +
				                     "-fx-background-radius: 10;" +
				                     "-fx-border-color: #b7e4f7;" +
				                     "-fx-border-width: 1.5;" +
				                     "-fx-border-radius: 10;" +
				                     "-fx-padding: 10;" +
				                     "-fx-text-fill: #3c2f5f;" +
				                     "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		grid.add(new Label("Зарплата:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 6);
		grid.add(salaryField, 1, 6);
		
		TextField experienceField = new TextField(String.valueOf(employee.getExperience()));
		experienceField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		experienceField.setStyle("-fx-background-color: #f5f5f5;" +
				                         "-fx-background-radius: 10;" +
				                         "-fx-border-color: #b7e4f7;" +
				                         "-fx-border-width: 1.5;" +
				                         "-fx-border-radius: 10;" +
				                         "-fx-padding: 10;" +
				                         "-fx-text-fill: #3c2f5f;" +
				                         "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		grid.add(new Label("Стаж:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 7);
		grid.add(experienceField, 1, 7);
		
		TextField emailField = new TextField(employee.getEmail());
		emailField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		emailField.setStyle("-fx-background-color: #f5f5f5;" +
				                    "-fx-background-radius: 10;" +
				                    "-fx-border-color: #b7e4f7;" +
				                    "-fx-border-width: 1.5;" +
				                    "-fx-border-radius: 10;" +
				                    "-fx-padding: 10;" +
				                    "-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		grid.add(new Label("Email:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 8);
		grid.add(emailField, 1, 8);
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Новый пароль");
		passwordField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		passwordField.setStyle("-fx-background-color: #f5f5f5;" +
				                       "-fx-background-radius: 10;" +
				                       "-fx-border-color: #b7e4f7;" +
				                       "-fx-border-width: 1.5;" +
				                       "-fx-border-radius: 10;" +
				                       "-fx-padding: 10;" +
				                       "-fx-text-fill: #3c2f5f;" +
				                       "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		grid.add(new Label("Пароль:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 9);
		grid.add(passwordField, 1, 9);
		
		ComboBox<String> roleCombo = new ComboBox<>();
		roleCombo.getItems().addAll("Администратор", "Сотрудник");
		roleCombo.setValue(controller.getCurrentUser().getRole());
		roleCombo.setStyle("-fx-background-color: #f5f5f5;" +
				                   "-fx-background-radius: 10;" +
				                   "-fx-border-color: #b7e4f7;" +
				                   "-fx-border-width: 1.5;" +
				                   "-fx-border-radius: 10;" +
				                   "-fx-padding: 10;" +
				                   "-fx-text-fill: #3c2f5f;" +
				                   "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		grid.add(new Label("Роль:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 10);
		grid.add(roleCombo, 1, 10);
		
		Button saveButton = new Button("Сохранить");
		styleButton(saveButton);
		Button cancelButton = new Button("Назад");
		styleButton(cancelButton);
		HBox buttonBox = new HBox(10, saveButton, cancelButton);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setStyle("-fx-padding: 10;");
		grid.add(buttonBox, 1, 11);
		
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
				String role = roleCombo.getValue();
				
				if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty()) {
					throw new IllegalArgumentException("Заполните все обязательные поля");
				}
				
				if (!List.of("Мужской", "Женский").contains(gender)) {
					throw new IllegalArgumentException("Недопустимое значение пола");
				}
				
				double salary = Double.parseDouble(salaryText);
				int experience = Integer.parseInt(experienceText);
				
				employee.setLastName(lastName);
				employee.setFirstName(firstName);
				employee.setMiddleName(middleName);
				employee.setGender(gender);
				employee.setPosition(position);
				employee.setSalary(salary);
				employee.setExperience(experience);
				employee.setEmail(email);
				
				controller.updateEmployee(employee, password.isEmpty() ? null : password, role);
				
				messageLabel.setText("Изменения сохранены");
				messageLabel.setStyle("-fx-text-fill: #19ff19;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
			} catch (Exception ex) {
				messageLabel.setText("Ошибка: " + ex.getMessage());
				messageLabel.setStyle("-fx-text-fill: #ff9999;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
			}
		});
		
		cancelButton.setOnAction(e -> {
			MainController mainController = new MainController(primaryStage, controller.getCurrentUser());
			mainController.showMainView();
		});
		
		vbox.getChildren().addAll(titleLabel, scrollPane);
		
		Scene scene = new Scene(vbox, 800, 600);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), vbox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void showAllEmployeesView(Stage primaryStage) {
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				              "-fx-background-radius: 20;" +
				              "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Button backButton = new Button("Назад");
		styleButton(backButton);
		backButton.setOnAction(e -> {
			MainController mainController = new MainController(primaryStage, controller.getCurrentUser());
			mainController.showMainView();
		});
		
		Label titleLabel = new Label("Список сотрудников");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		
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
		
		table.getColumns().addAll(lastNameCol, firstNameCol, middleNameCol, gender, positionCol, salary, experience);
		
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
								((TableCell<?, ?>) node).setStyle("-fx-background-color: rgba(255,193,204,0.5); -fx-text-fill: #000000;");
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
		
		vbox.getChildren().addAll(backButton, titleLabel, table);
		
		Scene scene = new Scene(vbox, 600, 400);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), vbox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setScene(scene);
		primaryStage.show();
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
}