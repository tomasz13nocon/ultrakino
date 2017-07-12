package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Rating;
import pl.ultrakino.repository.RatingRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

	@Override
	public Optional<Rating> findByUserIdAndContentId(int userId, int contentId) throws NoRecordWithSuchIdException {
		try {
			Rating rating = em
					.createQuery("FROM Rating WHERE ratedBy.id=:userId AND content.id=:contentId", Rating.class)
					.setParameter("userId", userId)
					.setParameter("contentId", contentId)
					.getSingleResult();
			return Optional.of(rating);
		}
		catch (NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	public Rating save(Rating rating) {
		em.persist(rating);
		return rating;
	}

}
