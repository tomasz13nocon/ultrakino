package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Content;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonResource {

	private int uid;
	private String name;
	private String role;
	private List<Content> filmography = new ArrayList<>();

}
