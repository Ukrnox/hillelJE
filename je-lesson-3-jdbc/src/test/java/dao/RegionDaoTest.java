package dao;


import entities.Country;
import entities.Region;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tablecreator.CreateTablesFromEntities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RegionDaoTest {
    private static RegionDao regionDao;
    private static Connection connection;
    private static Region region;
    private static Region region2;
    private static Region region3;

    @BeforeAll
    static void beforeAll() {
        region = new Region(1, "Test");
        region2 = new Region(2, "Test");
        region3 = new Region(3, "Test");
    }

    @BeforeEach
    void setUp() throws SQLException {
        connection = getConnection();
        createTables();
        regionDao = new RegionDao(connection);
    }

    private static void createTables() {
        try {
            new CreateTablesFromEntities(connection).createTablesFromEntities();
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:file:./test");
    }

    @Test
    public void create() {
        regionDao.create(region);
        assertEquals(region, regionDao.readById(1));
        regionDao.delete(1);
    }

    @Test
    public void readById() {
        regionDao.create(region);
        assertEquals(region, regionDao.readById(1));
    }

    @Test
    public void readAll() {
        List<Region> allRegions = new ArrayList<>();
        allRegions.add(region);
        allRegions.add(region2);
        allRegions.add(region3);
        regionDao.create(region);
        regionDao.create(region2);
        regionDao.create(region3);
        assertEquals(allRegions, regionDao.readAll());
    }

    @Test
    public void readByName() {
        regionDao.create(region);
        assertEquals(region, regionDao.readByName("Test").stream().findFirst().orElse(null));
    }

    @Test
    public void update() {
        regionDao.create(region);
        Region updatedRegion = new Region(1, "Test2");
        regionDao.update(updatedRegion);
        assertEquals(updatedRegion, regionDao.readById(1));
    }

    @Test
    public void delete() {
        regionDao.create(region);
        regionDao.delete(1);
        assertNull(regionDao.readById(1));
    }
}