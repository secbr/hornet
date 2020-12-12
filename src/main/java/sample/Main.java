package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane();
		root.getStyleClass().add(getClass().getResource("sample.fxml").toExternalForm());

		HBox hBox = new HBox();
		// 设置布局位置
		hBox.setAlignment(Pos.CENTER);
		// 设备内部距离边框距离
		hBox.setPadding(new Insets(15));
		// 设置组件之间的间隙
		hBox.setSpacing(15);
		Label ipLabel = new Label("IP：");
		TextField ipField = new TextField();
		Label portLabel = new Label("端口：");
		TextField portField = new TextField();
		Button connect = new Button("连接");
		hBox.getChildren().addAll(ipLabel, ipField, portLabel, portField, connect);

		root.setTop(hBox);

		primaryStage.setTitle("Hornet");
		Scene scene = new Scene(root, 600, 550);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
