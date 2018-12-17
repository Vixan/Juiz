package persistence.hibernate;

import persistence.abstractions.DifficultyRepository;
import shared.domain.Difficulty;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

public class HbnDifficultyRepository extends HbnRepository<Difficulty> implements DifficultyRepository {
    public HbnDifficultyRepository() {
        super(Difficulty.class);
    }

    public Difficulty getByName(String userName) {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Difficulty> criteriaQuery = criteriaBuilder.createQuery(Difficulty.class);

        Root<Difficulty> root = criteriaQuery.from(Difficulty.class);
        criteriaQuery.select(root);

        ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), params));

        TypedQuery<Difficulty> typedQuery = getCurrentSession().createQuery(criteriaQuery);
        typedQuery.setParameter(params, userName);

        List<Difficulty> searchedDifficulties = typedQuery.getResultList();
        Difficulty difficulty = null;

        if (searchedDifficulties.size() > 0) {
            difficulty = searchedDifficulties.get(0);
        }

        return difficulty;
    }
}