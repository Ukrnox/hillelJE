package nox.cvs;

import lombok.extern.slf4j.Slf4j;
import nox.entities.CityEntity;
import nox.entities.CountryEntity;
import nox.entities.RegionEntity;
import nox.repo.CityRepository;
import nox.repo.CountryRepository;
import nox.repo.RegionRepository;
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

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;

    @Autowired
    public ImportCSVFromZIPIntoDB(CityRepository cityRepository, CountryRepository countryRepository, RegionRepository regionRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
    }

    public void importCSVFromFileINCurrentDir(String zipFileName) throws IOException {
        log.debug("method : 'importCSVFromFileINCurrentDir(String zipFileName)'");
        String path = System.getProperty("user.dir");
        importCSVFromFileWithFullPath(path + File.separator + zipFileName);
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
        List<CityEntity> tutorials = CSVHelper.csvToCities(is);
        cityRepository.saveAll(tutorials);
    }

    private void saveCountries(InputStream is) {
        List<CountryEntity> countries = CSVHelper.csvToCountries(is);
        countryRepository.saveAll(countries);
    }

    private void saveRegions(InputStream is) {
        List<RegionEntity> regions = CSVHelper.csvToRegions(is);
        regionRepository.saveAll(regions);
    }
}
