package tests.day01;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class metaWeatherTests {

    /**
     * API documentation on this API
     * https://www.metaweather.com/api/
     */


    /**
     * based on this query : https://www.metaweather.com/api/location/search/?query=scan        =>> instead of scan => {query}
     * write 2 test cases: 1 Positive and 1 Negative
     */

    @Test
    public void testPositive(){
        Response response = given().queryParams("query", "Moscow")
                .when().get("https://www.metaweather.com/api/location/search/");
        response.prettyPeek();
        System.out.println("=============================");
        response.print();
        response.prettyPeek().then().statusCode(200);
        assertTrue(response.asString().contains("Moscow"));
    }

    @Test
    public void testNegative(){
        Response response = given().pathParam("query", "asdasd")
                .when().get("https://www.metaweather.com/api/location/search/?query={query}");
        response.then().statusCode(400);
    }

    @Test // description = show at least 2 Cities
    public void testPositive2(){
        Response response = given().pathParam("query1", "Moscow")
                .pathParam("query", "Denver")
                .when().get("https://www.metaweather.com/api/location/search/?query={query1}&query={query}");
        // query={query}&query1={query}

        response.prettyPeek();

        assertTrue(response.asString().contains("Moscow") && response.asString().contains("Denver"));
    }

    /**
     * add scenario to verify header content type
     */
    @Test
    public void test3(){
        Response response = when().get("https://www.metaweather.com/api/location/search/?query=san");
        String contentType = response.header("Content-Type");
        assertEquals("application/json", contentType);
    }
}
