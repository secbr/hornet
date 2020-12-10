package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
//		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

		BorderPane root1 = new BorderPane();
		root1.getStyleClass().add(getClass().getResource("sample.fxml").toExternalForm());
		Label label = new Label("公众号：程序新视界");
		root1.setCenter(label);

		primaryStage.setTitle("Hornet");
		Scene scene = new Scene(root1, 600, 550);
		primaryStage.setScene(scene);

		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
