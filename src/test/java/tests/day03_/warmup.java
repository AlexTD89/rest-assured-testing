package tests.day03_;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;


public class warmup {

    /*
     given url "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/regions/{id}"
     when user makes get request with path param id=1
     and region id is equals to 1
     then assert that status code is 200
     and assert that region name is Europe

     Try to do everything in one line by chaining methods instead of creating objects
     */

    @Test
    public void warmUp() {
        given().log().all()
                .pathParam("id", "1")
                .when().get("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/regions/{id}")
                .prettyPeek()
                .then().assertThat().statusCode(200)
                .and().assertThat().body("region_id", equalTo(1))
                .and().assertThat().body("region_name", equalTo("Europe"));
    }




}
