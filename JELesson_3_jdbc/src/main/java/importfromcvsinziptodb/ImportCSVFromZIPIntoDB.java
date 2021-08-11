package importfromcvsinziptodb;

import lombok.extern.slf4j.Slf4j;
import tablecreator.TableCreator;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class ImportCSVFromZIPIntoDB {

    private final Connection connection;

    public ImportCSVFromZIPIntoDB(Connection connection) {
        this.connection = connection;
    }

    public void importCSVFromFileINCurrentDir(String zipFileName) throws SQLException, IOException {
        log.info(this.getClass().getName() + " method : 'importCSVFromFileINCurrentDir(String zipFileName)'");
        String path = new File("").getAbsolutePath();
        importCSVFromFileWithFullPath(path + "\\" + zipFileName);
    }

    public void importCSVFromFileWithFullPath(String path) throws SQLException, IOException {
        log.info(this.getClass().getName() + " method : 'importCSVFromFileWithFullPath(String path)'");
        importCSV(path);
    }

    private void importCSV(String path) throws IOException, SQLException {
        log.info(this.getClass().getName() + " method : 'importCSV()'");
        ZipFile zipFile = new ZipFile(path);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String fileName = entry.getName();
            if (fileName.endsWith(".csv")) {
                String tableName = fileName.split("\\.")[0];
                String line;
                InputStream stream = zipFile.getInputStream(entry);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream), 1024);
                String[] columnsNames = null;
                if ((line = bufferedReader.readLine()) != null) {
                    columnsNames = line.split(",");
                    TableCreator.createTableIFNotExist(connection, tableName, Arrays.stream(columnsNames).skip(1).toArray(String[]::new));
                }
                while ((line = bufferedReader.readLine()) != null) {
                    insertInTable(tableName, columnsNames, line);
                }
                bufferedReader.close();
            }
        }
        zipFile.close();
    }

    public void insertInTable(String tableName, String[] columnsNames, String columnsValues) throws SQLException {
        log.info(this.getClass().getName() + " method : 'insertInTable(...)'");
        Statement statement = connection.createStatement();
        StringBuilder sqlStatement = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        for (String columnName :
                columnsNames) {
            if (!columnName.equals("id"))
                sqlStatement.append(", ").append(columnName);
            else sqlStatement.append("id");
        }
        sqlStatement.append(") VALUES (");
        String[] values = columnsValues.split(",");
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                sqlStatement.append(values[i]);
            }
            else {
                sqlStatement.append(", ").append("'").append(values[i]).append("'");
            }
        }
        sqlStatement.append(");");
        log.info(sqlStatement.toString());
        statement.execute(sqlStatement.toString());
    }
}
