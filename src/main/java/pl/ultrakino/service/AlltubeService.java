package pl.ultrakino.service;

import pl.ultrakino.exceptions.AlltubeException;
import pl.ultrakino.model.Film;

import java.io.IOException;
import java.util.List;

public interface AlltubeService {

	/**
	 * Process films
	 * @param pageNumber alltube page number.
	 * @return
	 * @throws IOException
	 */
	List<Film> processFilms(int pageNumber) throws IOException, AlltubeException;

}
