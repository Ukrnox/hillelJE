package nox.services;

import lombok.extern.slf4j.Slf4j;
import nox.entities.CountryEntity;
import nox.repo.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<CountryEntity> findAllCountries() {
        return countryRepository.findAll();
    }

    @Transactional
    public CountryEntity save(CountryEntity country) {
        return countryRepository.save(country);
    }

    public Optional<CountryEntity> findCountryById(Long countryId) {
        return countryRepository.findById(countryId);
    }

    public List<CountryEntity> findCountryByName(String name){
        return countryRepository.findByName(name);
    }
}
