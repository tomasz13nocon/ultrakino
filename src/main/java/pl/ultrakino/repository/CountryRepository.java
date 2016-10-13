package pl.ultrakino.repository;

import pl.ultrakino.model.Country;

import java.util.Optional;

public interface CountryRepository {

	Optional<Country> findByName(String name);

	Country save(Country country);
}
