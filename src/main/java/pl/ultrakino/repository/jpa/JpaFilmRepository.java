package pl.ultrakino.repository.jpa;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Film_;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.Player_;
import pl.ultrakino.repository.FilmQuery;
import pl.ultrakino.repository.FilmRepository;
import pl.ultrakino.repository.Page;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class JpaFilmRepository implements FilmRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Film film) {
		em.persist(film);
	}

	@Override
	public Film findById(int id) throws NoRecordWithSuchIdException {
		List<Film> filmResults = em.createQuery("FROM Film f " +
						"LEFT JOIN FETCH f.players p " +
						"LEFT JOIN FETCH f.ratings " +
						"LEFT JOIN FETCH f.comments c " +
						"LEFT JOIN FETCH f.castAndCrew cac " +
						"LEFT JOIN FETCH cac.person " +
						"WHERE f.id=:id ",
				Film.class)
				.setParameter("id", id)
				.getResultList();
		if (filmResults.isEmpty()) throw new NoRecordWithSuchIdException();
		return filmResults.get(0);
	}



	@Override
	public Page<Film> find(FilmQuery query) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// Query needed for fetch joins and pagination to work properly together.
		// We select the actual results alone (IDs only), properly paginated,
		// and then pass it to the fetching query which selects all the details and joins on collections.
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
		Root<Film> root = cq.from(Film.class);
		cq.select(root.get(Film_.id));

		List<Predicate> predicates = new ArrayList<>();

		// =========================== TITLE =========================== //
		String title = query.getTitle();
		if (title != null) {
			title = "%" + title.toLowerCase().replaceAll(" ", "%") + "%";
			predicates.add(cb.or(
					cb.like(cb.lower(root.get(Film_.title)), title),
					cb.like(cb.lower(root.get(Film_.originalTitle)), title)
			));
		}
		// ============================================================= //

		// =========================== RELEASE YEAR =========================== //
		Integer yearFrom = query.getYearFrom();
		Integer yearTo = query.getYearTo();
		if (yearFrom != null && yearTo != null) {
			predicates.add(cb.between(root.get(Film_.year), yearFrom, yearTo));
		}
		else if (yearFrom != null) {
			predicates.add(cb.ge(root.get(Film_.year), yearFrom));
		}
		else if (yearTo != null) {
			predicates.add(cb.le(root.get(Film_.year), yearTo));
		}
		// ==================================================================== //

		// =========================== CATEGORIES =========================== //
		List<Integer> categories = query.getCategories();
		if (categories != null) {
			predicates.add(root.join(Film_.categories).in(categories));
		}
		// ================================================================== //

		// =========================== LANGUAGE VERSIONS =========================== //
		Set<Player.LanguageVersion> versions = query.getVersions();
		if (versions != null) {
			Join<Film, Player> playersJoin = root.join(Film_.players);
			predicates.add(playersJoin.get(Player_.languageVersion).in(versions));
		}
		// ========================================================================= //

		// =========================== ORDER BY =========================== //
		FilmQuery.OrderBy orderBy = query.getOrderBy();
		boolean asc = query.isAsc();
		Order order = null;
		if (orderBy != null) {
			Path p = null;
			switch (orderBy) {
				case ADDITION_DATE:
					p = root.get(Film_.additionDate);
					break;
				case PREMIERE:
					p = root.get(Film_.worldPremiere);
					break;
				case TITLE:
					p = root.get(Film_.title);
					break;
				case RECOMMENDATION_DATE:
					p = root.get(Film_.recommendationDate);
					predicates.add(root.get(Film_.recommendationDate).isNotNull());
					break;
				case VIEWS:
					p = root.get(Film_.views);
					break;
			}
			order = asc ? cb.asc(p) : cb.desc(p);
			cq.orderBy(order);
		}
		// Else we might want to handle the case, when there is no orderBy but we still want to respect the asc parameter
		// Not necessary right now
		// ================================================================ //


		/*
		TODO:
		personNames
		 */

		cq.where(predicates.toArray(new Predicate[]{}));
		TypedQuery<Integer> q = em.createQuery(cq);

		// =========================== RESULT LIMIT =========================== //
		Integer resultLimit = query.getResultLimit();
		if (resultLimit == null)
			resultLimit = 10;
		q.setMaxResults(resultLimit);
		// ==================================================================== //

		// =========================== RESULT OFFSET / PAGINATION =========================== //
		Integer pageNumber = query.getPageNumber();
		if (pageNumber != null) {
			q.setFirstResult(pageNumber * resultLimit);
		}
		else {
			pageNumber = 0;
		}
		// ================================================================================== //

		List<Integer> ids = q.getResultList();
		if (ids.isEmpty())
			return new Page<>();

		CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
		Root<Film> countRoot = countCq.from(Film.class);
		if (categories != null)
			countRoot.join(Film_.categories);
		if (versions != null) {
			countRoot.join(Film_.players);
		}
		countCq.select(cb.count(countRoot));
		countCq.where(predicates.toArray(new Predicate[]{}));
		TypedQuery<Long> countQ = em.createQuery(countCq);
		int resultCount = countQ.getResultList().get(0).intValue();
		int pageCount = resultCount / resultLimit;
		if (resultCount % resultLimit != 0) // if 20 results (with 10 per page) then 2 pages, if 21 then 3 pages
			pageCount++;


		// Main query
		CriteriaQuery<Film> mainCq = cb.createQuery(Film.class);
		Root<Film> mainRoot = mainCq.from(Film.class);
		mainCq.select(mainRoot);
		mainCq.distinct(true);
		if (order != null)
			mainCq.orderBy(order);

		mainRoot.fetch(Film_.players, JoinType.LEFT);
		mainRoot.fetch(Film_.categories, JoinType.LEFT);
		mainCq.where(mainRoot.get(Film_.id).in(ids));
		TypedQuery<Film> mainQ = em.createQuery(mainCq);

		return new Page<>(mainQ.getResultList(), pageNumber, pageCount);
	}

	@Override
	public Film save(Film film) {
		em.unwrap(Session.class).saveOrUpdate(film);
		return film;
	}

}
