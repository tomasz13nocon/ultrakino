package pl.ultrakino.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.FilmographyEntry;
import pl.ultrakino.resources.PersonResource;
import pl.ultrakino.service.PersonService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	@Override
	public PersonResource toResource(FilmographyEntry entry) {
		PersonResource res = new PersonResource();
		res.setName(entry.getPerson().getName());
		//TODO
		return res;
	}

	@Override
	public List<PersonResource> toResources(Collection<FilmographyEntry> entries) {
		return entries.stream().map(this::toResource).collect(Collectors.toList());
	}

}
