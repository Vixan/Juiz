package presentation;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.services.InitializationService;

public class Presenter extends Application {
    public void start(Stage rootStage) {
        new InitializationService().initDatabaseConnection();
        Navigator navigator = Navigator.getInstance();
        navigator.showAuthentication();
    }
}
