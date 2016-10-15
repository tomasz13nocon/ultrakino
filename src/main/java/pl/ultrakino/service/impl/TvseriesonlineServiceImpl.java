package pl.ultrakino.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.Constants;
import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.exceptions.TvseriesonlineException;
import pl.ultrakino.exceptions.WebScraperException;
import pl.ultrakino.model.Episode;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.Series;
import pl.ultrakino.repository.EpisodeRepository;
import pl.ultrakino.repository.FilmRepository;
import pl.ultrakino.repository.SeriesRepository;
import pl.ultrakino.service.FilmwebService;
import pl.ultrakino.service.TvseriesonlineService;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class TvseriesonlineServiceImpl implements TvseriesonlineService {

	private FilmwebService filmwebService;
	private SeriesRepository seriesRepository;
	private EpisodeRepository episodeRepository;
	private int skipped, created, updated;
	private Map<String, Player.LanguageVersion> langs;

	public int getSkipped() {
		return skipped;
	}

	public int getCreated() {
		return created;
	}

	public int getUpdated() {
		return updated;
	}

	@Autowired
	public TvseriesonlineServiceImpl(FilmwebService filmwebService, SeriesRepository seriesRepository, EpisodeRepository episodeRepository) {
		this.filmwebService = filmwebService;
		this.seriesRepository = seriesRepository;
		this.episodeRepository = episodeRepository;
		langs = new HashMap<>();
		langs.put("pl-subtitles", Player.LanguageVersion.POLISH_SUBS);
		langs.put("english-version", Player.LanguageVersion.ORIGINAL);
		langs.put("lector", Player.LanguageVersion.VOICE_OVER);
//		langs.put("", Player.LanguageVersion.);
	}


	@Override
	public List<Series> getAllShows() throws IOException, FilmwebException, TvseriesonlineException {
		skipped = 0; created = 0; updated = 0;
		Document mainDoc = Jsoup.connect("http://www.tvseriesonline.pl/").userAgent(Constants.USER_AGENT).get();
		List<String> showLinks = mainDoc.select("ul#categories li a").stream().map(e -> e.attr("href")).collect(Collectors.toList());
		List<Series> result = new ArrayList<>();
		int i = 0;//TODO: remove
		for (String showLink : showLinks) {
			if (i++ < 2) continue;//TODO: remove
			Optional<Series> op = getShow(showLink);
			if (!op.isPresent())
				skipped++;
			else {
				result.add(op.get());
				created++;
			}
			break;//TODO: remove
		}

		return result;
	}

	private Optional<Series> getShow(String showLink) throws TvseriesonlineException, FilmwebException, IOException {
		Document showDoc = Jsoup.connect(showLink).userAgent(Constants.USER_AGENT).get();
		Pattern yearPattern = Pattern.compile("<h2><span.*?>(.+?)</span>\\s*\\(<a href=\"http://www\\.tvseriesonline\\.pl\\?page_id=5573&rok=\\d{4}\">(\\d{4})</a>\\)");
		Elements articles = showDoc.select("article");
		if (articles.size() != 1)
			throw new TvseriesonlineException("Web scraper: Unexpected website format.");
		Matcher m = yearPattern.matcher(articles.get(0).html());
		if (!m.find())
			throw new TvseriesonlineException("Web scraper: Unexpected website format.");
		String title = m.group(1);
		int year = Integer.parseInt(m.group(2));

		Series series;
		Optional<Series> existingSeries = seriesRepository.findByTitleAndYear(title, year);
		if (existingSeries.isPresent()) {
			series = existingSeries.get();
		}
		else {
			try {
				List<String> ids = filmwebService.searchForSeries(title, year);
				if (ids.size() == 1)
					series = filmwebService.getFullSeriesInfo(ids.get(0));
				else
					return Optional.empty();
			} catch (Exception e) {
				throw new FilmwebException(e);
			}
		}

		Elements episodes = showDoc.select("div.post h4.title");
		Pattern p = Pattern.compile("^(\\d{1,2})(×|&#215;)(\\d{1,3}).*");
		for (Element episodeEl : episodes) {
			m = p.matcher(episodeEl.select("a").attr("title"));
			if (!m.matches()) // These are probably whole seasons hosted on weird websites, so we skip.
				continue;

			int season = Integer.parseInt(m.group(0));
			int episodeNumber = Integer.parseInt(m.group(2));
			Episode episode;
			Optional<Episode> existingEpisode = episodeRepository.findBySeasonAndEpisodeNumber(season, episodeNumber);
			if (existingEpisode.isPresent()) {
				episode = existingEpisode.get();
			}
			else {
				episode = new Episode();
				episode.setSeason(season);
				episode.setEpisodeNumber(episodeNumber);
				series.getEpisodes().add(episode);
			}

			String epHref = episodeEl.select("a").attr("href");
			System.out.println(epHref);
			Document episodeDoc = Jsoup.connect(epHref).userAgent(Constants.USER_AGENT).get();
			Elements linkGroups = episodeDoc.select("div.video-links ul li");
			for (Element linkGroup : linkGroups) {
				Elements groupTitle = linkGroup.select("h5");
				if (groupTitle.isEmpty())
					continue;
				String lang = groupTitle.attr("class");
				Player.LanguageVersion version = langs.get(lang);
				Elements links = linkGroup.select("a");
				for (Element linkEl : links) {
					String link = linkEl.attr("href");
					String hosting;
					if (link.contains("openload.co/"))

				}
			}

			break;//TODO: remove
		}
		return Optional.of(series);
	}

}
