package nox.controllers;

public class ExpectedResult {
    public static String expected(String expected) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Spring</title>\n" +
                "    <link media=\"all\" rel=\"stylesheet\" href=\"/css/button.css\" type=\"text/css\"/>\n" +
                "    <style>\n" +
                "        BODY {\n" +
                "            background: url(/img/polit-map.jpg) no-repeat center center fixed;\n" +
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
                "<button class=\"button button1\" onclick=\"get('http://localhost/cities/name/', true, 'getCityByName')\">getCityByName\n" +
                "</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<form action=\"/cities/create\" method=\"post\">\n" +
                "    <input type=\"text\" name=\"name\" required placeholder=\"CityName\">\n" +
                "    <button type=\"submit\" class=\"button button1\">POST new City</button>\n" +
                "</form>\n" +
                "<br>\n" +
                "<br>\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/cities/', false)\">Get all cities</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<input type=\"text\" id=\"countryId\" placeholder=\"CountryId\">\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/countries/', true, 'countryId')\">GET countryId by id\n" +
                "</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<input type=\"text\" id=\"getCountryByName\" placeholder=\"CountryName\">\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/countries/name/', true, 'getCountryByName')\">\n" +
                "    getCountryByName\n" +
                "</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<form action=\"/countries/create\" method=\"post\">\n" +
                "    <input type=\"text\" name=\"name\" required placeholder=\"CountryName\">\n" +
                "    <button type=\"submit\" class=\"button button1\">POST new country</button>\n" +
                "</form>\n" +
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
                "<button class=\"button button1\" onclick=\"get('http://localhost/regions/name/', true, 'getRegionByName')\">\n" +
                "    getRegionByName\n" +
                "</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<form action=\"/regions/create\" method=\"post\">\n" +
                "    <input type=\"text\" name=\"name\" required placeholder=\"regionName\">\n" +
                "    <button type=\"submit\" class=\"button button1\">POST new region</button>\n" +
                "</form>\n" +
                "<br>\n" +
                "<br>\n" +
                "<button class=\"button button1\" onclick=\"get('http://localhost/regions/', false)\">Get all regions</button>\n" +
                "<br>\n" +
                "<br>\n" +
                "<br>\n" +
                "<p class=\"result\" style=\"color:white\">\n<h3><span>");
        stringBuilder.append(expected);
        stringBuilder.append("</span></h3>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "<script>\n" +
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
        return stringBuilder.toString().replaceAll("\\s+", "");
    }
}
