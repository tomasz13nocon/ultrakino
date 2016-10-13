package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Country;
import pl.ultrakino.repository.CountryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaCountryRepository implements CountryRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Country> findByName(String name) {
		List<Country> countries = em.createQuery(
				"FROM Country WHERE name=:name",
				Country.class)
				.setParameter("name", name)
				.getResultList();
		if (countries.isEmpty()) return Optional.empty();
		return Optional.of(countries.get(0));
	}

	@Override
	public Country save(Country country) {
		em.persist(country);
		return country;
	}
}
