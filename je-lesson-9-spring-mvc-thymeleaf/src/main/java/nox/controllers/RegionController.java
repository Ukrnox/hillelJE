package nox.controllers;

import lombok.extern.slf4j.Slf4j;
import nox.entities.RegionEntity;
import nox.exceptions.ValidationException;
import nox.services.RegionService;
import nox.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(path = "/regions")
public class RegionController {

    private final RegionService regionService;
    private final ValidationService validationService;

    @Autowired
    public RegionController(RegionService regionService, ValidationService validationService) {
        this.regionService = regionService;
        this.validationService = validationService;
    }

    @GetMapping
    public String getAllRegions(Model model) {
        List<RegionEntity> allRegions = regionService.findAllRegions();
        model.addAttribute("information", allRegions.isEmpty() ? "No regions in DB!" : allRegions);
        return "main";
    }

    @GetMapping("/{regionId}")
    public String getRegionById(@PathVariable("regionId") String regionId, Model model) {
        try {
            Long id = validationService.idValidation(regionId);
            Optional<RegionEntity> regionById = regionService.findRegionById(id);
            model.addAttribute("information", regionById.isPresent()
                    ? regionById.get()
                    : "Region with id " + regionId + " not found!");
        }
        catch (ValidationException e) {
            model.addAttribute("information", e.getMessage());
        }
        return "main";
    }

    @GetMapping("/name/{regionName}")
    public String getRegionByName(@PathVariable("regionName") String regionName, Model model) {
        List<RegionEntity> regionByName = regionService.findRegionByName(regionName);
        model.addAttribute("information", regionByName.isEmpty()
                ? "No Regions with name " + regionName
                : regionByName);
        return "main";
    }

    @PostMapping("/create")
    public String createNewRegion(@ModelAttribute RegionEntity region) {
        String name = region.getName();
        RegionEntity newRegion = new RegionEntity();
        newRegion.setName(name);
        regionService.save(newRegion);
        return "redirect:/regions/name/" + name;
    }
}
