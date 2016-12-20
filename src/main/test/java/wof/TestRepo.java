package wof;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wof.entities.Category;
import wof.entities.WallEntry;
import wof.repositories.CategoryRepository;
import wof.repositories.WallEntryRepository;

import javax.transaction.Transactional;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
public class TestRepo {

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
    public void testAdd() {

        Set<Category> categorySet = newHashSet();
        Set<Category> categorySet1 = newHashSet();
        categorySet.add(new Category("asd"));
        categorySet1.add(new Category("asd"));
        WallEntry wallEntry = new WallEntry("asd", "asd");
        WallEntry wallEntry2 = new WallEntry("asd2", "asd2");
        wallEntry.setCategories(categorySet);

        wallEntryRepository.save(wallEntry);
        Category category = categoryRepository.findByCategoryNameIgnoreCase("asd").get();
        wallEntry2.setCategories(newHashSet(category));
        wallEntryRepository.save(wallEntry2);
        assertEquals(wallEntryRepository.findAll().size(), 2);
        for (WallEntry wallEntry1 : wallEntryRepository.findAll()) {
            System.out.println(wallEntry1.getCategories());
        }
    }
}
