package persistence.hibernate;

import persistence.abstractions.CategoryRepository;
import shared.domain.Category;

public class HbnCategoryRepository extends HbnRepository<Category> implements CategoryRepository {
    public HbnCategoryRepository() {
        super(Category.class);
    }
}
