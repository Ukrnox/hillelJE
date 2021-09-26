package nox.services;

import lombok.extern.slf4j.Slf4j;
import nox.entities.RegionEntity;
import nox.repo.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RegionService {
    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<RegionEntity> findAllRegions() {
        return regionRepository.findAll();
    }

    @Transactional
    public RegionEntity save(RegionEntity region) {
        return regionRepository.save(region);
    }

    public Optional<RegionEntity> findRegionById(Long regionId) {
        return regionRepository.findById(regionId);
    }

    public List<RegionEntity> findRegionByName(String name) {
        return regionRepository.findByName(name);
    }
}
