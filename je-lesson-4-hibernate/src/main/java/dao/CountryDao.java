package dao;

import entities.Country;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

@Slf4j
public class CountryDao extends Dao<Country> {
    public CountryDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
