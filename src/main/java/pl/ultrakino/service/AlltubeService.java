package pl.ultrakino.service;

import pl.ultrakino.exceptions.AlltubeException;
import pl.ultrakino.model.Film;

import java.io.IOException;
import java.util.List;

public interface AlltubeService {

	List<Film> fetchAndSaveFilms(int pageNumber) throws IOException, AlltubeException;

}
