package pl.ultrakino.repository;

import pl.ultrakino.model.Player;

import java.util.Optional;

public interface PlayerRepository {

	Optional<Player> findBySrcWithFullLink(String src);

}
