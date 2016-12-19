package wof.services;

import wof.entities.Category;

import java.util.Optional;

public interface CategoryService {

    Category addCategory(String category);

    Optional<Category> getCategory(String category);

    void delete(String categoryName);
}
