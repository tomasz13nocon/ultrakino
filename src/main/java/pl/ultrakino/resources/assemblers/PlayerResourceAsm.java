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
		String src = player.getSrc();
		int width = 1060;
		int height = 595;
		switch (player.getHosting()) {
			case "openload":
				src = "https://openload.co/embed/" + src;
				break;
			case "streamin":
				src = "https://streamin.to/embed-" + src + ".html";
				break;
			case "vshare":
				src = "https://vshare.io/v/" + src + "/width-" + width + "/height-" + height + "/";
				break;
			case "vidto":
				src = "https://vidto.me/embed-" + src + "-" + width + "x" + height + ".html";
				break;
			case "videowood":
				src = "https://videowood.tv/embed/" + src;
				break;
			case "cda":
				src = "http://ebd.cda.pl/" + width + "x" + height + "/" + src;
				break;
		}
		res.setSrc(src);
		res.setAdditionDate(player.getAdditionDate());
		res.setLanguageVersion(player.getLanguageVersion());
		res.setQuality(player.getQuality());
		res.setAddedBy(userDetailsResourceAsm.toResource(player.getAddedBy()));
		return res;
	}

}
