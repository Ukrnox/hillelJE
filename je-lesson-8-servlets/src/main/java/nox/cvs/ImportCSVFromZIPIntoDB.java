package nox.cvs;

import lombok.extern.slf4j.Slf4j;
import nox.dao.CityDao;
import nox.dao.CountryDao;
import nox.dao.RegionDao;
import nox.entities.CityEntity;
import nox.entities.CountryEntity;
import nox.entities.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
@Service
public class ImportCSVFromZIPIntoDB {

    private final CityDao cityDao;
    private final CountryDao countryDao;
    private final RegionDao regionDao;

    @Autowired
    public ImportCSVFromZIPIntoDB(CityDao cityDao, CountryDao countryDao, RegionDao regionDao) {
        this.cityDao = cityDao;
        this.countryDao = countryDao;
        this.regionDao = regionDao;
    }

    public void importCSVFromFileINCurrentDir(String zipFileName) {
        log.debug("method : 'importCSVFromFileINCurrentDir(String zipFileName)'");
        String path = System.getProperty("user.dir");
//        String path = new File("").getAbsolutePath();
        try {
            importCSVFromFileWithFullPath(path + File.separator + zipFileName);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
                InputStream stream = zipFile.getInputStream(entry);
                switch (tableName) {
                    case "cities":
                        saveCities(stream);
                        break;
                    case "countries":
                        saveCountries(stream);
                        break;
                    case "regions":
                        saveRegions(stream);
                        break;
                }
            }
        }
        zipFile.close();
    }

    private void saveCities(InputStream is) {
        List<CityEntity> cities = CSVHelper.csvToCities(is);
        cityDao.saveAll(cities);
    }

    private void saveCountries(InputStream is) {
        List<CountryEntity> countries = CSVHelper.csvToCountries(is);
        countryDao.saveAll(countries);
    }

    private void saveRegions(InputStream is) {
        List<RegionEntity> regions = CSVHelper.csvToRegions(is);
        regionDao.saveAll(regions);
    }
}
