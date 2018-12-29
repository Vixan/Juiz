package persistence.hibernate;

import persistence.abstractions.AnswerRepository;
import shared.domain.Answer;

/**
 * The Hibernate {@link Answer} repository that implements the
 * {@link AnswerRepository} methods.
 */
class HbnAnswerRepository extends HbnRepository<Answer> implements AnswerRepository {
    /**
     * Construct a {@link HbnRepository} for the {@link Answer} entity.
     */
    HbnAnswerRepository() {
        super(Answer.class);
    }
}