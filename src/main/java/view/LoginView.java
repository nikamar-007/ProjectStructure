package view;

import controller.LoginController;
import controller.RegistrationController;
import controller.RoleController;
import model.User;
import javafx.animation.FadeTransition;
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

public class LoginView {
	private final Object controller;
	
	public LoginView(Object controller) {
		this.controller = controller;
	}
	
	public void showLoginView(Stage primaryStage, String role) {
		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(30));
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				              "-fx-background-radius: 20;" +
				              "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Label titleLabel = new Label("Вход в систему");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		grid.setPadding(new Insets(20));
		grid.setStyle("-fx-background-color: rgba(255,255,255,0.3);" +
				              "-fx-background-radius: 15;" +
				              "-fx-border-color: #b7e4f7;" +
				              "-fx-border-width: 1.5;" +
				              "-fx-border-radius: 15;");
		
		Label emailLabel = new Label("Email:");
		emailLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		emailLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-padding: 8;");
		
		TextField emailField = new TextField();
		emailField.setPromptText("Введите email");
		emailField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		emailField.setStyle("-fx-background-color: #f5f5f5;" +
				                    "-fx-background-radius: 10;" +
				                    "-fx-border-color: #b7e4f7;" +
				                    "-fx-border-width: 1.5;" +
				                    "-fx-border-radius: 10;" +
				                    "-fx-padding: 10;" +
				                    "-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		
		Label passwordLabel = new Label("Пароль:");
		passwordLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 15));
		passwordLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                       "-fx-padding: 8;");
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Введите пароль");
		passwordField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		passwordField.setStyle("-fx-background-color: #f5f5f5;" +
				                       "-fx-background-radius: 10;" +
				                       "-fx-border-color: #b7e4f7;" +
				                       "-fx-border-width: 1.5;" +
				                       "-fx-border-radius: 10;" +
				                       "-fx-padding: 10;" +
				                       "-fx-text-fill: #3c2f5f;" +
				                       "-fx-effect: dropshadow(gaussian, rgba(183,228,247,0.2), 6, 0.3, 0, 0);");
		
		Button loginButton = new Button("Войти");
		styleButton(loginButton);
		Button registerButton = new Button("Регистрация");
		styleButton(registerButton);
		Button backButton = new Button("Назад");
		styleButton(backButton);
		
		Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
		messageLabel.setStyle("-fx-text-fill: #ff9999;" +
				                      "-fx-padding: 10;");
		
		HBox buttonBox = new HBox(15, backButton, loginButton, registerButton);
		buttonBox.setAlignment(Pos.CENTER);
		
		grid.add(emailLabel, 0, 0);
		grid.add(emailField, 1, 0);
		grid.add(passwordLabel, 0, 1);
		grid.add(passwordField, 1, 1);
		grid.add(buttonBox, 0, 2, 2, 1);
		grid.add(messageLabel, 1, 3);
		
		loginButton.setOnAction(e -> {
			String email = emailField.getText();
			String password = passwordField.getText();
			User user = null;
			if (controller instanceof LoginController) {
				user = ((LoginController) controller).authenticate(email, password);
			} else if (controller instanceof RegistrationController) {
				user = ((RegistrationController) controller).authenticate(email, password);
			} else if (controller instanceof RoleController) {
				user = ((RoleController) controller).authenticate(email, password);
			}
			if (user != null) {
				messageLabel.setStyle("-fx-text-fill: #f7e8aa;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
				messageLabel.setText("Вход успешен");
				if (controller instanceof LoginController) {
					((LoginController) controller).navigateToMainView(user);
				} else if (controller instanceof RegistrationController) {
					((RegistrationController) controller).navigateToMainView(user);
				} else if (controller instanceof RoleController) {
					((RoleController) controller).navigateToMainView(user);
				}
			} else {
				messageLabel.setStyle("-fx-text-fill: #ff9999;" +
						                      "-fx-font-family: 'Segoe UI';" +
						                      "-fx-font-weight: bold;" +
						                      "-fx-font-size: 14;" +
						                      "-fx-padding: 10;");
				messageLabel.setText("Неверный email или пароль");
			}
		});
		
		registerButton.setOnAction(e -> {
			if (controller instanceof LoginController) {
				((LoginController) controller).showRegistrationView();
			} else if (controller instanceof RegistrationController) {
				((RegistrationController) controller).showRegistrationView();
			} else if (controller instanceof RoleController) {
				((RoleController) controller).showRegistrationView();
			}
		});
		
		backButton.setOnAction(e -> {
			if (controller instanceof LoginController) {
				((LoginController) controller).backToRoleSelection();
			} else if (controller instanceof RegistrationController) {
				((RegistrationController) controller).backToRoleSelection();
			} else if (controller instanceof RoleController) {
				((RoleController) controller).backToRoleSelection();
			}
		});
		
		vbox.getChildren().addAll(titleLabel, grid);
		Scene scene = new Scene(vbox, 600, 450);
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