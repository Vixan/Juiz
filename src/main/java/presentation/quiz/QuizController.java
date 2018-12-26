package presentation.quiz;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import shared.domain.Answer;
import shared.domain.Difficulty;
import shared.domain.Question;
import shared.domain.Quiz;
import shared.domain.User;

import java.util.List;

public class QuizController {
    public ProgressBar timeProgressBar;
    public VBox questionsContainer;
    public Label timeProgressLabel;
    private Quiz startedQuiz;
    private Difficulty selectedDifficulty;
    private User signedInUser;
    private List<CheckBox> answerCheckboxes;

    @FXML
    public void initialize() {
    }

    public void startQuiz(Quiz quiz, Difficulty difficulty, User user) {
        startedQuiz = quiz;
        selectedDifficulty = difficulty;
        signedInUser = user;

        drawQuestions();
    }

    private void drawQuestions() {
        for (Question question : startedQuiz.getQuestions()) {
            VBox questionContainer = new VBox();
            questionContainer.getStyleClass().add("quiz__question");
            Label questionNameLabel = new Label(question.getName());
            questionNameLabel.getStyleClass().add("quiz__question_name");

            questionContainer.getChildren().add(questionNameLabel);
            for (Answer answer : question.getAnswers()) {
                CheckBox answerCheckBox = new CheckBox(answer.getName());
                answerCheckBox.getStyleClass().add("quiz__question-checkbox");
                answerCheckBox.setSelected(false);
                questionContainer.getChildren().add(answerCheckBox);
            }

            questionsContainer.getChildren().add(questionContainer);
        }
    }
}
