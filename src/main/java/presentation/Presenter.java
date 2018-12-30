package presentation;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.services.InitializationService;

/**
 * The presentation layer is represented by the views, which are handled by
 * a JavaFX {@link Application}.<br/>
 * On application start, the database connection is initialized and
 * the authentication view is loaded.
 *
 * @see Navigator
 */
public class Presenter extends Application {
    public void start(Stage rootStage) {
        new InitializationService().initDatabaseConnection();
        Navigator navigator = Navigator.getInstance();
        navigator.showAuthentication();
    }
}
