package pl.ultrakino.service;

import pl.ultrakino.model.Episode;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.User;

public interface MainService {

	void saveUser(User user);

	void saveFilm(Film film);

	void saveEpisode(Episode episode);

}
