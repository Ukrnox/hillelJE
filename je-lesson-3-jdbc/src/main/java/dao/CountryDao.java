package dao;

import entities.City;
import entities.Country;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CountryDao implements Dao<Country> {
    private final Connection connection;
    private final String COUNTRY_INSERT = "INSERT INTO countries(name) VALUES (?)";
    private final String COUNTRY_SELECT = "SELECT * from countries WHERE ID =";
    private final String COUNTRY_UPDATE = "UPDATE countries SET name = ? WHERE id = ?";
    private final String COUNTRY_DELETE = "DELETE FROM countries WHERE id = ";
    private final String COUNTRY_SELECT_BY_NAME = "SELECT *  FROM countries WHERE name = ";

    private final String FIND_ALL_COUNTRIES = "SELECT * from countries LIMIT 500";

    public CountryDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Country entity) {
        log.info(this.getClass().getName() + " method : 'create(Country entity)'");
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNTRY_INSERT)) {
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
    public Country readById(long id) {
        log.info(this.getClass().getName() + " method : 'readById(long id)'");
        return read(COUNTRY_SELECT + id).stream().findFirst().orElse(null);
    }

    private List<Country> read(String sql) {
        log.info(this.getClass().getName() + " method : 'read(String sql)'");
        List<Country> countries = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            log.info(sql);
            while (rs.next()) {
                countries.add(new Country(rs.getInt("id"), rs.getString("name")));
            }
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return countries;
    }

    public List<Country> readAll() {
        log.info(this.getClass().getName() + " method : 'readAll()'");
        return read(FIND_ALL_COUNTRIES);
    }

    @Override
    public List<Country> readByName(String name) {
        log.info(this.getClass().getName() + " method : 'readByName(String name)'");
        return read(COUNTRY_SELECT_BY_NAME + '\'' + name + '\'');
    }

    @Override
    public void update(Country entity) {
        log.info(this.getClass().getName() + " method : 'update(Country entity)'");
        try (PreparedStatement ps = connection.prepareStatement(COUNTRY_UPDATE)) {
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
            stmt.execute(COUNTRY_DELETE + id);
            log.info(stmt.toString());
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
