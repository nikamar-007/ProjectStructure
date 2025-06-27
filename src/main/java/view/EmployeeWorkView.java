package view;

import controller.EmployeeWorkController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Employee;
import model.EmployeeWork;
import model.User;
import model.Work;
import dao.UserDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeWorkView {
	private final EmployeeWorkController controller;
	
	public EmployeeWorkView(EmployeeWorkController controller) {
		this.controller = controller;
	}
	
	public void showEmployeeWorkManagementView(Stage primaryStage) {
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				              "-fx-background-radius: 20;" +
				              "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Label titleLabel = new Label(controller.isAdmin() ? "Управление назначениями" : "Мои назначения");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		
		Button backButton = new Button("Назад");
		styleButton(backButton);
		backButton.setOnAction(e -> controller.backToMainView());
		
		TableView<EmployeeWork> employeeWorkTable = new TableView<>();
		employeeWorkTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		employeeWorkTable.setStyle("-fx-background-color: rgba(255,255,255,0.95);" +
				                           "-fx-background-radius: 15;" +
				                           "-fx-border-color: #b7e4f7;" +
				                           "-fx-border-width: 1.5;" +
				                           "-fx-border-radius: 15;" +
				                           "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.3), 8, 0.4, 0, 0);");
		
		TableColumn<EmployeeWork, String> employeeCol = new TableColumn<>("Сотрудник");
		employeeCol.setCellValueFactory(cellData -> {
			Employee emp = controller.getEmployeeById(cellData.getValue().getIdEmployee());
			return new SimpleStringProperty(emp != null ? emp.toString() : "");
		});
		
		TableColumn<EmployeeWork, String> workCol = new TableColumn<>("Задача");
		workCol.setCellValueFactory(cellData -> {
			Work work = controller.getWorkById(cellData.getValue().getIdWork());
			return new SimpleStringProperty(work != null ? work.getTitle() : "");
		});
		
		TableColumn<EmployeeWork, String> urgencyCol = new TableColumn<>("Срочность");
		urgencyCol.setCellValueFactory(new PropertyValueFactory<>("urgency"));
		
		TableColumn<EmployeeWork, String> startDateCol = new TableColumn<>("Дата начала");
		startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		
		TableColumn<EmployeeWork, String> endDateCol = new TableColumn<>("Дата окончания");
		endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		
		TableColumn<EmployeeWork, String> additionalPaymentCol = new TableColumn<>("Итог. оплата");
		additionalPaymentCol.setCellValueFactory(new PropertyValueFactory<>("additionalPayment"));
		
		employeeWorkTable.getColumns().addAll(employeeCol, workCol, urgencyCol, startDateCol, endDateCol, additionalPaymentCol);
		
		employeeWorkTable.setItems(FXCollections.observableArrayList(
				controller.isAdmin() ? controller.getAllEmployeeWorks() : controller.getUserEmployeeWorks()));
		
		Platform.runLater(() -> {
			employeeWorkTable.lookupAll(".column-header-background .label").forEach(node ->
					                                                                        node.setStyle("-fx-background-color: linear-gradient(to right, #c9a9a6, #d7b7b4);" +
							                                                                                      "-fx-text-fill: #f5f5f5;" +
							                                                                                      "-fx-font-family: 'Verdana';" +
							                                                                                      "-fx-font-weight: bold;" +
							                                                                                      "-fx-padding: 10;" +
							                                                                                      "-fx-alignment: center;"));
		});
		
		employeeWorkTable.setRowFactory(tv -> new TableRow<EmployeeWork>() {
			@Override
			protected void updateItem(EmployeeWork item, boolean empty) {
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
							setStyle(getIndex() % 2 == 0 ? "-fx-background-color: rgba(255,193,204,0.05); -fx-text-fill: #333333;" :
									         "-fx-background-color: rgba(183,228,247,0.05); -fx-text-fill: #333333;");
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
					setStyle(getIndex() % 2 == 0 ? "-fx-background-color: rgba(255,193,204,0.05); -fx-text-fill: #333333;" :
							         "-fx-background-color: rgba(183,228,247,0.05); -fx-text-fill: #333333;");
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
			
			addButton.setOnAction(e -> showAddEmployeeWorkForm(primaryStage));
			editButton.setOnAction(e -> {
				EmployeeWork selected = employeeWorkTable.getSelectionModel().getSelectedItem();
				if (selected != null) {
					showEditEmployeeWorkForm(primaryStage, selected);
				} else {
					showAlert("Ошибка", "Выберите назначение для редактирования");
				}
			});
			deleteButton.setOnAction(e -> {
				EmployeeWork selected = employeeWorkTable.getSelectionModel().getSelectedItem();
				if (selected != null) {
					Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
					confirmAlert.setTitle("Подтверждение удаления");
					confirmAlert.setHeaderText(null);
					confirmAlert.setContentText("Вы уверены, что хотите удалить это назначение?");
					ButtonType yesButton = new ButtonType("Да");
					ButtonType noButton = new ButtonType("Нет");
					confirmAlert.getButtonTypes().setAll(yesButton, noButton);
					confirmAlert.showAndWait().ifPresent(response -> {
						if (response == yesButton) {
							try {
								controller.deleteEmployeeWork(selected);
								employeeWorkTable.setItems(FXCollections.observableArrayList(
										controller.isAdmin() ? controller.getAllEmployeeWorks() : controller.getUserEmployeeWorks()));
								showAlert("Успех", "Назначение успешно удалено");
							} catch (Exception ex) {
								showAlert("Ошибка", "Ошибка удаления: " + ex.getMessage());
							}
						}
					});
				} else {
					showAlert("Ошибка", "Выберите назначение для удаления");
				}
			});
			
			buttonBox.getChildren().addAll(addButton, editButton, deleteButton);
		}
		
		vbox.getChildren().addAll(backButton, titleLabel, employeeWorkTable, buttonBox);
		
		Scene scene = new Scene(vbox, 1000, 600);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), vbox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void showAddEmployeeWorkForm(Stage primaryStage) {
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
		
		Label titleLabel = new Label("Добавление назначения");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		grid.add(titleLabel, 0, 0, 2, 1);
		
		UserDAO userDAO = UserDAO.getInstance();
		ComboBox<Employee> employeeCombo = new ComboBox<>();
		List<Employee> nonAdminEmployees = controller.getAllEmployees().stream()
				                                   .filter(employee -> {
					                                   User user = userDAO.getUserByEmail(employee.getEmail());
					                                   return user == null || !"Администратор".equalsIgnoreCase(user.getRole());
				                                   })
				                                   .collect(Collectors.toList());
		employeeCombo.setItems(FXCollections.observableArrayList(nonAdminEmployees));
		employeeCombo.setPromptText("Выберите сотрудника");
		styleComboBox(employeeCombo);
		grid.add(new Label("Сотрудник:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 1);
		grid.add(employeeCombo, 1, 1);
		
		ComboBox<Work> workCombo = new ComboBox<>();
	
		List<Work> availableWorks = controller.getAllWorks().stream()
				                            .filter(work -> {
					                            List<Employee> assignedEmployees = controller.getAssignedEmployees(work.getIdWork());
					                            Employee responsibleEmployee = controller.getEmployeeById(work.getIdResponsible());
					                            int assignedCount = assignedEmployees.size();
					                            boolean responsibleIsAssigned = responsibleEmployee != null &&
							                                                            assignedEmployees.stream().anyMatch(e -> e.getIdEmployee() == responsibleEmployee.getIdEmployee());
					                            int totalEmployeeCount = assignedCount + (responsibleEmployee != null && !responsibleIsAssigned ? 1 : 0);
					                            return totalEmployeeCount < work.getRecommendedEmployees();
				                            })
				                            .collect(Collectors.toList());
		workCombo.setItems(FXCollections.observableArrayList(availableWorks));
		workCombo.setPromptText("Выберите задачу");
		styleComboBox(workCombo);
		grid.add(new Label("Задача:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 2);
		grid.add(workCombo, 1, 2);
		
		workCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				List<Employee> assignedEmployees = controller.getAssignedEmployees(newValue.getIdWork());
				List<Employee> availableEmployees = nonAdminEmployees.stream()
						                                    .filter(employee -> assignedEmployees.stream()
								                                                        .noneMatch(assigned -> assigned.getIdEmployee() == employee.getIdEmployee()))
						                                    .collect(Collectors.toList());
				employeeCombo.setItems(FXCollections.observableArrayList(availableEmployees));
				employeeCombo.setValue(null);
			} else {
				employeeCombo.setItems(FXCollections.observableArrayList(nonAdminEmployees));
				employeeCombo.setValue(null);
			}
		});
		
		ComboBox<String> urgencyCombo = new ComboBox<>();
		urgencyCombo.getItems().addAll("Низкая", "Средняя", "Высокая", "Критическая");
		urgencyCombo.setPromptText("Выберите срочность");
		styleComboBox(urgencyCombo);
		grid.add(new Label("Срочность:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 3);
		grid.add(urgencyCombo, 1, 3);
		
		TextField startDateField = new TextField();
		startDateField.setPromptText("Дата начала (ГГГГ-ММ-ДД)");
		styleTextField(startDateField);
		grid.add(new Label("Дата начала:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 4);
		grid.add(startDateField, 1, 4);
		
		TextField endDateField = new TextField();
		endDateField.setPromptText("Дата конца (ГГГГ-ММ-ДД)");
		styleTextField(endDateField);
		grid.add(new Label("Дата конца:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 5);
		grid.add(endDateField, 1, 5);
		
		TextField additionalPaymentField = new TextField();
		additionalPaymentField.setPromptText("Дополнительная оплата");
		additionalPaymentField.setEditable(false);
		additionalPaymentField.setText("0.00");
		styleTextField(additionalPaymentField);
		grid.add(new Label("Итог. оплата:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 6);
		grid.add(additionalPaymentField, 1, 6);
		
		endDateField.textProperty().addListener((obs, oldValue, newValue) -> {
			if (employeeCombo.getValue() != null && workCombo.getValue() != null && !newValue.isEmpty()) {
				try {
					LocalDate endDate = LocalDate.parse(newValue);
					double payment = controller.calculateAdditionalPayment(employeeCombo.getValue(), workCombo.getValue(), endDate);
					additionalPaymentField.setText(String.format("%.2f", payment));
				} catch (Exception e) {
					additionalPaymentField.setText("0.00");
				}
			} else {
				additionalPaymentField.setText("0.00");
			}
		});
		
		employeeCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && workCombo.getValue() != null && !endDateField.getText().isEmpty()) {
				try {
					LocalDate endDate = LocalDate.parse(endDateField.getText());
					double payment = controller.calculateAdditionalPayment(newValue, workCombo.getValue(), endDate);
					additionalPaymentField.setText(String.format("%.2f", payment));
				} catch (Exception e) {
					additionalPaymentField.setText("0.00");
				}
			} else {
				additionalPaymentField.setText("0.00");
			}
		});
		
		workCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && employeeCombo.getValue() != null && !endDateField.getText().isEmpty()) {
				try {
					LocalDate endDate = LocalDate.parse(endDateField.getText());
					double payment = controller.calculateAdditionalPayment(employeeCombo.getValue(), newValue, endDate);
					additionalPaymentField.setText(String.format("%.2f", payment));
				} catch (Exception e) {
					additionalPaymentField.setText("0.00");
				}
			} else {
				additionalPaymentField.setText("0.00");
			}
		});
		
		Button saveButton = new Button("Сохранить");
		Button cancelButton = new Button("Отмена");
		styleButton(saveButton);
		styleButton(cancelButton);
		HBox buttonBox = new HBox(10, saveButton, cancelButton);
		buttonBox.setAlignment(Pos.CENTER);
		grid.add(buttonBox, 1, 7);
		
		final Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
		messageLabel.setStyle("-fx-text-fill: #ff9999; -fx-padding: 10;");
		grid.add(messageLabel, 1, 8);
		
		saveButton.setOnAction(e -> {
			try {
				Employee employee = employeeCombo.getValue();
				Work work = workCombo.getValue();
				String urgency = urgencyCombo.getValue();
				String startDateText = startDateField.getText();
				
				if (employee == null || work == null || urgency == null || startDateText.isEmpty()) {
					throw new IllegalArgumentException("Заполните все обязательные поля");
				}
				
				LocalDate startDate = LocalDate.parse(startDateText);
				String endDateText = endDateField.getText();
				LocalDate endDate = endDateText.isEmpty() ? null : LocalDate.parse(endDateText);
				double additionalPayment = controller.calculateAdditionalPayment(employee, work, endDate);
				
				EmployeeWork newEmployeeWork = new EmployeeWork(
						employee.getIdEmployee(),
						work.getIdWork(),
						urgency,
						startDate,
						endDate,
						additionalPayment
				);
				controller.addEmployeeWork(newEmployeeWork);
				employeeCombo.setValue(null);
				workCombo.setValue(null);
				urgencyCombo.setValue(null);
				startDateField.clear();
				endDateField.clear();
				additionalPaymentField.clear();
				showEmployeeWorkManagementView(primaryStage);
			} catch (Exception ex) {
				messageLabel.setText("Ошибка: " + ex.getMessage());
				messageLabel.setStyle("-fx-text-fill: #ff9999;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
			}
		});
		
		cancelButton.setOnAction(e -> showEmployeeWorkManagementView(primaryStage));
		
		VBox formVBox = new VBox(20, titleLabel, scrollPane);
		formVBox.setPadding(new Insets(20));
		formVBox.setAlignment(Pos.CENTER);
		formVBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				                  "-fx-background-radius: 20;" +
				                  "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Scene scene = new Scene(formVBox, 600, 600);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), formVBox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void showEditEmployeeWorkForm(Stage primaryStage, EmployeeWork employeeWork) {
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
		
		Label titleLabel = new Label("Редактирование назначения");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		grid.add(titleLabel, 0, 0, 2, 1);
		
		ComboBox<Employee> employeeCombo = new ComboBox<>();
		employeeCombo.setItems(FXCollections.observableArrayList(controller.getAllEmployees()));
		employeeCombo.setValue(controller.getEmployeeById(employeeWork.getIdEmployee()));
		employeeCombo.setDisable(true);
		styleComboBox(employeeCombo);
		grid.add(new Label("Сотрудник:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 1);
		grid.add(employeeCombo, 1, 1);
		
		ComboBox<Work> workCombo = new ComboBox<>();
		workCombo.setItems(FXCollections.observableArrayList(controller.getAllWorks()));
		workCombo.setValue(controller.getWorkById(employeeWork.getIdWork()));
		workCombo.setDisable(true);
		styleComboBox(workCombo);
		grid.add(new Label("Задача:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 2);
		grid.add(workCombo, 1, 2);
		
		ComboBox<String> urgencyCombo = new ComboBox<>();
		urgencyCombo.getItems().addAll("Низкая", "Средняя", "Высокая", "Критическая");
		urgencyCombo.setValue(employeeWork.getUrgency());
		styleComboBox(urgencyCombo);
		grid.add(new Label("Срочность:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 3);
		grid.add(urgencyCombo, 1, 3);
		
		TextField startDateField = new TextField(employeeWork.getStartDate().toString());
		startDateField.setDisable(true);
		styleTextField(startDateField);
		grid.add(new Label("Дата начала:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 4);
		grid.add(startDateField, 1, 4);
		
		TextField endDateField = new TextField();
		if (employeeWork.getEndDate() != null) {
			endDateField.setText(employeeWork.getEndDate().toString());
		}
		endDateField.setPromptText("ГГГГ-ММ-ДД");
		styleTextField(endDateField);
		grid.add(new Label("Дата окончания:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 5);
		grid.add(endDateField, 1, 5);
		
		TextField additionalPaymentField = new TextField();
		additionalPaymentField.setText(String.format("%.2f", controller.calculateAdditionalPayment(
				controller.getEmployeeById(employeeWork.getIdEmployee()),
				controller.getWorkById(employeeWork.getIdWork()),
				employeeWork.getEndDate())));
		additionalPaymentField.setEditable(false);
		styleTextField(additionalPaymentField);
		grid.add(new Label("Итог. оплата:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f; -fx-padding: 8;");
		}}, 0, 6);
		grid.add(additionalPaymentField, 1, 6);
		
		endDateField.textProperty().addListener((obs, oldValue, newValue) -> {
			try {
				LocalDate endDate = newValue.isEmpty() ? null : LocalDate.parse(newValue);
				double payment = controller.calculateAdditionalPayment(
						controller.getEmployeeById(employeeWork.getIdEmployee()),
						controller.getWorkById(employeeWork.getIdWork()),
						endDate);
				additionalPaymentField.setText(String.format("%.2f", payment));
			} catch (Exception e) {
				additionalPaymentField.setText("0.00");
			}
		});
		
		Button saveButton = new Button("Сохранить");
		Button cancelButton = new Button("Отмена");
		styleButton(saveButton);
		styleButton(cancelButton);
		HBox buttonBox = new HBox(10, saveButton, cancelButton);
		buttonBox.setAlignment(Pos.CENTER);
		grid.add(buttonBox, 1, 7);
		
		final Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
		messageLabel.setStyle("-fx-text-fill: #ff9999; -fx-padding: 10;");
		grid.add(messageLabel, 1, 8);
		
		saveButton.setOnAction(e -> {
			try {
				String urgency = urgencyCombo.getValue();
				String endDateText = endDateField.getText();
				
				if (urgency == null) {
					throw new IllegalArgumentException("Заполните все обязательные поля");
				}
				
				LocalDate endDate = endDateText.isEmpty() ? null : LocalDate.parse(endDateText);
				double additionalPayment = controller.calculateAdditionalPayment(
						controller.getEmployeeById(employeeWork.getIdEmployee()),
						controller.getWorkById(employeeWork.getIdWork()),
						endDate);
				
				employeeWork.setUrgency(urgency);
				employeeWork.setEndDate(endDate);
				employeeWork.setAdditionalPayment(additionalPayment);
				
				controller.updateEmployeeWork(employeeWork);
				showEmployeeWorkManagementView(primaryStage);
			} catch (Exception ex) {
				messageLabel.setText("Ошибка: " + ex.getMessage());
				messageLabel.setStyle("-fx-text-fill: #ff9999;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
			}
		});
		
		cancelButton.setOnAction(e -> showEmployeeWorkManagementView(primaryStage));
		
		VBox formVBox = new VBox(20, titleLabel, scrollPane);
		formVBox.setPadding(new Insets(20));
		formVBox.setAlignment(Pos.CENTER);
		formVBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				                  "-fx-background-radius: 20;" +
				                  "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Scene scene = new Scene(formVBox, 600, 600);
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
				                   "-fx-effect: dropshadow( gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
	}
	
	private void styleComboBox(ComboBox<?> comboBox) {
		comboBox.setStyle("-fx-background-color: #f5f5f5;" +
				                  "-fx-background-radius: 10;" +
				                  "-fx-border-color: #b7e4f7;" +
				                  "-fx-border-width: 1.5;" +
				                  "-fx-border-radius: 10;" +
				                  "-fx-padding: 10;" +
				                  "-fx-text-fill: #3c2f5f;" +
				                  "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
	}
}