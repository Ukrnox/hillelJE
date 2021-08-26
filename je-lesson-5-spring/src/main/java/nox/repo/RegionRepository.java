package nox.repo;

import nox.entities.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {
    List<RegionEntity> findByName(String name);
}
