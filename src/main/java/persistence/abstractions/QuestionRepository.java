package persistence.abstractions;

import shared.domain.Question;

import javax.persistence.Entity;

/**
 * The Question {@link Entity} repository interface with custom behaviour.
 *
 * @see Repository
 * @see Question
 */
public interface QuestionRepository extends Repository<Question> {
}
