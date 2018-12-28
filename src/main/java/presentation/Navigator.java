package presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import presentation.quiz.QuizController;
import presentation.results.ResultsController;
import shared.domain.*;
import shared.utils.ConfigProperties;

import java.io.IOException;
import java.util.Map;

public class Navigator {
    private static final String ICON_PATH = "/presentation/assets/juiz.icon.png";
    private static final String AUTHENTICATION_VIEW_PATH = "/presentation/auth/auth.fxml";
    private static final String DASHBOARD_VIEW_PATH = "/presentation/dashboard/dashboard.fxml";
    private static final String QUIZ_VIEW_PATH = "/presentation/quiz/quiz.fxml";
    private static final String RESULTS_VIEW_PATH = "/presentation/results/results.fxml";

    private ConfigProperties configProperties = ConfigProperties.getInstance();
    private static Navigator instance = null;
    private Stage rootStage = null;

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
            instance.rootStage = new Stage();
            instance.initStage();
        }

        return instance;
    }

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

    public void showDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(instance.getClass().getResource(DASHBOARD_VIEW_PATH));
            Scene scene = new Scene(fxmlLoader.load());

            instance.rootStage.setTitle("Juiz Dashboard");
            instance.rootStage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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

    public Stage getRootStage() {
        return rootStage;
    }
}
