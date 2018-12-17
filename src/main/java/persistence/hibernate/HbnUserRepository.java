package persistence.hibernate;

import persistence.abstractions.UserRepository;
import shared.domain.User;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

public class HbnUserRepository extends HbnRepository<User> implements UserRepository {
    public HbnUserRepository() {
        super(User.class);
    }

    public User getByName(String userName) {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), params));

        TypedQuery<User> typedQuery = getCurrentSession().createQuery(criteriaQuery);
        typedQuery.setParameter(params, userName);

        List<User> usersWithSearchedName = typedQuery.getResultList();
        User user = null;

        if (usersWithSearchedName.size() > 0) {
            user = usersWithSearchedName.get(0);
        }

        return user;
    }
}