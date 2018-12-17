package persistence.hibernate;

import persistence.abstractions.QuizRepository;
import shared.domain.Quiz;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

public class HbnQuizRepository extends HbnRepository<Quiz> implements QuizRepository {
    public HbnQuizRepository() {
        super(Quiz.class);
    }

    public Quiz getByName(String quizName) {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Quiz> criteriaQuery = criteriaBuilder.createQuery(Quiz.class);

        Root<Quiz> root = criteriaQuery.from(Quiz.class);
        criteriaQuery.select(root);

        ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), params));

        TypedQuery<Quiz> typedQuery = getCurrentSession().createQuery(criteriaQuery);
        typedQuery.setParameter(params, quizName);

        List<Quiz> searchedQuizzes = typedQuery.getResultList();
        Quiz quiz = null;

        if (searchedQuizzes.size() > 0) {
            quiz = searchedQuizzes.get(0);
        }

        return quiz;
    }
}
