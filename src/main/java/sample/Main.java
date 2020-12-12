package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.util.StringUtils;

/**
 * @author zzs
 */
public class Main extends Application {

	final String[] protocols = new String[]{"TCP", "UDP"};
	final ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(protocols));

	/**
	 * 通信信息展示
	 */
	final TextArea result = new TextArea();

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		root.getStyleClass().add(getClass().getResource("sample.fxml").toExternalForm());

		// 第一行连接相关信息
		HBox hBox = addHBox();
		root.setTop(hBox);

		GridPane gridPane = addGridPane();
		root.setCenter(gridPane);

		primaryStage.setTitle("Hornet");
		Scene scene = new Scene(root, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private GridPane addGridPane() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(2);
		gridPane.setVgap(1);
		gridPane.setPadding(new Insets(0, 0, 0, 10));

		// -----第1行start----
		HBox hBox = new HBox();
		// 设置布局位置
		hBox.setAlignment(Pos.CENTER_LEFT);
		// padding属性可设置为管理节点与HBox窗格边缘之间的距离,分别是上，右，下，左
		hBox.setPadding(new Insets(15, 10, 15, 0));
		// 设置组件之间的间隙
		hBox.setSpacing(15);
		Label ipLabel = new Label("发送内容");
		TextArea content = new TextArea();
		content.setPrefRowCount(4);
		content.setPrefWidth(600);

		Button sendBtn = new Button("发送");
		sendBtn.setOnAction(event -> {
			result.appendText("发送消息：【" + content.getText() + "】\n\r");
		});

		hBox.getChildren().addAll(ipLabel, content, sendBtn);

		gridPane.add(hBox, 1, 0);
		// -----第1行end----

		// -----第2行start----
		HBox resultBox = new HBox();
		// 设置布局位置
		resultBox.setAlignment(Pos.CENTER_LEFT);
		// padding属性可设置为管理节点与HBox窗格边缘之间的距离,分别是上，右，下，左
		resultBox.setPadding(new Insets(15, 10, 15, 0));
		// 设置组件之间的间隙
		resultBox.setSpacing(15);
		Label resultLabel = new Label("通信信息");
		result.setPrefRowCount(20);
		result.setPrefWidth(600);

		Button cleanBtn = new Button("清空");
		cleanBtn.setOnAction(event -> {
			result.setText("");
		});

		resultBox.getChildren().addAll(resultLabel, result, cleanBtn);

		gridPane.add(resultBox, 1, 1);
		// -----第2行end----
		return gridPane;
	}

	private HBox addHBox() {
		HBox hBox = new HBox();
		// 设置布局位置
		hBox.setAlignment(Pos.CENTER);
		// padding属性可设置为管理节点与HBox窗格边缘之间的距离,分别是上，右，下，左
		hBox.setPadding(new Insets(15, 10, 15, 10));
		// 设置组件之间的间隙
		hBox.setSpacing(15);

		// 默认选择第一个选项
		choiceBox.getSelectionModel().select(0);
		Label protocolLabel = new Label("协议：");

		Label ipLabel = new Label("IP：");
		TextField ipField = new TextField();
		ipField.setText("127.0.0.1");

		Label portLabel = new Label("端口：");
		TextField portField = new TextField();
		portField.setText("8888");
		portField.setMaxWidth(80);

		Button connect = new Button("连接");
		connect.setOnAction((event -> {
			// check ip
			String ip = ipField.getText();
			if (StringUtils.isEmpty(ip)) {
				new Alert(Alert.AlertType.NONE, "IP不能为空", new ButtonType[]{ButtonType.CLOSE}).show();
				return;
			}

			// check port
			String portText = portField.getText();
			int port;
			try {
				port = Integer.parseInt(portText);
			} catch (Exception e) {
				port = 0;
			}

			if (port < 1000 || port > 65536) {
				new Alert(Alert.AlertType.NONE, "端口号错误", new ButtonType[]{ButtonType.CLOSE}).show();
				return;
			}

			// TODO to connect

			// add connect info to result
			String text = "连接" + ip + ":" + port + " 成功！\n\r";
			result.appendText(text);

		}));

		Button disconnect = new Button("断开");
		disconnect.setOnAction(event -> {
			String ip = ipField.getText();
			String portText = portField.getText();
			String text = "断开与" + ip + ":" + portText + "的连接！\n\r";
			result.appendText(text);
		});
		hBox.getChildren().addAll(protocolLabel, choiceBox, ipLabel, ipField,
				portLabel, portField, connect, disconnect);
		return hBox;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
