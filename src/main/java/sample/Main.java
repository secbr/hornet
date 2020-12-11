package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        root.getStyleClass().add(getClass().getResource("sample.fxml").toExternalForm());
        Label label = new Label("公众号：程序新视界");
        label.setStyle("-fx-label-padding: 0 100px");
        root.setBottom(label);

        Button button = new Button("连接");
        button.setStyle("-fx-end-margin: 100px");
        button.setOnAction(event -> {
            String text = button.getText();
            if ("连接".equals(text)) {
                button.setText("断开");
            } else {
                button.setText("连接");
            }

        });
        root.setCenter(button);
//        root.getCenter().setStyle("");

        root.setRight(new Label("Right"));
        root.setLeft(new Label("Left"));

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(createAboutMenu());
        root.setTop(menuBar);

        primaryStage.setTitle("Hornet");
        Scene scene = new Scene(root, 600, 550);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 关于菜单
     */
    private Menu createAboutMenu() {
        Menu fileMenu = new Menu("About");
        fileMenu.getItems().addAll(aboutAuthor(), exitMenuItem());
        return fileMenu;
    }

    private MenuItem aboutAuthor() {
        return new MenuItem("About Author");
    }

    /**
     * 退出按钮
     */
    private MenuItem exitMenuItem() {
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        return exitMenuItem;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
