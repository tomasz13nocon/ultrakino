package pl.ultrakino.repository;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Rating;

import java.util.Optional;

public interface RatingRepository {

	Optional<Rating> findByUsernameAndContentId(String username, int contentId);

	Optional<Rating> findByUserIdAndContentId(int userId, int contentId) throws NoRecordWithSuchIdException;

	Rating save(Rating rating);
}
