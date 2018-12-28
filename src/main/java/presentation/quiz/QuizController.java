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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class QuizController {
    public ProgressBar timeProgressBar;
    public VBox questionsContainer;
    public Label timeProgressLabel;
    public Button evaluateButton;
    public Button cancelButton;
    public Label titleLabel;
    private Quiz currentQuiz;
    private Difficulty selectedDifficulty;
    private User currentUser;
    private List<CheckBox> answerCheckboxes = new ArrayList<>();
    private ScheduledExecutorService timer;

    public void init(Quiz quiz, Difficulty difficulty, User user) {
        selectedDifficulty = difficulty;
        currentUser = user;
        currentQuiz = quiz;
        currentQuiz.setTimeLimit((int) (currentQuiz.getTimeLimit() / selectedDifficulty.getModifier()));

        drawQuiz();
    }

    private void drawQuiz() {
        titleLabel.setText(currentQuiz.getName());
        drawQuestions();

        redrawProgress();
        Navigator.getInstance().getRootStage().setOnCloseRequest(event -> timer.shutdown());
    }

    private void drawQuestions() {
        for (Question question : currentQuiz.getQuestions()) {
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
            }

            questionsContainer.getChildren().add(questionContainer);
        }
    }

    private void redrawProgress() {
        TimerTask timerScheduledTask = new TimerTask() {
            int quizTimeLimit = currentQuiz.getTimeLimit();

            @Override
            public void run() {
                if (quizTimeLimit >= 0) {
                    Platform.runLater(() -> {
                        int minutes = quizTimeLimit / 60;
                        int seconds = quizTimeLimit % 60;
                        timeProgressLabel.setText(String.format("%d:%02d", minutes, seconds));
                        double progress = (quizTimeLimit * 1.0) / currentQuiz.getTimeLimit();
                        timeProgressBar.setProgress(progress);
                        quizTimeLimit--;
                    });
                } else {
                    answerCheckboxes.forEach(checkBox -> checkBox.setDisable(true));
                    evaluateGivenAnswers();
                }
            }
        };

        timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(timerScheduledTask, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void cancelQuiz() {
        timer.shutdown();
        Navigator.getInstance().showDashboard(currentUser);
    }

    public void evaluateGivenAnswers() {
        List<CheckBox> selectedCheckboxes = answerCheckboxes.stream()
                .filter(CheckBox::isSelected).collect(Collectors.toList());
        List<String> givenAnswers = selectedCheckboxes.stream().map(Labeled::getText).collect(Collectors.toList());

        Map<Question, Boolean> validatedQuestions = new HashMap<>();
        for (Question question : currentQuiz.getQuestions()) {
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

        timer.shutdown();
        Navigator.getInstance().showResults(currentQuiz, currentUser, validatedQuestions);
    }
}
