package nox.controllers;

import nox.entities.RegionEntity;
import nox.exceptions.ElementNotFound;
import nox.services.RegionService;
import nox.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public List<RegionEntity> getAllRegions() {
        List<RegionEntity> allRegions = regionService.findAllRegions();
        if (allRegions.isEmpty()) {
            throw new ElementNotFound("No regions in DB!");
        }
        return regionService.findAllRegions();
    }

    @GetMapping("/{regionId}")
    public RegionEntity getRegionById(@PathVariable("regionId") String regionId) {
        Long id = validationService.idValidation(regionId);
        return regionService.findRegionById(id)
                .orElseThrow(() -> new ElementNotFound("Region with id " + regionId + " not found!"));
    }

    @GetMapping("/name/{regionName}")
    public List<RegionEntity> getRegionByName(@PathVariable("regionName") String regionName) {
        List<RegionEntity> regionByName = regionService.findRegionByName(regionName);
        if (regionByName.isEmpty()) {
            throw new ElementNotFound("No Regions with name " + regionName);
        }
        return regionByName;
    }

    @PostMapping("/")
    public RegionEntity createNewRegion(@RequestBody RegionEntity regionJson) {
        RegionEntity newRegion = new RegionEntity();
        newRegion.setName(regionJson.getName());
        regionService.save(newRegion);
        return newRegion;
    }

    @ExceptionHandler
    public String handleElementNotFound(RuntimeException ex) {
        return ex.getMessage();
    }
}
