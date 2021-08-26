package nox.controllers;

import nox.entities.CityEntity;
import nox.exceptions.ElementNotFound;
import nox.services.CityService;
import nox.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cities")
public class CityController {
    private final CityService cityService;
    private final ValidationService validationService;

    @Autowired
    public CityController(CityService cityService, ValidationService validationService) {
        this.cityService = cityService;
        this.validationService = validationService;
    }

    @GetMapping
    public List<CityEntity> getAllCities() {
        List<CityEntity> allCities = cityService.findAllCities();
        if (allCities.isEmpty()) {
            throw new ElementNotFound("No cities in DB!");
        }
        return allCities;
    }

    @GetMapping("/{cityId}")
    public CityEntity getCityById(@PathVariable("cityId") String cityId) {
        Long id = validationService.idValidation(cityId);
        return cityService.findCityById(id).orElseThrow(() -> new ElementNotFound("No city with ID " + cityId + " in DB!"));
    }

    @GetMapping("/name/{cityName}")
    public List<CityEntity> getCityByName(@PathVariable("cityName") String cityName) {
        List<CityEntity> cityByName = cityService.findCityByName(cityName);
        if (cityByName.isEmpty()) {
            throw new ElementNotFound("No city with name " + cityName + " in DB!");
        }
        return cityByName;
    }

    @PostMapping("/")
    public CityEntity createNewCity(@RequestBody CityEntity cityJson) {
        CityEntity newCity = new CityEntity();
        newCity.setName(cityJson.getName());
        cityService.save(newCity);
        return newCity;
    }

    @ExceptionHandler
    public String handleElementNotFound(RuntimeException ex) {
        return ex.getMessage();
    }
}
