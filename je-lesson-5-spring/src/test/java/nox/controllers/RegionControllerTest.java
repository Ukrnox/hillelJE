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
@Sql(value = {"classpath:regions-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:regions-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RegionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllRegions() {
        String body = this.restTemplate.getForObject("/regions", String.class);
        assertThat(body).isEqualTo("[{\"id\":1,\"name\":\"Arra\",\"cities\":[],\"country\":null}]");
    }

    @Test
    void getRegionById() {
        String body = this.restTemplate.getForObject("/regions/1", String.class);
        assertThat(body).isEqualTo("{\"id\":1,\"name\":\"Arra\",\"cities\":[],\"country\":null}");
    }

    @Test
    void getRegionByName() {
        String body = this.restTemplate.getForObject("/regions/name/Arra", String.class);
        assertThat(body).isEqualTo("[{\"id\":1,\"name\":\"Arra\",\"cities\":[],\"country\":null}]");
    }

    @Test
    void createNewRegion() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"Arra\"}", headers);
        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("/regions/", request, String.class);
        assertThat(responseEntityStr.getBody()).isEqualTo("{\"id\":2,\"name\":\"Arra\",\"cities\":null,\"country\":null}");
    }

    @Test
    void handleElementNotFound() {
        String body = this.restTemplate.getForObject("/regions/33", String.class);
        assertThat(body).isEqualTo("Region with id 33 not found!");
    }
}