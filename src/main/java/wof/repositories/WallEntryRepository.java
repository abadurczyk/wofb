package wof.repositories;

import org.springframework.data.repository.CrudRepository;
import wof.entities.Category;
import wof.entities.WallEntry;

import java.util.Set;

public interface WallEntryRepository extends CrudRepository<WallEntry, Integer> {

    @Override
    Set<WallEntry> findAll();

    Set<WallEntry> findByCategories(Category category);
}
