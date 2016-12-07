package wof.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wof.entities.WallEntry;
import wof.exceptions.WallEntryNotFoundException;
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

    @Override
    public void update(int id, WallEntry newWallEntry) {
        WallEntry oldEntry = wallEntryRepository.findOne(id);
        if (oldEntry == null) {
            throw new WallEntryNotFoundException();
        }
        if (newWallEntry.getDescription() != null) {
            oldEntry.setDescription(newWallEntry.getDescription());
        }
        if (newWallEntry.getHeadline() != null) {
            oldEntry.setHeadline(newWallEntry.getHeadline());
        }
        wallEntryRepository.save(oldEntry);
    }

    @Override
    public void delete(int id) {
        if (wallEntryRepository.findOne(id) == null) {
            return;
        }
        wallEntryRepository.delete(id);
    }
}
