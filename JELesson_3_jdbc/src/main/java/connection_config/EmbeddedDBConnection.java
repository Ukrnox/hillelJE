package connection_config;

import java.sql.Connection;
import java.sql.SQLException;

public interface EmbeddedDBConnection {
    Connection getConnection(String fileNameOrFullPath) throws SQLException;
}
