package connection_config;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class H2DBConnection implements EmbeddedDBConnection {
    private static Connection connection;

    public Connection getConnection(String fileName) throws SQLException {
//        String properties = "jdbc:h2:file:~/H2DBforHW/" + fileName; //in User folder
        log.info(this.getClass().getName() + " method : 'getConnection(...)'");
        String properties = "jdbc:h2:file:./" + fileName;
        if (connection == null) {
            connection = DriverManager.getConnection(properties);
            log.info("DriverManager.getConnection(...)'");
        }
        return connection;
    }
}
