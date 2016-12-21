package wof.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wof.entities.Category;
import wof.exceptions.CategoryExistsAlreadyException;
import wof.exceptions.CategoryNotFoundException;
import wof.repositories.CategoryRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category addCategory(String categoryName) {
        Optional<Category> categoryOptional = getCategory(categoryName);
        if (categoryOptional.isPresent()) {
            throw new CategoryExistsAlreadyException();
        }
        Category category = new Category(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategory(String categoryName) {
        return categoryRepository.findByCategoryNameIgnoreCase(categoryName);
    }

    @Override
    public Set<Category> getCategories(Set<String> categoryNames) {
        Set<Category> categories = categoryRepository.findByCategoryNameIgnoreCaseIn(categoryNames);
        if (categoryNames.size() != categories.size()) {
            throw new CategoryNotFoundException();
        }
        return categories;
    }

    @Override
    public Set<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public void changeCategoryName(String oldCategoryName, String newCategoryName) {
        if (oldCategoryName.equalsIgnoreCase(newCategoryName)) {
            return;
        }
        // check if the new name exists in the repository
        Optional<Category> oldCategory = categoryRepository.findByCategoryNameIgnoreCase(newCategoryName);
        if (oldCategory.isPresent()) {
            throw new CategoryExistsAlreadyException();
        }

        Optional<Category> categoryOptional = categoryRepository.findByCategoryNameIgnoreCase(oldCategoryName);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setCategoryName(newCategoryName);
            categoryRepository.save(category);
        } else {
            throw new CategoryNotFoundException();
        }
    }
}
