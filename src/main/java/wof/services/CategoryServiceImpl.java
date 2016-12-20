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
    public void delete(String categoryName) {
        Optional<Category> categoryOptional = getCategory(categoryName);
        if (!categoryOptional.isPresent()) {
            return;
        }
        Category category = categoryOptional.get();
        categoryRepository.delete(category);
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
}
