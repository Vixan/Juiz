package presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import presentation.dashboard.DashboardController;
import presentation.quiz.QuizController;
import presentation.results.ResultsController;
import shared.domain.*;
import shared.utils.ConfigProperties;

import java.io.IOException;
import java.util.Map;

/**
 * The singleton in charge of view loading.
 * All views are displayed using this class.
 *
 * @see ConfigProperties
 */
public class Navigator {
    private static final String ICON_PATH = "/presentation/assets/juiz.icon.png";
    private static final String AUTHENTICATION_VIEW_PATH = "/presentation/auth/auth.fxml";
    private static final String DASHBOARD_VIEW_PATH = "/presentation/dashboard/dashboard.fxml";
    private static final String QUIZ_VIEW_PATH = "/presentation/quiz/quiz.fxml";
    private static final String RESULTS_VIEW_PATH = "/presentation/results/results.fxml";

    private ConfigProperties configProperties = ConfigProperties.getInstance();
    private static Navigator instance = null;
    private Stage rootStage = null;

    /**
     * Retrieve the {@link Navigator} singleton instance.
     * If it does not exist, a new one will be created.
     *
     * @return the navigator instance.
     * @see Stage
     */
    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
            instance.rootStage = new Stage();
            instance.initStage();
        }

        return instance;
    }

    /**
     * Initialize the JavaFX {@link Stage} with the
     * default {@link ConfigProperties} and display the empty window.
     * The main views will be loaded in the same root stage, such that no new
     * windows will be created.
     *
     * @see Stage
     * @see Navigator
     */
    private void initStage() {
        int windowWidth = 800;
        int windowHeight = 600;

        if (instance.configProperties != null) {
            windowWidth = instance.configProperties.getWindowWidth();
            windowHeight = instance.configProperties.getWindowHeight();
        }

        instance.rootStage.setTitle("Juiz");
        instance.rootStage.setMinHeight(windowHeight);
        instance.rootStage.setMinWidth(windowWidth);
        instance.rootStage.setHeight(windowHeight);
        instance.rootStage.setWidth(windowWidth);
        instance.rootStage.getIcons().add(new Image(ICON_PATH));
        instance.rootStage.show();
    }

    /**
     * Load the FXML authentication view from the .fxml file using the JavaFX {@link FXMLLoader}.
     *
     * @see Scene
     * @see Navigator
     */
    public void showAuthentication() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(instance.getClass().getResource(AUTHENTICATION_VIEW_PATH));
            Scene scene = new Scene(fxmlLoader.load());

            instance.rootStage.setTitle("Juiz Authentication");
            instance.rootStage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load the FXML dashboard view from the .fxml file using the JavaFX {@link FXMLLoader}.
     *
     * @param user the authenticated {@link User}.
     * @see Scene
     * @see Navigator
     */
    public void showDashboard(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(instance.getClass().getResource(DASHBOARD_VIEW_PATH));
            Scene scene = new Scene(fxmlLoader.load());

            DashboardController dashboardController = fxmlLoader.getController();
            dashboardController.init(user);

            instance.rootStage.setTitle("Juiz Dashboard");
            instance.rootStage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load the FXML quiz view from the .fxml file using the JavaFX {@link FXMLLoader}.
     *
     * @param quiz       the started {@link Quiz}.
     * @param difficulty the selected {@link Difficulty} level.
     * @param user       the authenticated {@link User}.
     */
    public void showQuiz(Quiz quiz, Difficulty difficulty, User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(QUIZ_VIEW_PATH));
            Scene scene = new Scene(fxmlLoader.load());

            QuizController quizController = fxmlLoader.getController();
            quizController.init(quiz, difficulty, user);

            instance.rootStage.setTitle("Juiz Quiz");
            instance.rootStage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load the FXML quiz results view from the .fxml file using the JavaFX {@link FXMLLoader}.
     *
     * @param quiz    the taken {@link Quiz}.
     * @param user    the authenticated {@link User}.
     * @param results the results for each {@link Question}.
     * @see ResultsController
     * @see Scene
     */
    public void showResults(Quiz quiz, User user, Map<Question, Boolean> results) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(RESULTS_VIEW_PATH));
            Scene scene = new Scene(fxmlLoader.load());

            ResultsController resultsController = fxmlLoader.getController();
            resultsController.init(quiz, user, results);

            instance.rootStage.setTitle("Juiz Quiz Results");
            instance.rootStage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieve the root {@link Stage} of the application, where
     * the main views are loaded.
     *
     * @return the root stage of the application.
     */
    public Stage getRootStage() {
        return rootStage;
    }
}
