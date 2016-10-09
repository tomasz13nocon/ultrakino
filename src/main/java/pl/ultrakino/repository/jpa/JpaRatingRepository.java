package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Rating;
import pl.ultrakino.repository.RatingRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaRatingRepository implements RatingRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Rating> findByUsernameAndContentId(String username, int contentId) {
		List<Rating> ratings = em
				.createQuery("FROM Rating WHERE ratedBy.username=:username AND content.id=:contentId", Rating.class)
				.setParameter("username", username)
				.setParameter("contentId", contentId)
				.getResultList();
		if (ratings.isEmpty()) return Optional.empty();
		return Optional.of(ratings.get(0));
	}

}
