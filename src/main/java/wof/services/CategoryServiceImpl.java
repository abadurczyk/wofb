package wof.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wof.entities.Category;
import wof.exceptions.CategoryExistsAlreadyException;
import wof.repositories.CategoryRepository;

import java.util.Optional;

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
}
