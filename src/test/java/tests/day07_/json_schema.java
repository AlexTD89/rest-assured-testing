package tests.day07_;

import Utilities.ConfigurationReader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class json_schema {

    /**
     * Go to http://www.omdbapi.com/apikey.aspx
     * Click  on radiobutton FREE! (1,000 daily limit)
     * Enter your email
     * Click submit
     * Check your email for the api key from omdbapi.com
     */

    /**
     * call th comapny training api
     * get teacher by id 101
     * verify that matches the given schema
     * schema is in resources folder
     * Name: teacher_spartan.json
     */
    @Test
    public void testSchema(){
        // picks up a file from resource folder
        given().pathParam("id", 101).
                get(ConfigurationReader.get("companyAPIBaseURL")+"teacher/{id}").
                prettyPeek().then().statusCode(200).
                body(matchesJsonSchemaInClasspath("teacher_template.json")); // method used to verify the json payload matches the given schema template.
                                // by default it points to the resources test/folder
        /**
         * this test compares the response payload with json schema
         * only validating if the data comes in a wright format
         */
    }
}
