package pl.ultrakino.repository;

import pl.ultrakino.model.Player;

import java.util.Optional;

public interface PlayerRepository {

	Optional<Player> findBySrc(String src);

	Optional<Player> findBySrcAndHosting(String src, String hosting);

	boolean remove(int id);

	boolean exists(String hosting, String src);
}
