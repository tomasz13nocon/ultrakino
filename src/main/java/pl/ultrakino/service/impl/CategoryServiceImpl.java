package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.Category;
import pl.ultrakino.model.Country;
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
	public Category getCategory(String name) {
		Optional<Category> op = categoryRepository.findByName(name);
		if (op.isPresent())
			return op.get();
		else {
			Category category = new Category(name);
			return categoryRepository.save(category);
		}
	}
}
