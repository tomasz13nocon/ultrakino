package pl.ultrakino.repository;

import pl.ultrakino.model.Player;

import java.util.Optional;

public interface PlayerRepository {

	Optional<Player> findBySrc(String src);

	Optional<Player> findBySrcAndHosting(String src, String hosting);

}
