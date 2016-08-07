package pl.ultrakino.bots;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;
import pl.ultrakino.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static pl.ultrakino.model.LanguageVersion.*;

/**
 * This has a lot of ugly code, my excuse being it's a one-timer.
 * After I run it, I delete it and forget about it.
 */
@SuppressWarnings("Duplicates")
public class ImportDB {

	private EntityManagerFactory emf;
	private EntityManager em;

	public static void main(String[] args) {
		ImportDB qwe = new ImportDB();
//		qwe.deleteAll();
		qwe.importFirst();
		qwe.importSecond();
		qwe.cleanup();
	}

	private ImportDB() {
		emf = Persistence.createEntityManagerFactory("dev");
		em = emf.createEntityManager();
	}

	private void cleanup() {
		em.close();
		emf.close();
	}

	private void deleteAll() {
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE TABLE contents CASCADE").executeUpdate();
		em.getTransaction().commit();
	}

	private boolean isInt(String s) {
		try {
			//noinspection ResultOfMethodCallIgnored
			Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private void loadCommonStuff(Map<String, Object> map, List<String> xfields, Film film) {
		String description = (String) map.get("short_story");
		film.setDescription(description.substring(0, Math.min(250, description.length() - 1)));

		List<Integer> categories = Arrays.stream(((String) map.get("category")).split(","))
				.filter(this::isInt)
				.map(Integer::parseInt)
				.filter(cat -> cat != 2)
				.collect(Collectors.toList());
		film.setCategories(categories);


		String cover = xfields.get(0);
		film.setCoverFilename("images/covers" + cover.substring(cover.lastIndexOf('/')));

		film.setYear(Integer.parseInt(xfields.get(1)));

		List<String> cast = Arrays.asList(xfields.get(3).split(","));
		for (String actorName : cast) {
			film.getCast().add(getPerson(actorName.trim()));
		}

		String originalTitle = xfields.get(4);
		if (!originalTitle.equals("alternative")) // If there even is original title
			film.setOriginalTitle(originalTitle);
	}

	private void setTitle(Film film, Map<String, Object> map) {
		String title = (String) map.get("title");
		film.setTitle(title.substring(0, title.lastIndexOf(" /")));
	}

	private boolean isDuplicate(Film film) {
		em.getTransaction().begin();
		Query q = em.createNativeQuery("SELECT EXISTS(SELECT * FROM films f WHERE f.title=:title LIMIT 1)");
		q.setParameter("title", film.getTitle());
		List list = q.getResultList();
		em.getTransaction().commit();
		return (Boolean) list.get(0);
	}

	private Person getPerson(String name) {
		em.getTransaction().begin();
		Query q = em.createQuery("FROM Person p WHERE p.name=:name");
		q.setParameter("name", name);
		List<Person> results = q.getResultList();
		int count = results.size();
		em.getTransaction().commit();
		if (count == 0) {
			Person person = new Person();
			person.setName(name);
			person.setRole(PersonRole.ACTOR);
			return person;
		}
		else {
			return results.get(0);
		}
	}

	private void importFirst() {
		Yaml yaml = new Yaml();
		try {
			InputStream f = new FileInputStream(new File("dle_post.yml"));
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> l = (List<Map<String, Object>>) yaml.load(f);
			for (Map<String, Object> map : l) {
				Film film = new Film();
				setTitle(film, map);
				if (isDuplicate(film))
					continue;

				List<String> xfields = Arrays.stream(((String)map.get("xfields")).split(Pattern.quote("||")))
						.map(xfield -> xfield.substring(xfield.lastIndexOf('|') + 1))
						.collect(Collectors.toList());


				String language = xfields.get(5);
				LanguageVersion lang = null;
				switch(language) {
					case "Lektor":
						lang = VOICE_OVER;
						break;
					case "Napisy PL":
						lang = POLISH_SUBS;
						break;
					case "Dubbing":
						lang = DUBBING;
						break;
					case "Napisy ENG":
						lang = ENGLISH_SUBS;
						break;
					case "Film polski":
						lang = POLISH_FILM;
						break;
					case "Oryginał":
						lang = ORIGINAL;
						break;
				}

				String full = (String) map.get("full_story");
				full = full.substring(6, full.lastIndexOf('['));

				Player player = new Player();
				player.setLanguageVersion(lang);
				film.getPlayers().add(player);

				if ((Integer) map.get("foreign_link") == 0) { // Link is on our openload
					HttpURLConnection connection = (HttpURLConnection)
							new URL("https://api.openload.co/1/remotedl/status?login=18464bc517c7cfc6&key=sQ3HEP_r&id=" + full)
									.openConnection();
					ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
					TypeReference typeRef = new TypeReference<HashMap<String, Object>>() {};
					Map<String, Object> response = objectMapper.readValue(connection.getInputStream(), typeRef);
					Collection result = (Collection)response.get("result");
					if (result.size() == 0) { // Link is lost.
						player.setSrc(full);
						player.setLostSrc(true);
					}
					else {
						String playerId = (String) ((Map)((Map)result).get(full)).get("extid");
						player.setSrc(playerId);
					}
				}
				else { // foreign link
					player.setSrc(full);
					player.setForeignSrc(true);
				}

				loadCommonStuff(map, xfields, film);
				persistFilm(film);
			}

			f.close();
		} catch (IOException e) {
			e.printStackTrace();
			cleanup();
		}
	}

	private void persistFilm(Film film) {
		em.getTransaction().begin();
		em.persist(film);
		em.getTransaction().commit();
	}

	private void importSecond() {
		Yaml yaml = new Yaml();
		try {
			InputStream f = new FileInputStream(new File("dle_post2.yml"));
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> l = (List<Map<String, Object>>) yaml.load(f);
			for (Map<String, Object> map : l) {
				Film film = new Film();
				setTitle(film, map);
				if (isDuplicate(film))
					continue;


				List<String> xfields = Arrays.stream(((String)map.get("xfields")).split(Pattern.quote("||")))
						.map(xfield -> xfield.substring(xfield.lastIndexOf('|') + 1))
						.collect(Collectors.toList());

				String full = (String) map.get("full_story");
				full = full.substring(6, full.lastIndexOf('['));
				String[] links = full.split(Pattern.quote("[/play]{PAGEBREAK}[play]"));

				for (String link : links) {
					String[] qwe = link.split(Pattern.quote("||"));
					String language = qwe[0];
					String src;
					try {
						src = qwe[1];
					} catch(IndexOutOfBoundsException e) {
						src = qwe[0];
						language = xfields.get(5);
					}

					LanguageVersion lang = null;
					switch(language) {
						case "LEK":
						case "Lektor":
							lang = VOICE_OVER;
							break;
						case "NAP":
						case "Napisy PL":
						case "Napisy":
							lang = POLISH_SUBS;
							break;
						case "DUB":
						case "Dubbing":
							lang = DUBBING;
							break;
						case "ORG":
						case "Napisy ENG":
							lang = ENGLISH_SUBS;
							break;
						case "PL":
						case "Film polski":
							lang = POLISH_FILM;
							break;
						case "ENG":
						case "Oryginał":
						case "Oryginalna":
							lang = ORIGINAL;
							break;
					}

					Player player = new Player();
					player.setLanguageVersion(lang);
					film.getPlayers().add(player);

					if ((Integer) map.get("foreign_link") == 0) { // Link is on our openload
						HttpURLConnection connection = (HttpURLConnection)
								new URL("https://api.openload.co/1/remotedl/status?login=18464bc517c7cfc6&key=sQ3HEP_r&id=" + src)
										.openConnection();
						ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
						TypeReference typeRef = new TypeReference<HashMap<String, Object>>() {};
						Map<String, Object> response = objectMapper.readValue(connection.getInputStream(), typeRef);
						try {
							Map result = (Map) response.get("result");
							String playerId = (String) ((Map)(result).get(src)).get("extid");
							player.setSrc(playerId);
						} catch(ClassCastException e) { // result is an (empty) ArrayList, and not Map so link is lost.
							player.setSrc(src);
							player.setLostSrc(true);
						}
					}
					else { // foreign link
						player.setSrc(src);
						player.setForeignSrc(true);
					}
				}

				loadCommonStuff(map, xfields, film);
				persistFilm(film);
			}

			f.close();
		} catch (IOException e) {
			e.printStackTrace();
			cleanup();
		}
	}

}
