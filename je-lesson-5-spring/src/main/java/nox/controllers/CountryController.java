package nox.controllers;

import nox.entities.Country;
import nox.exceptions.ElementNotFound;
import nox.services.CountryService;
import nox.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/countries")
public class CountryController {
    private final CountryService countryService;
    private final ValidationService validationService;

    @Autowired
    public CountryController(CountryService countryService, ValidationService validationService) {
        this.countryService = countryService;
        this.validationService = validationService;
    }

    @GetMapping
    public List<Country> getAllCities() {
        List<Country> allCountries = countryService.findAllCountries();
        if (allCountries.isEmpty()) {
            throw new ElementNotFound("No countries in DB!");
        }
        return allCountries;
    }

    @GetMapping("/{countryId}")
    public Country getCountryById(@PathVariable("countryId") String countryId) {
        Long id = validationService.idValidation(countryId);
        return countryService.findCountryById(id).orElseThrow(() -> new ElementNotFound("No country with ID " + countryId + " in the DB"));
    }

    @GetMapping("/name/{countryName}")
    public List<Country> getCountryByName(@PathVariable("countryName") String countryName) {
        List<Country> countryByName = countryService.findCountryByName(countryName);
        if (countryByName.isEmpty()) {
            throw new ElementNotFound("No country with name " + countryName + " in the DB");
        }
        return countryByName;
    }

    @PostMapping
    public Country createNewCountry(@RequestBody Country countryJson) {
        Country newCountry = new Country();
        newCountry.setName(countryJson.getName());
        countryService.save(newCountry);
        return newCountry;
    }

    @ExceptionHandler
    public String handleElementNotFound(RuntimeException ex) {
        return ex.getMessage();
    }
}
