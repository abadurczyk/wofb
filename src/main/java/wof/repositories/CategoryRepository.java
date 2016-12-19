package wof.repositories;

import org.springframework.data.repository.CrudRepository;
import wof.entities.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);
}
