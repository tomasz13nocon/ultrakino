package pl.ultrakino.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.Rating;
import pl.ultrakino.resources.RatingResource;
import pl.ultrakino.service.RatingService;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

	@Override
	public RatingResource toResource(Rating rating) {
		RatingResource res = new RatingResource();
		res.setRating(rating.getRating());
		res.setUid(rating.getId());
		res.setContentId(rating.getContent().getId());
		res.setUserId(rating.getRatedBy().getId());
		return null;
	}
}
