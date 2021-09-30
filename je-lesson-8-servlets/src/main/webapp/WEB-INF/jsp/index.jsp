<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spring</title>
    <style>
        <%@include file="../css/button.css"%>
        BODY {
            background: url(/img/polit-map.jpg);
            color: blue;
        }

        h3 {
            border: 3px solid green;
            background-color: black;
            margin: auto;
            width: 20%;
            color: white;
        }
    </style>
</head>

<body align="center">

<form action="/importFromZip" method="post">
    <button class="button button1">Import</button>
</form>

<input type="text" id="cityId" placeholder="cityId">
<button class="button button1" onclick="get('http://localhost/cities/', true, 'cityId')">GET city by id</button>
<br>
<br>
<input type="text" id="getCityByName" placeholder="cityName">
<button class="button button1" onclick="get('http://localhost/cities/name/', true, 'getCityByName')">getCityByName</button>
<br>
<br>
<input type="text" id="cityName" placeholder="cityName">
<button class="button button1" onclick="post('http://localhost/cities/', 'cityName')">POST new City</button>
<br>
<br>
<button class="button button1" onclick="get('http://localhost/cities/', false)">Get all cities</button>
<br>
<br>
<input type="text" id="countryId" placeholder="CountryId">
<button class="button button1" onclick="get('http://localhost/countries/', true, 'countryId')">GET countryId by id</button>
<br>
<br>
<input type="text" id="getCountryByName" placeholder="CountryName">
<button class="button button1" onclick="get('http://localhost/countries/name/', true, 'getCountryByName')">getCountryByName</button>
<br>
<br>
<input type="text" id="countryName" placeholder="CountryName">
<button class="button button1" onclick="post('http://localhost/countries/', 'countryName')">POST new country</button>
<br>
<br>
<button class="button button1" onclick="get('http://localhost/countries/', false)">Get all countries</button>
<br>
<br>
<input type="text" id="regionId" placeholder="regionId">
<button class="button button1" onclick="get('http://localhost/regions/', true, 'regionId')">GET regionId by id</button>
<br>
<br>
<input type="text" id="getRegionByName" placeholder="regionName">
<button class="button button1" onclick="get('http://localhost/regions/name/', true, 'getRegionByName')">getRegionByName</button>
<br>
<br>
<input type="text" id="regionName" placeholder="regionName">
<button class="button button1" onclick="post('http://localhost/regions/', 'regionName')">POST new region</button>
<br>
<br>
<button class="button button1" onclick="get('http://localhost/regions/', false)">Get all regions</button>
<br>
<br>
<br>
<h3>${information}</h3>

</body>

<script>
    function post(url, whichField) {
        let name = document.querySelector('#' + whichField);
        if (name.value.length === 0 || !name.value.trim()) {
            alert("Field " + whichField + " is empty");
            return false;
        }
        let result = document.querySelector('.result');
        let xhr = new XMLHttpRequest();
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                result.innerHTML = this.responseText;
            }
        };
        const data = JSON.stringify({"name": name.value});
        xhr.send(data);
    }

    function get(url, getOne, whichField) {
        let fieldValue = document.querySelector('#' + whichField);
        let url2 = url;
        if (getOne === true) {
            if (fieldValue.value.length === 0 || !fieldValue.value.trim()) {
                alert("Field " + whichField + " is empty");
                return false;
            }
            url2 = url + fieldValue.value;
        }
        document.location.href = url2;
    }
</script>

</html>