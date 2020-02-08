package tests.day03_;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DataDrivernAPITest {

    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr";
    }

    /**
     * ParameterizedTest ->> junit 5 annotiation for data driven tests
     * in junit 5 data can come from multiple sources
     * data can come csv files, methods, enums, @value source
     * the source is indicated next to the ParameterizedTest tag
     * @ValueSource ->> data is given with the test method
     *
     */

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4})
    public void validateRegionNameTest1(int id){
        given().pathParam("id", id)
                .when().get("/regions/{id}")
                .prettyPeek()
                .then().assertThat().statusCode(200)
                .and().assertThat().body("region_id", equalTo(id));
    }

    // CsvSource is 2d data
    @ParameterizedTest
    @CsvSource({    // csv >> comma separated values
            "1, Europe",
            "2, Americas",
            "3, Asia",
            "4, Middle East and Africa"})
    public void validateRegionNameTest2(int id, String name){
        given().pathParam("id", id)
                .when().get("/regions/{id}")
                .prettyPeek()
                .then().assertThat().statusCode(200)
                .and().assertThat().body("region_id", equalTo(id))
                .and().assertThat().body("region_name", equalTo(name));
    }


    /**
        CsvSource is 2d data
        junit 5 will look for the given file name in the test/resources folder
     */

    @ParameterizedTest
    @CsvFileSource(resources = "/regions.csv")
    public void validateRegionNameTest3(int id, String name){
        given().pathParam("id", id)
                .when().get("/regions/{id}")
                .prettyPeek()
                .then().assertThat().statusCode(200)
                .and().assertThat().body("region_id", equalTo(id))
                .and().assertThat().body("region_name", equalTo(name));
    }

}
