package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.model.User;
import pl.ultrakino.resources.UserResource;
import pl.ultrakino.web.UserController;

@Component
public class UserResourceAsm extends ResourceAssemblerSupport<User, UserResource> {

	public UserResourceAsm() {
		super(UserController.class, UserResource.class);
	}

	@Override
	public UserResource toResource(User user) {
		UserResource res = new UserResource();
		res.setUid(user.getId());
		res.setUsername(user.getUsername());
		res.setAvatarFilename(user.getAvatarFilename());
		return res;
	}
}
