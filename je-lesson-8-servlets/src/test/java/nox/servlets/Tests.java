package nox.servlets;

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
@Sql(value = {"classpath:cities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:cities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class Tests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testImportFromZip() {
        this.restTemplate.getForObject("/importFromZip", String.class);
        String body = this.restTemplate.getForObject("/cities", String.class);
        System.out.println(body);
        assertThat(body.replaceAll("\\s+","")).isEqualTo(returnExpectedResult("CityEntity(id=1, name=Arra)CityEntity(id=2, name=Odesa)CityEntity(id=3, name=Kiev)CityEntity(id=4, name=Lviv)").replaceAll("\\s+",""));
    }

    @Test
    void getAllEntities() {
        String body = this.restTemplate.getForObject("/cities", String.class);
        assertThat(body.replaceAll("\\s+","")).isEqualTo(returnExpectedResult("CityEntity(id=1, name=Arra)").replaceAll("\\s+",""));
    }

    @Test
    void getById() {
        String body = this.restTemplate.getForObject("/cities/1", String.class);
        System.out.println(body);
        assertThat(body.replaceAll("\\s+","")).isEqualTo(returnExpectedResult("CityEntity(id=1, name=Arra)").replaceAll("\\s+",""));
    }

    @Test
    void getByName() {
        String body = this.restTemplate.getForObject("/cities/name/Arra", String.class);
        System.out.println(body);
        assertThat(body.replaceAll("\\s+","")).isEqualTo(returnExpectedResult("[CityEntity(id=1, name=Arra)]").replaceAll("\\s+",""));
    }

    @Test
    void createNewEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"Arra\"}", headers);
        ResponseEntity<String> responseEntityStr = restTemplate.
                postForEntity("/cities/", request, String.class);
        assertThat(responseEntityStr.getBody()).isEqualTo("CityEntity(id=5, name=Arra)");
    }

    @Test
    void handleElementNotFoundById() {
        String body = this.restTemplate.getForObject("/cities/33", String.class);
        System.out.println(body);
        assertThat(body.replaceAll("\\s+","")).isEqualTo(returnExpectedResult("CityEntity was not found!").replaceAll("\\s+",""));
    }

    @Test
    void handleElementNotFoundByName() {
        String body = this.restTemplate.getForObject("/cities/name/33", String.class);
        System.out.println(body);
        assertThat(body.replaceAll("\\s+","")).isEqualTo(returnExpectedResult("CityEntity was not found!").replaceAll("\\s+",""));
    }

    @Test
    void validException() {
        String body = this.restTemplate.getForObject("/cities/3d", String.class);
        System.out.println(body);
        assertThat(body.replaceAll("\\s+","")).isEqualTo(returnExpectedResult("Wrong format!").replaceAll("\\s+",""));
    }

    private String returnExpectedResult (String expected){
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Spring</title>\n" +
                "    <style>\n" +
                "        .button {\n" +
                "    background-color: #4CAF50;\n" +
                "    border: none;\n" +
                "    color: white;\n" +
                "    /*padding: 16px 32px;*/\n" +
                "    text-align: center;\n" +
                "    text-decoration: none;\n" +
                "    display: inline-block;\n" +
                "    font-size: 16px;\n" +
                "    margin: 4px 2px;\n" +
                "    transition-duration: 0.4s;\n" +
                "    cursor: pointer;\n" +
                "}\n" +
                "\n" +
                ".button1 {\n" +
                "    background-color: white;\n" +
                "    color: black;\n" +
                "    border: 3px solid #000000;\n" +
                "}\n" +
                "\n" +
                ".button1:hover {\n" +
                "    background-color: #4CAF50;\n" +
                "    color: white;\n" +
                "}\n" +
                "\n" +
                ".result {\n" +
                "    border: 3px solid green;\n" +
                "    background-color: black;\n" +
                "    margin: auto;\n" +
                "    width: 20%;\n" +
                "    color: white;\n" +
                "}\n" +
                "        BODY {\n" +
                "            background: url(/img/polit-map.jpg);\n" +
                "            color: blue;\n" +
                "        }\n" +
                "\n" +
                "        h3 {\n" +
                "            border: 3px solid green;\n" +
                "            background-color: black;\n" +
                "            margin: auto;\n" +
                "            width: 20%;\n" +
                "            color: white;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body align=\"center\">\n" +
                "\n" +
                "<form action=\"/importFromZip\" method=\"post\">\n" +
                "    <button class=\"button button1\">Import</button>\n" +
                "</form>\n" +
                "\n" +
                "<input type=\"text\" id=\"cityId\" placeholder=\"cityId\">\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/cities/', true, 'cityId')\">GET city by id</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<input type=\"text\" id=\"getCityByName\" placeholder=\"cityName\">\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/cities/name/', true, 'getCityByName')\">getCityByName</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<input type=\"text\" id=\"cityName\" placeholder=\"cityName\">\n" +
                "<button class=\"button button1\" onclick=\"post('http://localhost/cities/', 'cityName')\">POST new City</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/cities/', false)\">Get all cities</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<input type=\"text\" id=\"countryId\" placeholder=\"CountryId\">\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/countries/', true, 'countryId')\">GET countryId by id</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<input type=\"text\" id=\"getCountryByName\" placeholder=\"CountryName\">\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/countries/name/', true, 'getCountryByName')\">getCountryByName</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<input type=\"text\" id=\"countryName\" placeholder=\"CountryName\">\n" +
                "<button class=\"button button1\" onclick=\"post('http://localhost/countries/', 'countryName')\">POST new country</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/countries/', false)\">Get all countries</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<input type=\"text\" id=\"regionId\" placeholder=\"regionId\">\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/regions/', true, 'regionId')\">GET regionId by id</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<input type=\"text\" id=\"getRegionByName\" placeholder=\"regionName\">\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/regions/name/', true, 'getRegionByName')\">getRegionByName</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<input type=\"text\" id=\"regionName\" placeholder=\"regionName\">\n" +
                "<button class=\"button button1\" onclick=\"post('http://localhost/regions/', 'regionName')\">POST new region</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/regions/', false)\">Get all regions</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<br>\n" +
                "<h3>");
        sb.append(expected);
        sb.append("</h3>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "<script>\n" +
                "    function post(url, whichField) {\n" +
                "        let name = document.querySelector('#' + whichField);\n" +
                "        if (name.value.length === 0 || !name.value.trim()) {\n" +
                "            alert(\"Field \" + whichField + \" is empty\");\n" +
                "            return false;\n" +
                "        }\n" +
                "        let result = document.querySelector('.result');\n" +
                "        let xhr = new XMLHttpRequest();\n" +
                "        xhr.open(\"POST\", url, true);\n" +
                "        xhr.setRequestHeader(\"Content-Type\", \"application/json\");\n" +
                "        xhr.onreadystatechange = function () {\n" +
                "            if (xhr.readyState === 4 && xhr.status === 200) {\n" +
                "                result.innerHTML = this.responseText;\n" +
                "            }\n" +
                "        };\n" +
                "        const data = JSON.stringify({\"name\": name.value});\n" +
                "        xhr.send(data);\n" +
                "    }\n" +
                "\n" +
                "    function get(url, getOne, whichField) {\n" +
                "        let fieldValue = document.querySelector('#' + whichField);\n" +
                "        let url2 = url;\n" +
                "        if (getOne === true) {\n" +
                "            if (fieldValue.value.length === 0 || !fieldValue.value.trim()) {\n" +
                "                alert(\"Field \" + whichField + \" is empty\");\n" +
                "                return false;\n" +
                "            }\n" +
                "            url2 = url + fieldValue.value;\n" +
                "        }\n" +
                "        document.location.href = url2;\n" +
                "    }\n" +
                "</script>\n" +
                "\n" +
                "</html>");
        return sb.toString();
    }
}