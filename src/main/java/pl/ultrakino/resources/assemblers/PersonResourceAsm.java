package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.model.FilmographyEntry;
import pl.ultrakino.model.Person;
import pl.ultrakino.resources.PersonResource;
import pl.ultrakino.web.PersonController;

@Component
public class PersonResourceAsm extends ResourceAssemblerSupport<FilmographyEntry, PersonResource> {

	public PersonResourceAsm() {
		super(PersonController.class, PersonResource.class);
	}

	@Override
	public PersonResource toResource(FilmographyEntry entry) {
		PersonResource res = new PersonResource();
		res.setName(entry.getPerson().getName());
		//TODO
		return res;
	}
}
