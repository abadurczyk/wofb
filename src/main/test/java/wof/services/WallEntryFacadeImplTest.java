package wof.services;

import org.junit.Before;
import org.junit.Test;
import wof.entities.Category;
import wof.entities.WallEntry;
import wof.exceptions.CannotDeleteCategoryException;

import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WallEntryFacadeImplTest {

    private CategoryService categoryService;
    private WallEntryService wallEntryService;
    private WallEntryFacade wallEntryFacade;
    private final String categoryName = "category";
    private final Category category = new Category(categoryName);
    private final WallEntry wallEntry = new WallEntry();


    @Before
    public void setUp() throws Exception {
        wallEntryService = mock(WallEntryService.class);
        categoryService = mock(CategoryService.class);
        wallEntryFacade = new WallEntryFacadeImpl(wallEntryService, categoryService);
    }

    @Test
    public void deleteCategory_categoryDoesNotExist() throws Exception {
        when(categoryService.getCategory(categoryName)).thenReturn(Optional.empty());
        wallEntryFacade.deleteCategory(categoryName);
    }

    @Test(expected = CannotDeleteCategoryException.class)
    public void deleteCategory_categoryExistsInWallEntry() throws Exception {
        when(categoryService.getCategory(categoryName)).thenReturn(Optional.of(category));
        when(wallEntryService.getWallEntriesWithCategory(category)).thenReturn(newHashSet(wallEntry));
        wallEntryFacade.deleteCategory(categoryName);
    }

    @Test
    public void deleteCategory_categoryDeleted() throws Exception {
        when(categoryService.getCategory(categoryName)).thenReturn(Optional.of(category));
        when(wallEntryService.getWallEntriesWithCategory(category)).thenReturn(newHashSet());
        wallEntryFacade.deleteCategory(categoryName);
        verify(categoryService).deleteCategory(category);
    }

}