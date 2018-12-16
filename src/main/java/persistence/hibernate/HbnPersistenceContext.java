package persistence.hibernate;

import org.mindrot.jbcrypt.BCrypt;
import shared.domain.*;

import java.util.ArrayList;
import java.util.List;

public class HbnPersistenceContext {
    private HbnCategoryRepository categoryRepository = new HbnCategoryRepository();
    private HbnUserRepository userRepository = new HbnUserRepository();
    private HbnDifficultyRepository difficultyRepository = new HbnDifficultyRepository();
    private HbnQuizRepository quizRepository = new HbnQuizRepository();
    private HbnQuestionRepository questionRepository = new HbnQuestionRepository();
    private HbnAnswerRepository answerRepository = new HbnAnswerRepository();

    public void initializeData() {
        quizRepository.openCurrentSession();
        if (quizRepository.getAll().size() == 0) {
            List<User> initialUsers = loadInitialUsers();
            List<Category> initialCategories = loadInitialCategories();
            List<Difficulty> initialDifficulties = loadInitialDifficulties();
            List<Quiz> initialQuizzes = loadInitialQuizzes(initialCategories);
            List<Question> initialQuestions = loadInitialQuestions(initialQuizzes);
            List<Answer> initialAnswers = loadInitialAnswers(initialQuestions);
        }
        quizRepository.closeCurrentSession();
    }

    private ArrayList<User> loadInitialUsers() {
        userRepository.openCurrentSessionWithTransaction();

        String[][] initialUserData = new String[][]{{"admin", "admin"}, {"guest", "guest"}};
        ArrayList<User> initialUsers = new ArrayList<User>();

        for (String[] userData : initialUserData) {
            User user = new User();
            user.setName(userData[0]);
            String hashedPassword = BCrypt.hashpw(userData[1], BCrypt.gensalt());
            user.setPassword(hashedPassword);
            initialUsers.add(user);
            userRepository.add(user);
        }

        userRepository.closeCurrentSessionWithTransaction();

        return initialUsers;
    }

    private ArrayList<Category> loadInitialCategories() {
        categoryRepository.openCurrentSessionWithTransaction();

        String[] initialCategoryNames = new String[]{"Computer Science", "Astronomy", "Music"};
        ArrayList<Category> initialCategories = new ArrayList<Category>();

        for (String categoryName : initialCategoryNames) {
            Category category = new Category();
            category.setName(categoryName);
            initialCategories.add(category);
            categoryRepository.add(category);
        }

        categoryRepository.closeCurrentSessionWithTransaction();

        return initialCategories;
    }

    private ArrayList<Difficulty> loadInitialDifficulties() {
        difficultyRepository.openCurrentSessionWithTransaction();

        String[][] initialDifficultyData = new String[][]{{"Easy", "0.5"}, {"Medium", "1.0"}, {"High", "1.5"}};
        ArrayList<Difficulty> initialDifficulties = new ArrayList<Difficulty>();

        for (String[] difficultyData : initialDifficultyData) {
            Difficulty difficulty = new Difficulty();
            difficulty.setName(difficultyData[0]);
            difficulty.setModifier(Float.parseFloat(difficultyData[1]));
            initialDifficulties.add(difficulty);
            difficultyRepository.add(difficulty);
        }

        difficultyRepository.closeCurrentSessionWithTransaction();

        return initialDifficulties;
    }

    private ArrayList<Quiz> loadInitialQuizzes(List<Category> initialCategories) {
        quizRepository.openCurrentSessionWithTransaction();

        String[][] initialQuizData = new String[][]{
                {"Pointers in C", "A quiz to review the usage of pointers in C.", "0"},
                {"Algorithms & Structures", "Algorithms are important, so you have a test on them.", "0"},
                {"Supernovas", "Test your knowledge about some of the impressive cosmic objects.", "1"}};
        ArrayList<Quiz> initialQuizzes = new ArrayList<Quiz>();

        for (String[] quizData : initialQuizData) {
            Quiz quiz = new Quiz();
            quiz.setName(quizData[0]);
            quiz.setDescription(quizData[1]);
            quiz.setCategory(initialCategories.get(Integer.parseInt(quizData[2])));
            initialQuizzes.add(quiz);
            quizRepository.add(quiz);
        }

        quizRepository.closeCurrentSessionWithTransaction();

        return initialQuizzes;
    }

    private ArrayList<Question> loadInitialQuestions(List<Quiz> initialQuizzes) {
        questionRepository.openCurrentSessionWithTransaction();

        String[][] initialQuestionData = new String[][]{
                {"How do you represent a pointer in C?", "0"},
                {"What is a pointer?", "0"},
                {"Is the next declaration correct?\n char** characters = NULL;", "0"}};
        ArrayList<Question> initialQuestions = new ArrayList<Question>();

        for (String[] questionData : initialQuestionData) {
            Question question = new Question();
            question.setName(questionData[0]);
            question.setQuiz(initialQuizzes.get(Integer.parseInt(questionData[1])));
            initialQuestions.add(question);
            questionRepository.add(question);
        }

        questionRepository.closeCurrentSessionWithTransaction();

        return initialQuestions;
    }

    private ArrayList<Answer> loadInitialAnswers(List<Question> initialQuestions) {
        answerRepository.openCurrentSessionWithTransaction();

        String[][] initialAnswerData = new String[][]{
                {"Cannot represent a pointer in C.", "0", "false"},
                {"Using an asterisk (*) next to a type in a variable declaration.", "0", "true"},
                {"Calling the create_pointer() function;", "0", "false"},
                {"A pointer is a standard library class.", "1", "false"},
                {"A pointer is an object that stores the location in memory of another value", "1", "true"}};
        ArrayList<Answer> initialAnswers = new ArrayList<Answer>();

        for (String[] answerData : initialAnswerData) {
            Answer answer = new Answer();
            answer.setName(answerData[0]);
            answer.setQuestion(initialQuestions.get(Integer.parseInt(answerData[1])));
            answer.setCorrect(Boolean.parseBoolean(answerData[2]));
            initialAnswers.add(answer);
            answerRepository.add(answer);
        }

        answerRepository.closeCurrentSessionWithTransaction();

        return initialAnswers;
    }
}
