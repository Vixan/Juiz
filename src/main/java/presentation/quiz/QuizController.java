package presentation.quiz;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import shared.domain.Difficulty;
import shared.domain.Quiz;

import java.util.List;

public class QuizController {
    public ProgressBar timeProgressBar;
    public VBox questionsContainer;
    public Label timeProgressLabel;
    private Quiz startedQuiz;
    private Difficulty selectedDifficulty;
    private List<CheckBox> answerCheckboxes;

    @FXML
    public void initialize() {
    }

    public void setQuiz(Quiz quiz) {
        startedQuiz = quiz;
    }

    public void setDifficulty(Difficulty difficulty) {
        selectedDifficulty = difficulty;
    }
}
