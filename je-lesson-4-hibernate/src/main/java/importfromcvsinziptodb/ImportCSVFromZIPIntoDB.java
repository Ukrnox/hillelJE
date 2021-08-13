package importfromcvsinziptodb;

import hibernateutil.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.*;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class ImportCSVFromZIPIntoDB {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void importCSVFromFileINCurrentDir(String zipFileName) throws SQLException, IOException {
        log.debug("method : 'importCSVFromFileINCurrentDir(String zipFileName)'");
        String path = new File("").getAbsolutePath();
        importCSVFromFileWithFullPath(path + "/" + zipFileName);
    }

    public void importCSVFromFileWithFullPath(String path) throws IOException {
        log.debug("method : 'importCSV()'");
        importCSV(path);
    }

    private void importCSV(String path) throws IOException {
        log.debug("method : 'importCSV()'");
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
                }
                while ((line = bufferedReader.readLine()) != null) {
                    insertInTable(tableName, columnsNames, line);
                }
                bufferedReader.close();
            }
        }
        zipFile.close();
    }

    public void insertInTable(String tableName, String[] columnsNames, String columnsValues) {
        log.debug("method : 'insertInTable(...)'");
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

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sqlStatement.toString()).executeUpdate();
            transaction.commit();
        }
    }
}
