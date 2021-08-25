package nox.cvs;

import nox.entities.City;
import nox.entities.Country;
import nox.entities.Region;
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

public class CSVHelper {
//    public static String TYPE = "text/csv";
//    static String[] HEADERs = {"id", "name"};
//
//    public static boolean hasCSVFormat(MultipartFile file) {
//        if (!TYPE.equals(file.getContentType())) {
//            return false;
//        }
//        return true;
//    }

    public static List<City> csvToCities(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<City> cities = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                City city = new City(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("name")
                );
                System.out.println(city);
                cities.add(city);
            }
            return cities;
        }
        catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<Country> csvToCountries(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<Country> countries = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Country country = new Country(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("name")
                );
                countries.add(country);
            }
            return countries;
        }
        catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<Region> csvToRegions(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<Region> regions = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Region region = new Region(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("name")
                );
                regions.add(region);
            }
            return regions;
        }
        catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
