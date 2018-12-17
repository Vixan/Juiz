package presentation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logic.services.UserService;
import shared.domain.User;
import shared.utils.ConfigProperties;

public class Presenter extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        initStage(primaryStage);
    }

    private void initStage(Stage primaryStage) {
        int windowWidth = 800;
        int windowHeight = 600;
        String windowTitle = "Juiz";
        BorderPane rootPane = new BorderPane();
        ConfigProperties configProps = ConfigProperties.getInstance();

        if (configProps != null) {
            windowTitle = configProps.getWindowTitle();
            windowWidth = configProps.getWindowWidth();
            windowHeight = configProps.getWindowHeight();
        }

        Scene scene = new Scene(rootPane, windowWidth, windowHeight);
        primaryStage.setTitle(windowTitle);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
