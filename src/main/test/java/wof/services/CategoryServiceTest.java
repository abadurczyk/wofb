package wof.services;

import org.junit.Before;
import org.junit.Test;
import wof.entities.Category;
import wof.exceptions.CategoryExistsAlreadyException;
import wof.exceptions.CategoryNotFoundException;
import wof.repositories.CategoryRepository;

import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private CategoryService categoryService;
    private final String categoryName = "name";
    private CategoryRepository categoryRepository;
    private final Category category = new Category(categoryName);

    @Before
    public void setup() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void categoryIsSaved() {
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName)).thenReturn(Optional.empty());
        categoryService.addCategory(categoryName);
        verify(categoryRepository).save(category);
    }

    @Test(expected = CategoryExistsAlreadyException.class)
    public void saveAlreadyExistingCategory() {
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName)).thenReturn(Optional.of(category));
        categoryService.addCategory(categoryName);
    }

    @Test(expected = CategoryExistsAlreadyException.class)
    public void saveAlreadyExistingCategory_ignoreCase() {
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName.toUpperCase())).thenReturn(Optional.of(category));
        categoryService.addCategory(categoryName.toUpperCase());
    }

    @Test
    public void categoryIsReturned() {
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName)).thenReturn(Optional.empty());
        when(categoryRepository.save(category)).thenReturn(category);
        Category actual = categoryService.addCategory(categoryName);
        assertEquals(category, actual);
    }

    @Test
    public void deleteCategory_notDeletedIfNotFound() {
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName)).thenReturn(Optional.empty());
        categoryService.delete(categoryName);
        verify(categoryRepository, times(0)).delete(category);
    }

    @Test
    public void deleteCategory() {
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName)).thenReturn(Optional.of(category));
        categoryService.delete(categoryName);
        verify(categoryRepository).delete(category);
    }

    @Test
    public void checkIfAllCategoriesExist_noCategories() {
        Set<String> categoryNames = newHashSet();
        categoryService.getCategories(categoryNames);

    }

    @Test(expected = CategoryNotFoundException.class)
    public void checkIfAllCategoriesExist_categoryDoesNotExist() {
        Set<String> categoryNames = newHashSet(categoryName);
        categoryService.getCategories(categoryNames);

    }

    @Test
    public void checkIfAllCategoriesExist_allCategoriesExist() {
        Set<String> categoryNames = newHashSet(categoryName);
        Set<Category> categories = newHashSet(new Category(categoryName));
        when(categoryRepository.findByCategoryNameIgnoreCaseIn(categoryNames)).thenReturn(categories);
        categoryService.getCategories(categoryNames);
    }

}
