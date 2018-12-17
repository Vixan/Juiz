package logic.services;

import logic.abstractions.Service;
import persistence.hibernate.HbnCategoryRepository;
import shared.domain.Category;

import java.util.List;

public class CategoryService implements Service<Category> {
    private HbnCategoryRepository categoryRepository = new HbnCategoryRepository();

    @Override
    public Category getById(Integer categoryId) {
        categoryRepository.openCurrentSession();
        Category category = categoryRepository.getById(categoryId);
        categoryRepository.closeCurrentSession();

        return category;
    }

    @Override
    public List<Category> getAll() {
        categoryRepository.openCurrentSession();
        List<Category> categories = categoryRepository.getAll();
        categoryRepository.closeCurrentSession();

        return categories;
    }

    @Override
    public void add(Category category) {
        categoryRepository.openCurrentSessionWithTransaction();
        if (categoryRepository.getByName(category.getName()) == null) {
            categoryRepository.add(category);
        }
        categoryRepository.closeCurrentSessionWithTransaction();
    }

    @Override
    public void update(Category category) {
        categoryRepository.openCurrentSessionWithTransaction();
        if (categoryRepository.getByName(category.getName()) != null) {
            categoryRepository.update(category);
        }
        categoryRepository.closeCurrentSessionWithTransaction();
    }

    @Override
    public void delete(Integer categoryId) {
        categoryRepository.openCurrentSessionWithTransaction();
        Category category = categoryRepository.getById(categoryId);
        if (category != null) {
            categoryRepository.delete(category);
        }
        categoryRepository.closeCurrentSessionWithTransaction();
    }
}
