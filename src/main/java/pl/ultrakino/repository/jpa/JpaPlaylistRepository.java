package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.PlaylistRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaPlaylistRepository implements PlaylistRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void removeByFilm(Film film) {
		em.createNativeQuery("DELETE FROM users_contents_watched link WHERE link.content_id=:filmId")
				.setParameter("filmId", film.getId())
				.executeUpdate();
		em.createNativeQuery("DELETE FROM users_contents_watchlist link WHERE link.content_id=:filmId")
				.setParameter("filmId", film.getId())
				.executeUpdate();
		em.createNativeQuery("DELETE FROM users_contents_favorites link WHERE link.content_id=:filmId")
				.setParameter("filmId", film.getId())
				.executeUpdate();
	}

}
