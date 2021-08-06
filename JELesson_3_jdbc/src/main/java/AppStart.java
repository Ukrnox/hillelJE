import connection_config.H2DBConnection;
import gui.GUI;
import tablecreator.CreateTablesFromEntities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AppStart {

    private static void createTables() {
        try {
            Connection connection = new H2DBConnection().getConnection("testDB");
            new CreateTablesFromEntities(connection).createTablesFromEntities();
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        createTables();
        new GUI();
    }
}
