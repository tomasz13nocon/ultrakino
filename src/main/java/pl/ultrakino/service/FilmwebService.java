package pl.ultrakino.service;

import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.FilmographyEntry;
import pl.ultrakino.model.Series;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface FilmwebService {

	List<String> searchForFilm(String title) throws FilmwebException;

	List<String> searchForSeries(String title, Integer year) throws FilmwebException;

	List<String> search(ContentType contentType, String title, Integer year) throws FilmwebException;

	Series getSeriesInfo(String filmwebId) throws FilmwebException;

	Series getFullSeriesInfo(String filmwebId) throws FilmwebException;

	Film getFilmInfo(String filmwebId) throws FilmwebException, IOException;

	Set<FilmographyEntry> getFilmPersons(String filmwebId, Content content) throws FilmwebException, IOException;

	/**
	 * Gets film info for a given filmwebId, icluding FilmograpgyEntries.
	 * This is equivalent of using getFilmInfo() and getFilmPersons().
	 * given filmwebId will be included in returned Film object
	 * @param filmwebId
	 * @return
	 * @throws FilmwebException
	 * @throws IOException
	 */
	Film getFullFilmInfo(String filmwebId) throws FilmwebException, IOException;

}
