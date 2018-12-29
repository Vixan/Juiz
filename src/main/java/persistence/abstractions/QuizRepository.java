package persistence.abstractions;

import shared.domain.Quiz;

import javax.persistence.Entity;

/**
 * The Quiz {@link Entity} repository interface with custom behaviour.
 *
 * @see Repository
 * @see Quiz
 */
public interface QuizRepository extends Repository<Quiz> {
    /**
     * @param quizName the name to retrieve the quiz by.
     * @return the quiz queried.
     */
    Quiz getByName(String quizName);
}
