package pl.ultrakino.resources;

import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Content;

import java.util.ArrayList;
import java.util.List;

public class PersonResource extends ResourceSupport {

	private String name;
	private String role;
	private List<Content> filmography = new ArrayList<>();


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Content> getFilmography() {
		return filmography;
	}

	public void setFilmography(List<Content> filmography) {
		this.filmography = filmography;
	}
}
