package pl.ultrakino.repository;

import pl.ultrakino.model.Film;

public interface PlaylistRepository {

	/**
	 * Removes all playlist entries for specified film from playlist link tables.
	 * @param film Film
	 */
	void removeByFilm(Film film);
}
