package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.Country;
import pl.ultrakino.repository.CountryRepository;
import pl.ultrakino.service.CountryService;

import java.util.Optional;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

	private CountryRepository countryRepository;

	@Autowired
	public CountryServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	public Country getCountry(String name) {
		Optional<Country> op = countryRepository.findByName(name);
		if (op.isPresent())
			return op.get();
		else {
			Country country = new Country(name);
			return countryRepository.save(country);
		}
	}
}
