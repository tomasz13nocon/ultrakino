package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.FilmCategory;
import pl.ultrakino.repository.FilmCategoryRepository;
import pl.ultrakino.service.FilmCategoryService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FilmCategoryServiceImpl implements FilmCategoryService {

	private FilmCategoryRepository filmCategoryRepository;

	@Autowired
	public FilmCategoryServiceImpl(FilmCategoryRepository filmCategoryRepository) {
		this.filmCategoryRepository = filmCategoryRepository;
	}

	@Override
	public FilmCategory findByName(String name) {
		Optional<FilmCategory> op = filmCategoryRepository.findByName(name);
		if (op.isPresent())
			return op.get();
		else {
			FilmCategory category = new FilmCategory(name);
			return filmCategoryRepository.save(category);
		}
	}

	@Override
	public List<FilmCategory> findAll() {
		return filmCategoryRepository.findAll();
	}

}
