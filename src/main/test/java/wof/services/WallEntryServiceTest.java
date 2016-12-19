package wof.services;

import org.junit.Before;
import org.junit.Test;
import wof.entities.WallEntry;
import wof.exceptions.WallEntryNotFoundException;
import wof.repositories.WallEntryRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WallEntryServiceTest {

    private WallEntryService wallEntryService;
    private WallEntryRepository wallEntryRepository;
    private final String headline = "headline";
    private final String description = "description";
    private final int id = 2;

    @Before
    public void setup() {
        wallEntryRepository = mock(WallEntryRepository.class);
        wallEntryService = new WallEntryServiceImpl(wallEntryRepository);
    }

    @Test
    public void testAddWallEntry() {
        WallEntry wallEntry = new WallEntry(headline, description);

        WallEntry newWallEntry = wallEntryService.add(wallEntry);

        assertEquals(wallEntry, newWallEntry);
        verify(wallEntryRepository).save(wallEntry);
    }

    @Test
    public void testAddWallEntryWithCategory() {
        WallEntry wallEntry = new WallEntry(headline, description);

        WallEntry newWallEntry = wallEntryService.add(wallEntry);

        assertEquals(wallEntry, newWallEntry);
        verify(wallEntryRepository).save(wallEntry);
    }

    @Test
    public void getAllWallEntry() {
        wallEntryService.getAllEntries();
    }

    @Test(expected = WallEntryNotFoundException.class)
    public void updateWallEntry_entryNotFound() throws Exception {

        WallEntry newWallEntry = new WallEntry();
        newWallEntry.setHeadline("newHeadline");

        wallEntryService.update(id, newWallEntry);
    }

    @Test
    public void updateWallEntry_headLineAdded() throws Exception {

        WallEntry oldWallEntry = new WallEntry(null, description);
        WallEntry newWallEntry = new WallEntry(headline, null);
        WallEntry expectedEntry = new WallEntry(headline, description);

        testUpdate(oldWallEntry, newWallEntry, expectedEntry);
    }

    @Test
    public void updateWallEntry_descriptionAdded() throws Exception {

        WallEntry oldWallEntry = new WallEntry(headline, null);
        WallEntry newWallEntry = new WallEntry(null, description);
        WallEntry expectedEntry = new WallEntry(headline, description);

        testUpdate(oldWallEntry, newWallEntry, expectedEntry);
    }

    @Test
    public void updateWallEntry_noChanges() throws Exception {

        WallEntry oldWallEntry = new WallEntry(headline, description);
        WallEntry newWallEntry = new WallEntry();
        WallEntry expectedEntry = new WallEntry(headline, description);

        testUpdate(oldWallEntry, newWallEntry, expectedEntry);
    }

    @Test
    public void deleteWallEntry_entryNotFound() {
        when(wallEntryRepository.findOne(id)).thenReturn(new WallEntry());
        wallEntryService.delete(id);
    }

    @Test
    public void deleteWallEntry() {
        wallEntryService.delete(id);
    }

    private void testUpdate(WallEntry oldWallEntry, WallEntry newWallEntry, WallEntry expectedEntry) {
        when(wallEntryRepository.findOne(id)).thenReturn(oldWallEntry);

        wallEntryService.update(id, newWallEntry);
        verify(wallEntryRepository).save(expectedEntry);
    }

}
