package tablecreator;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import connection_config.H2DBConnection;
import lombok.extern.slf4j.Slf4j;
import tablecreator.annotations.Table;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CreateTablesFromEntities {

    private final Connection connection;

    public CreateTablesFromEntities(Connection connection) {
        this.connection = connection;
    }

    public void createTablesFromEntities() throws IOException, SQLException {
        log.info(this.getClass().getName() + " method : 'createTablesFromEntities()'");
        ClassPath cp = ClassPath.from(getClass().getClassLoader());
        final ImmutableSet<ClassPath.ClassInfo> allClassesBelow = cp.getTopLevelClasses("entities");
        for (ClassPath.ClassInfo classInfo :
                allClassesBelow) {
            Class<?> cl = classInfo.load();
            if (cl.isAnnotationPresent(Table.class)) {
                String tableName = cl.getAnnotation(Table.class).name();
                Field[] fields = cl.getDeclaredFields();

                Statement statement = connection.createStatement();
                statement.execute("DROP TABLE IF EXISTS " + tableName);

                List<String> columns = new ArrayList<>();
                for (Field field :
                        fields) {
                    columns.add(field.getName());
                }
                TableCreator.createTableIFNotExist(connection, tableName, columns.toArray(new String[0]));
            }
        }
    }
}
