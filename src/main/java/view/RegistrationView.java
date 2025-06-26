package view;

import controller.UserController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;

public class RegistrationView {
	private final UserController controller;

	public RegistrationView(UserController controller) {
		this.controller = controller;
	}

	public void showRegistrationView(Stage primaryStage, String role) {
		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(30));
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				              "-fx-background-radius: 20;" +
				              "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");

		Label titleLabel = new Label("Регистрация " + (role.equals("Администратор") ? "администратора" : "сотрудника"));
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
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

		Label lastNameLabel = new Label("Фамилия:");
		lastNameLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		lastNameLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                       "-fx-padding: 8;");
		TextField lastNameField = new TextField();
		lastNameField.setPromptText("Введите фамилию");
		styleTextField(lastNameField);

		Label firstNameLabel = new Label("Имя:");
		firstNameLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		firstNameLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                        "-fx-padding: 8;");
		TextField firstNameField = new TextField();
		firstNameField.setPromptText("Введите имя");
		styleTextField(firstNameField);

		Label middleNameLabel = new Label("Отчество:");
		middleNameLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		middleNameLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                         "-fx-padding: 8;");
		TextField middleNameField = new TextField();
		middleNameField.setPromptText("Введите отчество (опционально)");
		styleTextField(middleNameField);

		Label genderLabel = new Label("Пол:");
		genderLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		genderLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                     "-fx-padding: 8;");
		ComboBox<String> genderCombo = new ComboBox<>();
		genderCombo.getItems().addAll("Мужской", "Женский");
		genderCombo.setPromptText("Выберите пол");
		styleComboBox(genderCombo);

		Label positionLabel = new Label("Должность:");
		positionLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		positionLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                       "-fx-padding: 8;");
		TextField positionField = new TextField();
		positionField.setPromptText("Введите должность");
		styleTextField(positionField);

		Label salaryLabel = new Label("Зарплата:");
		salaryLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		salaryLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                     "-fx-padding: 8;");
		TextField salaryField = new TextField();
		salaryField.setPromptText("Введите зарплату");
		styleTextField(salaryField);

		Label experienceLabel = new Label("Стаж (лет):");
		experienceLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		experienceLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                         "-fx-padding: 8;");
		TextField experienceField = new TextField();
		experienceField.setPromptText("Введите стаж");
		styleTextField(experienceField);

		Label emailLabel = new Label("Email:");
		emailLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		emailLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-padding: 8;");
		TextField emailField = new TextField();
		emailField.setPromptText("Введите email");
		styleTextField(emailField);

		Label passwordLabel = new Label("Пароль:");
		passwordLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		passwordLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                       "-fx-padding: 8;");
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Введите пароль");
		styleTextField(passwordField);

		Label confirmPasswordLabel = new Label("Подтвердите пароль:");
		confirmPasswordLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		confirmPasswordLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                              "-fx-padding: 8;");
		PasswordField confirmPasswordField = new PasswordField();
		confirmPasswordField.setPromptText("Повторите пароль");
		styleTextField(confirmPasswordField);

		Button registerButton = new Button("Зарегистрироваться");
		Button backButton = new Button("Назад");
		styleButton(registerButton);
		styleButton(backButton);

		Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
		messageLabel.setStyle("-fx-text-fill: #ff9999;" +
				                      "-fx-padding: 10;");

		grid.add(lastNameLabel, 0, 0);
		grid.add(lastNameField, 1, 0);
		grid.add(firstNameLabel, 0, 1);
		grid.add(firstNameField, 1, 1);
		grid.add(middleNameLabel, 0, 2);
		grid.add(middleNameField, 1, 2);
		grid.add(genderLabel, 0, 3);
		grid.add(genderCombo, 1, 3);
		grid.add(positionLabel, 0, 4);
		grid.add(positionField, 1, 4);
		grid.add(salaryLabel, 0, 5);
		grid.add(salaryField, 1, 5);
		grid.add(experienceLabel, 0, 6);
		grid.add(experienceField, 1, 6);
		grid.add(emailLabel, 0, 7);
		grid.add(emailField, 1, 7);
		grid.add(passwordLabel, 0, 8);
		grid.add(passwordField, 1, 8);
		grid.add(confirmPasswordLabel, 0, 9);
		grid.add(confirmPasswordField, 1, 9);

		HBox buttonBox = new HBox(10, backButton, registerButton);
		buttonBox.setAlignment(Pos.CENTER);
		grid.add(buttonBox, 0, 10, 2, 1);
		grid.add(messageLabel, 0, 11, 2, 1);

		registerButton.setOnAction(e -> {
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
				String confirmPassword = confirmPasswordField.getText();

				if (lastName.isEmpty() || firstName.isEmpty() || gender == null ||
						    position.isEmpty() || salaryText.isEmpty() || experienceText.isEmpty() ||
						    email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
					throw new IllegalArgumentException("Заполните все обязательные поля");
				}

				if (!password.equals(confirmPassword)) {
					throw new IllegalArgumentException("Пароли не совпадают");
				}

				double salary = Double.parseDouble(salaryText);
				int experience = Integer.parseInt(experienceText);

				User newUser = new User(
						email,
						password,
						role,
						lastName,
						firstName,
						middleName.isEmpty() ? null : middleName,
						gender,
						position,
						salary,
						experience
				);

				if (controller.registerUser(newUser)) {
					messageLabel.setStyle("-fx-text-fill: #f7e8aa;" +
							                      "-fx-font-family: 'Segoe UI';" +
							                      "-fx-font-weight: bold;" +
							                      "-fx-font-size: 14;" +
							                      "-fx-padding: 10;");
					messageLabel.setText("Регистрация прошла успешно");
					controller.showLoginView();
				} else {
					throw new IllegalArgumentException("Ошибка регистрации пользователя");
				}
			} catch (Exception ex) {
				messageLabel.setStyle("-fx-text-fill: #ff9999;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
				messageLabel.setText("Ошибка: " + ex.getMessage());
			}
		});

		backButton.setOnAction(e -> controller.showLoginView());

		vbox.getChildren().addAll(titleLabel, scrollPane);

		Scene scene = new Scene(vbox, 600, 600);
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
				                  "-fx-padding: 10;" +
				                  "-fx-text-fill: #3c2f5f;" +
				                  "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
	}
}