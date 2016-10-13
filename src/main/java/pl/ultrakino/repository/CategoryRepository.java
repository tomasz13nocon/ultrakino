package pl.ultrakino.repository;

import pl.ultrakino.model.Category;

import java.util.Optional;

public interface CategoryRepository {

	Optional<Category> findByName(String name);

	Category save(Category category);
}
