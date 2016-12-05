package wof.services;

import org.junit.Before;
import org.junit.Test;
import wof.entities.WallEntry;
import wof.repositories.WallEntryRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WallEntryServiceTest {

    private WallEntryService wallEntryService;
    private WallEntryRepository wallEntryRepository;

    @Before
    public void setup() {
        wallEntryRepository = mock(WallEntryRepository.class);
        wallEntryService = new WallEntryServiceImpl(wallEntryRepository);
    }

    @Test
    public void testAddWallEntry() {
        String headLine = "headline";
        String description = "description";
        WallEntry wallEntry = new WallEntry();
        wallEntry.setHeadline(headLine);
        wallEntry.setDescription(description);

        WallEntry newWallEntry = wallEntryService.add(headLine, description);

        assertEquals(wallEntry, newWallEntry);
        verify(wallEntryRepository).save(wallEntry);
    }

    @Test
    public void getAllWallEntry() {
        wallEntryService.getAllEntries();
    }
}
