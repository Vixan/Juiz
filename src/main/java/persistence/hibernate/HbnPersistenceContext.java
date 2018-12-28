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

        ArrayList<User> initialUsers = new ArrayList<>();
        String[][] initialUserData = new String[][]{{
                "admin",
                "admin"
        }, {
                "guest",
                "guest"
        }, {
                "juiz",
                "Java8"
        }};

        for (String[] userData : initialUserData) {
            User user = new User();
            String userName = userData[0];
            String userPassword = userData[1];
            String hashedUserPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());

            user.setName(userName);
            user.setPassword(hashedUserPassword);

            initialUsers.add(user);
            userRepository.add(user);
        }

        userRepository.closeCurrentSessionWithTransaction();

        return initialUsers;
    }

    private ArrayList<Category> loadInitialCategories() {
        categoryRepository.openCurrentSessionWithTransaction();

        ArrayList<Category> initialCategories = new ArrayList<>();
        String[] initialCategoryNames = new String[]{
                "Computer Science",
                "Astronomy",
                "Music",
                "Art"
        };

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

        ArrayList<Difficulty> initialDifficulties = new ArrayList<>();
        String[][] initialDifficultyData = new String[][]{{
                "Easy",
                "0.5"
        }, {
                "Normal",
                "1.0"
        }, {
                "Difficult",
                "1.5"
        }};

        for (String[] difficultyData : initialDifficultyData) {
            Difficulty difficulty = new Difficulty();
            String difficultyName = difficultyData[0];
            Float difficultyModifier = Float.parseFloat(difficultyData[1]);

            difficulty.setName(difficultyName);
            difficulty.setModifier(difficultyModifier);

            initialDifficulties.add(difficulty);
            difficultyRepository.add(difficulty);
        }

        difficultyRepository.closeCurrentSessionWithTransaction();

        return initialDifficulties;
    }

    private ArrayList<Quiz> loadInitialQuizzes(List<Category> initialCategories) {
        quizRepository.openCurrentSessionWithTransaction();

        ArrayList<Quiz> initialQuizzes = new ArrayList<>();
        String[][] initialQuizData = new String[][]{{
                "Pointers in C",
                "A quiz to review the usage of pointers in C.",
                "0",
                "30"
        }, {
                "Algorithms & Structures",
                "Algorithms are important, so you have a test on them.",
                "0",
                "120"
        }, {
                "Supernovas",
                "Test your knowledge about some of the impressive cosmic objects.",
                "1",
                "360"
        }, {
                "The uniqueness of Jupiter",
                "A short quiz on the largest planet in the solar system.",
                "1",
                "220"
        }, {
                "The greatest classical music composers",
                "Check whether you know some of the greatest classic music creators",
                "2",
                "280"
        }, {
                "The art of colors",
                "Colors make up a lot of our life. Let's learn something new about them.",
                "3",
                "170"
        }};

        for (String[] quizData : initialQuizData) {
            Quiz quiz = new Quiz();
            String quizName = quizData[0];
            String quizDescription = quizData[1];
            int quizCategoryId = Integer.parseInt(quizData[2]);
            int quizTimeLimit = Integer.parseInt(quizData[3]);

            quiz.setName(quizName);
            quiz.setDescription(quizDescription);
            quiz.setCategory(initialCategories.get(quizCategoryId));
            quiz.setTimeLimit(quizTimeLimit);

            initialQuizzes.add(quiz);
            quizRepository.add(quiz);
        }

        quizRepository.closeCurrentSessionWithTransaction();

        return initialQuizzes;
    }

    private ArrayList<Question> loadInitialQuestions(List<Quiz> initialQuizzes) {
        questionRepository.openCurrentSessionWithTransaction();

        ArrayList<Question> initialQuestions = new ArrayList<>();
        String[][] initialQuestionData = new String[][]{{
                "How do you represent a pointer in C?",
                "0"
        }, {
                "What is a pointer?",
                "0"
        }, {
                "Is the below declaration correct?\n char** characters = NULL;",
                "0"
        }, {
                "What is a nova?",
                "2"
        }, {
                "What is the difference between novas and supernovae?",
                "2"
        }, {
                "When was the term \"supernova\" coined by Walter Baade and Fritz Zwicky?",
                "2"
        }, {
                "Are supernovae strong galactic sources of gravitational waves?",
                "2"
        }, {
                "What is Beethoven's full name?",
                "4"
        }, {
                "When did Frederic Chopin live?",
                "4"
        }};

        for (String[] questionData : initialQuestionData) {
            Question question = new Question();
            String questionName = questionData[0];
            int questionQuizId = Integer.parseInt(questionData[1]);

            question.setName(questionName);
            question.setQuiz(initialQuizzes.get(questionQuizId));

            initialQuestions.add(question);
            questionRepository.add(question);
        }

        questionRepository.closeCurrentSessionWithTransaction();

        return initialQuestions;
    }

    private ArrayList<Answer> loadInitialAnswers(List<Question> initialQuestions) {
        answerRepository.openCurrentSessionWithTransaction();

        ArrayList<Answer> initialAnswers = new ArrayList<>();
        String[][] initialAnswerData = new String[][]{{
                "Cannot represent a pointer in C.",
                "0",
                "false"
        }, {
                "Using an asterisk (*) next to a type in a variable declaration.",
                "0",
                "true"
        }, {
                "Calling the create_pointer() function.",
                "0",
                "false"
        }, {
                "A pointer is a standard library class.",
                "1",
                "false"
        }, {
                "A pointer is an object that stores the location in memory of another value.",
                "1",
                "true"
        }, {
                "Yes, it is correct.",
                "2",
                "true"
        }, {
                "No, it should be:\n char** characters = \"\0\";.",
                "2",
                "false"
        }, {
                "A permanent cosmic object.",
                "3",
                "false"
        }, {
                "An explosion of a star, followed by the expelling of star material and energy.",
                "3",
                "true"
        }, {
                "An event that occurs upon the death of a certain types of stars.",
                "3",
                "true"
        }, {
                "The expanding shock waves from Supernovae can trigger the formation of new stars.",
                "4",
                "true"
        }, {
                "Supernovae are larger than novas.",
                "4",
                "false",
        }, {
                "Supernovae happen when giant stars collapse.",
                "4",
                "false"
        }, {
                "In 1931",
                "5",
                "true"
        }, {
                "In 1904",
                "5",
                "false"
        }, {
                "It was not coined by Walter Baade and Fritz Zwicky.",
                "5",
                "false"
        }, {
                "No, they are strong sources of light only.",
                "6",
                "false"
        }, {
                "Yes.",
                "6",
                "true"
        }, {
                "Ludwig van Beethoven",
                "7",
                "true"
        }, {
                "Johann von Beethoven",
                "7",
                "false"
        }, {
                "1801-1869",
                "8",
                "false"
        }, {
                "1810-1849",
                "8",
                "true"
        }};

        for (String[] answerData : initialAnswerData) {
            Answer answer = new Answer();
            String answerName = answerData[0];
            int answerQuestionId = Integer.parseInt(answerData[1]);
            boolean answerIsCorrect = Boolean.parseBoolean(answerData[2]);

            answer.setName(answerName);
            answer.setQuestion(initialQuestions.get(answerQuestionId));
            answer.setCorrect(answerIsCorrect);

            initialAnswers.add(answer);
            answerRepository.add(answer);
        }

        answerRepository.closeCurrentSessionWithTransaction();

        return initialAnswers;
    }
}
