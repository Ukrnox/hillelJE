package tablecreator;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class TableCreator {

    public static void createTableIFNotExist(Connection connection, String name, String[] columns) throws SQLException {
        log.info("connection_config.tablecreator.TableCreator method : 'createTableIFNotExist(...)'");
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE IF EXISTS " + name);
        StringBuilder sqlStatement = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(name).append(" (id INT NOT NULL AUTO_INCREMENT");
        for (String column :
                columns) {
            if (!column.equals("id"))
                sqlStatement.append(", ").append(column).append(" VARCHAR(255)");
        }
        sqlStatement.append(");");
        log.info(sqlStatement.toString());
        statement.execute(sqlStatement.toString());
    }
}
