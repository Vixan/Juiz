package persistence.abstractions;

import shared.abstractions.Initializer;

public interface PersistenceContext extends Initializer {
    QuizRepository getQuizRepository();
    CategoryRepository getCategoryRepository();
}
