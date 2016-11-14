package pl.ultrakino.service;

import pl.ultrakino.model.FilmographyEntry;
import pl.ultrakino.resources.PersonResource;

import java.util.Collection;
import java.util.List;

public interface PersonService {

	PersonResource toResource(FilmographyEntry entry);

	List<PersonResource> toResources(Collection<FilmographyEntry> entries);
}
