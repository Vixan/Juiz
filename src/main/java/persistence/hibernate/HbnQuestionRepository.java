package persistence.hibernate;

import persistence.abstractions.QuestionRepository;
import shared.domain.Question;

public class HbnQuestionRepository extends HbnRepository<Question> implements QuestionRepository {
    public HbnQuestionRepository() {
        super(Question.class);
    }
}