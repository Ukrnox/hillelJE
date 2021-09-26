package nox.controllers;

import nox.entities.RegionEntity;
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
@Sql(value = {"classpath:regions-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:regions-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RegionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RegionController regionController;

    @Test
    void getAllRegions() {
        String body = this.restTemplate.getForObject("/regions", String.class);
        String expected = ExpectedResult.expected("[RegionEntity(id=1, name=Arra)]");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void getRegionById() {
        String body = this.restTemplate.getForObject("/regions/1", String.class);
        String expected = ExpectedResult.expected("RegionEntity(id=1,name=Arra)");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void getRegionByName() {
        String body = this.restTemplate.getForObject("/regions/name/Arra", String.class);
        String expected = ExpectedResult.expected("[RegionEntity(id=1,name=Arra)]");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void createNewRegion() {
        regionController.createNewRegion(new RegionEntity("Arra"));
        String body = this.restTemplate.getForObject("/regions/name/Arra", String.class);
        String expected = ExpectedResult.expected("[RegionEntity(id=1,name=Arra),RegionEntity(id=2,name=Arra)]");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }

    @Test
    void handleElementNotFound() {
        String body = this.restTemplate.getForObject("/regions/33", String.class);
        String expected = ExpectedResult.expected("Region with id 33 not found!");
        assertThat(body.replaceAll("\\s+", "")).isEqualTo(expected);
    }
}