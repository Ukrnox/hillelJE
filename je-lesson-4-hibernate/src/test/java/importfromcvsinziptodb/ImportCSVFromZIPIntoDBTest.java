package importfromcvsinziptodb;

import dao.CityDao;
import dao.CountryDao;
import dao.RegionDao;
import entities.City;
import entities.Country;
import entities.Region;
import hibernateutil.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportCSVFromZIPIntoDBTest {
    @Test
    void importCSVFromFileINCurrentDir() {
        try {
            new ImportCSVFromZIPIntoDB().importCSVFromFileINCurrentDir("import.zip");
        }
        catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        City city1 = new City(1, "Odesa", null, null);
        City city2 = new City(2, "Kiev", null, null);
        City city3 = new City(3, "Lviv", null, null);
        List<City> citiesList = new ArrayList<>();
        citiesList.add(city1);
        citiesList.add(city2);
        citiesList.add(city3);
        CityDao cityDao = new CityDao(sessionFactory);
        Region region1 = new Region(1, "Florida", null, null);
        Region region2 = new Region(2, "Georgia", null, null);
        Region region3 = new Region(3, "Alabama", null, null);
        Region region4 = new Region(4, "Arizona", null, null);
        List<Region> regionsList = new ArrayList<>();
        regionsList.add(region1);
        regionsList.add(region2);
        regionsList.add(region3);
        regionsList.add(region4);
        RegionDao regionDao = new RegionDao(sessionFactory);
        Country country1 = new Country(1, "USA", Collections.emptyList(), Collections.emptyList());
        Country country2 = new Country(2, "Ukraine", Collections.emptyList(), Collections.emptyList());
        Country country3 = new Country(3, "Bosnia", Collections.emptyList(), Collections.emptyList());
        Country country4 = new Country(4, "Bulgari", Collections.emptyList(), Collections.emptyList());
        List<Country> countriesList = new ArrayList<>();
        countriesList.add(country1);
        countriesList.add(country2);
        countriesList.add(country3);
        countriesList.add(country4);
        CountryDao countryDao = new CountryDao(sessionFactory);
        assertEquals(citiesList, cityDao.readAll(City.class));
        assertEquals(regionsList, regionDao.readAll(Region.class));
        assertEquals(countriesList, countryDao.readAll(Country.class));
    }
}