package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.model.Person;
import pl.ultrakino.resources.PersonResource;
import pl.ultrakino.web.PersonController;

@Component
public class PersonResourceAsm extends ResourceAssemblerSupport<Person, PersonResource> {

	private boolean includeFilmography;

	public PersonResourceAsm() {
		super(PersonController.class, PersonResource.class);
	}

	public PersonResourceAsm filmography() {
		includeFilmography = true;
		return this;
	}

	@Override
	public PersonResource toResource(Person person) {
		PersonResource res = new PersonResource();
		res.setName(person.getName());
		//TODO
//		res.setRole(person.getRole());
		if (includeFilmography) {
			//TODO
//			res.setFilmography(person.getFilmography());
			includeFilmography = false;
		}

		return res;
	}
}
