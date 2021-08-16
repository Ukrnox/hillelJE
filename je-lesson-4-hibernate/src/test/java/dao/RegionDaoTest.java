package dao;

import entities.Region;
import hibernateutil.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RegionDaoTest {
    private static RegionDao regionDao = new RegionDao(HibernateUtil.getSessionFactory());
    private static Region region;
    private static Region region2;
    private static Region region3;

    @BeforeEach
    void beforeEach() {
        region = new Region("Test", null, null);
        region2 = new Region("Test", null, null);
        region3 = new Region("Test", null, null);
    }

    @Test
    public void create() {
        regionDao.create(region);
        assertEquals(region, regionDao.read(region.getId(), Region.class).orElse(null));
        regionDao.delete(region.getId(), Region.class);
    }

    @Test
    public void readById() {
        regionDao.create(region);
        assertEquals(region, regionDao.read(region.getId(), Region.class).orElse(null));
        regionDao.delete(region.getId(), Region.class);
    }

    @Test
    public void readAll() {
        List<Region> allRegions = new ArrayList<>();
        regionDao.create(region);
        regionDao.create(region2);
        regionDao.create(region3);
        allRegions.add(region);
        allRegions.add(region2);
        allRegions.add(region3);
        assertEquals(allRegions, regionDao.readAll(Region.class));
    }

    @Test
    public void readByName() {
        regionDao.create(region);
        Optional<Region> regionOptional = regionDao.readByName("Test", Region.class).stream().findFirst();
        assertEquals(RegionDaoTest.region.getName(), regionOptional.map(Region::getName).orElse(null));
        regionDao.delete(RegionDaoTest.region.getId(), Region.class);
    }

    @Test
    public void update() {
        regionDao.create(region);
        region.setName("New name");
        regionDao.update(region);
        assertEquals(region, regionDao.read(region.getId(), Region.class).orElse(null));
        regionDao.delete(region.getId(), Region.class);
    }

    @Test
    public void delete() {
        regionDao.create(region);
        regionDao.delete(region.getId(), Region.class);
        assertNull(regionDao.read(region.getId(), Region.class).orElse(null));
    }
}