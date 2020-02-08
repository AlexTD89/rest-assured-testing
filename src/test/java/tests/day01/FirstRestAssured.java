package tests.day01;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class FirstRestAssured {


    /**
     * when I send a request to this place: http://api.openrates.io/latest
     * Then status code must be 200
     */
    @Test
    public void verifyStatusCode(){
        //response —>> response that us sent by the server as result of our request
        // when() is a Static method imported directly from the restassured class
        //get -->> send the request to the given url
        Response response = when().get("http://api.openrates.io/latest");
        //print the response
        response.prettyPrint();
        //verify the response
        //verifies that status code matches the provided option
        response.then().statusCode(404);
    }

    /**
     * when I send a request to this place: http://api.openrates.io/latest
     * Then body should  contain "base": "EUR"
     */
    @Test
    public void verifyBodyContains(){
        Response response = when().get("http://api.openrates.io/latest");
        // asString() -->> returns the body as a single String
        String bodyStr = response.asString();
        System.out.println(bodyStr);
        // verifies if
        assertTrue(bodyStr.contains("\"base\":\"EUR\""));
    }

    /**
     * when I send a request to this place: http://api.openrates.io/latest
     * Then response should contain header application/json
     */
    @Test
    public void verifyHeader1(){
        Response response = when().get("http://api.openrates.io/latest");
        // response.header() ->> returns the value of the provided header
        String contentType = response.header("Content-Type");
        String date = response.header("Date");

        System.out.println("contentType = " + contentType);
        System.out.println("date = " + date);
        /*
                contentType = application/json
                date = Thu, 23 Jan 2020 16:18:25 GMT
         */

        assertEquals("applASDASDication/json", contentType);
        assertTrue(date.contains("2020"));
    }

    /**
     * when I send a request to this place: http://api.openrates.io/latest
     * Then response should contain header application/json
     */
    @Test
    public void verifyContentType(){
        Response response = when().get("http://api.openrates.io/latest");
        // response.getContentType(); ->> returns content type of the response
        String contentType = response.getContentType();
        System.out.println(contentType);
        // response.getStatusCode(); ->> returns the status code of the response
        int statusCode = response.getStatusCode();
        System.out.println("statusCode = " + statusCode);

        assertEquals("application/json", contentType);

        //this line will print and also verify the status code and that contentType is a json format
        response.prettyPeek().then().statusCode(200).and().contentType(ContentType.JSON);
    }

    /**
         when i send request to  http://api.zippopotam.us/us/22031
     *    Then the status must 200
     *    And verify that response contains Fairfax
     */
    @Test
    public void verifyStatusContains(){
        Response response = when().get("http://api.zippopotam.us/us/22031");
        response.then().statusCode(200);
        String bodyStr = response.asString();
        System.out.println("bodyStr = " + bodyStr);
        assertTrue(bodyStr.contains("Fairfax"));
    }



    /**
     * when i send request to  http://api.zippopotam.us/us/22031111
     * Then the status must 400
     */
    @Test
    public void verifyStatus(){
        Response response = when().get("http://api.zippopotam.us/us/22031111");
        // prettyPeak() will allow to print and continue with other tasks in the same line
        response.prettyPeek().then().statusCode(404);
    }



}
