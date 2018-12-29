package persistence.hibernate;

import persistence.abstractions.QuestionRepository;
import shared.domain.Question;

/**
 * The Hibernate {@link Question} repository that implements the
 * {@link QuestionRepository} methods.
 */
class HbnQuestionRepository extends HbnRepository<Question> implements QuestionRepository {
    /**
     * Construct a {@link HbnRepository} for the {@link Question} entity.
     */
    HbnQuestionRepository() {
        super(Question.class);
    }
}