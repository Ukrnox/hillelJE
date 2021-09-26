package nox.controllers;

import lombok.extern.slf4j.Slf4j;
import nox.entities.CountryEntity;
import nox.exceptions.ValidationException;
import nox.services.CountryService;
import nox.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
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
    public String getAllCountries(Model model) {
        List<CountryEntity> allCountries = countryService.findAllCountries();
        model.addAttribute("information", allCountries.isEmpty() ? "No countries in DB!" : allCountries);
        return "main";
    }

    @GetMapping("/{countryId}")
    public String getCountryById(@PathVariable("countryId") String countryId, Model model) {
        try {
            Long id = validationService.idValidation(countryId);
            Optional<CountryEntity> countryById = countryService.findCountryById(id);
            model.addAttribute("information", countryById.isPresent()
                    ? countryById.get()
                    : "No country with ID " + countryId + " in the DB");
        }
        catch (ValidationException e) {
            model.addAttribute("information", e.getMessage());
        }
        return "main";
    }

    @GetMapping("/name/{countryName}")
    public String getCountryByName(@PathVariable("countryName") String countryName, Model model) {
        List<CountryEntity> countryByName = countryService.findCountryByName(countryName);
        model.addAttribute("information", countryByName.isEmpty()
                ? "No country with name " + countryName + " in the DB"
                : countryByName);
        return "main";
    }

    @PostMapping("/create")
    public String createNewCountry(@ModelAttribute CountryEntity country) {
        CountryEntity newCountry = new CountryEntity();
        newCountry.setName(country.getName());
        countryService.save(newCountry);
        return "redirect:/countries/name/" + country.getName();
    }
}
