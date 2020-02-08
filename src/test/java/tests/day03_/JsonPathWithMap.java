package tests.day03_;



import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class JsonPathWithMap {

    /**
            get the employee with id 100
            verify last_name is King
            verify salary is 24000
     */
    @Test
    public void employeeInformationTest(){
        JsonPath jsonPath = given().pathParam("id", 100).contentType(ContentType.JSON)
                .when().get("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/employees/{id}").jsonPath();

        Map<String, Object> personInfo = jsonPath.getMap(""); // "" >> means > go to the root element, or top level of the payload
        System.out.println("personInfo = " + personInfo);

        System.out.println(personInfo.get("employee_id"));
        System.out.println(personInfo.get("salary"));
        System.out.println(personInfo.get("hire_date"));
        System.out.println(personInfo.get("links"));

        assertThat(personInfo.get("last_name").toString(), is("King"));
        assertThat(personInfo.get("salary").toString(), is("24000"));

        List<Map<String,String>> links = jsonPath.getList("links");
//        System.out.println("links = " + links);

        Map<String, String> map = jsonPath.getMap("links[0]");
        System.out.println("map = " + map);

    }

    /**
     * When users sends a GET request to "https://www.metaweather.com/api/location/search"
     * when user makes get request with query param query = London
     *     expected list of params should match the actual one
     */
    @Test
    public void metaWeatherTest(){
        JsonPath jsonPath = given().queryParams("query", "London").
                when().get("https://www.metaweather.com/api/location/search").jsonPath();
        jsonPath.prettyPrint();
        Map<String, Object> map = jsonPath.getMap("[0]");
        System.out.println(map);

        Map<String,Object> expected = new HashMap<>();  // why object >> because the values can be string and int and double, etc...
        expected.put("title", "London");
        expected.put("location_type", "City");
        expected.put("woeid", 44418);
        expected.put("latt_long", "51.506321,-0.12714");

        assertThat(map, equalTo(expected));
    }


    /**
        call the metaweather api with query param = san
        verify all names contain string 'san'
 */
    @Test
    public void testNameContains(){
        JsonPath jsonPath = given().queryParams("query", "san").when()
                .get("https://www.metaweather.com/api/location/search").jsonPath();
        jsonPath.prettyPrint();

        List<Map<String, String>> list = jsonPath.getList(""); // list of maps

        for (Map<String, String> city : list){
            assertTrue(city.get("title").toLowerCase().contains("san"));
        }
    }


}
