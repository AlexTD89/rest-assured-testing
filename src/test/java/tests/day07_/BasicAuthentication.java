package tests.day07_;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BasicAuthentication {

    /*

 Basic authentication: using username and password in authenticating a request.
        preemptive -> authentication happens before anything else. In our request we include the username/password. There is one call
                    made to get authentication which only sends the username/password. After the server approves/authenticates, the actual request is made. In this call we
                    2 requests going out.

        challenged -> we send the request with username/password. And everything is sent to server together. In challenged authentication
                    only one call is made.


   given().auth() ->> all the authentication config is done in the auth() method in rest assured.

     */

    @Test
    public void preemptive(){
        given().
                auth().preemptive().basic("admin", "admin").
                when().get("https://the-internet.herokuapp.com/basic_auth").
                then().statusCode(200);
    }

    @Test
    public void challenged(){
        given().
                auth().basic("admin", "admin").
                when().get("https://the-internet.herokuapp.com/basic_auth").
                then().statusCode(200);
    }
}
