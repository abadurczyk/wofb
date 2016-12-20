package wof.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import wof.entities.Category;
import wof.entities.WallEntry;
import wof.exceptions.CannotDeleteCategoryException;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class WallEntryFacadeImpl implements WallEntryFacade {

    private final WallEntryService wallEntryService;
    private final CategoryService categoryService;

    public WallEntryFacadeImpl(WallEntryService wallEntryService, CategoryService categoryService) {
        this.wallEntryService = wallEntryService;
        this.categoryService = categoryService;
    }

    @Override
    public void deleteCategory(String categoryName) {
        Optional<Category> categoryOptional = categoryService.getCategory(categoryName);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            Set<WallEntry> wallEntrySet = wallEntryService.getWallEntriesWithCategory(category);
            if (wallEntrySet.isEmpty()) {
                // in the meantime there could be a entity be persisted.
                try {
                    categoryService.deleteCategory(category);
                } catch (DataIntegrityViolationException exception) {
                    log.error("cannot delete category because wall entry contains category", exception);
                    throw new CannotDeleteCategoryException();
                }
            } else {
                throw new CannotDeleteCategoryException();
            }
        }
    }
}
