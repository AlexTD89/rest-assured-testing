package tests.day07_;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Authentication_Authorization {
    /*

Authentication ->> who you are, in api it means can we access this api at all
Authorization >> what rights do you have, means do I have the permission to see certain resources in API

    There are several ty[es of authentication:
    Basic authentication -> using username and password ( for every call we are making)
    Key based authentication -> use the key that can be recognized by the server
    Token based authentication -> using a token that can be recognized by server
    Oath 1/Oath 2 -> using 3rd party to get authenticated


    401 -> Unauthorized. We do not have permission to use this service

        Basic authentication: using username and password in authenticating a request.
        preemptive -> authentication happens before anything else. In our request we include the username/password. There is one call
                    made to get authentication which only sends the username/password. After the server approves/authenticates, the actual request is made. In this call we
                    2 requests going out.

        challenged -> we send the request with username/password. And everything is sent to server together. In challenged authentication
                    only one call is made.

     */

    /** API Key Test */

    /**
     * test the 401 status code by not passing the api key
     */
    @Test
    public void noKey(){
        given().queryParams("t", "A star is born").
                when().get("http://www.omdbapi.com").prettyPeek()
                .then().statusCode(401)
                .body("Error", is("No API key provided."));
    }

    /**
     * call the omdbapi with a valid api key
     */
    @Test
    public void withKey(){
        given().
                queryParams("apikey", "e0484f01"). // sends the key for authentication
                queryParams("t", "Goonies").    // searching for a movie
                when().get("http://www.omdbapi.com").prettyPeek()
                .then().statusCode(200).body("Title", equalTo("The Goonies"))
                .body("Year", equalTo("1985"));
    }
}
