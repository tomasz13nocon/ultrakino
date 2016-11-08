package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.*;
import pl.ultrakino.repository.ContentRepository;
import pl.ultrakino.repository.RatingRepository;
import pl.ultrakino.repository.UserRepository;
import pl.ultrakino.resources.RatingResource;
import pl.ultrakino.service.RatingService;

import java.util.Optional;
import java.util.OptionalDouble;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

	private RatingRepository ratingRepository;
	private UserRepository userRepository;
	private ContentRepository contentRepository;

	@Autowired
	public RatingServiceImpl(RatingRepository ratingRepository, UserRepository userRepository, ContentRepository contentRepository) {
		this.ratingRepository = ratingRepository;
		this.userRepository = userRepository;
		this.contentRepository = contentRepository;
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

	@Override
	public Rating save(int contentId, String username, float rating) throws NoRecordWithSuchIdException, NoUserWithSuchUsernameException {
		if (ratingRepository.findByUsernameAndContentId(username, contentId).isPresent())
			throw new IllegalStateException();
		if (rating < 0 || rating > 10)
			throw new IllegalArgumentException();
		Optional<User> user = userRepository.findByUsername(username);
		if (!user.isPresent()) throw new NoUserWithSuchUsernameException();
		Content content = contentRepository.findById(contentId);
		Rating r = new Rating();
		r.setContent(content);
		r.setRating(rating);
		r.setRatedBy(user.get());
		content.getRatings().add(r);
		calculateRating(content);
		ratingRepository.save(r);
		return r;
	}

	@Override
	public void calculateRating(Rateable r) {
		OptionalDouble rating = r.getRatings().stream().mapToDouble(Rating::getRating).average();
		if (rating.isPresent()) {
			r.setRating((float) rating.getAsDouble());
			r.setTimesRated(r.getRatings().size());
			contentRepository.merge(r);
		}
	}

}
