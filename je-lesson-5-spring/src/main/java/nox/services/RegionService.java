package nox.services;

import nox.entities.Region;
import nox.repo.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> findAllRegions() {
        return regionRepository.findAll();
    }

    @Transactional
    public Region save(Region region) {
        return regionRepository.save(region);
    }

    public Optional<Region> findRegionById(Long regionId) {
        return regionRepository.findById(regionId);
    }

    public List<Region> findRegionByName(String name) {
        return regionRepository.findByName(name);
    }
}
