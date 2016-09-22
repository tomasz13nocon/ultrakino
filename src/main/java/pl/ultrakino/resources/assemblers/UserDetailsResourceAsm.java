package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.model.User;
import pl.ultrakino.resources.UserDetailsResource;
import pl.ultrakino.web.UserController;

@Component
public class UserDetailsResourceAsm extends ResourceAssemblerSupport<User, UserDetailsResource> {

	public UserDetailsResourceAsm() {
		super(UserController.class, UserDetailsResource.class);
	}

	@Override
	public UserDetailsResource toResource(User user) {
		// TODO: BULLSHIT CODE!!! FIX THIS!!!!
		if (user != null) {
			UserDetailsResource res = new UserDetailsResource();
			res.setUsername(user.getUsername());
			return res;
		}
		return null;
	}

}
