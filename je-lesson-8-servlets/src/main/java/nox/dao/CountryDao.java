package nox.dao;


import lombok.extern.slf4j.Slf4j;
import nox.entities.CountryEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class CountryDao extends Dao<CountryEntity> {
    @Autowired
    public CountryDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public void create(CountryEntity entity) {
        super.create(entity);
    }

    @Override
    @Transactional
    public void saveAll(List<CountryEntity> list) {
        super.saveAll(list);
    }

    @Override
    public Optional<CountryEntity> read(long id, Class<CountryEntity> countryEntityClass) {
        return super.read(id, countryEntityClass);
    }

    @Override
    @Transactional
    public void update(CountryEntity entity) {
        super.update(entity);
    }

    @Override
    @Transactional
    public boolean delete(long id, Class<CountryEntity> countryEntityClass) {
        return super.delete(id, countryEntityClass);
    }

    @Override
    public List<CountryEntity> readAll(Class<CountryEntity> countryEntityClass) {
        return super.readAll(countryEntityClass);
    }

    @Override
    public List<CountryEntity> readByName(String name, Class<CountryEntity> countryEntityClass) {
        return super.readByName(name, countryEntityClass);
    }
}
