package presentation.quiz;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import presentation.Navigator;
import shared.domain.Answer;
import shared.domain.Difficulty;
import shared.domain.Question;
import shared.domain.Quiz;
import shared.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class QuizController {
    public ProgressBar timeProgressBar;
    public VBox questionsContainer;
    public Label timeProgressLabel;
    public Button evaluateButton;
    public Button cancelButton;
    public Label titleLabel;
    private Quiz startedQuiz;
    private Difficulty selectedDifficulty;
    private User signedInUser;
    private List<CheckBox> answerCheckboxes = new ArrayList<>();
    private Timer timer = new Timer();
    private List<String> correctAnswers = new ArrayList<>();

    public void startQuiz(Quiz quiz, Difficulty difficulty, User user) {
        selectedDifficulty = difficulty;
        startedQuiz = quiz;
        startedQuiz.setTimeLimit((int) (startedQuiz.getTimeLimit() / selectedDifficulty.getModifier()));
        signedInUser = user;

        drawQuiz();
    }

    private void drawQuiz() {
        titleLabel.setText(startedQuiz.getName());
        drawQuestions();

        redrawProgress();
        Navigator.getInstance().getRootStage().setOnCloseRequest(event -> timer.cancel());
    }

    private void drawQuestions() {
        for (Question question : startedQuiz.getQuestions()) {
            VBox questionContainer = new VBox();
            questionContainer.getStyleClass().add("quiz__question");
            Label questionNameLabel = new Label(question.getName());
            questionNameLabel.getStyleClass().add("quiz__question_name");

            questionContainer.getChildren().add(questionNameLabel);
            for (Answer answer : question.getAnswers()) {
                CheckBox answerCheckbox = new CheckBox(answer.getName());
                answerCheckbox.getStyleClass().add("quiz__question-checkbox");
                answerCheckbox.setSelected(false);
                questionContainer.getChildren().add(answerCheckbox);
                answerCheckboxes.add(answerCheckbox);
                if (answer.isCorrect()) {
                    correctAnswers.add(answer.getName());
                }
            }

            questionsContainer.getChildren().add(questionContainer);
        }
    }

    private void redrawProgress() {
        timer.scheduleAtFixedRate(new TimerTask() {
            int quizTimeLimit = startedQuiz.getTimeLimit();

            @Override
            public void run() {
                if (quizTimeLimit >= 0) {
                    Platform.runLater(() -> {
                        int minutes = quizTimeLimit / 60;
                        int seconds = quizTimeLimit % 60;
                        timeProgressLabel.setText(String.format("%d:%02d", minutes, seconds));
                        double progress = (quizTimeLimit * 1.0) / startedQuiz.getTimeLimit();
                        timeProgressBar.setProgress(progress);
                        quizTimeLimit--;
                    });
                } else {
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    public void cancelQuiz() {
        timer.cancel();
        Navigator.getInstance().showDashboard();
    }

    public void evaluateGivenAnswers() {
        List<CheckBox> selectedCheckboxes = answerCheckboxes.stream()
                .filter(CheckBox::isSelected).collect(Collectors.toList());
        List<String> givenAnswers = selectedCheckboxes.stream().map(Labeled::getText).collect(Collectors.toList());

        Map<Question, Boolean> validatedQuestions = new HashMap<>();
        for (Question question : startedQuiz.getQuestions()) {
            boolean isCorrect = true;

            for (Answer answer : question.getAnswers()) {
                if (!answer.isCorrect() && givenAnswers.contains(answer.getName()) ||
                        (answer.isCorrect() && !givenAnswers.contains(answer.getName()))) {
                    isCorrect = false;
                    break;
                }
            }

            validatedQuestions.put(question, isCorrect);
        }

        System.out.println(validatedQuestions.toString());
    }
}
