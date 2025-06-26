package model;

import javafx.application.Application;
import javafx.stage.Stage;
import view.RoleView;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Система распределения дополнительной работы в компании");
		RoleView roleSelectionView = new RoleView();
		roleSelectionView.showRoleSelectionView(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}