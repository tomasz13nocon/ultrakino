package pl.ultrakino.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.Utils;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.PlayerVote;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.PlayerRepository;
import pl.ultrakino.resource.PlayerResource;
import pl.ultrakino.service.ContentService;
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
	@Autowired
	private ContentService contentService;
	private PlayerRepository playerRepository;

	@Autowired
	public PlayerServiceImpl(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Override
	public PlayerResource toResource(Player player) {
		return toResource(player, false);
	}

	@Override
	public PlayerResource toResource(Player player, boolean content) {
		PlayerResource res = new PlayerResource();
		res.setUid(player.getId());
		res.setHosting(player.getHosting());
		String src = player.getSrc();
		int width = 1100;
		int height = 617;
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
			case "nowvideo":
				src = "http://embed.nowvideo.sx/embed.php?v=" + src; // + "&width=" + width + "&height=" + height;
				break;
		}
		res.setSrc(src);
		res.setAdditionDate(player.getAdditionDate());
		res.setLanguageVersion(player.getLanguageVersion());
		if (player.getAddedBy() != null)
			res.setAddedBy(userService.toResource(player.getAddedBy()));
		if (content)
			res.setContent(contentService.toResource(player.getContent()));
		if (Hibernate.isInitialized(player.getVotes())) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (Utils.isUser(auth)) {
				User user = (User) auth.getPrincipal();
				int id = user.getId();
				for (PlayerVote vote : player.getVotes()) {
					if (vote.getUser().getId().equals(id))
						res.setUserVote(vote.isPositive());
				}
			}
		}
		return res;
	}

	@Override
	public List<PlayerResource> toResources(Collection<Player> players) {
		return toResources(players, false);
	}

	@Override
	public List<PlayerResource> toResources(Collection<Player> players, boolean content) {
		return players.stream().map(p -> toResource(p, content)).collect(Collectors.toList());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public boolean remove(int id) {
		return playerRepository.remove(id);
	}

	@Override
	public boolean exists(String hosting, String src) {
		return playerRepository.exists(hosting, src);
	}

}
