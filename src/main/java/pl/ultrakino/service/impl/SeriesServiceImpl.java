package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Rating;
import pl.ultrakino.model.Series;
import pl.ultrakino.repository.ContentQuery;
import pl.ultrakino.repository.Page;
import pl.ultrakino.repository.RatingRepository;
import pl.ultrakino.repository.SeriesRepository;
import pl.ultrakino.resource.SeriesDetailsResource;
import pl.ultrakino.resource.SeriesResource;
import pl.ultrakino.service.EpisodeService;
import pl.ultrakino.service.PersonService;
import pl.ultrakino.service.SeriesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SeriesServiceImpl implements SeriesService {

	private SeriesRepository seriesRepository;
	private EpisodeService episodeService;
	private RatingRepository ratingRepository;
	private PersonService personService;

	@Autowired
	public SeriesServiceImpl(SeriesRepository seriesRepository, EpisodeService episodeService, RatingRepository ratingRepository, PersonService personService) {
		this.seriesRepository = seriesRepository;
		this.episodeService = episodeService;
		this.ratingRepository = ratingRepository;
		this.personService = personService;
	}

	@Override
	public Series findById(int seriesId) throws NoRecordWithSuchIdException {
		return seriesRepository.findById(seriesId);
	}

	@Override
	public Page<Series> find(MultiValueMap<String, String> params) {
		return seriesRepository.find(new ContentQuery(params));
	}

	@Override
	public SeriesDetailsResource toDetailsResource(Series series) {
		SeriesDetailsResource res = new SeriesDetailsResource();
		res.setUid(series.getId());
		res.setTitle(series.getTitle());
		res.setYear(series.getYear());
		res.setRating(series.getRating());
		res.setTimesRated(series.getTimesRated());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).contains("ROLE_USER")){
			Optional<Rating> userRating = ratingRepository.findByUsernameAndContentId(
					((UserDetails) auth.getPrincipal()).getUsername(),
					series.getId());
			if (userRating.isPresent())
				res.setUserRating(userRating.get().getRating());
		}
		res.setOriginalTitle(series.getOriginalTitle());
		res.setDescription(series.getDescription());
		res.setCoverFilename(series.getCoverFilename());
		res.setWorldPremiere(series.getWorldPremiere());
		res.setCategories(series.getCategories());
		res.setSeasonCount(series.getSeasonCount());
		res.setEpisodeCount(series.getEpisodeCount());

		res.setCastAndCrew(personService.toResources(series.getCastAndCrew()));
		res.setEpisodes(episodeService.toResources(series.getEpisodes()));
		res.setSeasonCount(series.getSeasonCount());
		res.setEpisodeCount(series.getEpisodeCount());
		res.setRunningTime(series.getRunningTime());

		return res;
	}


	@Override
	public SeriesResource toResource(Series series) {
		SeriesResource res = new SeriesResource();
		res.setUid(series.getId());
		res.setTitle(series.getTitle());
		res.setYear(series.getYear());
		res.setRating(series.getRating());
		res.setTimesRated(series.getTimesRated());
		res.setOriginalTitle(series.getOriginalTitle());
		res.setDescription(series.getDescription());
		res.setCoverFilename(series.getCoverFilename());
		res.setWorldPremiere(series.getWorldPremiere());
		res.setCategories(series.getCategories());
		res.setSeasonCount(series.getSeasonCount());
		res.setEpisodeCount(series.getEpisodeCount());
		return res;
	}

	@Override
	public List<SeriesResource> toResources(List<Series> series) {
		return series.stream()
				.map(this::toResource)
				.collect(Collectors.toList());
	}
}
