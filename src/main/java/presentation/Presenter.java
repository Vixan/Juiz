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
        initStage(primaryStage);
    }

    private void initStage(Stage primaryStage) {
        BorderPane rootPane = new BorderPane();
        ConfigProperties configProps = ConfigProperties.getInstance();
        Scene scene = new Scene(rootPane,
                configProps.getWindowWidth(),
                configProps.getWindowHeight());

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Juiz");
    }
}
