package tests.day01;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class PathAndQuaryParameters {

    /**
     * Documentation for the API that we test in this class
     * https://github.com/openratesapi/openrates
     *
     */


    /**
     * Given i create a request with param 2020-01-02
     * when i send my request to http://api.openrates.io/{date}
     * then the response should contains "2020-01-02"
     */
    @Test
    public void pathParaTest() {
        // {date} ->> in restAssured is a path parameter
        // find path param {date} and give a new param {latest}
        Response response = given().pathParam("date", "latest").
                            when().get("http://api.openrates.io/{date}");

        response.prettyPeek().then().statusCode(200);
        assertTrue(response.asString().contains("2020-01-23"));

        // pass a param with a specific date (make sure date format is right, to get the right format need to read from documentation of the specif API)
        response = given().pathParam("date", "2020-01-02").
                   when().get("http://api.openrates.io/{date}");

        response.prettyPeek().then().statusCode(200);
        assertTrue(response.asString().contains("2020-01-02"));
    }

    /**
     * 400 message test
     * Given i create a request with wrong param 2020-30-02
     * when i send my request to http://api.openrates.io/{date}
     * then the status code should be 400
     */
    @Test
    public void pathParaTestNegative() {
        Response response = given().pathParam("date", "2020-30-02").
                when().get("http://api.openrates.io/{date}");
        // verify that status code is 400
        response.prettyPeek().then().assertThat().statusCode(400);
    }

    /**
     * pass one param, USD
     * "base":"USD"
     * check the response contains the passed param
     */
    @Test
    public void queryParams(){
        Response response = given().queryParams("base", "USD").
                when().get("http://api.openrates.io/latest");  // why end param = latest, other wise we would have to give 2 params now, date and base !!!!!
        // url for postman : http://api.openrates.io/latest?base=USD

        response.prettyPeek();
        assertTrue(response.asString().contains("\"base\":\"USD\""));

    }

    /**
     * postman request build : http://api.openrates.io/latest?date=latest&base=USD&symbols=MYR
     * where:
     * latest: 1st param
     * &base = second param
     * symbols = 3rd param
     *
     * given i create request with query parameter base=USD and symbol=MYR
     *  when i send my request to http://api.openrates.io/latest
     *  then the response should contain "base": "USD"
     *  and body should contain MYR
     *  but should not contain EUR
     *
     *  to start your query start with given()
     */
    @Test
    public void test1(){
        Response response = given().queryParams("base", "USD")
                            .queryParams("symbols","MYR")
                            .when().get("http://api.openrates.io/latest");

        response.prettyPeek();
        String responseStr = response.asString();
        assertTrue(responseStr.contains("USD") && responseStr.contains("MYR"));
        assertFalse(responseStr.contains("EUR"));

    }


    /**
     *          given i create request with query parameter base=USD and symbol=MYR
     *          and parameter date = 2020-01-02
     *          when i send my request to http://api.openrates.io/{date}
     *          then the response should contain "base": "USD"
     *          and body should contain MYR
     *          but should not contain EUR
     */
    @Test
    public void method2(){
        Response response = given().pathParam("date", "2020-01-02")
                            .queryParams("base","USD")
                            .queryParams("symbols","MYR")
                            .when().get("http://api.openrates.io/{date}");

        response.prettyPeek();
        String respStr = response.asString();
        assertTrue(respStr.contains("2020-01-02") && respStr.contains("MYR"));
        assertFalse(respStr.contains("EUR"));

    }

}
