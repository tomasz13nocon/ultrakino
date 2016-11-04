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
		res.setHosting(player.getHosting());
		if (player.getHosting().equals("openload")) {
			res.setSrc("https://openload.co/embed/" + player.getSrc());
		}
		else
			res.setSrc(player.getSrc());
		res.setAdditionDate(player.getAdditionDate());
		res.setLanguageVersion(player.getLanguageVersion());
		res.setQuality(player.getQuality());
		res.setAddedBy(userDetailsResourceAsm.toResource(player.getAddedBy()));
		return res;
	}

}
