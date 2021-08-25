package nox.services;

import nox.entities.Country;
import nox.repo.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    @Transactional
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    public Optional<Country> findCountryById(Long countryId) {
        return countryRepository.findById(countryId);
    }

    public List<Country> findCountryByName(String name){
        return countryRepository.findByName(name);
    }
}
