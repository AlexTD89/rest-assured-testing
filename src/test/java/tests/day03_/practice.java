package tests.day03_;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class practice {
    // "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr"

    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr";
    }


    @Test
    public void test1(){
        given().pathParam("id", "1").when().get("/regions/{id}")
                .then().statusCode(200)
        .and().assertThat().body("region_id", equalTo(1))
        .and().assertThat().body("region_name", is("Europe"));
    }

    @Test
    public void validateRegionName1(){
        Response response = given().pathParam("id", 1).
                when().get("/regions/{id}");
        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();
        String regionID = jsonPath.getString("region_id");
        assertThat(regionID, is("1"));
        String regionName = jsonPath.getString("region_name");
        assertThat(regionName, is("Europe"));
    }

    @Test
    public void test3(){
        given().contentType(ContentType.JSON).pathParam("id", 100).when().get("/employees/{id}").then().statusCode(200)
                .and().assertThat().body("last_name", is("King"));
    }

    @Test
    public void test4(){
        Response response = given().contentType(ContentType.JSON).pathParam("id", 100).
                when().get("/employees/{id}");
        response.then().statusCode(200);
        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        List<Object> list = jsonPath.getList("links.href");
        assertThat(list.get(0), is("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/employees/100"));
    }

    @Test
    public void test5(){
        Response response =  given().contentType(ContentType.JSON).when().get("/employees");
        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();
        String firstName = jsonPath.get("items[0].last_name").toString();
        assertThat(firstName, is("King"));
        String salary = jsonPath.get("items[0].salary").toString();
        assertThat(salary, is("24000"));
        String lastEmplFN = jsonPath.get("items[-1].last_name").toString();
        assertThat(lastEmplFN, is("Mourgos"));
        String lastEmpSalary = jsonPath.get("items[-1].salary").toString();
        assertThat(lastEmpSalary, is("5800"));
    }

    @Test
    public void test6(){
        Response response = given().contentType(ContentType.JSON).queryParams("id", "102").
                            when().get("/employees");
        response.then().statusCode(200);
        response.then().contentType("application/json");
        JsonPath jsonPath = response.jsonPath();
        System.out.println(jsonPath.getString("items.find{it.employee_id==102}.first_name"));
        String name = jsonPath.getString("items.find{it.employee_id==102}.first_name");
        assertThat(name, is("Lex"));
    }

    @Test
    public void test7(){
        JsonPath jsonPath = when().get("/countries").jsonPath();
        List<Object> listOfCountries = jsonPath.getList("items.country_name");
        List<Object> exp = new ArrayList<>(Arrays.asList("Argentina", "Brazil", "Mexico", "United States of America", "Zambia"));
        assertThat(listOfCountries, hasItems("Argentina", "Brazil", "Mexico", "United States of America", "Zambia"));
    }

    @Test
    public void test8(){
        Response response = given().contentType(ContentType.JSON).when().get("/employees");
        response.then().statusCode(200);
        response.then().contentType("application/json");
        JsonPath jsonPath = response.jsonPath();
        List<Integer> salaryList = jsonPath.getList("items.salary", Integer.class);
        assertThat(salaryList, everyItem(greaterThan(100)));
    }

    @Test
    public void test9(){
        JsonPath jsonPath = given().pathParam("id", 100).contentType(ContentType.JSON)
                .when().get("/employees/{id}").jsonPath();
        Map<String, Object> personinfo = jsonPath.getMap("");
        assertThat(personinfo.get("last_name"), is("King"));
        assertThat(personinfo.get("salary"), is(24000));
    }

    @Test
    public void test10(){
        Map<Object,Object> expected = new HashMap<>();  // why object >> because the values can be string and int and double, etc...
        expected.put("title", "London");
        expected.put("location_type", "City");
        expected.put("woeid", 44418);
        expected.put("latt_long", "51.506321,-0.12714");
        JsonPath jsonPath = given().queryParams("query", "London").when()
                .get("https://www.metaweather.com/api/location/search").jsonPath();
        Map<Object, Object> map = jsonPath.getMap("[0]");
        assertEquals(expected, map);
        assertThat(map, equalTo(expected));
    }

    @Test
    public void test11(){
        JsonPath jsonPath = given().queryParams("query", "san").when()
                .get("https://www.metaweather.com/api/location/search").jsonPath();
        List<Map<String, String>> list = jsonPath.getList("");
        for (Map<String, String> map : list) {
            assertTrue(map.get("title").toLowerCase().contains("san"));
        }
    }

    @ParameterizedTest
    @ValueSource(ints={1,2,3,4})
    public void test12(int id){
        given().pathParam("id", id).when().get("/regions/{id}")
        .prettyPeek().
        then().assertThat().statusCode(200)
        .and().assertThat().body("region_id", equalTo(id));
    }

    @ParameterizedTest
    @CsvSource({
            "1, Europe",
            "2, Americas",
            "3, Asia",
            "4, Middle East and Africa"})
    public void test13(int id, String name){
        given().pathParam("id", id).when().get("/regions/{id}")
                .then().assertThat().statusCode(200)
                .and().assertThat().body("region_id", is(id))
                .and().assertThat().body("region_name", is(name));
    }

    public static void main(String[] args) {
        int[] arr = {3,2,5,1};
        List<Integer> list = new ArrayList<>();
        for (int i : arr) {
            list.add(i);
        }

        Collections.sort(list);
        System.out.println(list);

        for (int i=0; i<arr.length;i++) {
            arr[i]=list.get(i);
        }

        System.out.println(Arrays.toString(arr));

    }

}
