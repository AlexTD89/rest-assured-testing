package tests.day10_;

import Utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojos.Student_Small_Pojo;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class warmup {

    /**
     WARM UP
     Create a student pojo for following json
     {
     “id”: 1766,
     “firstName”: “Meg”,
     “lastName”: “Besset”,
     “role”: “student-team-member”
     }
     Make a get request to Book it api:
     endpoint: 		{{qa1_url}}/api/students/:id
     path param id: 	1766
     Verify student information
     “id”: 1766,
     “firstName”: “Meg”,
     “lastName”: “Besset”,
     “role”: “student-team-member”
     */

    @BeforeAll
    public static void setup(){
//        RestAssured.baseURI = ConfigurationReader.get("bookitQa1Url");
//        RestAssured.baseURI = "https://cybertek-reservation-qa.herokuapp.com";
        RestAssured.baseURI = "https://cybertek-reservation-api-qa2.herokuapp.com";

    }

    @Test
    public void test1(){
        Response response = given().pathParam("id", "1766").
                when().get("/api/students/{id}").prettyPeek();
        Student_Small_Pojo newStudent = new Student_Small_Pojo(1766,"Meg","Besset","student-team-member");

//        JsonPath jsonPath = response.jsonPath();
//        List<Map<String, Object>> list = jsonPath.getMap("");
//        assertThat(map.get("id"), is(newStudent.getId()));
    }
}
