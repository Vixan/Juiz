package presentation.dashboard;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.services.DifficultyService;
import logic.services.QuizService;
import shared.domain.Difficulty;
import shared.domain.Quiz;
import shared.domain.User;
import shared.utils.ConfigProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    public VBox quizzesContainer;
    public Label titleQuizCountLabel;
    public MenuButton userButton;
    public Menu difficultySettingMenu;
    private List<Quiz> quizzes;
    private List<Difficulty> difficulties;
    private ConfigProperties configProperties = ConfigProperties.getInstance();
    private QuizService quizService = new QuizService();
    private User signedInUser;

    @FXML
    public void initialize() {
        initDifficulties();
        initQuizzes();
    }

    private void initQuizzes() {
        getQuizzes();
        drawQuizzes();
    }

    private void getQuizzes() {
        this.quizzes = quizService.getAll();
    }

    private void drawQuizzes() {
        titleQuizCountLabel.setText(quizzes.size() + " available");

        for (Quiz quiz : quizzes) {
            HBox quizContainer = new HBox();
            quizContainer.getStyleClass().add("dashboard__quiz");

            Label quizCategoryLabel = new Label(quiz.getCategory().getName());
            String categoryClass = "category-" + quiz.getCategory()
                    .getName().toLowerCase().replaceAll("\\s+", "-");
            quizCategoryLabel.getStyleClass().addAll("dashboard__quiz__category", categoryClass);

            Label quizNameLabel = new Label(quiz.getName());
            quizNameLabel.getStyleClass().add("dashboard__quiz__name");
            quizNameLabel.setWrapText(true);

            Label quizQuestionsLabel = new Label(quiz.getQuestions().size() + " questions");
            quizQuestionsLabel.getStyleClass().add("dashboard__quiz__questions");

            quizContainer.getChildren().addAll(quizCategoryLabel, quizNameLabel, quizQuestionsLabel);
            quizContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleStartQuiz(quiz));
            quizzesContainer.getChildren().add(quizContainer);
        }
    }

    private void initDifficulties() {
        getDifficulties();
        drawDifficulties();
    }

    private void drawDifficulties() {
        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        List<RadioMenuItem> difficultyOptions = new ArrayList<>();

        for (Difficulty difficulty : difficulties) {
            RadioMenuItem difficultyOption = new RadioMenuItem();
            difficultyOption.setText(difficulty.getName());
            difficultyOption.setToggleGroup(difficultyToggleGroup);
            difficultyOptions.add(difficultyOption);
        }

        difficultyOptions.get(0).setSelected(true);
        difficultySettingMenu.getItems().addAll(difficultyOptions);
    }

    private void getDifficulties() {
        DifficultyService difficultyService = new DifficultyService();
        difficulties = difficultyService.getAll();
    }

    public void setUser(User user) {
        userButton.setText(user.getName());
        signedInUser = user;
    }

    public void handleSignOut() {
        Stage stage = (Stage) userButton.getScene().getWindow();
        stage.close();
        showAuthentication();
    }

    private void handleStartQuiz(Quiz quiz) {
        System.out.println(quiz.getName());
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
