package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import shared.utils.ConfigProperties;

import java.io.IOException;

public class Presenter extends Application {
    private Pane rootPane = new GridPane();
    private ConfigProperties configProperties = ConfigProperties.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        initStage(primaryStage);
        showAuthentication();
    }

    private void initStage(Stage primaryStage) {
        int windowWidth = 800;
        int windowHeight = 600;
        String windowTitle = "Juiz";

        if (configProperties != null) {
            windowTitle = configProperties.getWindowTitle();
            windowWidth = configProperties.getWindowWidth();
            windowHeight = configProperties.getWindowHeight();
        }

//        Scene scene = new Scene(rootPane, windowWidth, windowHeight);
//        primaryStage.setTitle(windowTitle);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    private void showAuthentication() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/presentation/auth/views/auth.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("New Window");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
