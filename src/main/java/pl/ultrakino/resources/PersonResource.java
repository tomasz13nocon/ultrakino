package pl.ultrakino.resources;

import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Person;
import pl.ultrakino.model.PersonRole;

import java.util.ArrayList;
import java.util.List;

public class PersonResource extends ResourceSupport {

	private String name;
	private PersonRole role;
	private List<Content> filmography = new ArrayList<>();


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PersonRole getRole() {
		return role;
	}

	public void setRole(PersonRole role) {
		this.role = role;
	}

	public List<Content> getFilmography() {
		return filmography;
	}

	public void setFilmography(List<Content> filmography) {
		this.filmography = filmography;
	}
}
