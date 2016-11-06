package pl.ultrakino.service;

import pl.ultrakino.model.SeriesCategory;

import java.util.List;

public interface SeriesCategoryService {

	SeriesCategory findByName(String name);

	List<SeriesCategory> findAll();
}
