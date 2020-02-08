package Utilities;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojos.Spartan;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class SpartanApiUtils {

    /**
     * create a new Spartan
     * @param spartan
     * @return
     */
    public static Response createSpartan(Spartan spartan){
        Response response = given().log().everything().contentType(ContentType.JSON).
                body(spartan).
                post("/api/spartans").prettyPeek();
        return response;
    }

    /**
     * create a Spartan Object, using Java Faker
     * @return
     */
    public static Spartan createSpartanObject(){
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String gender = new Random().nextBoolean() ? "Female" : "Male";
        int phone = 1246537864;
        Spartan spartan = new Spartan(gender, name, phone);
        return spartan;
    }

    /**
     * find Spartan by id
     * returns response
     * @param id
     * @return
     */
    public static Response getSpartan(int id){
        Response response = given().log().everything().pathParam("id", id).when().get("/api/spartans/{id}").prettyPeek();
        return response;
    }

    /**
     * finds Spartan by id
     * returns POJO object
     * @param id
     * @return
     */
    public static Spartan getSpartanObject(int id){
        Response response =  given().log().everything().pathParam("id", id).when().get("/api/spartans/{id}").prettyPeek();
        Spartan spartan = response.as(Spartan.class);
        return spartan;
    }


    /**
     * deletes a Spartan by id
     * return response
     * @param id
     * @return
     */
    public static Response deleteSpartan(int id){
        Response response = given().log().everything().pathParam("id", id).when().delete("/api/spartans/{id}").prettyPeek();
        return response;
    }


    /**
     * method takes a POJO, finds Spartan by id
     * updates the Spartan info
     * @param spartan
     * @return
     */
    public static Response updateSpartan(Spartan spartan){
        return null;
    }
}
