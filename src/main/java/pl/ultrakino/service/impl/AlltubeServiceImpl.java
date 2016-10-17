package pl.ultrakino.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
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
	public List<Film> fetchAndSaveFilms(int pageNumber) throws IOException, AlltubeException {
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
		Film film;

		String body = doc.body().html();
		Matcher m = Pattern.compile
				(Pattern.quote("url: 'http://alltube.tv/photos/") + "\\d*" + Pattern.quote("',"))
				.matcher(body);

		// players
		Elements trs = doc.select("#links-container tr");
		if (trs.isEmpty())
			return Optional.empty();

		String filmwebId;
		if (m.find()) {
			String match = m.group();
			filmwebId = match.substring(match.lastIndexOf('/') + 1, match.lastIndexOf('\''));
		}
		else {
			System.err.println("Pattern `" + m.pattern().toString() + "` didn't match the string:\n" + body);
			throw new AlltubeException("Unexpected website format.");
		}

		if (filmwebId.length() > 10 || filmwebId.length() == 0) // It's not a real filmweb ID
			return Optional.empty();

		System.out.println(filmwebId);//TODO: remove

		Optional<Film> existingFilm = filmRepository.findByFilmwebId(filmwebId);
		if (!existingFilm.isPresent()) {
			try {
				film = filmwebService.getFullFilmInfo(filmwebId);
				filmRepository.save(film);
			} catch (FilmwebException e) {
				throw new AlltubeException(e);
			}
		}
		else {
			film = existingFilm.get();
		}

		System.out.println(film.getTitle());//TODO: remove


		Set<Player> players = new HashSet<>();
		for (Element tr : trs) { // players
			Player player = new Player();
			String link = new String(Base64.getDecoder().decode(tr.select("a.watch").attr("data-iframe")));

			String hosting, src;

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
			}
			else throw new AlltubeException("Unsupported link format: " + link);

			Player.LanguageVersion version = versions.get(tr.select("td:nth-child(4)").text());

			player.setSrc(src);
			player.setHosting(hosting);
			player.setLanguageVersion(version);
			players.add(player);
		}

		for (Player player : film.getPlayers()) {
			Iterator<Player> it = players.iterator();
			while (it.hasNext()) {
				Player newPlayer = it.next();
				if (player.getHosting().equals(newPlayer.getHosting()) && player.getSrc().equals(newPlayer.getSrc()))
					it.remove();
			}
		}
		film.getPlayers().addAll(players);

		return Optional.of(film);
	}

}
