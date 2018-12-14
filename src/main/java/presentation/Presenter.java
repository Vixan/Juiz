package presentation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import shared.utils.ConfigProperties;

public class Presenter extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        ConfigProperties configProperties = ConfigProperties.getInstance();
        BorderPane rootPane = new BorderPane();

        Scene scene = new Scene(rootPane, configProperties.getWindowWidth(), configProperties.getWindowHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Juiz");
    }
}
