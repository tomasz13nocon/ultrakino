package pl.ultrakino.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ultrakino.exceptions.AlltubeException;
import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Player;
import pl.ultrakino.repository.FilmRepository;
import pl.ultrakino.service.AlltubeService;
import pl.ultrakino.service.FilmwebService;
import pl.ultrakino.Constants;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pl.ultrakino.model.Player.LanguageVersion.*;

@Service
public class AlltubeServiceImpl implements AlltubeService {

	private FilmwebService filmwebService;
	private FilmRepository filmRepository;

	private static Map<String, Player.LanguageVersion> versions = new HashMap<>();

	static {
		versions.put("Lektor", VOICE_OVER);
		versions.put("Oryginalna", ORIGINAL);
		versions.put("ENG", ORIGINAL);
		versions.put("Dubbing", DUBBING);
		versions.put("PL", POLISH_FILM);
		versions.put("Napisy", POLISH_SUBS);
		versions.put("Napisy ENG", ENGLISH_SUBS);
//		versions.put("Lektor IVO" ,IVONA);
	}

	@Autowired
	public AlltubeServiceImpl(FilmwebService filmwebService, FilmRepository filmRepository) {
		this.filmwebService = filmwebService;
		this.filmRepository = filmRepository;
	}

	@Override
	public List<Film> getFilms(int pageNumber) throws IOException, AlltubeException {
		if (pageNumber < 1) throw new IllegalArgumentException("pageNumber has to be a positive integer.");
		Document filmsDoc = Jsoup.connect("http://alltube.tv/filmy-online/strona[" + pageNumber + "]+").userAgent(Constants.USER_AGENT).get();
		// Iterate through all the films in this page
		Elements els = filmsDoc.select(".item-block a");
		List<Film> films = new ArrayList<>();
		for (Element el : els) {
			String href = el.attr("href");
			Optional<Film> op = getFilm(href);
			if (op.isPresent())
				films.add(op.get());
		}
		return films;
	}

	private Optional<Film> getFilm(String href) throws IOException, AlltubeException {
		Document doc = Jsoup.connect(href).userAgent(Constants.USER_AGENT).get();
		Film film = new Film();

		String body = doc.body().html();
		Matcher m = Pattern.compile
				(Pattern.quote("url: 'http://alltube.tv/photos/") + "\\d+" + Pattern.quote("',"))
				.matcher(body);

		Set<Player> players = new HashSet<>();
		Elements trs = doc.select("#links-container tr");
		if (trs.isEmpty())
			return Optional.empty();

		if (m.find()) {
			String match = m.group();
			String filmwebId = match.substring(match.lastIndexOf('/') + 1, match.lastIndexOf('\''));
			System.out.println("Filmweb ID: " + filmwebId);
			if (filmwebId.length() < 10) { // Else it's not a real filmweb ID
				film.setFilmwebId(filmwebId);
				try {
					filmwebService.getFullFilmInfo(film.getFilmwebId());
				} catch (FilmwebException e) {
					throw new AlltubeException(e);
				}
			}
			else {
				return Optional.empty();
			}
		}

		for (Element tr : trs) { // players
			Player player = new Player();
			String link = new String(Base64.getDecoder().decode(tr.select("a.watch").attr("data-iframe")));

			String hosting, src;

			System.out.println("link: " + link);
			int hostingIndex = link.indexOf("hosting=");
			int idIndex = link.indexOf("&id=");
			int vIndex = link.indexOf("?v=");
			if (hostingIndex != -1 && idIndex != -1) {
				hosting = link.substring(hostingIndex + 8, idIndex);
				src = link.substring(idIndex + 4);
			}
			else if (vIndex != -1) {
				hosting = "nowvideo";
				src = link.substring(vIndex + 3);
				System.out.println("src: " + src);
			}
			else throw new AlltubeException("Unsupported link format: " + link);

			Player.LanguageVersion version = versions.get(tr.select("td:nth-child(4)").text());

			player.setSrc(src);
			player.setHosting(hosting);
			player.setLanguageVersion(version);
			players.add(player);
		}

		film.setPlayers(players);

		return Optional.of(film);
	}

}
