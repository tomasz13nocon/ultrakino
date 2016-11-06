package pl.ultrakino.service;

import pl.ultrakino.model.FilmCategory;

import java.util.List;

public interface FilmCategoryService {

	FilmCategory findByName(String name);

	List<FilmCategory> findAll();
}
