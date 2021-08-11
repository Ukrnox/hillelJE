package dao;

import entities.Country;
import entities.Region;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RegionDao implements Dao<Region> {
    private final Connection connection;
    private final String REGION_INSERT = "INSERT INTO regions(name) VALUES (?)";
    private final String REGION_SELECT = "SELECT * from regions WHERE ID =";
    private final String REGION_UPDATE = "UPDATE regions SET name = ? WHERE id = ?";
    private final String REGION_DELETE = "DELETE FROM regions WHERE id = ";
    private final String REGION_SELECT_BY_NAME = "SELECT *  FROM regions WHERE name = ";

    private final String FIND_ALL_REGIONS = "SELECT * from regions LIMIT 500";

    public RegionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Region entity) {
        log.info(this.getClass().getName() + " method : 'create(Region entity)'");
        try (PreparedStatement preparedStatement = connection.prepareStatement(REGION_INSERT)) {
            preparedStatement.setString(1, entity.getName());
            log.info(preparedStatement.toString());
            preparedStatement.execute();
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Region> read(String sql) {
        log.info(this.getClass().getName() + " method : 'read(String sql)'");
        List<Region> regions = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            log.info(sql);
            while (rs.next()) {
                regions.add(new Region(rs.getInt("id"), rs.getString("name")));
            }
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return regions;
    }

    public List<Region> readAll() {
        log.info(this.getClass().getName() + " method : 'readAll()'");
        return read(FIND_ALL_REGIONS);
    }

    @Override
    public Region readById(long id) {
        log.info(this.getClass().getName() + " method : 'readById(long id)'");
        return read(REGION_SELECT + id).stream().findFirst().orElse(null);
    }

    @Override
    public List<Region> readByName(String name) {
        log.info(this.getClass().getName() + " method : 'readByName(String name)'");
        return read(REGION_SELECT_BY_NAME + '\'' + name + '\'');
    }

    @Override
    public void update(Region entity) {
        log.info(this.getClass().getName() + " method : 'update(Region entity)'");
        try (PreparedStatement ps = connection.prepareStatement(REGION_UPDATE)) {
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
            stmt.execute(REGION_DELETE + id);
            log.info(stmt.toString());
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
