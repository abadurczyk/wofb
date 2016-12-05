package wof.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wof.entities.WallEntry;
import wof.repositories.WallEntryRepository;

import java.util.Set;

@Service
public class WallEntryServiceImpl implements WallEntryService {


    private final WallEntryRepository wallEntryRepository;

    @Autowired
    public WallEntryServiceImpl(WallEntryRepository wallEntryRepository) {
        this.wallEntryRepository = wallEntryRepository;
    }

    @Override
    public WallEntry add(String headLine, String description) {
        WallEntry wallEntry = new WallEntry();
        wallEntry.setHeadline(headLine);
        wallEntry.setDescription(description);
        wallEntryRepository.save(wallEntry);
        return wallEntry;
    }

    @Override
    public Set<WallEntry> getAllEntries() {
        return wallEntryRepository.findAll();
    }
}
