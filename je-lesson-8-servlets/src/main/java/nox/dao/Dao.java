package nox.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class Dao<T> {

    private final SessionFactory sessionFactory;

    public Dao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(T entity) {
        if (entity != null) {
            log.debug("method : 'create( {} )", entity.getClass().getSimpleName());
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                session.save(entity);

                session.getTransaction().commit();
            }
        }
    }

    public void saveAll(List<T> list) {
        list.forEach(this::create);
    }

    public Optional<T> read(long id, Class<T> tClass) {
        log.debug("method : 'read(long id, {} )", tClass.getSimpleName());
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(tClass, id));
        }
    }

    public void update(T entity) {
        if (entity != null) {
            log.debug("method : 'update( {} )", entity.getClass().getSimpleName());
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                session.update(entity);

                session.getTransaction().commit();
            }
        }
    }

    public boolean delete(long id, Class<T> tClass) {
        T entity;
        boolean result = false;
        log.debug("method : 'delete(long id, {} )", tClass.getSimpleName());
        try (Session session = sessionFactory.openSession()) {
            entity = session.get(tClass, id);
            if (entity != null) {
                session.beginTransaction();

                session.delete(entity);

                session.getTransaction().commit();
                result = true;
            }
        }
        return result;
    }

    public List<T> readAll(Class<T> tClass) {
        log.debug("method : 'readAll( {} )", tClass.getSimpleName());
        List<T> entities;
        try (Session session = sessionFactory.openSession()) {
            entities = session.createQuery("from " + tClass.getSimpleName()).list();
        }
        return entities;
    }

    public List<T> readByName(String name, Class<T> tClass) {
        log.debug("method : 'readByName(String name, {} )", tClass.getSimpleName());
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(tClass);
            Root<T> e = query.from(tClass);
            query.where(cb.equal(e.get("name"), name));
            return session.createQuery(query).getResultList();
        }
    }
}
