package nox.dao;


import lombok.extern.slf4j.Slf4j;
import nox.entities.CityEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class CityDao extends Dao<CityEntity> {

    @Autowired
    public CityDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    @Transactional
    public void create(CityEntity entity) {
        super.create(entity);
    }

    @Override
    public Optional<CityEntity> read(long id, Class<CityEntity> cityEntityClass) {
        return super.read(id, cityEntityClass);
    }

    @Override
    @Transactional
    public void update(CityEntity entity) {
        super.update(entity);
    }

    @Override
    @Transactional
    public boolean delete(long id, Class<CityEntity> cityEntityClass) {
        return super.delete(id, cityEntityClass);
    }

    @Override
    public List<CityEntity> readAll(Class<CityEntity> cityEntityClass) {
        return super.readAll(cityEntityClass);
    }

    @Override
    public List<CityEntity> readByName(String name, Class<CityEntity> cityEntityClass) {
        return super.readByName(name, cityEntityClass);
    }
}
