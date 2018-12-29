package persistence.hibernate;

import persistence.abstractions.CategoryRepository;
import shared.domain.Category;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The Hibernate {@link Categoryer} repository that implements the
 * {@link CategoryRepository} methods.
 */
public class HbnCategoryRepository extends HbnRepository<Category> implements CategoryRepository {
    /**
     * Construct a {@link HbnRepository} for the {@link Category} entity.
     */
    public HbnCategoryRepository() {
        super(Category.class);
    }

    public Category getByName(String categoryName) {
        CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);

        Root<Category> root = criteriaQuery.from(Category.class);
        criteriaQuery.select(root);

        ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), params));

        TypedQuery<Category> typedQuery = getCurrentSession().createQuery(criteriaQuery);
        typedQuery.setParameter(params, categoryName);

        List<Category> searchedCategories = typedQuery.getResultList();
        Category category = null;

        if (searchedCategories.size() > 0) {
            category = searchedCategories.get(0);
        }

        return category;
    }
}
