package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.*;
import pl.ultrakino.repository.ContentQuery;
import pl.ultrakino.repository.Page;
import pl.ultrakino.repository.SeriesRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class JpaSeriesRepository implements SeriesRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Series> findByTitleAndYear(String title, int year) {
		List<Series> series = em.createQuery("FROM Series WHERE title=:title AND year=:year", Series.class)
				.setParameter("title", title)
				.setParameter("year", year)
				.getResultList();
		if (series.isEmpty())
			return Optional.empty();
		return Optional.of(series.get(0));
	}

	@Override
	public Optional<Series> findByTvseriesonlineTitleAndYear(String tvseriesonlineTitle, int year) {
		List<Series> series = em.createQuery("FROM Series WHERE tvseriesonlineTitle=:tvseriesonlineTitle AND year=:year", Series.class)
				.setParameter("tvseriesonlineTitle", tvseriesonlineTitle)
				.setParameter("year", year)
				.getResultList();
		if (series.isEmpty())
			return Optional.empty();
		return Optional.of(series.get(0));
	}

	@Override
	public Series save(Series series) {
		em.persist(series);
		return series;
	}

	@Override
	public Series findById(int seriesId) throws NoRecordWithSuchIdException {
		List<Series> seriesList = em.createQuery("SELECT s FROM Series s " +
						"LEFT JOIN FETCH s.filmCategories " +
						"LEFT JOIN FETCH s.productionCountries " +
						"LEFT JOIN FETCH s.episodes " +
//						"LEFT JOIN FETCH s.ratings " +
						"LEFT JOIN FETCH s.castAndCrew cac " +
						"LEFT JOIN FETCH cac.person " +
						"WHERE s.id=:id ",
				Series.class)
				.setParameter("id", seriesId)
				.getResultList();
		if (seriesList.isEmpty())
			throw new NoRecordWithSuchIdException();
		return seriesList.get(0);
	}

	@Override
	public Page<Series> find(ContentQuery query) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// Query needed for fetch joins and pagination to work properly together.
		// We select the actual results alone (IDs only), properly paginated,
		// and then pass it to the fetching query which selects all the details and joins on collections.
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
		Root<Series> root = cq.from(Series.class);
		cq.select(root.get(Series_.id));

		List<Predicate> predicates = new ArrayList<>();

		// =========================== TITLE =========================== //
		String title = query.getTitle();
		if (title != null) {
			title = "%" + title.toLowerCase().replaceAll(" ", "%") + "%";
			predicates.add(cb.or(
					cb.like(cb.lower(root.get(Series_.title)), title),
					cb.like(cb.lower(root.get(Series_.originalTitle)), title)
			));
		}
		// ============================================================= //

		// =========================== RELEASE YEAR =========================== //
		Integer yearFrom = query.getYearFrom();
		Integer yearTo = query.getYearTo();
		if (yearFrom != null && yearTo != null) {
			predicates.add(cb.between(root.get(Series_.year), yearFrom, yearTo));
		}
		else if (yearFrom != null) {
			predicates.add(cb.ge(root.get(Series_.year), yearFrom));
		}
		else if (yearTo != null) {
			predicates.add(cb.le(root.get(Series_.year), yearTo));
		}
		// ==================================================================== //

		// =========================== CATEGORIES =========================== //
		List<Integer> categories = query.getCategories();
		if (categories != null) {
			predicates.add(root.join(Series_.seriesCategories).in(categories));
		}
		// ================================================================== //

		// =========================== PRODUCTION COUNTRIES =========================== //
		List<Integer> countries = query.getCountries();
		if (countries != null) {
			predicates.add(root.join(Series_.productionCountries).in(countries));
		}
		// ================================================================== //

		// =========================== ORDER BY =========================== //
		ContentQuery.OrderBy orderBy = query.getOrderBy();
		boolean asc = query.isAsc();
		Order order = null;
		if (orderBy != null) {
			Path p = null;
			switch (orderBy) {
				case ADDITION_DATE:
					p = root.get(Series_.updateDate);
				case PREMIERE:
					p = root.get(Series_.worldPremiere);
					break;
				case TITLE:
					p = root.get(Series_.title);
					break;
				case RECOMMENDATION_DATE:
					p = root.get(Series_.recommendationDate);
					predicates.add(root.get(Series_.recommendationDate).isNotNull());
					break;
			}
			if (p != null) {
				order = asc ? cb.asc(p) : cb.desc(p);
				cq.orderBy(order);
			}
		}
		// ================================================================ //


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
		Root<Series> countRoot = countCq.from(Series.class);
		if (categories != null)
			countRoot.join(Series_.seriesCategories);
		if (countries != null)
			countRoot.join(Series_.productionCountries);
		countCq.select(cb.count(countRoot));
		countCq.where(predicates.toArray(new Predicate[]{}));
		TypedQuery<Long> countQ = em.createQuery(countCq);
		int resultCount = countQ.getResultList().get(0).intValue();
		int pageCount = resultCount / resultLimit;
		if (resultCount % resultLimit != 0) // if 20 results (with 10 per page) then 2 pages, if 21 then 3 pages
			pageCount++;


		// Main query
		CriteriaQuery<Series> mainCq = cb.createQuery(Series.class);
		Root<Series> mainRoot = mainCq.from(Series.class);
		mainCq.select(mainRoot);
		mainCq.distinct(true);
		if (order != null)
			mainCq.orderBy(order);

		mainRoot.fetch(Series_.seriesCategories, JoinType.LEFT);
		mainRoot.fetch(Series_.productionCountries, JoinType.LEFT);
		mainCq.where(mainRoot.get(Series_.id).in(ids));
		TypedQuery<Series> mainQ = em.createQuery(mainCq);

		return new Page<>(mainQ.getResultList(), pageNumber, pageCount);
	}
}
