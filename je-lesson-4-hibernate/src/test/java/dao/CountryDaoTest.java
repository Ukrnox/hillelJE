package dao;

import entities.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CountryDaoTest {
    static CountryDao countryDao = new CountryDao();
    ;
    private static Country country;
    private static Country country2;
    private static Country country3;

    @BeforeEach
    void beforeEach() {
        country = new Country("Test1", Collections.emptyList(), Collections.emptyList());
        country2 = new Country("Test2", Collections.emptyList(), Collections.emptyList());
        country3 = new Country("Test3", Collections.emptyList(), Collections.emptyList());
    }


    @Test
    public void create() {
        countryDao.create(country);
        assertEquals(country, countryDao.read(country.getId(), Country.class));
        countryDao.delete(country.getId(), Country.class);
    }

    @Test
    public void readById() {
        countryDao.create(country);
        assertEquals(country, countryDao.read(country.getId(), Country.class));
        countryDao.delete(country.getId(), Country.class);
    }

    @Test
    public void readAll() {
        List<Country> allCountries = new ArrayList<>();
        countryDao.create(country);
        countryDao.create(country2);
        countryDao.create(country3);
        allCountries.add(country);
        allCountries.add(country2);
        allCountries.add(country3);
        assertEquals(allCountries, countryDao.readAll(Country.class));
    }

    @Test
    public void readByName() {
        countryDao.create(country);
        assertEquals(country.getName(), countryDao.readByName("Test1", Country.class).stream().findFirst().orElse(null).getName());
        countryDao.delete(country.getId(), Country.class);
    }

    @Test
    public void update() {
        countryDao.create(country);
        country.setName("New name");
        countryDao.update(country);
        assertEquals(country, countryDao.read(country.getId(), Country.class));
        countryDao.delete(country.getId(), Country.class);
    }

    @Test
    public void delete() {
        countryDao.create(country);
        countryDao.delete(country.getId(), Country.class);
        assertNull(countryDao.read(country.getId(), Country.class));
    }
}