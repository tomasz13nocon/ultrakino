package pl.ultrakino.service;

import pl.ultrakino.model.Player;
import pl.ultrakino.resource.PlayerResource;

import java.util.Collection;
import java.util.List;

public interface PlayerService {

	PlayerResource toResource(Player player);

	List<PlayerResource> toResources(Collection<Player> players);
}
