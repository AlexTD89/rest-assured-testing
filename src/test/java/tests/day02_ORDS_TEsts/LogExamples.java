package tests.day02_ORDS_TEsts;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class LogExamples {
    /*
    Request Logging
    given().log().all(). .. // Log all request specification details including parameters, headers and body
    given().log().params(). .. // Log only the parameters of the request
    given().log().body(). .. // Log only the request body
    given().log().headers(). .. // Log only the request headers
    given().log().cookies(). .. // Log only the request cookies
    given().log().method(). .. // Log only the request method
    given().log().path(). .. // Log only the request path
    Response Logging
    get(“/x”).then().log().body() ..
    get(“/x”).then().log().ifError(). ..
    get(“/x”).then().log().all(). ..
    get(“/x”).then().log().statusLine(). .. // Only log the status line
    get(“/x”).then().log().headers(). .. // Only log the response headers
    get(“/x”).then().log().cookies(). .. // Only log the response cookies
    get(“/x”).then().log().ifStatusCodeIsEqualTo(302). .. // Only log if the status code is equal to 302
    get(“/x”).then().log().ifStatusCodeMatches(matcher). .. // Only log if the status code matches the supplied Hamcrest matcher
     */

    // @BeforeAll ->> runs everything before everything in this class. just like @BeforeClass from testNG
    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr";
    }

    @Test
    public void test1(){
        //request logging
        // log().all(). ->> prints everything in the request
        // everything() ->> will print all log info
        // log().ifError() ->> prints if we get a error status code ( will print, if instead of "7" will put "asjhdakd") there is no region with such id....
        // ifStatusCodeIsEqualTo(400) ->> prints if the status code matches the specified one
        // ifValidationFails() ->> cares about the test, prints if an assertion fails
//        given().log().all().
//                pathParam("id","7")
//                .when().get("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/regions/{id}")
//                .then().log() {......}
//                .everything().statusCode(200);

        given().log().all().
                pathParam("id","7")
                .when().get("/regions/{id}")
                .then().log()
                .everything().statusCode(200);
    }

    @Test
    public void test2(){
        given().log().everything().pathParam("id","101")
                .when().get("/employees/{id}")
                .then().log()
                .everything().statusCode(200);
    }



}
