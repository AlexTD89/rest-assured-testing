package tests.day07_;

import Utilities.ConfigurationReader;
import Utilities.TokenUtility;
import com.sun.tools.jxc.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TokenBased_Authentication {

    /*
    Using a token as a proof in order to be authenticated.
    We can get token from one server and use it in another server/api where it us recognized.

    This is similar to how Oath 2 operates.


    In this jwp based authentication token is issued by the same api and we use it to login to this api.
    This authentication also should be used over https, just like basic authentication.

    Webservices work over network. The network works base on set of rules/protocols (http, smtp, ftp, etc..)

    Rest APIs use http only. There are 2 types of http: secure and not-secure
    http: not secure
    https: secure

    how to get a token: read api documentation


    In token based, first we get token from one endpoint then we can use the token to reach other endpoints.  In this example we are calling the sign endpoint in order to get token. We extract the token from the response:
    When trying to use the other endpoints we pass the token in header as decried in documentation.

     */

    /**
     * BookItTest
     */

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = ConfigurationReader.get("bookitQa1Url");
    }

    /**
     * verify no sign message
     * request api without token
     *
     * 422 -> Unprocessable Entity
     */
    @Test
    public void test1(){
        when().get("/api/cmpuses")
        .prettyPeek()
        .then().statusCode(422);
    }

    /**
     * get access token
     */
    @Test
    public void test2(){
        //team_member_email
        String email = ConfigurationReader.get("team_member_email");
        String password = ConfigurationReader.get("team_member_password");

        Response response = given().
                queryParams("email", email)
                .queryParams("password", password)
                .when().get("/sign");
        response.prettyPeek()
                .then().statusCode(200);

        /**
        after successful request:
                {
                    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxOTc0IiwiYXVkIjoic3R1ZGVudC10ZWFtLW1lbWJlciJ9.raPyuRcS8xM5eOhEW4qxepwbs9XHPjlV4Xo8CIPxaPs",
                    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxOTc0IiwiYXVkIjoic3R1ZGVudC10ZWFtLW1lbWJlciJ9.raPyuRcS8xM5eOhEW4qxepwbs9XHPjlV4Xo8CIPxaPs"
                }
         */

        String accessToken = response.path("accessToken");
        System.out.println("accessToken = " + accessToken);


        /** a token  is not forever, it expires after some time         */

    }


    /**
     * get all campuses by providing access token
     */
    @Test
    public void test3(){
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxOTc0IiwiYXVkIjoic3R1ZGVudC10ZWFtLW1lbWJlciJ9.raPyuRcS8xM5eOhEW4qxepwbs9XHPjlV4Xo8CIPxaPs";
        given().
                header("Authorization", accessToken ). // this header was taken from the api documentation, it describers how to use the token in the request
                                // this api is asking for a token (that we received in previous test2()) to make any actions with this api
                when().
                get("/api/campuses").
                prettyPeek().
                then().statusCode(200);
    }

    /**
     * use getToken() util
     */
    @Test
    public void test4(){
        String accessToken = TokenUtility.getToken(TokenUtility.USER_TYPE.TEACHER);
        given().
                header("Authorization", accessToken ). // this header was taken from the api documentation, it describers how to use the token in the request
                // this api is asking for a token (that we received in previous test2()) to make any actions with this api
                        when().
                get("/api/campuses").
                prettyPeek().
                then().statusCode(200);
    }

    /**  Unit Test the token utility  wih teacher */
    @Test
    public void test5(){
        String token = TokenUtility.getToken(TokenUtility.USER_TYPE.TEACHER);
        System.out.println("token = " + token);
        assertThat(token, not(emptyString()));
    }

    /**  Unit Test the token utility  wih team member */
    @Test
    public void test6(){
        String token = TokenUtility.getToken(TokenUtility.USER_TYPE.TEAM_MEMBER);
        /**
         * getTokens(enums), enums it is like a class where we
         * will store the only values that we want to use in this method
         *
         * it works when you need to have a limited choices to pass for a method
         */

        System.out.println("token = " + token);
        assertThat(token, not(emptyString()));
    }
}
