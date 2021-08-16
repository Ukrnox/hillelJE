package dao;

import entities.City;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

@Slf4j
public class CityDao extends Dao<City> {
    public CityDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
