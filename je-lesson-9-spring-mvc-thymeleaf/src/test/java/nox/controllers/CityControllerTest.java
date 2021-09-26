package nox.controllers;

import nox.entities.CityEntity;
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
@Sql(value = {"classpath:cities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:cities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CityControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CityController cityController;

    @Test
    void getAllCities() {
        String body = this.restTemplate.getForObject("/cities", String.class);
        String expected = ExpectedResult.expected("[CityEntity(id=1, name=Arra)]");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void getCityById() {
        String body = this.restTemplate.getForObject("/cities/1", String.class);
        String expected = ExpectedResult.expected("CityEntity(id=1,name=Arra)");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void getCityByName() {
        String body = this.restTemplate.getForObject("/cities/name/Arra", String.class);
        String expected = ExpectedResult.expected("[CityEntity(id=1,name=Arra)]");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void handleElementNotFound() {
        String body = this.restTemplate.getForObject("/cities/33", String.class);
        String expected = ExpectedResult.expected("No city with ID 33 in DB!");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void validException() {
        String body = this.restTemplate.getForObject("/cities/3d", String.class);
        String expected = ExpectedResult.expected("Wrong id format");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void createNewCity() throws Exception {
        cityController.createNewCity(new CityEntity("Arra"));
        String body = this.restTemplate.getForObject("/cities/name/Arra", String.class);
        String expected = ExpectedResult.expected("[CityEntity(id=1,name=Arra),CityEntity(id=2,name=Arra)]");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }
}