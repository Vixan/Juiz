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
import shared.domain.Category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The controller bound to the dashboard view.
 * Any logic regarding the view like event handling is coded in this class.
 */
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

    /**
     * Initialize the view with data and setup event handlers.
     *
     * @param user the authenticated {@link User}.
     */
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

    /**
     * Redraw the quiz list in the view,
     * ordering it by the specified property name.<br/>
     * <i>Example:</i> the list can be ordered by category or quiz name.
     *
     * @param orderByProperty the property to sort the {@link Quiz} list by.
     */
    private void handleChangeOrder(String orderByProperty) {
        if (orderByProperty.equals("Category")) {
            drawQuizzes(getSortedQuizzesByCategory());
        } else {
            drawQuizzes(getSortedQuizzesByName());
        }
    }

    /**
     * Redraw the quiz list in the view,
     * filtering the quizzes that are part of the specified category.<br/>
     * By default, quizzes from all categories are shown.
     *
     * @param filterByCategory the {@link Category} to filter the {@link Quiz} list by.
     */
    private void handleChangeFilter(String filterByCategory) {
        if (filterByCategory.equals("All")) {
            drawQuizzes(quizzes);
        } else {
            drawQuizzes(getFilteredQuizzesByCategory(filterByCategory));
        }
    }

    /**
     * Redraw the {@link Quiz} list in the view, using the specified quizzes.
     * Also, add event handlers to quiz elements.
     *
     * @param specificQuizzes the quizzes sorted or filtered in a particular order.
     * @see MouseEvent
     */
    private void drawQuizzes(List<Quiz> specificQuizzes) {
        titleQuizCountLabel.setText(specificQuizzes.size() + " available");
        quizzesContainer.getChildren().clear();

        for (Quiz quiz : specificQuizzes) {
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

    /**
     * Retrieve a list of sorted quizzes by {@link Category}.
     *
     * @return the list of sorted quizzes by category.
     * @see Comparator
     */
    private List<Quiz> getSortedQuizzesByCategory() {
        List<Quiz> sortedQuizzes = quizzes;
        sortedQuizzes.sort(Comparator.comparing((Quiz quiz) -> quiz.getCategory().getId()));

        return sortedQuizzes;
    }

    /**
     * Retrieve a list of sorted quizzes by name.
     *
     * @return the list of sorted quizzes by name.
     * @see Comparator
     */
    private List<Quiz> getSortedQuizzesByName() {
        List<Quiz> sortedQuizzes = quizzes;
        sortedQuizzes.sort(Comparator.comparing(Quiz::getName));

        return sortedQuizzes;
    }

    /**
     * Retrieve a list of filtered quizzes by category name.
     *
     * @param categoryName the category name to filter the {@link Quiz} list by.
     * @return the list of filtered quizzes by category name.
     */
    private List<Quiz> getFilteredQuizzesByCategory(String categoryName) {
        return quizzes.stream()
                .filter(quiz -> quiz.getCategory().getName().equals(categoryName))
                .collect(Collectors.toList());
    }

    /**
     * Draw the {@link Difficulty} list elements in the view.<br/>
     * Each difficulty represents a selectable item in a menu and has it's
     * own difficulty modifier that affects the way the {@link User}
     * takes the {@link Quiz}.<br/>
     * On view load, the first difficulty level is preselected.
     *
     * @see RadioMenuItem
     */
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

    /**
     * Retrieve the selected {@link Difficulty} level from the view difficulty
     * menu.
     *
     * @return the selected difficulty level.
     */
    private Difficulty getSelectedDifficulty() {
        String selectedDifficultyName = ((RadioMenuItem) difficultyToggleGroup.getSelectedToggle()).getText();

        return difficultyService.getByName(selectedDifficultyName);
    }

    /**
     * Draw the {@link Category} names in the filter menu options in the view.
     */
    private void drawCategoriesFilter() {
        Set<String> categoryNames = quizzes.stream()
                .map(quiz -> quiz.getCategory().getName())
                .collect(Collectors.toSet());

        filterButton.getItems().add("All");
        filterButton.getItems().addAll(categoryNames);
    }

    /**
     * Sign out the {@link User} and load the authentication view.
     *
     * @see Navigator
     */
    public void handleSignOut() {
        Navigator.getInstance().showAuthentication();
    }

    /**
     * Start the specified quiz and load the {@link Quiz} view.
     *
     * @param quiz the quiz to start.
     */
    private void handleStartQuiz(Quiz quiz) {
        Navigator.getInstance().showQuiz(quiz, getSelectedDifficulty(), currentUser);
    }
}
