package dao;


import entities.City;
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

public class CityDaoTest {
    private static CityDao cityDao;
    private static Connection connection;
    private static City city;
    private static City city2;
    private static City city3;

    @BeforeAll
    static void beforeAll() {
        city = new City(1, "Test1");
        city2 = new City(2, "Test2");
        city3 = new City(3, "Test3");
    }

    @BeforeEach
    void setUp() throws SQLException {
        connection = getConnection();
        createTables();
        cityDao = new CityDao(connection);
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
        cityDao.create(city);
        assertEquals(city, cityDao.readById(1));
        cityDao.delete(1);
    }

    @Test
    public void readById() {
        cityDao.create(city);
        assertEquals(city, cityDao.readById(1));
    }

    @Test
    public void readAll() {
        List<City> allCities = new ArrayList<>();
        allCities.add(city);
        allCities.add(city2);
        allCities.add(city3);
        cityDao.create(city);
        cityDao.create(city2);
        cityDao.create(city3);
        assertEquals(allCities, cityDao.readAll());
    }

    @Test
    public void readByName() {
        cityDao.create(city);
        assertEquals(city, cityDao.readByName("Test1").stream().findFirst().orElse(null));
    }

    @Test
    public void update() {
        cityDao.create(city);
        City updatedCity = new City(1, "Test2");
        cityDao.update(updatedCity);
        assertEquals(updatedCity, cityDao.readById(1));
    }

    @Test
    public void delete() {
        cityDao.create(city);
        cityDao.delete(1);
        assertNull(cityDao.readById(1));
    }
}