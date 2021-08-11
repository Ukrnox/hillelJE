package gui;

import dao.CityDao;
import dao.CountryDao;
import dao.RegionDao;
import entities.City;
import entities.Country;
import entities.Region;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class GUI extends JFrame {

    private final CountryDao countryDao = new CountryDao();
    private final RegionDao regionDao = new RegionDao();
    private final CityDao cityDao = new CityDao();

    private final JFrame frame = new JFrame("");

    private final JTextArea textArea;
    private final JPanel centerPanel = new JPanel(new GridBagLayout());
    private final JPanel southPanel = new JPanel(new GridBagLayout());
    private final JPanel eastPanel = new JPanel(new GridBagLayout());

    private final JTextField cityTextField = new JTextField(10);
    private final JTextField countryTextField = new JTextField(10);
    private final JTextField regionTextField = new JTextField(10);

    private final JButton addNewCountryInDB = new JButton("addNewCountryInDB");
    private final JButton addNewRegionInDB = new JButton("addNewRegionInDB");
    private final JButton addNewCityInDB = new JButton("addNewCityInDB");

    private final JButton showAllCountriesFromDB = new JButton("showAllCountriesFromDB");
    private final JButton showAllRegionsFromDB = new JButton("showAllRegionsFromDB");
    private final JButton showAllCitiesFromDB = new JButton("showAllCitiesFromDB");

    private final JTextField countryIdTextField = new JTextField(1);
    private final JTextField regionIdTextField = new JTextField(1);
    private final JTextField cityIdTextField = new JTextField(1);

    private final JButton findCountryInDBbyId = new JButton("findCountryById");
    private final JButton findRegionInDBbyId = new JButton("findRegionById");
    private final JButton findCityInDBbyId = new JButton("findCityById");

    private final JTextField countryByNameTextField = new JTextField(1);
    private final JTextField regionByNameTextField = new JTextField(1);
    private final JTextField cityByNameTextField = new JTextField(1);

    private final JButton findCountryInDBbyName = new JButton("findCountryByName");
    private final JButton findRegionInDBbyName = new JButton("findRegionByName");
    private final JButton findCityInDBbyName = new JButton("findCityByName");

    private final JTextField country_Id_UpdateNameTextField = new JTextField(1);
    private final JTextField country_UpdateNameTextField = new JTextField(1);
    private final JTextField region_Id_UpdateNameTextField = new JTextField(1);
    private final JTextField region_UpdateNameTextField = new JTextField(1);
    private final JTextField city_Id_UpdateNameTextField = new JTextField(1);
    private final JTextField city_UpdateNameTextField = new JTextField(1);

    private final JButton updateCountryInDB = new JButton("updateCountryInDB");
    private final JButton updateRegionInDB = new JButton("updateRegionInDB");
    private final JButton updateCityInDB = new JButton("updateCityInDB");


    public GUI() {
        log.info(this.getClass().getName() + " constructor : 'GUI()'");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        gbc.gridheight = 1;

        textArea = new JTextArea("Logging:\n", 8, 2);
        textArea.setFont(new Font("No name", Font.PLAIN, 14));
        textArea.setEditable(false);
        JScrollPane scrollPaneTextArea = new JScrollPane(textArea);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 430);
        frame.setLocationRelativeTo(null);

        addButtonsActions();

        cityTextField.setText("City");
        regionTextField.setText("Region");
        countryTextField.setText("Country");

        JPanel centralCountryButtonPanel = new JPanel(new GridLayout(3, 1));
        centralCountryButtonPanel.add(countryTextField);
        centralCountryButtonPanel.add(addNewCountryInDB);
        centerPanel.add(centralCountryButtonPanel, gbc);

        JPanel centralRegionButtonPanel = new JPanel(new GridLayout(3, 1));
        centralRegionButtonPanel.add(regionTextField);
        centralRegionButtonPanel.add(addNewRegionInDB);
        centerPanel.add(centralRegionButtonPanel, gbc);

        JPanel centralCityButtonPanel = new JPanel(new GridLayout(3, 1));
        centralCityButtonPanel.add(cityTextField);
        centralCityButtonPanel.add(addNewCityInDB);
        centerPanel.add(centralCityButtonPanel, gbc);

        countryIdTextField.setText("CountryId");
        regionIdTextField.setText("RegionId");
        cityIdTextField.setText("CityId");

        countryByNameTextField.setText("CountryName");
        regionByNameTextField.setText("RegionName");
        cityByNameTextField.setText("CityName");

        JPanel centralButtonPanel = new JPanel(new GridLayout(3, 1));
        centralButtonPanel.add(countryIdTextField);
        centralButtonPanel.add(findCountryInDBbyId);
        centralButtonPanel.add(regionIdTextField);
        centralButtonPanel.add(findRegionInDBbyId);
        centralButtonPanel.add(cityIdTextField);
        centralButtonPanel.add(findCityInDBbyId);

        centralButtonPanel.add(countryByNameTextField);
        centralButtonPanel.add(findCountryInDBbyName);
        centralButtonPanel.add(regionByNameTextField);
        centralButtonPanel.add(findRegionInDBbyName);
        centralButtonPanel.add(cityByNameTextField);
        centralButtonPanel.add(findCityInDBbyName);

        centerPanel.add(centralButtonPanel, gbc);

        country_Id_UpdateNameTextField.setText("Country Id");
        country_UpdateNameTextField.setText("New Country Name");


        JPanel southButtonPanel = new JPanel(new GridLayout(3, 1));
        southButtonPanel.add(country_Id_UpdateNameTextField);
        southButtonPanel.add(country_UpdateNameTextField);
        southButtonPanel.add(updateCountryInDB);

        region_Id_UpdateNameTextField.setText("Region Id");
        region_UpdateNameTextField.setText("New Region Name");
        southButtonPanel.add(region_Id_UpdateNameTextField);
        southButtonPanel.add(region_UpdateNameTextField);
        southButtonPanel.add(updateRegionInDB);

        city_Id_UpdateNameTextField.setText("City Id");
        city_UpdateNameTextField.setText("New City Name");
        southButtonPanel.add(city_Id_UpdateNameTextField);
        southButtonPanel.add(city_UpdateNameTextField);
        southButtonPanel.add(updateCityInDB);
        southPanel.add(southButtonPanel, gbc);

        Box verticalBox = Box.createVerticalBox();

        verticalBox.add(showAllCountriesFromDB);
        verticalBox.add(showAllRegionsFromDB);
        verticalBox.add(showAllCitiesFromDB);
        eastPanel.add(verticalBox, gbc);

        frame.getContentPane().add(BorderLayout.NORTH, scrollPaneTextArea);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.getContentPane().add(BorderLayout.EAST, eastPanel);

        frame.setVisible(true);
    }

    private void addButtonsActions() {
        showAllCitiesFromDB();
        showAllCountriesFromDB();
        showAllRegionsFromDB();
        addNewCountryInDB();
        addNewRegionInDB();
        addNewCityInDB();
        findCountryInDBbyId();
        findRegionInDBbyId();
        findCityInDBbyId();
        findCountryInDBbyName();
        findRegionInDBbyName();
        findCityInDBbyName();
        updateCountryInDB();
        updateRegionInDB();
        updateCityInDB();
    }

    private void addNewCountryInDB() {
        addNewCountryInDB.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> addNewCountryInDB.addActionListener'");
            String countryName = countryTextField.getText();
            countryDao.create(new Country(countryName));
            textArea.append("You added new city: " + countryName + " into DB.\n");

        });
    }

    private void addNewRegionInDB() {
        addNewRegionInDB.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> addNewRegionInDB.addActionListener'");
            String regionName = regionTextField.getText();
            regionDao.create(new Region(regionName));
            textArea.append("You added new region: " + regionName + " into DB.\n");

        });
    }

    private void addNewCityInDB() {
        addNewCityInDB.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> addNewCityInDB.addActionListener'");
            String cityName = cityTextField.getText();
            cityDao.create(new City(cityName));
            textArea.append("You added new city : " + cityName + " into DB.\n");

        });
    }

    private void showAllCitiesFromDB() {
        showAllCitiesFromDB.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> showAllCitiesFromDB.addActionListener'");
            List<City> cities = cityDao.readAll(City.class);
            if (cities.isEmpty()) {
                textArea.append("No cities in DB!" + "\n");
            }
            else {
                for (City city :
                        cities) {
                    textArea.append(city.toString() + "\n");
                }
            }
        });
    }

    private void showAllCountriesFromDB() {
        showAllCountriesFromDB.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> showAllCountriesFromDB.addActionListener'");
            List<Country> countries = countryDao.readAll(Country.class);
            if (countries.isEmpty()) {
                textArea.append("No countries in DB!" + "\n");
            }
            else {
                for (Country country :
                        countries) {
                    textArea.append(country.toString() + "\n");
                }
            }
        });
    }

    private void showAllRegionsFromDB() {
        showAllRegionsFromDB.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> showAllRegionsFromDB.addActionListener'");
            List<Region> regions = regionDao.readAll(Region.class);
            if (regions.isEmpty()) {
                textArea.append("No regions in DB!" + "\n");
            }
            else {
                for (Region region :
                        regions) {
                    textArea.append(region.toString() + "\n");
                }
            }
        });
    }

    private void findCountryInDBbyId() {
        findCountryInDBbyId.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> findCountryInDBbyId.addActionListener'");
            String countryStringId = countryIdTextField.getText();
            try {
                int countryId = Integer.parseInt(countryStringId);
                Country country = countryDao.read(countryId, Country.class);
                if (country != null) {
                    textArea.append(country + "\n");
                }
                else {
                    textArea.append("No country with id " + countryId + "\n");
                }
            }
            catch (NumberFormatException e) {
                textArea.append("Please enter valid id in country id field!" + "\n");
                log.error(e.getMessage());
            }
        });
    }

    private void findRegionInDBbyId() {
        findRegionInDBbyId.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> findRegionInDBbyId.addActionListener'");
            String regionStringId = regionIdTextField.getText();
            try {
                int regionId = Integer.parseInt(regionStringId);
                Region region = regionDao.read(regionId, Region.class);
                if (region != null) {
                    textArea.append(region + "\n");
                }
                else {
                    textArea.append("No region with id " + regionId + "\n");
                }
            }
            catch (NumberFormatException e) {
                textArea.append("Please enter valid id in region id field!" + "\n");
                log.error(e.getMessage());
            }
        });
    }

    private void findCityInDBbyId() {
        findCityInDBbyId.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> findCityInDBbyId.addActionListener'");
            String cityStringId = cityIdTextField.getText();
            try {
                int cityId = Integer.parseInt(cityStringId);
                City city = cityDao.read(cityId, City.class);
                if (city != null) {
                    textArea.append(city + "\n");
                }
                else {
                    textArea.append("No city with id " + cityId + "\n");
                }
            }
            catch (NumberFormatException e) {
                textArea.append("Please enter valid id in city id field!" + "\n");
                log.error(e.getMessage());
            }
        });
    }

    private void findCountryInDBbyName() {
        findCountryInDBbyName.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> findCountryInDBbyName.addActionListener'");
            String countryName = countryByNameTextField.getText();
            List<Country> countries = countryDao.readByName(countryName, Country.class);
            if (countries.isEmpty()) {
                textArea.append("There is no country named " + countryName + " in the database\n");
            }
            for (Country country :
                    countries) {
                textArea.append(country + "\n");
            }
        });
    }

    private void findRegionInDBbyName() {
        findRegionInDBbyName.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> findRegionInDBbyName.addActionListener'");
            String regionName = regionByNameTextField.getText();
            List<Region> regions = regionDao.readByName(regionName, Region.class);
            if (regions.isEmpty()) {
                textArea.append("There is no region named " + regionName + " in the database\n");
            }
            for (Region region :
                    regions) {
                textArea.append(region + "\n");
            }
        });
    }

    private void findCityInDBbyName() {
        findCityInDBbyName.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> findCityInDBbyName.addActionListener'");
            String cityName = cityByNameTextField.getText();
            List<City> cities = cityDao.readByName(cityName, City.class);
            if (cities.isEmpty()) {
                textArea.append("There is no city named " + cityName + " in the database\n");
            }
            for (City city :
                    cities) {
                textArea.append(city + "\n");
            }
        });
    }

    private void updateCountryInDB() {
        updateCountryInDB.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> updateCountryInDB.addActionListener'");
            String countryStringId = country_Id_UpdateNameTextField.getText();
            String newCountryName = country_UpdateNameTextField.getText();
            try {
                int countryId = Integer.parseInt(countryStringId);
                Country country = countryDao.read(countryId, Country.class);
                if (country != null) {
                    country.setName(newCountryName);
                    countryDao.update(country);
                    textArea.append(country + "\n");
                }
                else {
                    textArea.append("No country with id " + countryId + "\n");
                }
            }
            catch (NumberFormatException e) {
                textArea.append("Please enter valid id in country id field!" + "\n");
                log.error(e.getMessage());
            }
        });
    }

    private void updateRegionInDB() {
        updateRegionInDB.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> updateRegionInDB.addActionListener'");
            String regionStringId = region_Id_UpdateNameTextField.getText();
            String newRegionName = region_UpdateNameTextField.getText();
            try {
                int regionId = Integer.parseInt(regionStringId);
                Region region = regionDao.read(regionId, Region.class);
                if (region != null) {
                    region.setName(newRegionName);
                    regionDao.update(region);
                    textArea.append(region + "\n");
                }
                else {
                    textArea.append("No region with id " + regionId + "\n");
                }
            }
            catch (NumberFormatException e) {
                textArea.append("Please enter valid id in region id field!" + "\n");
                log.error(e.getMessage());
            }
        });
    }

    private void updateCityInDB() {
        updateCityInDB.addActionListener(action ->
        {
            log.info(this.getClass().getName() + " method : 'buttonsActions() -> updateCityInDB.addActionListener'");
            String cityStringId = city_Id_UpdateNameTextField.getText();
            String newCityName = city_UpdateNameTextField.getText();
            try {
                int cityId = Integer.parseInt(cityStringId);
                City city = cityDao.read(cityId, City.class);
                if (city != null) {
                    city.setName(newCityName);
                    cityDao.update(city);
                    textArea.append(city + "\n");
                }
                else {
                    textArea.append("No city with id " + cityId + "\n");
                }
            }
            catch (NumberFormatException e) {
                textArea.append("Please enter valid id in city id field!" + "\n");
                log.error(e.getMessage());
            }
        });
    }
}
