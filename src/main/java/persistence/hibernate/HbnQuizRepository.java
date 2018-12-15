package persistence.hibernate;

import persistence.abstractions.QuizRepository;
import shared.domain.Quiz;

public class HbnQuizRepository extends HbnRepository<Quiz> implements QuizRepository {
    public HbnQuizRepository() {
        super(Quiz.class);
    }
}
