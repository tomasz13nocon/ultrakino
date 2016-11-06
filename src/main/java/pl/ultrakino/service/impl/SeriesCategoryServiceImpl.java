package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.FilmCategory;
import pl.ultrakino.model.SeriesCategory;
import pl.ultrakino.repository.SeriesCategoryRepository;
import pl.ultrakino.service.SeriesCategoryService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SeriesCategoryServiceImpl implements SeriesCategoryService {

	private SeriesCategoryRepository seriesCategoryRepository;

	@Autowired
	public SeriesCategoryServiceImpl(SeriesCategoryRepository seriesCategoryRepository) {
		this.seriesCategoryRepository = seriesCategoryRepository;
	}

	@Override
	public SeriesCategory findByName(String name) {
		Optional<SeriesCategory> op = seriesCategoryRepository.findByName(name);
		if (op.isPresent())
			return op.get();
		else {
			SeriesCategory category = new SeriesCategory(name);
			return seriesCategoryRepository.save(category);
		}
	}

	@Override
	public List<SeriesCategory> findAll() {
		return seriesCategoryRepository.findAll();
	}
}
