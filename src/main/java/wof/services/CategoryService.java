package wof.services;

import wof.entities.Category;

import java.util.Optional;
import java.util.Set;

public interface CategoryService {

    /**
     * Adds the category and persists it. The ID will be created.
     *
     * @param category The category without any id.
     * @return the created category
     */
    Category addCategory(String category);

    /**
     * Returns the category for the given category name.
     *
     * @param categoryName search name for category
     * @return the category that was persisted before.
     */
    Optional<Category> getCategory(String categoryName);


    /**
     * Returns the categories for given category names. Checks the size of categories that already exist ignoring case.
     * Throws CategoryNotFoundException the size of found categories does not match
     *
     * @param categoryNames The category names to check
     */
    Set<Category> getCategories(Set<String> categoryNames);

    /**
     * Returns all categories that are persisted.
     *
     * @return All categories
     */
    Set<Category> getAllCategories();

    /**
     * Deletes the category.
     *
     * @param category The to delete category
     */
    void deleteCategory(Category category);

    /**
     * Changes the category name.
     *
     * @param oldCategoryName the old name
     * @param newCategoryName the new category name
     */
    void changeCategoryName(String oldCategoryName, String newCategoryName);
}
