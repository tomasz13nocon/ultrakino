package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.FilmCategory;
import pl.ultrakino.repository.CategoryRepository;
import pl.ultrakino.service.CategoryService;

import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public FilmCategory getCategory(String name) {
		Optional<FilmCategory> op = categoryRepository.findByName(name);
		if (op.isPresent())
			return op.get();
		else {
			FilmCategory category = new FilmCategory(name);
			return categoryRepository.save(category);
		}
	}
}
