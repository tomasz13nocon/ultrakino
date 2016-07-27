package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.repository.FilmRepository;
import pl.ultrakino.service.FilmService;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {

	private FilmRepository filmRepository;

	@Autowired
	public FilmServiceImpl(FilmRepository filmRepository) {
		this.filmRepository = filmRepository;
	}

	@Override
	public void create(Film film) {
		filmRepository.create(film);
	}

	@Override
	public Film find(Integer id) throws NoRecordWithSuchIdException {
		return filmRepository.find(id);
	}
}
