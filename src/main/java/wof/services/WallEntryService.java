package wof.services;

import wof.entities.WallEntry;

public interface WallEntryService {
    WallEntry add(String headLine, String description);

    Iterable<WallEntry> getAllEntries();
}
