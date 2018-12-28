package persistence.abstractions;

import shared.domain.Category;

public interface CategoryRepository extends Repository<Category> {
    Category getByName(String categoryName);
}
