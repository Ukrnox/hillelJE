package nox.services;

import nox.entities.City;
import nox.repo.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    @Transactional
    public City save(City city) {
        return cityRepository.save(city);
    }

    public Optional<City> findCityById(Long cityId) {
        return cityRepository.findById(cityId);
    }

    public List<City> findCityByName(String name){
        return cityRepository.findByName(name);
    }
}
