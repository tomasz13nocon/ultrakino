package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.ContentComponent_;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Film_;
import pl.ultrakino.repository.FilmRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JpaFilmRepository implements FilmRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Film film) {
		em.persist(film);
	}

	@Override
	public Film findById(Integer id) throws NoRecordWithSuchIdException {
		if (id == null) throw new IllegalArgumentException("id must not be null.");
		Film film = em.find(Film.class, id);
		if (film == null) throw new NoRecordWithSuchIdException();
		return film;
	}

	@Override
	public List<Film> findRecommended() {
		TypedQuery<Film> q = em.createQuery("SELECT f FROM Film f ORDER BY f.recommendationDate DESC", Film.class);
		q.setMaxResults(10);
		return q.getResultList();
	}

	@Override
	public List<Film> findNewest() {
		TypedQuery<Film> q = em.createQuery("SELECT f FROM Film f JOIN FETCH Player p ON f.id=p.content.id ORDER BY p.additionDate DESC", Film.class);
		q.setMaxResults(10);
		return q.getResultList();
	}

	@Override
	public List<Film> findMostWatched() {
		TypedQuery<Film> q = em.createQuery("SELECT f FROM Film f ORDER BY f.episodeFilmComponent.views DESC", Film.class);
		q.setMaxResults(10);
		return q.getResultList();
	}

	@Override
	public List<Film> search(String query) {
		TypedQuery<Object[]> q = em.createQuery("SELECT " +
				"f.id," +
				"f.seriesFilmComponent.coverFilename," +
				"f.contentComponent.title," +
				"f.year," +
				"f.seriesFilmComponent.description " +
				"FROM Film f  WHERE " +
				"LOWER(f.contentComponent.title) LIKE :query OR " +
				"LOWER(f.seriesFilmComponent.originalTitle) LIKE :query", Object[].class);
		query = query.replaceAll(" ", "%");
		q.setParameter("query", "%" + query.toLowerCase() + "%");
		q.setMaxResults(5);
		List<Object[]> arrays = q.getResultList();
		List<Film> films = new ArrayList<>();
		for (Object[] arr : arrays) {
			Film film = new Film();
			film.setId((Integer) arr[0]);
			film.setCoverFilename((String) arr[1]);
			film.setTitle((String) arr[2]);
			film.setYear((Integer) arr[3]);
			film.setDescription((String) arr[4]);
			films.add(film);
		}
		return films;
	}

	@Override
	public List<Film> advancedSearch(MultiValueMap<String, String> params) {
		/*CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Film> cq = cb.createQuery(Film.class);
		Root<Film> root = cq.from(Film.class);
		cq.select(root);
		String title = params.get("title");
		TypedQuery<Film> q = em.createQuery(cq);
		if (title != null)
			cq.where(cb.like(root.get(), ""));*/

		String title = params.get("title").get(0);

		// Parse year values. If they're not parsable leave them with null.
		Integer yearLow = null;
		try {
			yearLow = Integer.parseInt(params.get("yearLow").get(0));
		} catch (NumberFormatException e) {}
		Integer yearHigh = null;
		try {
			yearHigh = Integer.parseInt(params.get("yearHigh").get(0));
		} catch (NumberFormatException e) {}

		List<Integer> categories = null;
		try {
			categories = params.get("categories").stream().map(Integer::parseInt).collect(Collectors.toList());
		}
		catch (NumberFormatException e) {}

		TypedQuery<Film> q = em.createQuery("SELECT f FROM Film f WHERE " +
				(title != null ? "LOWER(f.contentComponent.title) LIKE :title AND " : "") +
				(yearLow != null && yearHigh != null ? "f.year BETWEEN :yearLow AND :yearHigh " :
						(yearLow != null ?  :)) +
				"", Film.class);

		return q.getResultList();
	}
}

/*
title
yearLow
yearHigh
categories
personNames
version
pageNumber
 */















