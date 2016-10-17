package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.model.Player;
import pl.ultrakino.resources.PlayerResource;
import pl.ultrakino.web.PlayerController;

@Component
public class PlayerResourceAsm extends ResourceAssemblerSupport<Player, PlayerResource> {

	private UserDetailsResourceAsm userDetailsResourceAsm;

	public PlayerResourceAsm(UserDetailsResourceAsm userDetailsResourceAsm) {
		super(PlayerController.class, PlayerResource.class);
		this.userDetailsResourceAsm = userDetailsResourceAsm;
	}

	@Override
	public PlayerResource toResource(Player player) {
		PlayerResource res = new PlayerResource();
		res.setSrc(player.getSrc());
		res.setAdditionDate(player.getAdditionDate());
		res.setForeignSrc(player.isForeignSrc());
		res.setLostSrc(player.isLostSrc());
		res.setLanguageVersion(player.getLanguageVersion());
		res.setHosting(player.getHosting());
		res.setQuality(player.getQuality());
		res.setAddedBy(userDetailsResourceAsm.toResource(player.getAddedBy()));
		return res;
	}

}
