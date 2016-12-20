package wof.repositories;

import org.springframework.data.repository.CrudRepository;
import wof.entities.Category;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);

    Set<Category> findByCategoryNameIgnoreCaseIn(Set<String> categoryNames);

    Set<Category> findAll();
}
