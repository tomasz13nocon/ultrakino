package pl.ultrakino.repository;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Player;

import java.util.Optional;

public interface PlayerRepository {

	Optional<Player> findBySrc(String src);

	Optional<Player> findBySrcAndHosting(String src, String hosting);

	boolean remove(int id);

	boolean exists(String hosting, String src);

	Player save(Player player);

	Player findById(int id) throws NoRecordWithSuchIdException;
}
