package pl.ultrakino.service;

import pl.ultrakino.model.Rating;
import pl.ultrakino.resources.RatingResource;

public interface RatingService {

	 RatingResource toResource(Rating rating);


}
