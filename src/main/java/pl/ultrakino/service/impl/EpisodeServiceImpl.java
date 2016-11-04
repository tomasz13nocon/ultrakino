package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Episode;
import pl.ultrakino.repository.EpisodeRepository;
import pl.ultrakino.resources.EpisodeDetailsResource;
import pl.ultrakino.resources.EpisodeResource;
import pl.ultrakino.resources.assemblers.PlayerResourceAsm;
import pl.ultrakino.service.EpisodeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EpisodeServiceImpl implements EpisodeService {

	private PlayerResourceAsm playerResourceAsm;
	private EpisodeRepository episodeRepository;

	@Autowired
	public EpisodeServiceImpl(PlayerResourceAsm playerResourceAsm, EpisodeRepository episodeRepository) {
		this.playerResourceAsm = playerResourceAsm;
		this.episodeRepository = episodeRepository;
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
		res.setPlayers(playerResourceAsm.toResources(episode.getPlayers()));
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
