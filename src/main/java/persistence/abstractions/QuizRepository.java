package persistence.abstractions;

import shared.domain.Quiz;

public interface QuizRepository extends Repository<Quiz> {
    Quiz getByName(String quizName);
}
