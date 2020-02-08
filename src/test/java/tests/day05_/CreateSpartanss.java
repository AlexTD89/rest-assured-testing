package tests.day05_;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojos.Spartan;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateSpartanss {
    @BeforeAll
    public static void setup(){
//        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:8000";
        RestAssured.baseURI = "http://3.92.227.9:8000";
    }

    /**
     * create a new spartan object
     * by passing a valid gender, name , phone from file
     * by passing valid gender, name, phone
     *
     */
    @Test
    public void test1(){
        // create the file that we want to send
        File file = new File("src/test/resources/Spartan.json"); // will show the path to the Spartan.json (file where we give all params and values in order to create an spartan),
                                                                            // copy path of the Spartan.json
       given().contentType(ContentType.JSON) // sending json file as part of the request
               .body(file) // body ( "file path" ) ->> file shows what supposed to be in the body, in order to create a new spartan
       .when().post("/api/spartans")
       .prettyPeek().then().body("success",is("A Spartan is Born!"));

       // when created successful we supposed to receive as response status code : 201
    }

    /**
     * create a new spartan object
     * by passing a valid gender, name , phone from file
     * by passing valid gender, name, phone
     *
     */
    @Test
    public void test2(){
        Map<String, String> spartans = new HashMap<>();
        spartans.put("gender", "Female");
        spartans.put("name", "Rosalinda");
        spartans.put("phone", "3332225588");

        given().log().all().
                contentType(ContentType.JSON).
                body(spartans). // serialization happening here (objects to Json)
                when().post("/api/spartans")
                .prettyPeek().then()
                .statusCode(201);


    }

    @Test
    public void postANewSpartann(){
        String name = "Vasil";
        String gender = "Male";
        Long phone = 5178886644L;
        Map<String, Object> spartan = new HashMap<>();
        spartan.put("gender",gender);
        spartan.put("name",name);
        spartan.put("phone",phone);


        Response response = given().log().all().contentType(ContentType.JSON).body(spartan)
                .when().post("/api/spartans");
        response.prettyPeek()
                .then().statusCode(201)
                .body("success", is("A Spartan is Born!"));

        int id = response.path("data.id");
        System.out.println(id);
        given().pathParam("id",id).when().get("api/spartans/{id}")
                .then().statusCode(200).
                body("name", equalTo(name)).
                body("gender", equalTo(gender)).
                body("phone", equalTo(phone));
    }


    /**
     * create a new spartan
     * post it using Pojo files
     * verify status code 201
     *
     */
    @Test
    public void test3(){
        Spartan spartan = new Spartan("Male", "Neo Prime", 2002094578);

        given().log().everything().contentType(ContentType.JSON).
                body(spartan).
                post("/api/spartans").prettyPeek().
                then().statusCode(201);
    }

    /**
     * get single spartan using id
     * verify info
     */
    @Test
    public void test5(){
        Response response = given().accept(ContentType.JSON).pathParam("id", 482).
                when().get("/api/spartans/{id}");
        response.then().statusCode(200);
//        response.prettyPrint();

        // get the body and convert into Spartan object
        Spartan spartan = response.as(Spartan.class);

        System.out.println("spartan.toString() = " + spartan.toString());;
    }






}
