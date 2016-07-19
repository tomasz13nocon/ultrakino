package pl.ultrakino.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.Episode;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.EpisodeRepository;
import pl.ultrakino.repository.FilmRepository;
import pl.ultrakino.repository.UserRepository;

@Service
@Transactional
public class MainServiceImpl implements MainService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private EpisodeRepository episodeRepository;

	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void saveFilm(Film film) {
		filmRepository.save(film);
	}

	@Override
	public void saveEpisode(Episode episode) {
		episodeRepository.save(episode);
	}
}
