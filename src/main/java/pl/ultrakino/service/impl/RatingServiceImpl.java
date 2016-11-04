package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.Rating;
import pl.ultrakino.repository.RatingRepository;
import pl.ultrakino.resources.RatingResource;
import pl.ultrakino.service.RatingService;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

	private RatingRepository ratingRepository;

	@Autowired
	public RatingServiceImpl(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	@Override
	public Rating save(Rating rating) {
		return ratingRepository.save(rating);
	}

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
