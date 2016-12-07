package wof.services;

import wof.entities.WallEntry;

import java.util.Set;

public interface WallEntryService {
    WallEntry add(String headLine, String description);

    Set<WallEntry> getAllEntries();

    void update(int id, WallEntry wallEntry);

    void delete(int id);
}
