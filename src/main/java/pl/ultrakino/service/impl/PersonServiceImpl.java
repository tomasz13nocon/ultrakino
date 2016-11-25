package pl.ultrakino.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.FilmographyEntry;
import pl.ultrakino.resource.PersonResource;
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
		res.setUid(entry.getPerson().getId());
		res.setName(entry.getPerson().getName());
		res.setAvatarFilename(entry.getPerson().getAvatarFilename());
		res.setRole(entry.getRole());
		res.setNumber(entry.getNumber());
		return res;
	}

	@Override
	public List<PersonResource> toResources(Collection<FilmographyEntry> entries) {
		return entries.stream().map(this::toResource).collect(Collectors.toList());
	}

}
