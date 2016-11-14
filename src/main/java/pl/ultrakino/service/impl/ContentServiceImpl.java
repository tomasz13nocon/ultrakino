package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.exceptions.UnsupportedContentTypeException;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Episode;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Series;
import pl.ultrakino.resources.ContentResource;
import pl.ultrakino.service.ContentService;
import pl.ultrakino.service.EpisodeService;
import pl.ultrakino.service.FilmService;
import pl.ultrakino.service.SeriesService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {

	private FilmService filmService;
	private SeriesService seriesService;
	private EpisodeService episodeService;

	@Autowired
	public ContentServiceImpl(FilmService filmService, SeriesService seriesService, EpisodeService episodeService) {
		this.filmService = filmService;
		this.seriesService = seriesService;
		this.episodeService = episodeService;
	}

	// TODO: Make Content implement some interface (or do it in content..?) to return title, cover etc. Episodes will return details of their series.

	@Override
	public ContentResource toResource(Content content) {
		if (content instanceof Film)
			return filmService.toResource((Film) content);
		else if (content instanceof Episode)
			return episodeService.toResource((Episode) content);
		else if (content instanceof Series)
			return seriesService.toResource((Series) content);
		throw new UnsupportedContentTypeException();
	}

	@Override
	public List<ContentResource> toResources(Collection<Content> contents) {
		return contents.stream().map(this::toResource).collect(Collectors.toList());
	}

}
