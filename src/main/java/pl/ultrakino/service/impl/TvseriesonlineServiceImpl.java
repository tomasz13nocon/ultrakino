package pl.ultrakino.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.Constants;
import pl.ultrakino.exceptions.WebScraperException;
import pl.ultrakino.model.Film;
import pl.ultrakino.service.FilmwebService;
import pl.ultrakino.service.TvseriesonlineService;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class TvseriesonlineServiceImpl implements TvseriesonlineService {

	private FilmwebService filmwebService;

	@Autowired
	public TvseriesonlineServiceImpl(FilmwebService filmwebService) {
		this.filmwebService = filmwebService;
	}

	@Override
	public List<Film> getAllShows() throws IOException, WebScraperException {
		Document mainDoc = Jsoup.connect("http://www.tvseriesonline.pl/").userAgent(Constants.USER_AGENT).get();
		List<String> showLinks = mainDoc.select("ul#categories li a").stream().map(e -> e.attr("href")).collect(Collectors.toList());
		int i = 0;
		for (String showLink : showLinks) {
			if (i++ < 2) continue;//TODO: remove
			Document showDoc = Jsoup.connect(showLink).userAgent(Constants.USER_AGENT).get();
			Pattern yearPattern = Pattern.compile("<h2><span.*?>(.+?)</span>\\s*\\(<a href=\"http://www\\.tvseriesonline\\.pl\\?page_id=5573&rok=\\d{4}\">(\\d{4})</a>\\)");
			Elements articles = showDoc.select("article");
			if (articles.size() != 1)
				throw new WebScraperException();
			Matcher m = yearPattern.matcher(articles.get(0).html());
			if (!m.find())
				throw new WebScraperException();
			String title = m.group(1);
			int year = Integer.parseInt(m.group(2));


			;
			Elements episodes = showDoc.select("div.post h4.title");
			Pattern p = Pattern.compile("^(\\d{1,2})(×|&#215;)(\\d{1,3}).*");
			for (Element episode : episodes) {
				m = p.matcher(episode.select("a").attr("title"));
				if (!m.matches())
					continue;
				String epHref = episode.select("a").attr("href");
				System.out.println(epHref);
				Document episodeDoc = Jsoup.connect(epHref).userAgent(Constants.USER_AGENT).get();


				break;//TODO: remove
			}

			break;//TODO: remove
		}


		return null;
	}

}
