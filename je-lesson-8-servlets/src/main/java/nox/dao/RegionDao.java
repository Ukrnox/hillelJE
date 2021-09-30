package nox.dao;


import lombok.extern.slf4j.Slf4j;
import nox.entities.RegionEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Slf4j
@Repository
@Transactional
public class RegionDao extends Dao<RegionEntity> {
    @Autowired
    public RegionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
