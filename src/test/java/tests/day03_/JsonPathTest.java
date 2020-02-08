package tests.day03_;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonPathTest {

    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr";
    }

    /**
     * given url "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/employees/{id}"
     * when user makes get request with path param id=1
     * region id is 1
     * region name is Europe
     * then assert that status code is 200
     */
    @Test
    public void validateRegionName1(){
        Response response = given()
                .pathParam("id", "1")
                .when().get("/regions/{id}");
                response.then().statusCode(200);

                JsonPath jsonPath = response.jsonPath();
                String id = jsonPath.getString("region_id");
                assertThat(id, equalTo("1"));
                String name = jsonPath.getString("region_name");
                assertThat(name, equalTo("Europe"));

    }

    /**
     * given url "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/employees/{id}"
     * when user makes get request with path param id=100
     * and last_name id is equals to King
     * then assert that status code is 200
     * content type - application/json
     */
    @Test
    public void test1(){
        given().contentType(ContentType.JSON)                         // contentType(ContentType.JSON) >> accept type is .json
                .pathParam("id", "100")  // request with param id=100
                .when().get("/employees/{id}")                 // when user makes get request
                .then().assertThat().statusCode(200)                // check if the status code is 200
                .and().assertThat().body("last_name", is("King"));   // check last name is King
    }

    /**
     * given url "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/employees/{id}"
     * accept type is json
     * when user makes get request with path param id=100
     * and first href is equal to "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/employees/100"
     * then assert that status code is 200
     */
    @Test
    public void test2(){
        Response response = given().contentType(ContentType.JSON)
                .pathParam("id", "100")
                .when().get("/employees/{id}").prettyPeek();
        response.then().statusCode(200);

        JsonPath jsonPath = response.jsonPath();
        String link = jsonPath.getString("href");
        System.out.println(link); // null, because href is inside the links. we have to give a deeper path

        link = jsonPath.getString("links.href[0]"); // in json file, find key links, then find children href and get the first one
        System.out.println(link);
        assertThat(link, equalTo("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/employees/100"));
    }


    /**
            Given accept type is JSON
            When users sends a GET request to "/employees"
            Then status code is 200
            And Content type is application/json
            And last_name of the first employee from payload is "King"
            And salary of the first employee from payload is "24000"
            And last_name of the last employee from payload is "Mourgos"
            And salary of the last employee from payload is "5800"
     */
    @Test
    public void test3(){
        Response response = given().contentType(ContentType.JSON)
                .when().get("/employees");
        response.then().statusCode(200);
        response.then().contentType("application/json");

        JsonPath jsonPath = response.jsonPath();
        String firstLN = jsonPath.getString("items.last_name[0]");
        assertThat(firstLN, is("King"));
        String firstS = jsonPath.getString("items.salary[0]");
        assertThat(firstS, is("24000"));

        String lastFN = jsonPath.getString("items.last_name[-1]"); // [-1] > last one, [-2] > 2nd last, [-3] 3rd last
        assertThat(lastFN, is("Mourgos"));

        String lastS = jsonPath.getString("items.salary[-1]");
        assertThat(lastS, is("5800"));
    }

    /**
            Given accept type is JSON
            When users sends a GET request to "/employees"
            Then status code is 200
            And Content type is application/json
            And verify first_name of the employee whose employee_id is equal to 102 (Lex)
 */
    @Test
    public void gatValuesBasedOnAnotherValues(){
        Response response = given().contentType(ContentType.JSON)
                .when().get("/employees");
        response.then().statusCode(200);
        response.then().contentType("application/json");
        JsonPath jsonPath = response.jsonPath();
        jsonPath.prettyPrint();
        String name = jsonPath.getString("items.find{it.employee_id==102}.first_name");
        System.out.println(name);
        assertThat(name, is("Lex"));
        /*
                items.find {it.employee_id='101'}.first_name

                items  >> find element items
                find{query} >> keyword, it is like where, instead of the query we write the key/value info

                {it.employee_id='101'} >> it represents each individual node, it is like for each in java
                    employee_id='101' >> checks all nodes and find node where employee_id='101'

                .first_name returns the value of the first name from the result




                items.findALL{it.employee_id='101'}.first_name  >> find all elements with this query


         */
    }

    /**
     * Get all countries using the "/countries"
     * verify following countries are listed: Argentina, Brazil, Mexico, United States of America, Zambia
     */
    @Test
    public void testCountryList(){
        Response response = given().contentType(ContentType.JSON)
                .when().get("/countries");
        response.then().statusCode(200);
        List<String> actualList = response.jsonPath().getList("items.country_name");
//        System.out.println("actualList = " + actualList);
        List<String> expected = new ArrayList<>(Arrays.asList("Argentina", "Brazil", "Mexico"));
        assertThat(actualList, hasItems("Argentina", "Brazil", "Mexico"));

//        for (String Country : expected) {
//            assertTrue(actualList.contains(Country));
//        }

    }


    /**
     When users sends a GET request to "/employees"
     Given accept type is JSON
     Then status code is 200
     And Content type is application/json
     make sure that every item (salary) from this list is bigger than 100
     */
    @Test
    public void test5(){
        Response response = when().get("/employees");
        response.then().statusCode(200);

        List<Integer> listOfSalaries = response.jsonPath().getList("items.salary", Integer.class); // Integer.class >> converts directly the return type
        System.out.println("listOfSalaries = " + listOfSalaries);

        // make sure that every item (salary) from this list is bigger than 100
        assertThat(listOfSalaries, everyItem(greaterThan(100)));


    }

    /**
     *      List<String> salary = json.getList("items.findAll{it.salary > 100}.first_name");
     */
}
