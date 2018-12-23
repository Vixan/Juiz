package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        showAuthentication();
    }

    private void showAuthentication() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/presentation/auth/auth.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            int windowWidth = 800;
            int windowHeight = 600;

            if (configProperties != null) {
                windowWidth = configProperties.getWindowWidth();
                windowHeight = configProperties.getWindowHeight();
            }

            Stage stage = new Stage();
            stage.setTitle("Juiz Authentication");
            stage.setScene(scene);
            stage.setMinHeight(windowHeight);
            stage.setMinWidth(windowWidth);
            stage.getIcons().add(new Image("/presentation/assets/juiz.icon.png"));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
