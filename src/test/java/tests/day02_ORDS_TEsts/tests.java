package tests.day02_ORDS_TEsts;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class tests {


    /**
     *Write test case to test in new class ORDSTests:
     *     get employee from employees table with id 100 (use path parameters)
     *     Verify response contains King
     *         response contains 100
     *         status code 200
     *         header application/json
     */
    @Test
    public void test1(){
        Response response = given().pathParam("id","100")
                .when().get("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/employees/{id}");

        response.prettyPeek().then().statusCode(200).and().contentType("application/json"); // for content-type and for the statusCode we have special methods that will verify. for Rest we need to do assertions
        assertTrue(response.asString().contains("100"));
        assertTrue(response.asString().contains("King"));
    }

    /**
     *get country from countries table with id AR (use path parameters)
     *     Verify response contains Argentina
     *         response contains AR
     *         status code 200
     *         header application/json
     */
    @Test
    public void test2(){
        Response response = given().pathParam("id","AR")
                .when().get("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/countries/{id}");

        response.prettyPeek().then().statusCode(200).log().ifError();
        assertTrue(response.asString().contains("AR"));
        assertTrue(response.header("Content-Type").equals("application/json"));

    }

    /**
     *     get employee from departments table with id 2000 (use path parameters)
     * 	    Verify response
     * 		status code 404
     * 		header text/html
     */
    @Test
    public void test3(){
        Response response = given().pathParam("id","2000")
                .when().get("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/departments/{id}");
        response.then().statusCode(404).log().ifError(); // will print if Error happens (if different status code for example)
        assertTrue(response.getContentType().equals("text/html"));
    }

}
