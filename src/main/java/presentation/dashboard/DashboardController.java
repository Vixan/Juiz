package presentation.dashboard;

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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DashboardController {
    public VBox quizzesContainer;
    public Label titleQuizCountLabel;
    public MenuButton userButton;
    public Menu difficultySettingMenu;
    public ToggleGroup difficultyToggleGroup;
    public ChoiceBox<String> sortButton;
    public ChoiceBox<String> filterButton;
    private List<Quiz> quizzes;
    private List<Difficulty> difficulties;
    private QuizService quizService = new QuizService();
    private DifficultyService difficultyService = new DifficultyService();
    private User currentUser;

    public void init(User user) {
        currentUser = user;

        userButton.setText(currentUser.getName());
        quizzes = quizService.getAll();
        difficulties = difficultyService.getAll();

        drawDifficulties();
        drawQuizzes(getSortedQuizzesByCategory());
        drawCategoriesFilter();

        sortButton.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> handleChangeOrder(newValue));
        filterButton.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> handleChangeFilter(newValue));
    }

    private void handleChangeOrder(String orderByProperty) {
        if (orderByProperty.equals("Category")) {
            drawQuizzes(getSortedQuizzesByCategory());
        } else {
            drawQuizzes(getSortedQuizzesByName());
        }
    }

    private void handleChangeFilter(String filterByCategory) {
        if (filterByCategory.equals("All")) {
            drawQuizzes(quizzes);
        } else {
            drawQuizzes(getFilteredQuizzesByCategory(filterByCategory));
        }
    }

    private void drawQuizzes(List<Quiz> sortedQuizzes) {
        titleQuizCountLabel.setText(sortedQuizzes.size() + " available");
        quizzesContainer.getChildren().clear();

        for (Quiz quiz : sortedQuizzes) {
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

    private List<Quiz> getSortedQuizzesByCategory() {
        List<Quiz> sortedQuizzes = quizzes;
        sortedQuizzes.sort(Comparator.comparing((Quiz quiz) -> quiz.getCategory().getId()));

        return sortedQuizzes;
    }

    private List<Quiz> getSortedQuizzesByName() {
        List<Quiz> sortedQuizzes = quizzes;
        sortedQuizzes.sort(Comparator.comparing(Quiz::getName));

        return sortedQuizzes;
    }

    private List<Quiz> getFilteredQuizzesByCategory(String categoryName) {
        return quizzes.stream()
                .filter(quiz -> quiz.getCategory().getName().equals(categoryName))
                .collect(Collectors.toList());
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

    private Difficulty getSelectedDifficulty() {
        String selectedDifficultyName = ((RadioMenuItem) difficultyToggleGroup.getSelectedToggle()).getText();

        return difficultyService.getByName(selectedDifficultyName);
    }

    private void drawCategoriesFilter() {
        Set<String> categoryNames = quizzes.stream()
                .map(quiz -> quiz.getCategory().getName())
                .collect(Collectors.toSet());

        filterButton.getItems().add("All");
        filterButton.getItems().addAll(categoryNames);
    }

    public void handleSignOut() {
        Navigator.getInstance().showAuthentication();
    }

    private void handleStartQuiz(Quiz quiz) {
        Navigator.getInstance().showQuiz(quiz, getSelectedDifficulty(), currentUser);
    }
}
