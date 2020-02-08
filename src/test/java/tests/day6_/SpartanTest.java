package tests.day6_;


import Utilities.ConfigurationReader;
import Utilities.SpartanApiUtils;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojos.Spartan;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SpartanTest {
    /**
     * Create a new spartan by sending a request the spartan API
     * pass the given spartan parameter as the request body.
     * return the response object. response must contain json payload.
     * @param 'spartan'
     * @return response
     */
   @BeforeAll
   public static void setup(){
       RestAssured.baseURI = ConfigurationReader.get("spartanApiBaseURL");
   }

    /**
     * create a Spartan
     */
    @Test
    public void createSpartan(){
        Spartan spartan = SpartanApiUtils.createSpartanObject();
        Response response = SpartanApiUtils.createSpartan(spartan);
        response.then().statusCode(201);
        response.then().body("success", is("A Spartan is Born!"));
        response.prettyPeek().then().body("data.name", is(spartan.getName()));
    }


    /**
     * find a Spartan by id, return response
     */
    @Test
    public void test2(){
        //create a spartan
        Spartan spartan = SpartanApiUtils.createSpartanObject();
        //send request to api to create a spartan
        Response postResponse = SpartanApiUtils.createSpartan(spartan);
        postResponse.then().statusCode(201);

        // get the id of the new Spartan
        int id = postResponse.path("data.id");

        // find the Spartan using id
        Response response = SpartanApiUtils.getSpartan(id).prettyPeek();
    }

    /**
     * find a Spartan by id, return POJO object
     */
    @Test
    public void test3(){
        //create a spartan
        Spartan spartan = SpartanApiUtils.createSpartanObject();
        //send request to api to create a spartan
        Response postResponse = SpartanApiUtils.createSpartan(spartan);
        postResponse.then().statusCode(201);

        // get the id of the new Spartan
        int id = postResponse.path("data.id");

        //find the spartan Object
        Spartan findSpartan = SpartanApiUtils.getSpartanObject(id); // change id
        System.out.println(findSpartan.toString());
    }

    /**
     * delete a Spartan using it's id to find it
     */
    @Test
    public void test4(){
        //create a spartan
        Spartan spartan = SpartanApiUtils.createSpartanObject();
        //send request to api to create a spartan
        Response postResponse = SpartanApiUtils.createSpartan(spartan);
        postResponse.then().statusCode(201);

        // get the id of the new Spartan
        int id = postResponse.path("data.id");

        // delete Spartan using its id
        Response deleteResponse = SpartanApiUtils.deleteSpartan(id);

        //verify that spartan was successfully deleted, status code 204
        deleteResponse.then().statusCode(204);
        deleteResponse.prettyPeek();

        // verify that this Spartan doesn't exist anymore
        given().pathParam("id", id).when().get("/api/spartans/{id}").
                then().statusCode(404);
    }


    /**
     * how to use put() method
     *
     * Updating using put()
     * create new spartan
     * update the name
     * verify the name is updated
     * verify other information is not updated
     */
    @Test
    public void test5(){

        // creating new pojo
        Spartan spartan = SpartanApiUtils.createSpartanObject();
        //send request to api to create a spartan
        Response postResponse = SpartanApiUtils.createSpartan(spartan);
        postResponse.then().statusCode(201);

        // get the id of the new Spartan
        int id = postResponse.path("data.id");

        // after deserialization, change the name in the pojo
        spartan.setName("Zoom");

        //send new information (new pojo with new info) using put
        given().contentType(ContentType.JSON).  // telling the api what kind of data i am sending
                accept(ContentType.JSON).  // telling the api what kind of data i want to receive
                pathParam("id", id).
                body(spartan).
                when().put("/api/spartans/{id}").
                prettyPeek().then().statusCode(204);

        //verify the updated by getting the spartan by id
        given().pathParam("id", id)
                .accept(ContentType.JSON).when().get("/api/spartans/{id}").prettyPeek()
                .then().statusCode(200).
                body("name", equalTo(spartan.getName())).
                body("gender", equalTo(spartan.getGender()))
                .body("phone", is(spartan.getPhone()));

        // deleting the spartan from DB
        Response postresp = SpartanApiUtils.deleteSpartan(id);
        postresp.prettyPeek().then().statusCode(204);

    }
}
