package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Person;
import pl.ultrakino.repository.PersonRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class JpaPersonRepository implements PersonRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Person> findByName(String name) {
		return Optional.of(
				(Person)
				em.createQuery("SELECT p FROM Person p WHERE p.name=:name")
				.setParameter("name", name)
				.getResultList().get(0));
	}

}
