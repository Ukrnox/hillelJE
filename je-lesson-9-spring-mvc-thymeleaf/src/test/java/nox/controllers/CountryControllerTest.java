package nox.controllers;

import nox.entities.CountryEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = {"classpath:country-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:country-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CountryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CountryController countryController;

    @Test
    void getAllCities() {
        String body = this.restTemplate.getForObject("/countries", String.class);
        String expected = ExpectedResult.expected("[CountryEntity(id=1,name=Arra)]");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void getCountryById() {
        String body = this.restTemplate.getForObject("/countries/1", String.class);
        String expected = ExpectedResult.expected("CountryEntity(id=1,name=Arra)");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void getCountryByName() {
        String body = this.restTemplate.getForObject("/countries/name/Arra", String.class);
        String expected = ExpectedResult.expected("[CountryEntity(id=1,name=Arra)]");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void createNewCountry() {
        countryController.createNewCountry(new CountryEntity("Arra"));
        String body = this.restTemplate.getForObject("/countries/name/Arra", String.class);
        String expected = ExpectedResult.expected("[CountryEntity(id=1,name=Arra),CountryEntity(id=2,name=Arra)]");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void handleElementNotFound() {
        String body = this.restTemplate.getForObject("/countries/33", String.class);
        String expected = ExpectedResult.expected("No country with ID 33 in the DB");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }
}