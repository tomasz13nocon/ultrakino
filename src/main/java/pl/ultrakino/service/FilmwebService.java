package pl.ultrakino.service;

import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.FilmographyEntry;
import pl.ultrakino.model.Series;

import java.io.IOException;
import java.util.Set;

public interface FilmwebService {

	Series getSeriesInfo(String filmwebId) throws FilmwebException;

	Series getFullSeriesInfo(String filmwebId) throws FilmwebException;

	Film getFilmInfo(String filmwebId) throws FilmwebException, IOException;

	Set<FilmographyEntry> getFilmPersons(String filmwebId) throws FilmwebException, IOException;

	Film getFullFilmInfo(String filmwebId) throws FilmwebException, IOException;

}
