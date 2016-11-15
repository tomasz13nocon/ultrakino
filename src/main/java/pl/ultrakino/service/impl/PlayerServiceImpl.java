package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.Player;
import pl.ultrakino.resource.PlayerResource;
import pl.ultrakino.service.PlayerService;
import pl.ultrakino.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

	// Autowired at fields, because of circular dependencies
	@Autowired
	private UserService userService;

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
			case "youtube":
				src = "https://www.youtube.com/embed/" + src;
				break;
		}
		res.setSrc(src);
		res.setAdditionDate(player.getAdditionDate());
		res.setLanguageVersion(player.getLanguageVersion());
		res.setQuality(player.getQuality());
		if (player.getAddedBy() != null)
			res.setAddedBy(userService.toResource(player.getAddedBy()));
		return res;
	}

	@Override
	public List<PlayerResource> toResources(Collection<Player> players) {
		return players.stream().map(this::toResource).collect(Collectors.toList());
	}

}
