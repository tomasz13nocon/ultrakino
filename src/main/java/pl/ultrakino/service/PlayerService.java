package pl.ultrakino.service;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.PlayerVote;
import pl.ultrakino.model.User;
import pl.ultrakino.resource.PlayerResource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PlayerService {

	PlayerResource toResource(Player player);

	PlayerResource toResource(Player player, boolean content);

	List<PlayerResource> toResources(Collection<Player> players);

	List<PlayerResource> toResources(Collection<Player> players, boolean content);

	boolean remove(int id);

	boolean exists(String hosting, String src);

	Player save(Player player);

	boolean exists(Player player);

	/**
	 * @return empty Optional if {@code user} has already voted for this player. Optional with persisted PlayerVote otherwise.
	 * @throws NoRecordWithSuchIdException if Player with {@code playerId} doesn't exist.
	 */
	Optional<PlayerVote> vote(int playerId, boolean positive, User user) throws NoRecordWithSuchIdException;
}
