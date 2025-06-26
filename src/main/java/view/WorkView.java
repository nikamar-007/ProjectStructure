package view;

import controller.WorkController;
import dao.UserDAO;
import model.Employee;
import model.EmployeeWork;
import model.User;
import model.Work;
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
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Duration;

public class WorkView {
	private final WorkController controller;
	
	public WorkView(WorkController controller) {
		this.controller = controller;
	}
	
	public void showResponsibleWorksView(Stage primaryStage) {
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				              "-fx-background-radius: 20;" +
				              "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Label titleLabel = new Label("Ответственные работы");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		
		Button backButton = new Button("Назад");
		styleButton(backButton);
		backButton.setOnAction(e -> controller.backToMainView());
		
		TableView<Work> table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setStyle("-fx-background-color: rgba(255,255,255,0.95);" +
				               "-fx-background-radius: 15;" +
				               "-fx-border-color: #b7e4f7;" +
				               "-fx-border-width: 1.5;" +
				               "-fx-border-radius: 15;" +
				               "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.3), 8, 0.4, 0, 0);");
		
		TableColumn<Work, String> titleCol = new TableColumn<>("Название");
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		
		TableColumn<Work, String> descriptionCol = new TableColumn<>("Описание");
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		table.getColumns().addAll(titleCol, descriptionCol);
		table.setItems(FXCollections.observableArrayList(controller.getResponsibleWorks()));
		
		Platform.runLater(() -> {
			table.lookupAll(".column-header-background .label").forEach(node ->
					                                                            node.setStyle("-fx-background-color: linear-gradient(to right, #c9a9a6, #d7b7b4);" +
							                                                                          "-fx-text-fill: #f5f5f5;" +
							                                                                          "-fx-font-family: 'Verdana';" +
							                                                                          "-fx-font-weight: bold;" +
							                                                                          "-fx-padding: 10;" +
							                                                                          "-fx-alignment: center;"));
		});
		
		table.setRowFactory(tv -> new TableRow<Work>() {
			@Override
			protected void updateItem(Work item, boolean empty) {
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
		
		Button assignButton = new Button("Назначить сотрудников");
		styleButton(assignButton);
		assignButton.setOnAction(e -> {
			Work selected = table.getSelectionModel().getSelectedItem();
			if (selected != null) {
				showAssignEmployeesForm(primaryStage, selected);
			} else {
				showAlert("Ошибка", "Выберите задачу для назначения сотрудников");
			}
		});
		
		HBox buttonBox = new HBox(10, assignButton);
		buttonBox.setAlignment(Pos.CENTER);
		
		vbox.getChildren().addAll(backButton, titleLabel, table, buttonBox);
		
		Scene scene = new Scene(vbox, 700, 500);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), vbox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setResizable(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void showWorkManagementView(Stage primaryStage) {
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
		backButton.setOnAction(e -> controller.backToMainView());
		
		Label titleLabel = new Label(controller.isAdmin() ? "Управление задачами" : "Мои задачи");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		
		navPanel.getChildren().addAll(backButton, titleLabel);
		
		TableView<Work> workTable = new TableView<>();
		workTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		workTable.setStyle("-fx-background-color: rgba(255,255,255,0.95);" +
				                   "-fx-background-radius: 15;" +
				                   "-fx-border-color: #b7e4f7;" +
				                   "-fx-border-width: 1.5;" +
				                   "-fx-border-radius: 15;" +
				                   "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.3), 8, 0.4, 0, 0);");
		
		TableColumn<Work, String> titleCol = new TableColumn<>("Название");
		titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
		
		TableColumn<Work, String> responsibleCol = new TableColumn<>("Ответственный");
		responsibleCol.setCellValueFactory(cellData -> {
			Employee emp = controller.getEmployeeById(cellData.getValue().getIdResponsible());
			return new SimpleStringProperty(emp != null ? emp.toString() : "");
		});
		
		TableColumn<Work, String> fixedPaymentCol = new TableColumn<>("Фиксированная оплата");
		fixedPaymentCol.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getFixedPayment() != null ? String.valueOf(cellData.getValue().getFixedPayment()) : ""));
		
		TableColumn<Work, String> descriptionCol = new TableColumn<>("Описание");
		descriptionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
		
		workTable.getColumns().addAll(titleCol, responsibleCol, fixedPaymentCol, descriptionCol);
		
		if (controller.isAdmin()) {
			TableColumn<Work, String> laborIntensityCol = new TableColumn<>("Трудоемкость");
			laborIntensityCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLaborIntensity())));
			
			TableColumn<Work, String> recommendedEmployeesCol = new TableColumn<>("Рекомендуемое число сотрудников");
			recommendedEmployeesCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRecommendedEmployees())));
			
			workTable.getColumns().addAll(laborIntensityCol, recommendedEmployeesCol);
		}
		
		workTable.setItems(FXCollections.observableArrayList(
				controller.isAdmin() ? controller.getAllWorks() : controller.getUserWorks()));
		
		Platform.runLater(() -> {
			workTable.lookupAll(".column-header-background .label").forEach(node ->
					                                                                node.setStyle("-fx-background-color: linear-gradient(to right, #c9a9a6, #d7b7b4);" +
							                                                                              "-fx-text-fill: #f5f5f5;" +
							                                                                              "-fx-font-family: 'Verdana';" +
							                                                                              "-fx-font-weight: bold;" +
							                                                                              "-fx-padding: 10;" +
							                                                                              "-fx-alignment: center;"));
		});
		
		workTable.setRowFactory(t -> new TableRow<Work>() {
			@Override
			protected void updateItem(Work item, boolean empty) {
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
			Button assignButton = new Button("Назначить сотрудников");
			styleButton(addButton);
			styleButton(editButton);
			styleButton(deleteButton);
			styleButton(assignButton);
			
			addButton.setOnAction(e -> showAddWorkForm(primaryStage));
			editButton.setOnAction(e -> {
				Work selectedWork = workTable.getSelectionModel().getSelectedItem();
				if (selectedWork != null) {
					showEditWorkForm(primaryStage, selectedWork);
				} else {
					showAlert("Ошибка", "Выберите задачу для редактирования");
				}
			});
			deleteButton.setOnAction(e -> {
				Work selectedWork = workTable.getSelectionModel().getSelectedItem();
				if (selectedWork != null) {
					controller.deleteWork(selectedWork.getIdWork());
					workTable.setItems(FXCollections.observableArrayList(controller.getAllWorks()));
				} else {
					showAlert("Ошибка", "Выберите задачу для удаления");
				}
			});
			assignButton.setOnAction(e -> {
				Work selectedWork = workTable.getSelectionModel().getSelectedItem();
				if (selectedWork != null) {
					showAssignEmployeesForm(primaryStage, selectedWork);
				} else {
					showAlert("Ошибка", "Выберите задачу для назначения сотрудников");
				}
			});
			
			buttonBox.getChildren().addAll(addButton, editButton, deleteButton, assignButton);
		}
		
		vbox.getChildren().addAll(navPanel, workTable, buttonBox);
		
		Scene scene = new Scene(vbox, 900, 700);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), vbox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setResizable(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void showAddWorkForm(Stage primaryStage) {
		if (!controller.isAdmin()) {
			showAlert("Доступ запрещен", "Только администратор может добавлять задачи");
			return;
		}
		
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
		
		Label titleLabel = new Label("Добавление задачи");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		grid.add(titleLabel, 0, 0, 2, 1);
		
		ComboBox<Employee> responsibleCombo = new ComboBox<>();
		responsibleCombo.setItems(FXCollections.observableArrayList(controller.getAllEmployees()));
		responsibleCombo.setPromptText("Выберите ответственного");
		styleComboBox(responsibleCombo);
		grid.add(new Label("Ответственный:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 1);
		grid.add(responsibleCombo, 1, 1);
		
		TextField titleField = new TextField();
		titleField.setPromptText("Название задачи");
		styleTextField(titleField);
		grid.add(new Label("Название:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 2);
		grid.add(titleField, 1, 2);
		
		TextField laborIntensityField = new TextField();
		laborIntensityField.setPromptText("Трудоемкость (часы)");
		styleTextField(laborIntensityField);
		grid.add(new Label("Трудоемкость:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 3);
		grid.add(laborIntensityField, 1, 3);
		
		TextField fixedPaymentField = new TextField();
		fixedPaymentField.setPromptText("Фиксированная оплата (опционально)");
		styleTextField(fixedPaymentField);
		grid.add(new Label("Фиксированная оплата:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 4);
		grid.add(fixedPaymentField, 1, 4);
		
		TextField recommendedEmployeesField = new TextField();
		recommendedEmployeesField.setPromptText("Рекомендуемое число сотрудников");
		styleTextField(recommendedEmployeesField);
		grid.add(new Label("Рекомендуемое число сотрудников:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 5);
		grid.add(recommendedEmployeesField, 1, 5);
		
		TextField descriptionField = new TextField();
		descriptionField.setPromptText("Описание");
		styleTextField(descriptionField);
		grid.add(new Label("Описание:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 6);
		grid.add(descriptionField, 1, 6);
		
		Button saveButton = new Button("Сохранить");
		Button cancelButton = new Button("Отмена");
		styleButton(saveButton);
		styleButton(cancelButton);
		HBox buttonBox = new HBox(10, saveButton, cancelButton);
		buttonBox.setAlignment(Pos.CENTER);
		grid.add(buttonBox, 1, 7);
		
		final Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
		messageLabel.setStyle("-fx-text-fill: #ff9999;" +
				                      "-fx-padding: 10;");
		grid.add(messageLabel, 1, 8);
		
		saveButton.setOnAction(e -> {
			try {
				Employee responsible = responsibleCombo.getValue();
				String title = titleField.getText();
				String laborIntensityText = laborIntensityField.getText();
				String fixedPaymentText = fixedPaymentField.getText();
				String recommendedEmployeesText = recommendedEmployeesField.getText();
				String description = descriptionField.getText();
				
				if (responsible == null || title.isEmpty() || laborIntensityText.isEmpty() || recommendedEmployeesText.isEmpty()) {
					throw new IllegalArgumentException("Заполните все обязательные поля");
				}
				
				int laborIntensity = Integer.parseInt(laborIntensityText);
				Double fixedPayment = fixedPaymentText.isEmpty() ? null : Double.parseDouble(fixedPaymentText);
				int recommendedEmployees = Integer.parseInt(recommendedEmployeesText);
				
				Work newWork = new Work(
						responsible.getIdEmployee(),
						title,
						laborIntensity,
						fixedPayment,
						recommendedEmployees,
						description
				);
				controller.addWork(newWork);
				showWorkManagementView(primaryStage);
			} catch (Exception ex) {
				messageLabel.setText("Ошибка: " + ex.getMessage());
				messageLabel.setStyle("-fx-text-fill: #ff9999;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
			}
		});
		
		cancelButton.setOnAction(e -> showWorkManagementView(primaryStage));
		
		VBox formVBox = new VBox(20, titleLabel, scrollPane);
		formVBox.setPadding(new Insets(20));
		formVBox.setAlignment(Pos.CENTER);
		formVBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				                  "-fx-background-radius: 20;" +
				                  "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Scene scene = new Scene(formVBox, 650, 550);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), formVBox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setResizable(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void showEditWorkForm(Stage primaryStage, Work work) {
		if (!controller.isAdmin()) {
			showAlert("Доступ запрещен", "Только администратор может редактировать задачи");
			return;
		}
		
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
		
		Label titleLabel = new Label("Редактирование задачи");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		grid.add(titleLabel, 0, 0, 2, 1);
		
		ComboBox<Employee> responsibleCombo = new ComboBox<>();
		responsibleCombo.setItems(FXCollections.observableArrayList(controller.getAllEmployees()));
		responsibleCombo.setValue(controller.getEmployeeById(work.getIdResponsible()));
		styleComboBox(responsibleCombo);
		grid.add(new Label("Ответственный:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 1);
		grid.add(responsibleCombo, 1, 1);
		
		TextField titleField = new TextField(work.getTitle());
		styleTextField(titleField);
		grid.add(new Label("Название:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 2);
		grid.add(titleField, 1, 2);
		
		TextField laborIntensityField = new TextField(String.valueOf(work.getLaborIntensity()));
		styleTextField(laborIntensityField);
		grid.add(new Label("Трудоемкость:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 3);
		grid.add(laborIntensityField, 1, 3);
		
		TextField fixedPaymentField = new TextField(work.getFixedPayment() != null ? String.valueOf(work.getFixedPayment()) : "");
		fixedPaymentField.setPromptText("Фиксированная оплата (опционально)");
		styleTextField(fixedPaymentField);
		grid.add(new Label("Фиксированная оплата:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 4);
		grid.add(fixedPaymentField, 1, 4);
		
		TextField recommendedEmployeesField = new TextField(String.valueOf(work.getRecommendedEmployees()));
		styleTextField(recommendedEmployeesField);
		grid.add(new Label("Рекомендуемое число сотрудников:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 5);
		grid.add(recommendedEmployeesField, 1, 5);
		
		TextField descriptionField = new TextField(work.getDescription());
		styleTextField(descriptionField);
		grid.add(new Label("Описание:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 6);
		grid.add(descriptionField, 1, 6);
		
		Button saveButton = new Button("Сохранить");
		Button cancelButton = new Button("Отмена");
		styleButton(saveButton);
		styleButton(cancelButton);
		HBox buttonBox = new HBox(10, saveButton, cancelButton);
		buttonBox.setAlignment(Pos.CENTER);
		grid.add(buttonBox, 1, 7);
		
		final Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
		messageLabel.setStyle("-fx-text-fill: #ff9999;" +
				                      "-fx-padding: 10;");
		grid.add(messageLabel, 1, 8);
		
		saveButton.setOnAction(e -> {
			try {
				Employee responsible = responsibleCombo.getValue();
				String title = titleField.getText();
				String laborIntensityText = laborIntensityField.getText();
				String fixedPaymentText = fixedPaymentField.getText();
				String recommendedEmployeesText = recommendedEmployeesField.getText();
				String description = descriptionField.getText();
				
				if (responsible == null || title.isEmpty() || laborIntensityText.isEmpty() || recommendedEmployeesText.isEmpty()) {
					throw new IllegalArgumentException("Заполните все обязательные поля");
				}
				
				int laborIntensity = Integer.parseInt(laborIntensityText);
				Double fixedPayment = fixedPaymentText.isEmpty() ? null : Double.parseDouble(fixedPaymentText);
				int recommendedEmployees = Integer.parseInt(recommendedEmployeesText);
				
				work.setIdResponsible(responsible.getIdEmployee());
				work.setTitle(title);
				work.setLaborIntensity(laborIntensity);
				work.setFixedPayment(fixedPayment);
				work.setRecommendedEmployees(recommendedEmployees);
				work.setDescription(description);
				
				controller.updateWork(work);
				showWorkManagementView(primaryStage);
			} catch (Exception ex) {
				messageLabel.setText("Ошибка: " + ex.getMessage());
				messageLabel.setStyle("-fx-text-fill: #ff9999;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
			}
		});
		
		cancelButton.setOnAction(e -> showWorkManagementView(primaryStage));
		
		VBox formVBox = new VBox(20, titleLabel, scrollPane);
		formVBox.setPadding(new Insets(20));
		formVBox.setAlignment(Pos.CENTER);
		formVBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				                  "-fx-background-radius: 20;" +
				                  "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Scene scene = new Scene(formVBox, 650, 550);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), formVBox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setResizable(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void showAssignEmployeesForm(Stage primaryStage, Work work) {
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
		
		Label titleLabel = new Label("Назначение сотрудников на задачу: " + work.getTitle());
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 24));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		grid.add(titleLabel, 0, 0, 2, 1);
		
		VBox employeesBox = new VBox(5);
		List<Employee> allEmployees = controller.getAllEmployees();
		List<CheckBox> checkBoxes = new ArrayList<>();
		List<Employee> assignedEmployees = controller.getAssignedEmployees(work.getIdWork());
		Employee responsibleEmployee = controller.getEmployeeById(work.getIdResponsible());
		UserDAO userDAO = UserDAO.getInstance();
		
		for (Employee employee : allEmployees) {
			CheckBox checkBox = new CheckBox(employee.toString() +
					                                 (employee.getIdEmployee() == work.getIdResponsible() ? " (Ответственный)" : ""));
			boolean isAssigned = assignedEmployees.stream()
					                     .anyMatch(e -> e.getIdEmployee() == employee.getIdEmployee());
			boolean isResponsible = responsibleEmployee != null &&
					                        employee.getIdEmployee() == responsibleEmployee.getIdEmployee();
			checkBox.setSelected(isAssigned || isResponsible);
			
			User user = userDAO.getUserByEmail(employee.getEmail());
			if (user != null && "Администратор".equalsIgnoreCase(user.getRole()) && !checkBox.isSelected()) {
				checkBox.setDisable(true);
			}
			
			styleCheckBox(checkBox);
			checkBoxes.add(checkBox);
			employeesBox.getChildren().add(checkBox);
		}
		
		grid.add(new Label("Выберите сотрудников:") {{
			setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
			setStyle("-fx-text-fill: #3c2f5f;" +
					         "-fx-padding: 8;");
		}}, 0, 1);
		grid.add(employeesBox, 0, 2, 2, 1);
		
		Button saveButton = new Button("Сохранить");
		Button cancelButton = new Button("Отмена");
		styleButton(saveButton);
		styleButton(cancelButton);
		HBox buttonBox = new HBox(10, saveButton, cancelButton);
		buttonBox.setAlignment(Pos.CENTER);
		grid.add(buttonBox, 0, 3, 2, 1);
		
		final Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
		messageLabel.setStyle("-fx-text-fill: #ff9999;" +
				                      "-fx-padding: 10;");
		grid.add(messageLabel, 0, 4, 2, 1);
		
		saveButton.setOnAction(e -> {
			try {
				controller.removeAllAssignmentsForWork(work.getIdWork());
				
				for (int i = 0; i < checkBoxes.size(); i++) {
					if (checkBoxes.get(i).isSelected()) {
						Employee employee = allEmployees.get(i);
						User user = userDAO.getUserByEmail(employee.getEmail());
						if (user != null && "Администратор".equalsIgnoreCase(user.getRole())) {
							throw new IllegalArgumentException("Нельзя назначить администратора: " + employee.toString());
						}
						
						EmployeeWork assignment = new EmployeeWork(
								employee.getIdEmployee(),
								work.getIdWork(),
								"Средняя",
								LocalDate.now(),
								null,
								0
						);
						controller.assignWork(assignment);
					}
				}
				showWorkManagementView(primaryStage);
			} catch (Exception ex) {
				messageLabel.setText("Ошибка: " + ex.getMessage());
				messageLabel.setStyle("-fx-text-fill: #ff9999;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
			}
		});
		
		cancelButton.setOnAction(e -> showWorkManagementView(primaryStage));
		
		VBox formVBox = new VBox(20, titleLabel, scrollPane);
		formVBox.setPadding(new Insets(20));
		formVBox.setAlignment(Pos.CENTER);
		formVBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				                  "-fx-background-radius: 20;" +
				                  "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Scene scene = new Scene(formVBox, 550, 450);
		scene.setFill(Color.web("#f5f0f6"));
		
		FadeTransition fade = new FadeTransition(Duration.millis(500), formVBox);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.play();
		
		primaryStage.setResizable(true);
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
	
	private void styleCheckBox(CheckBox checkBox) {
		checkBox.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		checkBox.setStyle("-fx-text-fill: #3c2f5f;" +
				                  "-fx-padding: 5;");
	}
}