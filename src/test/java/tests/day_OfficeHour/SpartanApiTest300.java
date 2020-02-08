package tests.day_OfficeHour;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SpartanApiTest300 {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:8000";
    }

    /**
     * call the hello endpoint
     * verify statuc code : 200
     * content type: test/plain, charset = UTF-8
     * body: Hello from Sparta
     */
    @Test
    public void test1(){
        //sol 1

        when().get("/api/hello").then().statusCode(200). // status code assertion
                and().  // 3 ways to check the content type
                        assertThat().contentType("text/plain;charset=UTF-8").
                        and().assertThat().header("Content-Type", is("text/plain;charset=UTF-8")).
                        and().contentType(ContentType.TEXT).
                and().
                assertThat().body(is("Hello from Sparta")); // body assertion

        //sol 2
        Response response = when().get("/hello");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/plain;charset=UTF-8");
    }


    /**
     * bad headers test
     * call the hello end point "api/hello"
     * header ->>
     *          accept: application/json
     * verify:
     *      400 status code
     */
    @Test
    public void test2(){
        // accept()   ->> I (decide)  want to receive my response in Json format (or any other specific format)
        // contentType() ->> tells what type of payload we are sending in the request body, (Hey, I am sending you Json..)
        given().accept(ContentType.JSON).when().get("/api/hello").then().statusCode(406);
    }

    /**
   call the hello endpoint" /api/spartans
       header-->
       accept : application/json
   verify:
       200 status code
       content type: application/json;charset=UTF-8
    */
    @Test
    public void test3(){
        given().accept("application/json").when().get("/api/spartans").then().statusCode(200)
        .contentType("application/json;charset=UTF-8");
    }




 /**
   call the hello endpoint" /api/spartans
       header-->
       accept : application/xml
   verify:
       200 status code
       content type: application/xml;charset=UTF-8
    */
 @Test
    public void test4(){
        given().accept("application/xml").when().get("/api/spartans")
                .then().assertThat().statusCode(200)
                .contentType("application/xml;charset=UTF-8");
 }

 /**
        call the hello endpoint" /api/spartans{id}
            header-->
            accept : application/json
            path param-->
                id : 101
        verify:
            200 status code
            body
                "id":       101,
                "name":     "Mr.Post",
                "gender":   "Male",
                "phone":    2024567892
 */
    @Test
    public void test5(){
        int id = 101;
        given().accept(ContentType.JSON).
                pathParam("id", id)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
        .body("id", equalTo(id))
        .body("name", equalTo("Mr.Post"))
        .body("gender", equalTo("Male"))
        .body("phone", equalTo(2024567892));
    }

}
