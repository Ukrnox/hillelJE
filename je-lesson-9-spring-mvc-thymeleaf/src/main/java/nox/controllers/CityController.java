package nox.controllers;

import lombok.extern.slf4j.Slf4j;
import nox.entities.CityEntity;
import nox.exceptions.ValidationException;
import nox.services.CityService;
import nox.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
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
    public String getAllCities(Model model) {
        List<CityEntity> allCities = cityService.findAllCities();
        model.addAttribute("information", allCities.isEmpty() ? "No cities in DB!" : allCities);
        return "main";
    }

    @GetMapping("/{cityId}")
    public String getCityById(@PathVariable("cityId") String cityId, Model model) {
        try {
            Long id = validationService.idValidation(cityId);
            Optional<CityEntity> cityById = cityService.findCityById(id);
            model.addAttribute("information", cityById.isPresent() ? cityById.get() : "No city with ID " + cityId + " in DB!");
        }
        catch (ValidationException e) {
            model.addAttribute("information", e.getMessage());
        }
        return "main";
    }

    @GetMapping("/name/{cityName}")
    public String getCityByName(@PathVariable("cityName") String cityName, Model model) {
        List<CityEntity> cityByName = cityService.findCityByName(cityName);
        model.addAttribute("information", cityByName.isEmpty() ? "No city with name " + cityName + " in DB!" : cityByName);
        return "main";
    }

    @PostMapping("/create")
    public String createNewCity(@ModelAttribute CityEntity cityEntity) {
        CityEntity newCity = new CityEntity();
        newCity.setName(cityEntity.getName());
        cityService.save(newCity);
        return "redirect:/cities/name/" + cityEntity.getName();
    }
}
