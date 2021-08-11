package dao;


import entities.Country;
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

public class CountryDaoTest {
    static CountryDao countryDao;
    static Connection connection;
    private static Country country;
    private static Country country2;
    private static Country country3;

    @BeforeAll
    static void beforeAll() {
        country = new Country(1, "Test1");
        country2 = new Country(2, "Test2");
        country3 = new Country(3, "Test3");
    }


    @BeforeEach
    void setUp() throws SQLException {
        connection = getConnection();
        createTables();
        countryDao = new CountryDao(connection);
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
        countryDao.create(country);
        assertEquals(country, countryDao.readById(1));
        countryDao.delete(1);
    }

    @Test
    public void readById() {
        countryDao.create(country);
        assertEquals(country, countryDao.readById(1));
    }

    @Test
    public void readAll() {
        List<Country> allCountries = new ArrayList<>();
        allCountries.add(country);
        allCountries.add(country2);
        allCountries.add(country3);
        countryDao.create(country);
        countryDao.create(country2);
        countryDao.create(country3);
        assertEquals(allCountries, countryDao.readAll());
    }

    @Test
    public void readByName() {
        countryDao.create(country);
        assertEquals(country, countryDao.readByName("Test1").stream().findFirst().orElse(null));
    }

    @Test
    public void update() {
        countryDao.create(country);
        Country updatedCountry = new Country(1, "Test2");
        countryDao.update(updatedCountry);
        assertEquals(updatedCountry, countryDao.readById(1));
    }

    @Test
    public void delete() {
        countryDao.create(country);
        countryDao.delete(1);
        assertNull(countryDao.readById(1));
    }
}