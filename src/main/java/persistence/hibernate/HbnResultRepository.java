package persistence.hibernate;

import persistence.abstractions.ResultRepository;
import shared.domain.Result;
import shared.domain.User;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

public class HbnResultRepository extends HbnRepository<Result> implements ResultRepository {
    public HbnResultRepository() {
        super(Result.class);
    }

    public List<Result> getByUserId(Integer userId) {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Result> criteriaQuery = criteriaBuilder.createQuery(Result.class);

        Root<Result> root = criteriaQuery.from(Result.class);
        criteriaQuery.select(root);

        ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("user_id"), params));

        TypedQuery<Result> typedQuery = getCurrentSession().createQuery(criteriaQuery);
        typedQuery.setParameter(params, userId.toString());

        return typedQuery.getResultList();
    }
}
