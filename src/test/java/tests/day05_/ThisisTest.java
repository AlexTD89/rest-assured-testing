package tests.day05_;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThisisTest {

    @BeforeAll
    public static void setup(){
        /**
         * For Spartan API at the end of our aws vm we write:  :8000
         */
        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:8000/"; // school uri
//        RestAssured.baseURI = "http://ec2-3-95-230-194.compute-1.amazonaws.com:8000"; // my vm uri (always changes)
    }


    /**
     * get all spartans
     * verify status code 200
     */
    @Test
    public void test1(){
        when().get("/api/spartans").then().statusCode(200);
    }

    /**
     * get spartan with id 132
     * verify that response header
     *      content type - application.json
     *
     *      verify that response body
     *      "id": 16,
     *         "name": "Sinclair",
     *         "gender": "Male",
     *         "phone": 9714460354
     */
    @Test
    public void test2(){
        given().pathParam("id", 132).when()
                .get("api/spartans/{id}").prettyPeek().then().contentType("application/json")
                .and().statusCode(200)
        .and().assertThat().body("id", is(132))
        .and().assertThat().body("name", is("Sinclair"))
        .and().assertThat().body("gender", is("Male"))
        .and().assertThat().body("phone", is(9714460354l));
    }

    /**
     * make a get request to search api
     * query param nameContains : ha
     * verify
     *      status code: 200
     *      header: application/json
     *      body: names of all objects returned, contains the string
     */
    @Test
    public void test3(){
        given().log().uri().
                queryParams("nameContains", "ha").when()
                .get("/api/spartans/search") // /api/spartans/search   ->> search >> actually is the source for query params
                .prettyPeek()
                .then().statusCode(200)
        .and().assertThat().contentType(ContentType.JSON)
        .and().assertThat().body("content.name", everyItem(containsStringIgnoringCase("ha")));

        JsonPath jsonPath = given().queryParams("nameContains", "ha").when()
                .get("/api/spartans/search").jsonPath();

        List<String> list = jsonPath.getList("content.name");
        System.out.println("list = " + list);

        for (String eachName : list) {
            assertTrue((eachName.toLowerCase()).contains("ha"));
        }
    }
}
