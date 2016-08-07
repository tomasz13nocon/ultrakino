package pl.ultrakino.resources;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class FilmListResource extends ResourceSupport {

	private List<FilmResource> entries;

	public List<FilmResource> getEntries() {
		return entries;
	}

	public void setEntries(List<FilmResource> entries) {
		this.entries = entries;
	}

}
