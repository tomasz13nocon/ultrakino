package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
public class UserResource extends ResourceSupport {

	private int uid;
	private String username;
	private String avatarFilename;

}
