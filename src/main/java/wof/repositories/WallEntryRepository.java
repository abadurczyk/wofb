package wof.repositories;

import org.springframework.data.repository.CrudRepository;
import wof.entities.WallEntry;

import java.util.Set;

public interface WallEntryRepository extends CrudRepository<WallEntry, Long> {

    @Override
    Set<WallEntry> findAll();
}
