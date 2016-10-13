package pl.ultrakino.service;

import pl.ultrakino.exceptions.WebScraperException;
import pl.ultrakino.model.Film;

import java.io.IOException;
import java.util.List;

public interface TvseriesonlineService {

	List<Film> getAllShows() throws IOException, WebScraperException;

}
