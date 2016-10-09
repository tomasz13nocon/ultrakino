package pl.ultrakino.repository;

import pl.ultrakino.model.Rating;

import java.util.Optional;

public interface RatingRepository {

	Optional<Rating> findByUsernameAndContentId(String username, int contentId);

}
