package pl.ultrakino.service;

import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.exceptions.TvseriesonlineException;
import pl.ultrakino.exceptions.WebScraperException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Series;

import java.io.IOException;
import java.util.List;

public interface TvseriesonlineService {

	List<Series> getAllShows() throws IOException, FilmwebException, TvseriesonlineException;

}
