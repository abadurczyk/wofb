package wof.services;

import wof.entities.WallEntry;
import wof.exceptions.WallEntryNotFoundException;

import java.util.Set;

public interface WallEntryService {
    /**
     * Adds a new entry to the Wall of Fail. There is no check if this entity exists already. So duplicates are possible.
     *
     * @param wallEntry the wall entry to be added
     * @return The new WallEntry entity
     */
    WallEntry add(WallEntry wallEntry);

    /**
     * Gets all known WallEntries.
     *
     * @return all wallEntries known to the system
     */
    Set<WallEntry> getAllEntries();

    /**
     * Changes the WallEntry with the specified ID. If there is no entity with this ID, a WallEntryNotFoundException will be thrown.
     * If the id exists, the entity will be updated, no matter if the fields have changes.
     *
     * @param id        the id for the entity that should be changed
     * @param wallEntry the new wallEntry
     * @throws WallEntryNotFoundException if the id does not exist.
     */
    void update(int id, WallEntry wallEntry);

    /**
     * Deletes the entry with the specified id. If the id is not found, it will be ignored.
     *
     * @param id the id of the entity that should be deleted.
     */
    void delete(int id);
}
