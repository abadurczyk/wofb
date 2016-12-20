package wof.services;

import wof.entities.Category;

import java.util.Optional;
import java.util.Set;

public interface CategoryService {

    Category addCategory(String category);

    Optional<Category> getCategory(String category);

    void delete(String categoryName);

    /**
     * Returns the categories for given category names. Checks the size of categories that already exist ignoring case.
     * Throws CategoryNotFoundException the size of found categories does not match
     *
     * @param categoryNames The category names to check
     */
    Set<Category> getCategories(Set<String> categoryNames);

    Set<Category> getAllCategories();

    void deleteCategory(Category category);
}
