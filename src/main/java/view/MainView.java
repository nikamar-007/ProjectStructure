package view;

import model.User;
import controller.MainController;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainView {
	private final MainController controller;
	
	public MainView(MainController controller) {
		this.controller = controller;
	}
	
	public void showMainView(Stage primaryStage) {
		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(30));
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffc1cc 0%, #b7e4f7 50%, #f7e8aa 100%);" +
				              "-fx-background-radius: 20;" +
				              "-fx-effect: dropshadow(gaussian, rgba(255,193,204,0.4), 10, 0.5, 0, 0);");
		
		Label titleLabel = new Label("Добро пожаловать");
		titleLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 28));
		titleLabel.setStyle("-fx-text-fill: #3c2f5f;" +
				                    "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.7), 5, 0.4, 0, 0);" +
				                    "-fx-padding: 15;");
		
		Button profileButton = new Button("Мой профиль");
		styleButton(profileButton);
		profileButton.setOnAction(e -> controller.showEmployeeManagementView());
		
		Button logoutButton = new Button("Выйти");
		styleButton(logoutButton);
		logoutButton.setOnAction(e -> controller.logout());
		
		if (controller.isAdmin()) {
			Button assignmentsButton = new Button("Назначения");
			styleButton(assignmentsButton);
			assignmentsButton.setOnAction(e -> controller.showEmployeeWorkManagementView());
			
			Button worksButton = new Button("Список работ");
			styleButton(worksButton);
			worksButton.setOnAction(e -> controller.showWorkManagementView());
			
			Button employeesButton = new Button("Список сотрудников");
			styleButton(employeesButton);
			employeesButton.setOnAction(e -> controller.showAllEmployeesView());
			
			vbox.getChildren().addAll(titleLabel, profileButton, employeesButton,
					assignmentsButton, worksButton, logoutButton);
		} else {
			Button tasksButton = new Button("Мои задачи");
			styleButton(tasksButton);
			tasksButton.setOnAction(e -> controller.showWorkManagementView());
			
			Button responsibleButton = new Button("Ответственные работы");
			styleButton(responsibleButton);
			responsibleButton.setOnAction(e -> controller.showResponsibleWorksView());
			
			vbox.getChildren().addAll(titleLabel, profileButton, tasksButton,
					responsibleButton, logoutButton);
		}
		
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
}