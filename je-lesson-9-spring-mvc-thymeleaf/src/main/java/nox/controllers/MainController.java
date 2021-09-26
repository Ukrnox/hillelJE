package nox.controllers;

import lombok.extern.slf4j.Slf4j;
import nox.entities.CityEntity;
import nox.entities.CountryEntity;
import nox.entities.RegionEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/")
public class MainController {

    @GetMapping
    public String getMainPage(Model model) {
        model.addAttribute("city", new CityEntity());
        model.addAttribute("country", new CountryEntity());
        model.addAttribute("region", new RegionEntity());
        return "main";
    }
}
