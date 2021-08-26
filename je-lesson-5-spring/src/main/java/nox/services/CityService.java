package nox.services;

import nox.entities.CityEntity;
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

    public List<CityEntity> findAllCities() {
        return cityRepository.findAll();
    }

    @Transactional
    public CityEntity save(CityEntity city) {
        return cityRepository.save(city);
    }

    public Optional<CityEntity> findCityById(Long cityId) {
        return cityRepository.findById(cityId);
    }

    public List<CityEntity> findCityByName(String name){
        return cityRepository.findByName(name);
    }
}
