package persistence.hibernate;

import persistence.abstractions.CategoryRepository;
import persistence.abstractions.PersistenceContext;
import persistence.abstractions.QuizRepository;
import shared.domain.Category;

import java.util.ArrayList;

public class HbnPersistenceContext implements PersistenceContext {
    private HbnCategoryRepository categoryRepository;

    public HbnPersistenceContext() {
        categoryRepository = new HbnCategoryRepository();
    }

    public QuizRepository getQuizRepository() {
        return null;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public void initializeData() {
        categoryRepository.openCurrentSession();

        String[] initialCategoryNames = new String[] {"Computer Science", "Astronomy", "Music"};
        ArrayList<Category> initialCategories = new ArrayList<Category>();

        for (String categoryName: initialCategoryNames) {
            Category category = new Category();
            category.setName(categoryName);
            initialCategories.add(category);
            categoryRepository.add(category);
        }

        System.out.println(categoryRepository.getById(2).getName());
        categoryRepository.closeCurrentSession();
    }
}
