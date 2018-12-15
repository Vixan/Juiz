package persistence.hibernate;

import persistence.abstractions.AnswerRepository;
import shared.domain.Answer;

public class HbnAnswerRepository extends HbnRepository<Answer> implements AnswerRepository {
    public HbnAnswerRepository() {
        super(Answer.class);
    }
}