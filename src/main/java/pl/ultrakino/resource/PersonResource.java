package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;
import pl.ultrakino.model.Content;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonResource {

	private int uid;
	private String name;
	private String role;
	private String avatarFilename;
	private int number;

}
