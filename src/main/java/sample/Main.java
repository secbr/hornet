package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        root.getStyleClass().add(getClass().getResource("sample.fxml").toExternalForm());
        Label label = new Label("公众号：程序新视界");
        root.setBottom(label);

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
