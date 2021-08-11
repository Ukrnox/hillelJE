package importfromcvsinziptodb;

import dao.CityDao;
import dao.CountryDao;
import dao.RegionDao;
import entities.City;
import entities.Country;
import entities.Region;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportCSVFromZIPIntoDBTest {

    @Test
    void importCSVFromFileINCurrentDir() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:file:./test");
            new ImportCSVFromZIPIntoDB(connection).importCSVFromFileINCurrentDir("import.zip");
        }
        catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        City city1 = new City(1, "Odesa");
        City city2 = new City(2, "Kiev");
        City city3 = new City(3, "Lviv");
        List<City> citiesList = new ArrayList<>();
        citiesList.add(city1);
        citiesList.add(city2);
        citiesList.add(city3);
        CityDao cityDao = new CityDao(connection);
        Region region1 = new Region(1, "Florida");
        Region region2 = new Region(2, "Georgia");
        Region region3 = new Region(3, "Alabama");
        Region region4 = new Region(4, "Arizona");
        List<Region> regionsList = new ArrayList<>();
        regionsList.add(region1);
        regionsList.add(region2);
        regionsList.add(region3);
        regionsList.add(region4);
        RegionDao regionDao = new RegionDao(connection);
        Country country1 = new Country(1, "USA");
        Country country2 = new Country(2, "Ukraine");
        Country country3 = new Country(3, "Bosnia");
        Country country4 = new Country(4, "Bulgari");
        List<Country> countriesList = new ArrayList<>();
        countriesList.add(country1);
        countriesList.add(country2);
        countriesList.add(country3);
        countriesList.add(country4);
        CountryDao countryDao = new CountryDao(connection);
        assertEquals(citiesList, cityDao.readAll());
        assertEquals(regionsList, regionDao.readAll());
        assertEquals(countriesList, countryDao.readAll());
    }
}