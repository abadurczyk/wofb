package wof.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wof.Application;
import wof.entities.Category;
import wof.entities.WallEntry;

import javax.transaction.Transactional;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
public class WallEntryRepositoryTest {

    @Autowired
    private WallEntryRepository wallEntryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void setup() {
        categoryRepository.deleteAll();
        wallEntryRepository.deleteAll();
    }

    @Test
    @Transactional
    public void addMultipleWallEntriesWithSameCategory() {

        Set<Category> categorySet = newHashSet();
        categorySet.add(new Category("asd"));
        WallEntry wallEntry = new WallEntry("asd", "asd");
        WallEntry wallEntry2 = new WallEntry("asd2", "asd2");
        wallEntry.setCategories(categorySet);

        wallEntryRepository.save(wallEntry);
        Category category = categoryRepository.findByCategoryNameIgnoreCase("asd").get();
        wallEntry2.setCategories(newHashSet(category));
        wallEntryRepository.save(wallEntry2);
        assertEquals(wallEntryRepository.findAll().size(), 2);
        Set<WallEntry> wallEntries = wallEntryRepository.findAll();
        assertThat(wallEntries.contains(wallEntry));
        assertThat(wallEntries.contains(wallEntry2));
    }
}
