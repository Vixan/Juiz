package presentation.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.services.CategoryService;
import logic.services.QuizService;
import shared.domain.Category;
import shared.domain.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {
    public VBox quizzesContainer;
    private List<Quiz> quizzes;
    private QuizService quizService = new QuizService();

    @FXML
    public void initialize() {
        getQuizzes();
        drawQuizzes();
    }

    private void getQuizzes() {
        this.quizzes = quizService.getAll();
    }

    private void drawQuizzes() {
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
            quizzesContainer.getChildren().add(quizContainer);
        }
    }
}
