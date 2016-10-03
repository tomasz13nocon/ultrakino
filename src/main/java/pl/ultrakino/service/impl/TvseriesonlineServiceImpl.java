package pl.ultrakino.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.Constants;
import pl.ultrakino.model.Film;
import pl.ultrakino.service.TvseriesonlineService;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class TvseriesonlineServiceImpl implements TvseriesonlineService {

	@Override
	public List<Film> getAllShows() throws IOException {
		Document mainDoc = Jsoup.connect("http://www.tvseriesonline.pl/").userAgent(Constants.USER_AGENT).get();
		List<String> showLinks = mainDoc.select("ul#categories li a").stream().map(e -> e.attr("href")).collect(Collectors.toList());
		int i = 0;
		for (String showLink : showLinks) {
			if (i++ < 2) continue;
			Document showDoc = Jsoup.connect(showLink).userAgent(Constants.USER_AGENT).get();
			Elements episodes = showDoc.select("div.post h4.title");
			// Select title attribute of the first episode.
			// TODO: Check that pattern against each episode, as good episodes may be mixed with season links
			String title = episodes.select("a").attr("title");
			Pattern p = Pattern.compile("^(\\d{1,2})&#215;(\\d{1,3}).*");
			Matcher m = p.matcher(title);
			// if (m.)
				//&#215;
			for (Element episode : episodes) {
				String epHref = episode.select("a").attr("href");
				Document episodeDoc = Jsoup.connect(epHref).userAgent(Constants.USER_AGENT).get();


				break;
			}

			break;
		}


		return null;
	}

	public static void main(String[] args) {
		try {
			new TvseriesonlineServiceImpl().getAllShows();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
