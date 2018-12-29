package persistence.abstractions;

import shared.domain.Category;

import javax.persistence.Entity;

/**
 * The Category {@link Entity} repository interface with custom behaviour.
 *
 * @see Repository
 * @see Category
 */
public interface CategoryRepository extends Repository<Category> {
    /**
     * @param categoryName the name to retrieve the category by.
     * @return the category queried.
     */
    Category getByName(String categoryName);
}
