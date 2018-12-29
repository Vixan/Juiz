package persistence.abstractions;

import shared.domain.Answer;

import javax.persistence.Entity;

/**
 * The Answer {@link Entity} repository interface with custom behaviour.
 *
 * @see Repository
 * @see Answer
 */
public interface AnswerRepository extends Repository<Answer> {
}
