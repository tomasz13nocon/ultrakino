package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.User;
import pl.ultrakino.resources.PlayerResource;
import pl.ultrakino.web.PlayerController;

@Component
public class PlayerResourceAsm extends ResourceAssemblerSupport<Player, PlayerResource> {

	private UserResourceAsm userResourceAsm;

	public PlayerResourceAsm(UserResourceAsm userResourceAsm) {
		super(PlayerController.class, PlayerResource.class);
		this.userResourceAsm = userResourceAsm;
	}

	@Override
	public PlayerResource toResource(Player player) {
		PlayerResource res = new PlayerResource();
		res.setSrc(player.getSrc());
		res.setAdditionDate(player.getAdditionDate());
		res.setForeignSrc(player.isForeignSrc());
		res.setLostSrc(player.isLostSrc());
		res.setLanguageVersion(player.getLanguageVersion());
		res.setQuality(player.getQuality());
		res.setAddedBy(userResourceAsm.toResource(player.getAddedBy()));
		return res;
	}

}
