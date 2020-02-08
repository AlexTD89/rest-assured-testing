package tests.day04_tests_using_Pojo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;


public class warmUP {

    @BeforeAll
    public static void setUP(){
        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr";
    }

    /**
     WARMUP:
     when user makes get request to "/employees" in hr ORDS-HR api
     then user verifies that status code is 200
     and  user verifies that average salary is greater that 5000
     */

    @Test
    public void test1(){
        Response response = when().get("/employees");
        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();
        List<Integer> salaryList = jsonPath.getList("items.salary");
        Integer sum = 0;
        for (Integer eachSalary : salaryList) {
        sum+=eachSalary;
        }
//        Assertions.assertTrue((sum/salaryList.size())>5000);
        assertThat(sum/salaryList.size(), greaterThan(500));
    }

    @Test
    public void testByTeacher(){

    }
}
