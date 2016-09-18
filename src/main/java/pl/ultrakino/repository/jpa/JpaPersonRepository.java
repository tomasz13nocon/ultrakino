package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Person;
import pl.ultrakino.repository.PersonRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaPersonRepository implements PersonRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Person> findByName(String name) {
		List<Person> list = em
				.createQuery("SELECT p FROM Person p WHERE p.name=:name", Person.class)
				.setParameter("name", name)
				.getResultList();
		return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
	}

	@Override
	public Optional<Person> findByFilmwebId(String filmwebId) {
		List<Person> list = em
				.createQuery("SELECT p FROM Person p WHERE p.filmwebId=:filmwebId", Person.class)
				.setParameter("filmwebId", filmwebId)
				.getResultList();
		return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
	}

}
