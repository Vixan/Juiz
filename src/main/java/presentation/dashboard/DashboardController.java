package presentation.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.services.DifficultyService;
import logic.services.QuizService;
import presentation.Navigator;
import shared.domain.Difficulty;
import shared.domain.Quiz;
import shared.domain.User;

import java.util.ArrayList;
import java.util.List;

public class DashboardController {
    public VBox quizzesContainer;
    public Label titleQuizCountLabel;
    public MenuButton userButton;
    public Menu difficultySettingMenu;
    public ToggleGroup difficultyToggleGroup;
    private List<Quiz> quizzes;
    private List<Difficulty> difficulties;
    private QuizService quizService = new QuizService();
    private DifficultyService difficultyService = new DifficultyService();
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
        difficulties = difficultyService.getAll();
    }

    private Difficulty getSelectedDifficulty() {
        String selectedDifficultyName = ((RadioMenuItem) difficultyToggleGroup.getSelectedToggle()).getText();

        return difficultyService.getByName(selectedDifficultyName);
    }

    public void setUser(User user) {
        userButton.setText(user.getName());
        signedInUser = user;
    }

    public void handleSignOut() {
        Navigator.getInstance().showAuthentication();
    }

    private void handleStartQuiz(Quiz quiz) {
        Navigator.getInstance().showQuiz(quiz, getSelectedDifficulty());
    }
}
