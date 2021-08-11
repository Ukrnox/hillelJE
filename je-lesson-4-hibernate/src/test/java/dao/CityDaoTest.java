package dao;

import entities.City;
import hibernateutil.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CityDaoTest {
    private static CityDao cityDao = new CityDao();
    private static City city;
    private static City city2;
    private static City city3;

    @BeforeAll
    static void beforeAll() {
        city = new City("Test1", null, null);
        city2 = new City("Test2", null, null);
        city3 = new City("Test3", null, null);
    }

    @BeforeEach
    void setUp() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE cities").executeUpdate();
            transaction.commit();
        }
    }

    @Test
    public void create() {
        cityDao.create(city);
        assertEquals(city, cityDao.read(1, City.class));
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
        assertEquals(allCities, cityDao.readAll(City.class));
    }

    @Test
    public void readByName() {
        cityDao.create(city);
        assertEquals(city.getName(), cityDao.readByName("Test1", City.class).stream().findFirst().orElse(null).getName());
    }

    @Test
    public void update() {
        System.out.println(city);
        cityDao.create(city);
        System.out.println(city);
        city.setName("new name");
        cityDao.update(city);
        assertEquals(city, cityDao.read(city.getId(), City.class));
    }

    @Test
    public void delete() {
        cityDao.create(city);
        cityDao.delete(1, City.class);
        assertNull(cityDao.read(1, City.class));
    }
}