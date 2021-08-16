package dao;

import entities.Region;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

@Slf4j
public class RegionDao extends Dao<Region> {
    public RegionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
