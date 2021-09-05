package nox.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = {"classpath:cities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:cities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CityControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllCities() throws Exception {
        String body = this.restTemplate.getForObject("/cities", String.class);
        assertThat(body).isEqualTo("[{\"id\":1,\"name\":\"Arra\",\"country\":null,\"region\":null}]");
    }

    @Test
    void getCityById() {
        String body = this.restTemplate.getForObject("/cities/1", String.class);
        assertThat(body).isEqualTo("{\"id\":1,\"name\":\"Arra\",\"country\":null,\"region\":null}");
    }

    @Test
    void getCityByName() {
        String body = this.restTemplate.getForObject("/cities/name/Arra", String.class);
        assertThat(body).isEqualTo("[{\"id\":1,\"name\":\"Arra\",\"country\":null,\"region\":null}]");
    }

    @Test
    void createNewCity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"Arra\"}", headers);
        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("/cities/", request, String.class);
        assertThat(responseEntityStr.getBody()).isEqualTo("{\"id\":2,\"name\":\"Arra\",\"country\":null,\"region\":null}");
    }

    @Test
    void handleElementNotFound() {
        String body = this.restTemplate.getForObject("/cities/33", String.class);
        assertThat(body).isEqualTo("No city with ID 33 in DB!");
    }

    @Test
    void validException() {
        String body = this.restTemplate.getForObject("/cities/3d", String.class);
        assertThat(body).isEqualTo("Wrong id format");
    }
}