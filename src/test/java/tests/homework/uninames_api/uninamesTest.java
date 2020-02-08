package tests.homework.uninames_api;

import groovy.grape.GrapeIvy;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class uninamesTest {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "https://uinames.com";
    }

    /**
     * No params test
     * 1. Send a get request without providing any parameters
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that name, surname, gender, region fields have value
     */
    @Test
    public void noParamsTest(){
        when().get("/api").then().statusCode(200).contentType(ContentType.JSON)
                .and().
                contentType("application/json; charset=utf-8"). // same as ContentType.JSON
                body("name", is(notNullValue())).
                body("surname", is(notNullValue())).
                body("gender", is(notNullValue())).
                body("region", is(notNullValue()));
    }

    /**
     * Gender test
     * 1. Create a request by providing query parameter: gender, male or female
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that value of gender field is same from step 1
     */
    @Test
    public void genderTestMale(){
       given().queryParams("gender", "male").when()
       .get("/api/").prettyPeek().
       then().statusCode(200)
       .body("gender", is("male")).
       contentType(ContentType.JSON);
    }
    @Test
    public void genderTestFemale(){
        given().queryParams("gender", "female").when()
                .get("/api/").prettyPeek().
                then().statusCode(200)
                .body("gender", is("female")).
                contentType(ContentType.JSON);
    }

    /**
     * 2 params test
     * 1. Create a request by providing query parameters: a valid region and gender
     * NOTE: Available region values are given in the documentation
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that value of gender field is same from step 1
     * 4. Verify that value of region field is same from step 1
     */
    @Test
    public void twoParamsTest(){
        String gender = "male";
        String region = "Romania";
        given().queryParams("region", region).
                queryParams("gender", gender).
                when().get("/api/").prettyPeek().
        then().statusCode(200).
        contentType(ContentType.JSON).
                body("region", is(region)).
                body("gender", is(gender));
    }

    /**
     * Invalid gender test
     * 1. Create a request by providing query parameter: invalid gender
     * 2. Verify status code 400 and status line contains Bad Request
     * 3. Verify that value of error field is Invalid gender
     */
    @Test
    public void invalidGenderTest(){
        Response response = given().queryParams("gender", "invalid").
                when().get("/api/").prettyPeek();
                assertThat(response.getStatusCode(), is(400));
                assertThat(response.getStatusLine(), containsString("Bad Request"));

        JsonPath jsonPath = response.jsonPath();
                assertThat(jsonPath.get("error").toString(), is("Invalid gender"));
    }

    /**
     * Invalid region test
     * 1. Create a request by providing query parameter: invalid region
     * 2. Verify status code 400 and status line contains Bad Request
     * 3. Verify that value of error field is Region or language not found
     */
    @Test
    public void invalidRegionTest(){
        Response response = given().queryParams("region", "invalid").
                when().get("/api/").prettyPeek();
        assertThat(response.getStatusCode(), is(400));
        assertThat(response.getStatusLine().toString(), containsString("Bad Request"));
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.get("error").toString(), is("Region or language not found"));
    }

    /**
     * Amount and regions test
     * 1. Create request by providing query parameters: a valid region and amount (must be bigger than 1)
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that all objects have different name+surname combination
     */
    @Test
    public void amountAndRegionTest(){
        Response response = given().queryParams("region", "Canada").
                queryParams("amount", 25).
                when().get("/api/")
//                .prettyPeek()
                ;
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.contentType(), is("application/json; charset=utf-8"));

        JsonPath jsonPath = response.jsonPath();
        List<Map<String, String>> listOFMaps = jsonPath.getList("");
        System.out.println("listOFMaps = " + listOFMaps);

        int count=0;
        for (Map<String, String> eachMap : listOFMaps) {
            for (Map<String, String> assertEach : listOFMaps) {
                if ((eachMap.get("name")+" "+eachMap.get("surname")).equals(assertEach.get("name")+" "+assertEach.get("surname")))
                    count++;
            }
        }
        Assertions.assertEquals(count, listOFMaps.size());
    }

    /**
     * 3 params test
     * 1. Create a request by providing query parameters: a valid region, gender and amount (must be bigger
     * than 1)
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that all objects the response have the same region and gender passed in step 1
     */
    @Test
    public void threeParamsTest(){
        Response response = given().queryParams("region", "England").
                queryParams("gender", "female").
                queryParams("amount", 8).
                when().get("/api/");
        assertThat(response.getStatusCode(),is(200));
        assertThat(response.contentType(), is("application/json; charset=utf-8"));

        List<Map<String, String>> listOfMaps = response.getBody().jsonPath().getList("");
//        System.out.println("listOfMaps = " + listOfMaps);
        for (Map<String, String> each : listOfMaps) {
            assertThat(each.get("region"), is("England"));
            assertThat(each.get("gender"), is("female"));
        }

    }

    /**
     * Amount count test
     * 1. Create a request by providing query parameter: amount (must be bigger than 1)
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that number of objects returned in the response is same as the amount passed in step 1
     */
    @Test
    public void amountCountTest(){
        Response response = given().
                queryParams("amount", 8).
                when().get("/api/");
        assertThat(response.getStatusCode(),is(200));
        assertThat(response.contentType(), is("application/json; charset=utf-8"));

        List<Map<String, String>> listOfMaps = response.getBody().jsonPath().getList("");
        assertThat(listOfMaps.size(), is(8));
    }

}
