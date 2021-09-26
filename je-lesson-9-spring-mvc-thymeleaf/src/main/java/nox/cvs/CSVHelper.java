package nox.cvs;

import lombok.extern.slf4j.Slf4j;
import nox.entities.CityEntity;
import nox.entities.CountryEntity;
import nox.entities.RegionEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CSVHelper {
    public static List<CityEntity> csvToCities(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<CityEntity> cities = new ArrayList<>();
            List<CSVRecord> records = csvParser.getRecords();
            records.forEach((csvRecord)->{
                CityEntity city = new CityEntity(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("name"));
                cities.add(city);
            });
            return cities;
        }
        catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<CountryEntity> csvToCountries(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<CountryEntity> countries = new ArrayList<>();
            List<CSVRecord> records = csvParser.getRecords();
            records.forEach((csvRecord)->{
                CountryEntity country = new CountryEntity(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("name")
                );
                countries.add(country);
            });
            return countries;
        }
        catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<RegionEntity> csvToRegions(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<RegionEntity> regions = new ArrayList<>();
            List<CSVRecord> records = csvParser.getRecords();
            records.forEach((csvRecord)->{
                RegionEntity region = new RegionEntity(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("name")
                );
                regions.add(region);
            });
            return regions;
        }
        catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
