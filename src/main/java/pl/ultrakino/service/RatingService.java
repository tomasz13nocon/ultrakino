package pl.ultrakino.service;

import pl.ultrakino.model.Rating;
import pl.ultrakino.resources.RatingResource;

public interface RatingService {

	Rating save(Rating rating);

	RatingResource toResource(Rating rating);


}
