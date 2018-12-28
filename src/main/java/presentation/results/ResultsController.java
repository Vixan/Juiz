package presentation.results;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import presentation.Navigator;
import shared.domain.Question;
import shared.domain.User;
import shared.domain.Quiz;

import java.util.Map;

public class ResultsController {
    public Label quizTitleLabel;
    public VBox validatedQuestionsContainer;
    public Button returnButton;
    public Label scoreLabel;
    private Quiz currentQuiz;
    private User currentUser;
    private Map<Question, Boolean> validatedQuestions;

    public void init(Quiz quiz, User user, Map<Question, Boolean> results) {
        currentQuiz = quiz;
        currentUser = user;
        validatedQuestions = results;

        drawResults();
    }

    private void drawValidatedQuestions() {
        for (Question question : validatedQuestions.keySet()) {
            HBox questionContainer = new HBox();
            questionContainer.getStyleClass().add("results__question");

            HBox questionNameContainer = new HBox();
            questionNameContainer.getStyleClass().add("results__question-content");
            HBox.setHgrow(questionNameContainer, Priority.ALWAYS);

            Label questionNameLabel = new Label(question.getName());
            questionNameLabel.getStyleClass().add("results__question-name");

            boolean isCorrect = validatedQuestions.get(question);
            Label questionValidation = new Label(isCorrect ? "Correct" : "Incorrect");
            questionValidation.getStyleClass().addAll("results__question-validation",
                    isCorrect ? "results__question-validation--correct" : "results__question-validation--incorrect");

            questionNameContainer.getChildren().add(questionNameLabel);
            questionContainer.getChildren().addAll(questionNameContainer, questionValidation);
            validatedQuestionsContainer.getChildren().add(questionContainer);
        }
    }

    private void drawResults() {
        quizTitleLabel.setText(currentQuiz.getName());
        int correctQuestionsCount = validatedQuestions.values().stream()
                .filter(validation -> validation).toArray().length;
        scoreLabel.setText("Your score is " + correctQuestionsCount + " points");

        drawValidatedQuestions();
    }

    public void returnToQuizzes() {
        Navigator.getInstance().showDashboard(currentUser);
    }
}
