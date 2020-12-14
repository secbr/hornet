package sample;

import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
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
import sample.netty.NettyClient;
import sample.util.StringUtils;

/**
 * @author zzs
 */
public class Main extends Application {

    final String[] protocols = new String[]{"TCP", "UDP"};
    final ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(protocols));

    /**
     * history message
     */
    final TextArea result = new TextArea();

    NettyClient client = null;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add(getClass().getResource("sample.fxml").toExternalForm());

        GridPane gridPane = addGridPane();
        root.setCenter(gridPane);

        primaryStage.setTitle("Hornet");
        Scene scene = new Scene(root, 800, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane addGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(3);
        gridPane.setVgap(1);
        gridPane.setPadding(new Insets(0, 0, 0, 10));

        // -----first row start----
        HBox configBox = addBox();
        configBox.setAlignment(Pos.CENTER_LEFT);
        configBox.setPadding(new Insets(15, 10, 15, 0));
        configBox.setSpacing(15);
        gridPane.add(configBox, 1, 0);
        // ----- first end----

        // -----second row start----
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(15, 10, 15, 0));
        hBox.setSpacing(15);
        Label ipLabel = new Label("Content");
        TextArea content = new TextArea();
        content.setPrefRowCount(4);
        content.setPrefWidth(600);

        Button sendBtn = new Button("Send");
        sendBtn.setOnAction(event -> {
            if (client == null || !client.getChannel().isActive()) {
                new Alert(Alert.AlertType.NONE, "Channel closed!", new ButtonType[]{ButtonType.CLOSE}).show();
                return;
            }
            try {
                client.getChannel().writeAndFlush(Unpooled.copiedBuffer(content.getText(), CharsetUtil.UTF_8));
            } catch (Exception e) {
                result.appendText(e.getMessage() + "\n\r");
                return;
            }
            result.appendText("Send message：【" + content.getText() + "】\n\r");
        });

        hBox.getChildren().addAll(ipLabel, content, sendBtn);

        gridPane.add(hBox, 1, 1);
        // -----second row end----

        // -----third row start----
        HBox resultBox = new HBox();
        resultBox.setAlignment(Pos.CENTER_LEFT);
        resultBox.setPadding(new Insets(15, 10, 15, 0));
        resultBox.setSpacing(15);
        Label resultLabel = new Label("History");
        result.setPrefRowCount(20);
        result.setPrefWidth(600);

        Button cleanBtn = new Button("Clear All");
        cleanBtn.setOnAction(event -> {
            result.setText("");
        });

        resultBox.getChildren().addAll(resultLabel, result, cleanBtn);
        gridPane.add(resultBox, 1, 2);
        // -----third row end----

        return gridPane;
    }

    private HBox addBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(15, 10, 15, 10));
        hBox.setSpacing(15);

        // select the default option
        choiceBox.getSelectionModel().select(0);
        Label protocolLabel = new Label("Protocol：");

        Label ipLabel = new Label("IP：");
        TextField ipField = new TextField();
        ipField.setText("127.0.0.1");

        Label portLabel = new Label("Port：");
        TextField portField = new TextField();
        portField.setText("8888");
        portField.setMaxWidth(80);

        Button connect = new Button("Connect");
        connect.setOnAction((event -> {
            // check ip
            String ip = ipField.getText();
            if (StringUtils.isEmpty(ip)) {
                new Alert(Alert.AlertType.NONE, "IP is null!", new ButtonType[]{ButtonType.CLOSE}).show();
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
                new Alert(Alert.AlertType.NONE, "Port is wrong!", new ButtonType[]{ButtonType.CLOSE}).show();
                return;
            }

            // to connect server
            client = new NettyClient(ip, port, result);
            try {
                client.start();
            } catch (Exception e) {
                new Alert(Alert.AlertType.NONE, "Connect Failed!", new ButtonType[]{ButtonType.CLOSE}).show();
                return;
            }
            // add connect info to result
            String text = "Connect to " + ip + ":" + port + " success！\n\r";
            result.appendText(text);
        }));

        Button disconnect = new Button("Disconnect");
        disconnect.setOnAction(event -> {
            String ip = ipField.getText();
            String portText = portField.getText();
            client.close();
            String text = "Disconnect from " + ip + ":" + portText + "！\n\r";
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
