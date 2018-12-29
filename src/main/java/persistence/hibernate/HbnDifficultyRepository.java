package persistence.hibernate;

import persistence.abstractions.DifficultyRepository;
import shared.domain.Difficulty;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The Hibernate {@link Difficulty} repository that implements the
 * {@link DifficultyRepository} methods.
 */
public class HbnDifficultyRepository extends HbnRepository<Difficulty> implements DifficultyRepository {
    /**
     * Construct a {@link HbnRepository} for the {@link Difficulty} entity.
     */
    public HbnDifficultyRepository() {
        super(Difficulty.class);
    }

    public Difficulty getByName(String difficultyName) {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Difficulty> criteriaQuery = criteriaBuilder.createQuery(Difficulty.class);

        Root<Difficulty> root = criteriaQuery.from(Difficulty.class);
        criteriaQuery.select(root);

        ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), params));

        TypedQuery<Difficulty> typedQuery = getCurrentSession().createQuery(criteriaQuery);
        typedQuery.setParameter(params, difficultyName);

        List<Difficulty> searchedDifficulties = typedQuery.getResultList();
        Difficulty difficulty = null;

        if (searchedDifficulties.size() > 0) {
            difficulty = searchedDifficulties.get(0);
        }

        return difficulty;
    }
}