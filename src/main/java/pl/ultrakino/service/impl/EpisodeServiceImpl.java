package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.Utils;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Episode;
import pl.ultrakino.model.Rating;
import pl.ultrakino.repository.EpisodeRepository;
import pl.ultrakino.repository.RatingRepository;
import pl.ultrakino.resource.EpisodeDetailsResource;
import pl.ultrakino.resource.EpisodeResource;
import pl.ultrakino.service.CommentService;
import pl.ultrakino.service.EpisodeService;
import pl.ultrakino.service.PlayerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EpisodeServiceImpl implements EpisodeService {

	private PlayerService playerService;
	private EpisodeRepository episodeRepository;
	private CommentService commentService;
	private RatingRepository ratingRepository;

	@Autowired
	public EpisodeServiceImpl(PlayerService playerService, EpisodeRepository episodeRepository, CommentService commentService, RatingRepository ratingRepository) {
		this.playerService = playerService;
		this.episodeRepository = episodeRepository;
		this.commentService = commentService;
		this.ratingRepository = ratingRepository;
	}

	@Override
	public EpisodeResource toResource(Episode episode) {
		EpisodeResource res = new EpisodeResource();
		res.setUid(episode.getId());
		res.setTitle(episode.getTitle());
		res.setRating(episode.getRating());
		res.setTimesRated(episode.getTimesRated());
		res.setWorldPremiere(episode.getWorldPremiere());
		res.setLocalPremiere(episode.getLocalPremiere());
		res.setAdditionDate(episode.getAdditionDate());
		res.setViews(episode.getViews());
		res.setSeason(episode.getSeason());
		res.setEpisodeNumber(episode.getEpisodeNumber());
		return res;
	}

	@Override
	public List<EpisodeResource> toResources(List<Episode> episodes) {
		return episodes.stream()
				.map(this::toResource)
				.collect(Collectors.toList());
	}

	@Override
	public EpisodeDetailsResource toDetailsResource(Episode episode) {
		EpisodeDetailsResource res = new EpisodeDetailsResource();
		res.setUid(episode.getId());
		res.setTitle(episode.getTitle());
		res.setRating(episode.getRating());
		res.setTimesRated(episode.getTimesRated());
		res.setWorldPremiere(episode.getWorldPremiere());
		res.setLocalPremiere(episode.getLocalPremiere());
		res.setAdditionDate(episode.getAdditionDate());
		res.setViews(episode.getViews());
		res.setSeason(episode.getSeason());
		res.setEpisodeNumber(episode.getEpisodeNumber());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (Utils.isUser(auth)){
			Optional<Rating> userRating = ratingRepository.findByUsernameAndContentId(
					((UserDetails) auth.getPrincipal()).getUsername(),
					episode.getId());
			if (userRating.isPresent())
				res.setUserRating(userRating.get().getRating());
		}
		res.setComments(commentService.toResources(episode.getComments()));
		res.setPlayers(playerService.toResources(episode.getPlayers()));
		Optional<Episode> previous = findPrevious(episode);
		if (previous.isPresent())
			res.setPreviousEpisode(toResource(previous.get()));
		Optional<Episode> next = findNext(episode);
		if (next.isPresent())
			res.setNextEpisode(toResource(next.get()));
		return res;
	}

	private Optional<Episode> findNext(Episode episode) {
		return episodeRepository.findNext(episode);
	}

	private Optional<Episode> findPrevious(Episode episode) {
		return episodeRepository.findPrevious(episode);
	}

	@Override
	public List<Episode> findBySeriesIdAndSeason(int seriesId, int season) {
		return episodeRepository.findBySeriesIdAndSeason(seriesId, season);
	}

	@Override
	public Episode findByIdAndSeriesId(int id, int seriesId) throws NoRecordWithSuchIdException {
		return episodeRepository.findByIdAndSeriesId(id, seriesId);
	}
}
