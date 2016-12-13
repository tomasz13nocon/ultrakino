package pl.ultrakino.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.Constants;
import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.model.*;
import pl.ultrakino.repository.PersonRepository;
import pl.ultrakino.service.*;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class FilmwebServiceImpl implements FilmwebService {

	private PersonRepository personRepository;
	private CountryService countryService;
	private FilmCategoryService filmCategoryService;
	private SeriesCategoryService seriesCategoryService;

	private static final String FILM_INFO_METHOD = "getFilmInfoFull";
	private static final String PERSONS_METHOD = "getFilmPersons";
	private static final String IMAGES = "/opt/images/img/";
//	private static final String IMAGES = "/home/user/Projects/covers/";

	@Autowired
	public FilmwebServiceImpl(PersonRepository personRepository, CountryService countryService, FilmCategoryService filmCategoryService, SeriesCategoryService seriesCategoryService) {
		this.personRepository = personRepository;
		this.countryService = countryService;
		this.filmCategoryService = filmCategoryService;
		this.seriesCategoryService = seriesCategoryService;
	}


	/**
	 * Fetches information for a film with given filmwebID from filmweb API.
	 * Resulting array has a following format:
	 * 0 - title
	 * 1 - originalTitle
	 * 2 - avgRate
	 * 3 - votesCount
	 * 4 - genres
	 * 5 - year
	 * 6 - duration
	 * 7 - commentsCount
	 * 8 - forumUrl
	 * 9 - hasReview
	 * 10 - hasDescription
	 * 11 - imagePath
	 * 12 - video
	 * 13 - premiereWorld
	 * 14 - premiereCountry
	 * 15 - filmType
	 * 16 - seasonsCount
	 * 17 - episodesCount
	 * 18 - countriesString
	 * 19 - description
	 * @param filmwebId
	 * @return
	 */
	private Object[] fetchContentInfo(String filmwebId) throws FilmwebException {
		try {
			String response = IOUtils.toString(new URL(createFilmwebAPIUrl(FILM_INFO_METHOD, filmwebId)).openStream(), StandardCharsets.UTF_8);
			if (!response.startsWith("ok")) {
				throw new FilmwebException("API call didn't return ok. filmwebId: " + filmwebId);
			}
			String arrayString;
			try {
				arrayString = response.substring(response.indexOf('['), response.lastIndexOf(']') + 1);
			}
			catch (StringIndexOutOfBoundsException e) {
				throw new FilmwebException("Wrong format of filmweb API. filmwebId: " + filmwebId);
			}
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(arrayString, Object[].class);
		} catch (IOException e) {
			throw new FilmwebException(e);
		}
	}

	@Override
	public List<String> searchForFilm(String title) throws FilmwebException {
		return search(ContentType.FILM, title, null);
	}

	public List<String> searchForFilm(String title, int year) {
		throw new UnsupportedOperationException();
	}

	public List<String> searchForSeries(String title) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> searchForSeries(String title, Integer year) throws FilmwebException {
		return search(ContentType.SERIES, title, year);
	}

	@Override
	public List<String> search(ContentType contentType, String title, Integer year) throws FilmwebException {
		try {
			String type = "";
			switch (contentType) {
				case FILM:
					type = "/film";
					break;
				case SERIES:
					type = "/serial";
					break;
			}
			String yearStr = year == null ? "" : String.valueOf(year);
			String url = "http://www.filmweb.pl/search" +
					type +
					"?&q=" +
					URLEncoder.encode(title, StandardCharsets.UTF_8.name())
					+ "&startYear=" + yearStr + "&endYear=" + yearStr +
					"&startRate=&endRate=&startCount=&endCount="; // These are apparently necessary
			Document doc = Jsoup.connect(url).userAgent(Constants.USER_AGENT).get();
			Elements resultList = doc.select("ul.resultsList");
			List<String> ret = new ArrayList<>();

			if (resultList.size() < 1 || resultList.size() > 2)
				throw new FilmwebException("Unexpected filmweb format");
			else if (resultList.size() == 1) // No matches
				return ret;

			Elements results = resultList.get(1).children();
			ret.addAll(results.stream()
					.map(result -> result.attr("id").substring(5))
					.collect(Collectors.toList()));
			return ret;
		} catch (IOException e) {
			throw new FilmwebException(e);
		}
	}

	@SuppressWarnings("Duplicates")
	@Override
	public Series getSeriesInfo(String filmwebId) throws FilmwebException {
		Object[] seriesInfo = fetchContentInfo(filmwebId);
		Series series = new Series();
		series.setFilmwebId(filmwebId);

		if ((Integer) seriesInfo[15] != 1)
			throw new FilmwebException("Not a series.");

		series.setTitle((String) seriesInfo[0]);
		if (!series.getTitle().equals(seriesInfo[1])) // If original title is the same as the title then we do nothing
			series.setOriginalTitle((String) seriesInfo[1]);

		series.setCategories(Arrays.stream(((String) seriesInfo[4]).split(",")).map(seriesCategoryService::findByName).collect(Collectors.toSet()));

		series.setYear((Integer) seriesInfo[5]);
		series.setRunningTime((Integer) seriesInfo[6]);

		if (seriesInfo[19] != null)
			series.setDescription((String) seriesInfo[19]);

		if (seriesInfo[11] != null) {
			try {
				String filmwebImg = "http://1.fwcdn.pl/po" + ((String) seriesInfo[11]).replaceFirst("\\.\\d\\.jp", ".3.jp");
				InputStream is = new URL(filmwebImg).openStream();
				String filename = DigestUtils.md5Hex(series.getTitle() + series.getYear()) + ".jpg";
				// TODO: Change image location on prod
				OutputStream os = new FileOutputStream(IMAGES + filename);
				IOUtils.copy(is, os);
				is.close();
				os.close();
				series.setCoverFilename(filename);
			} catch (IOException e) {
				throw new FilmwebException(e);
			}
		}


		Pattern p1 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		Pattern p2 = Pattern.compile("\\d{4}-\\d{2}");

		if (seriesInfo[13] != null) {
			String worldPremiere = (String) seriesInfo[13];
			Matcher m1 = p1.matcher(worldPremiere);
			if (!m1.matches()) {
				Matcher m2 = p2.matcher(worldPremiere);
				if (m2.matches()) {
					worldPremiere += "-01";
				}
				else
					throw new FilmwebException("Unsupported date format: " + worldPremiere);
			}
			series.setWorldPremiere(LocalDate.parse(worldPremiere));
		}
		else if (series.getYear() != null) {
			series.setWorldPremiere(LocalDate.ofYearDay(series.getYear(), 1));
		}

		if (seriesInfo[18] != null)
			series.setProductionCountries(Arrays.stream(((String) seriesInfo[18]).split(", ")).map(countryService::getCountry).collect(Collectors.toSet()));

		if (seriesInfo[16] != null)
			series.setSeasonCount((Integer) seriesInfo[16]);

		if (seriesInfo[17] != null)
			series.setEpisodeCount((Integer) seriesInfo[17]);

		return series;
	}

	@Override
	public Series getFullSeriesInfo(String filmwebId) throws FilmwebException {
		Series series = getSeriesInfo(filmwebId);
		series.setCastAndCrew(getFilmPersons(filmwebId, series));
		return series;
	}

	@Override
	public Film getFullFilmInfo(String filmwebId) throws FilmwebException, IOException {
		Film film = getFilmInfo(filmwebId);
		film.setCastAndCrew(getFilmPersons(filmwebId, film));
		return film;
	}

	@SuppressWarnings("Duplicates")
	@Override
	public Film getFilmInfo(String filmwebId) throws FilmwebException, IOException {
		Object[] filmInfo = fetchContentInfo(filmwebId);
		Film film = new Film();
		film.setFilmwebId(filmwebId);

		if ((Integer) filmInfo[15] != 0)
			throw new FilmwebException("Not a film.");

		film.setTitle((String) filmInfo[0]);
		if (!film.getTitle().equals(filmInfo[1])) // If original title is the same as the title then we do nothing
			film.setOriginalTitle((String) filmInfo[1]);

		film.setCategories(Arrays.stream(((String) filmInfo[4]).split(",")).map(filmCategoryService::findByName).collect(Collectors.toSet()));

		film.setYear((Integer) filmInfo[5]);
		film.setRunningTime((Integer) filmInfo[6]);

		if (filmInfo[19] != null)
			film.setDescription((String) filmInfo[19]);

		if (filmInfo[11] != null) {
			String filmwebImg = "http://1.fwcdn.pl/po" + ((String) filmInfo[11]).replaceFirst("\\.\\d\\.jp", ".3.jp");
			InputStream is = new URL(filmwebImg).openStream();
			String filename = DigestUtils.md5Hex(film.getTitle() + film.getYear()) + ".jpg";
			OutputStream os = new FileOutputStream(IMAGES + filename);
			IOUtils.copy(is, os);
			is.close();
			os.close();
			film.setCoverFilename(filename);
		}


		Pattern p1 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		Pattern p2 = Pattern.compile("\\d{4}-\\d{2}");

		if (filmInfo[13] != null) {
			String worldPremiere = (String) filmInfo[13];
			Matcher m1 = p1.matcher(worldPremiere);
			if (!m1.matches()) {
				Matcher m2 = p2.matcher(worldPremiere);
				if (m2.matches()) {
					worldPremiere += "-01";
				}
				else
					throw new FilmwebException("Unsupported date format: " + worldPremiere);
			}
			film.setWorldPremiere(LocalDate.parse(worldPremiere));
		}
		else if (film.getYear() != null) {
			film.setWorldPremiere(LocalDate.ofYearDay(film.getYear(), 1));
		}

		if (filmInfo[14] != null) {
			String localPremiere = (String) filmInfo[14];
			Matcher m1 = p1.matcher(localPremiere);
			if (!m1.matches()) {
				Matcher m2 = p2.matcher(localPremiere);
				if (m2.matches()) {
					localPremiere += "-01";
				}
				else
					throw new FilmwebException("Unsupported date format: " + localPremiere);
			}
			film.setLocalPremiere(LocalDate.parse(localPremiere));
		}

		if (filmInfo[18] != null)
			film.setProductionCountries(Arrays.stream(((String) filmInfo[18]).split(", ")).map(countryService::getCountry).collect(Collectors.toSet()));

		return film;
	}

	@Override
	public Set<FilmographyEntry> getFilmPersons(String filmwebId, Content content) throws FilmwebException {
		Set<FilmographyEntry> filmographyEntries = new HashSet<>();
		for (Person.Role role : Person.Role.values()) {
			try {
				String personsResponse = IOUtils.toString(new URL(createFilmwebAPIUrl(PERSONS_METHOD, filmwebId, String.valueOf(role.getApiNumber()), "0", "9999")), StandardCharsets.UTF_8);
				if (!personsResponse.startsWith("ok"))
					throw new FilmwebException("API call didn't return ok");
				int firstBracketPos = personsResponse.indexOf('[');
				if (firstBracketPos == -1)
					throw new FilmwebException("API call didn't return proper JSON. It probably returned an exception.");

				ObjectMapper mapper = new ObjectMapper();
				Object[][] actors = mapper.readValue(personsResponse.substring(firstBracketPos, personsResponse.lastIndexOf(']') + 1), Object[][].class);
				int i = 1;
				for (Object[] actor : actors) {
					FilmographyEntry entry = new FilmographyEntry();
					Optional<Person> person = personRepository.findByFilmwebId(String.valueOf(actor[0]));
					if (person.isPresent()) {
						entry.setPerson(person.get());
					} else {
						Person p = new Person();
						p.setFilmwebId(String.valueOf(actor[0]));
						p.setName((String) actor[3]);

						if (actor[4] != null) {
							String filmwebImg = "http://1.fwcdn.pl/p" + actor[4];
							try (InputStream is = new URL(filmwebImg).openStream()) {
								String filename = DigestUtils.md5Hex(p.getName() + filmwebImg) + ".jpg";
								try (OutputStream os = new FileOutputStream(IMAGES + filename)) {
									IOUtils.copy(is, os);
								}
								p.setAvatarFilename(filename);
							}
							catch (FileNotFoundException e) {
								System.err.println("COULDN'T FIND OR SAVE PERSON IMAGE\n" + e);
							}
						}

						personRepository.save(p);
						entry.setPerson(p);
					}
					entry.setName((String) actor[1]);
					entry.setAttributes((String) actor[2]);
					entry.setRole(role.toString());
					entry.setContent(content);
					entry.setNumber(i++);
					filmographyEntries.add(entry);
				}
			} catch (IOException e) {
				throw new FilmwebException(e);
			}
		}
		return filmographyEntries;
	}

	private String createFilmwebAPIUrl(String methodName, String... args) {
		String url = "https://ssl.filmweb.pl/api?";
		final String VERSION = "1.0";
		final String KEY = "qjcGhW2JnvGT9dfCt3uT_jozR3s";
		final String METHOD = methodName + " [" + String.join(",", args) + "]\\n";
		final String HASH = DigestUtils.md5Hex(METHOD + "android" + KEY);
		try {
			url += "methods=" + URLEncoder.encode(METHOD, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError("This will never happen");
		}
		url += "&signature=" + VERSION + "," + HASH;
		url += "&version=" + VERSION;
		url += "&appId=android";
		return url;
	}

}



/*film.setFilmwebId(filmwebId);

		Document doc = Jsoup.connect("http://www.filmweb.pl/film?id=" + filmwebId).get();
		// This try-catch block is here in place of ~10 null-checks at every Element::select match.
		// Normally try-catch shouldn't be used for flow control, but here's why I did use it anyway:
		// - Multitude of null checks that would reduce code readability drastically
		// - NullPointerException happen when the website format changed, so it actually is
		//   an exceptional situation (We do rethrow the exception in the catch block after all)
		try {
			Element filmHeader = doc.select(".filmMainHeader").first();
			Elements filmInfoTable = filmHeader.select(".filmInfo table");
			String filmInfoScript = doc.select("script[data-type=\"setfilm\"]").first().html();

			// title
			film.setTitle(filmHeader.select("h1.filmTitle").first().text());
			// year
			Matcher yearMatcher = Pattern.compile(".*?(\\d{4}).*").matcher(filmHeader.select("h1.filmTitle + span").first().text());
			if (yearMatcher.find())
				film.setYear(Integer.parseInt(yearMatcher.group(1)));
			else
				throw new WebScraperException(WEB_SCRAPER_EXCEPTION_MSG);
			// original title
			Elements originalTitle = filmHeader.select("h2.cap.s-16.top-5");
			if (!originalTitle.isEmpty())
				film.setOriginalTitle(originalTitle.first().text());
			// running time
			Matcher runningTimeMatcher = Pattern.compile("duration:\"(\\d+)\"").matcher(filmInfoScript);
			if (runningTimeMatcher.find())
				film.setRunningTime(Integer.parseInt(runningTimeMatcher.group(1)));
			else
				throw new WebScraperException(WEB_SCRAPER_EXCEPTION_MSG);
			// directors
			List<String> directors = filmInfoTable.select("tr:nth-child(1) li a")
					.stream().map(Element::text).collect(Collectors.toList());
			// screenwriters
			List<String> screenwriters = filmHeader.select("tr:nth-child(2) li a")
					.stream().map(Element::text).collect(Collectors.toList());
			// categories
			List<Integer> categories = filmHeader.select("tr:nth-child(4) li a")
					.stream().map(e -> {
						String href = e.attr("href");
						return Integer.parseInt(href.substring(href.lastIndexOf('=') + 1));
					}).collect(Collectors.toList());
			// production countries
			List<Integer> productionCountries = filmHeader.select("tr:nth-child(5) li a")
					.stream().map(e -> {
						String href = e.attr("href");
						return Integer.parseInt(href.substring(href.lastIndexOf('=') + 1));
					}).collect(Collectors.toList());
			// premieres
			Elements premieres = filmHeader.select("tr:nth-child(6) td span");
			LocalDate localPremiere;

		}
		catch (NullPointerException e) {
			throw new WebScraperException(WEB_SCRAPER_EXCEPTION_MSG);
		}*/
