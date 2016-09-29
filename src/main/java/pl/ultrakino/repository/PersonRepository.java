package pl.ultrakino.repository;

import pl.ultrakino.model.Person;

import java.util.Optional;

public interface PersonRepository {

	Optional<Person> findByName(String name);

	Optional<Person> findByFilmwebId(String filmwebId);

	Person save(Person person);
}
