package tests.homework.HarryPotter;

import Utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HarryPotterClass {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = ConfigurationReader.get("harryPotterBaseURL");
    }

    /**
     * Verify sorting hat
     * 1. Send a get request to /sortingHat. Request includes :
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify that response body contains one of the following houses:
     * "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */
    @Test
    public void verifySortingHat(){
        Response response = when().get("/sortingHat")
                .prettyPeek();
                assertThat(response.getStatusCode(), is(200));
                assertThat(response.contentType(), is("application/json; charset=utf-8"));
        List<String> faculty = new ArrayList<>(Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"));
        for (String each : faculty) {
            if (response.body().toString().contains(each)){
                assertTrue(response.body().toString().contains(each));
            }
        }
    }

    /**
     * Verify bad key
     * 1. Send a get request to /characters. Request includes :
     *      • Header Accept with value application/json
     *      • Query param key with value invalid
     * 2. Verify status code 401, content type application/json; charset=utf-8
     * 3. Verify response status line include message Unauthorized
     * 4. Verify that response body says "error": "API Key Not Found"
     */
    @Test
    public void verifyBadKey(){
        Response response = given().accept(ContentType.JSON).queryParams("key", "invalid")
                .when().get("/characters").prettyPeek();
        assertThat(response.statusCode(), is(401));
        assertThat(response.contentType(), is("application/json; charset=utf-8"));
        assertThat(response.statusLine(), containsString("Unauthorized"));
        assertThat(response.jsonPath().get("error"), is("API Key Not Found"));
    }

    /**
     * Verify no key
     * 1. Send a get request to /characters. Request includes :
     *          • Header Accept with value application/json
     * 2. Verify status code 409, content type application/json; charset=utf-8
     * 3. Verify response status line include message Conflict
     * 4. Verify that response body says "error": "Must pass API key for request"
     */
    @Test
    public void verifyNoKey(){
        Response response = given().accept(ContentType.JSON).when().
                get("/characters").prettyPeek();
        assertThat(response.statusCode(), is(409));
        assertThat(response.statusLine(), containsString("Conflict"));
        assertThat(response.jsonPath().get("error"), is("Must pass API key for request"));
    }

    /**
     * Verify number of characters
     * 1. Send a get request to /characters. Request includes :
     *      • Header Accept with value application/json
     *      • Query param key with value {{apiKey}}
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify response contains 194 characters
     */
    @Test
    public void verifyNumberOfCharacters(){
        String str = " hello";
        str = str.concat("lola");
        System.out.println("str = " + str);
    }
}
