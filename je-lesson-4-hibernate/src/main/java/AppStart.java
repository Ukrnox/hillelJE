import gui.GUI;
import importfromcvsinziptodb.ImportCSVFromZIPIntoDB;

import java.io.IOException;
import java.sql.SQLException;

public class AppStart {
    public static void main(String[] args) throws SQLException, IOException {
        new ImportCSVFromZIPIntoDB().importCSVFromFileINCurrentDir("import.zip");
        new GUI();
    }
}
