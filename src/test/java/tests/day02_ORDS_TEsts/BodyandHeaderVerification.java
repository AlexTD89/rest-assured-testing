package tests.day02_ORDS_TEsts;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BodyandHeaderVerification {

    @BeforeAll
    public static void SetUP(){
        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr";
    }

    /**
     * verify that response contains header Content-Type: application/json
     * verify that first name is equal to Neena
     */
    @Test
    public void test1(){
        given().pathParam("id", "101")
                .when().get("employees/{id}").prettyPeek().then().
                and().
                assertThat().statusCode(200).
                and().
                // header("str", equalTo("str"))   ->>  does the extraction and the comparison at the same time
                header("Content-Type", equalTo("application/json")).
                // extract the value of the key first_name and verify
                and().body("first_name", equalTo("Neena"));
    }

    @Test
    public void test2(){
       JsonPath jsonPath = given().pathParam("id", "101")
                .when().get("employees/{id}").jsonPath();
       // JsonPath ->> class used to navigate through json body and extract values

        System.out.println(jsonPath.prettyPrint());

        String first_name = jsonPath.getString("first_name");
        System.out.println("first_name = " + first_name);

        String salary = jsonPath.getString("salary");
        System.out.println("salary = " + salary);

        String href = jsonPath.getString("links.href");
        System.out.println("href = " + href);

    }

    /**
     * Using ORDS api verify that Jennifer Whalen is the manger of the finance department.
     * get info of the finance dep
     * get the manager_id
     * get the person with this id
     */
    @Test
    public void test3(){
        // get info of the finance dep
        JsonPath jsonPath = given().pathParam("id", "100").
                when().get("/departments/{id}").jsonPath();

        String manager_id = jsonPath.getString("manager_id");
        System.out.println("manager_id = " + manager_id);

        jsonPath = given().pathParam("id", manager_id).when().get("/employees/{id}").jsonPath();

        assertThat(jsonPath.getString("first_name"), is("Nancy"));
    }

    /**
     * using base URI from the @BeforeAll method
     */
    @Test
    public void moreJsonPathTest(){
        JsonPath jsonPath = when().get("/countries/").jsonPath();
        // get all countries in a single string
        String all_countries = jsonPath.getString("items.country_name"); // follow the parent/child path
        System.out.println("all_countries = " + all_countries);
        // how to count how many countries are there?
        List<Object> list_countries = jsonPath.getList("items.country_name"); // or List<String> also works
        Map<String, String> list_countries1 = jsonPath.getMap("items.country_name"); // or List<String> also works
        System.out.println("===================");
        System.out.println("list_countries1 = " + list_countries1);
        System.out.println("===================");
        System.out.println(list_countries);
        System.out.println("list_countries.size() = " + list_countries.size());

        //get first country in the list
        String countryNo1 = jsonPath.getString("items.country_name[0]");
        System.out.println("countryNo1 = " + countryNo1);

        // get all countries id
        List<Integer> ids = jsonPath.getList("items.region_id");
        System.out.println("ids = " + ids);
    }
}
