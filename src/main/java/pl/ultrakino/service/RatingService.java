package pl.ultrakino.service;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.Rateable;
import pl.ultrakino.model.Rating;
import pl.ultrakino.resources.RatingResource;

public interface RatingService {

	RatingResource toResource(Rating rating);


	Rating save(int contentId, String username, float rating) throws NoRecordWithSuchIdException, NoUserWithSuchUsernameException;

	void calculateRating(Rateable r);
}
