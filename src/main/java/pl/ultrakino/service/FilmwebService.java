package pl.ultrakino.service;

import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.FilmographyEntry;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

public interface FilmwebService {


	Film loadFilmInfo(Film film) throws FilmwebException, IOException;

	Set<FilmographyEntry> getFilmPersons(String filmwebId) throws FilmwebException, IOException;

	Film loadFullFilmInfo(Film film) throws FilmwebException, IOException;

}
