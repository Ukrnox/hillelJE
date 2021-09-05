package nox.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Test
    void getAllCities() {
        String body = this.restTemplate.getForObject("/countries", String.class);
        assertThat(body).isEqualTo("[{\"id\":1,\"name\":\"Arra\",\"cities\":[],\"regions\":[]}]");
    }

    @Test
    void getCountryById() {
        String body = this.restTemplate.getForObject("/countries/1", String.class);
        assertThat(body).isEqualTo("{\"id\":1,\"name\":\"Arra\",\"cities\":[],\"regions\":[]}");
    }

    @Test
    void getCountryByName() {
        String body = this.restTemplate.getForObject("/countries/name/Arra", String.class);
        assertThat(body).isEqualTo("[{\"id\":1,\"name\":\"Arra\",\"cities\":[],\"regions\":[]}]");
    }

    @Test
    void createNewCountry() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"Arra\"}", headers);
        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("/countries/", request, String.class);
        assertThat(responseEntityStr.getBody()).isEqualTo("{\"id\":2,\"name\":\"Arra\",\"cities\":null,\"regions\":null}");
    }

    @Test
    void handleElementNotFound() {
        String body = this.restTemplate.getForObject("/countries/33", String.class);
        assertThat(body).isEqualTo("No country with ID 33 in the DB");
    }
}