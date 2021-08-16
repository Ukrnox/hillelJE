package dao;

import entities.City;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CityDao implements Dao<City> {

    private Connection connection;
    private final String CITY_INSERT = "INSERT INTO cities(name) VALUES (?)";
    private final String CITY_SELECT = "SELECT * from cities WHERE ID =";
    private final String CITY_UPDATE = "UPDATE cities SET name = ? WHERE id = ?";
    private final String CITY_DELETE = "DELETE FROM cities WHERE id = ";

    private final String FIND_ALL_CITIES = "SELECT * from cities LIMIT 500";

    private final String CITY_SELECT_BY_NAME = "SELECT *  FROM cities WHERE name = ";

    public CityDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(City entity) {
        log.info(this.getClass().getName() + " method : 'create(City entity)'");
        try (PreparedStatement preparedStatement = connection.prepareStatement(CITY_INSERT)) {
            preparedStatement.setString(1, entity.getName());
            log.info(preparedStatement.toString());
            preparedStatement.execute();
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Optional<City> readById(long id) {
        log.info(this.getClass().getName() + " method : 'readById(long id)'");
        return read(CITY_SELECT + id).stream().findFirst();
    }

    private List<City> read(String sql) {
        log.info(this.getClass().getName() + " method : 'read(String sql)'");
        List<City> cities = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            log.info(sql);
            while (rs.next()) {
                cities.add(new City(rs.getInt("id"), rs.getString("name")));
            }
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return cities;
    }

    public List<City> readAll() {
        log.info(this.getClass().getName() + " method : 'readAll()'");
        return read(FIND_ALL_CITIES);
    }


    @Override
    public List<City> readByName(String name) {
        log.info(this.getClass().getName() + " method : 'readByName(String name)'");
        return read(CITY_SELECT_BY_NAME + '\'' + name + '\'');
    }

    @Override
    public void update(City entity) {
        log.info(this.getClass().getName() + " method : 'update(City entity)'");
        try (PreparedStatement ps = connection.prepareStatement(CITY_UPDATE)) {
            ps.setString(1, entity.getName());
            ps.setLong(2, entity.getId());
            log.info(ps.toString());
            ps.execute();
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        log.info(this.getClass().getName() + " method : 'delete(long id)'");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(CITY_DELETE + id);
            log.info(stmt.toString());
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
