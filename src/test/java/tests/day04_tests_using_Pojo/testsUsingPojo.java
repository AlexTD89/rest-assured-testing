package tests.day04_tests_using_Pojo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojos.Employee;
import pojos.Job;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class testsUsingPojo {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr";
    }

    /**
     *
     */
    @Test
    public void getASingle(){
        Response response = given().pathParam("id", "IT_PROG").when().get("/jobs/{id}");
         response.then().statusCode(200);
//         response.prettyPeek();

         // convert the body into given type
        Job itJob = response.as(Job.class);
        System.out.println(itJob);

        System.out.println("itJob.getJob_title() = " + itJob.getJob_title());
        System.out.println("itJob.getJob_id() = " + itJob.getJob_id());


        // verify that job title is programmer
        assertThat(itJob.getJob_title(), equalTo("Programmer"));

    }

    @Test
    public void test1(){
        Job job = new Job("01", "accountant", 100, 1000000);
        System.out.println(job.toString());
    }

    /**
     * Store all jobs in a list of Job class
     */
    @Test
    public void getManyJobs(){
        Response response = when().get("/jobs");
        response.then().assertThat().statusCode(200);
//        response.prettyPrint();

        // get the response that contains all the jobs
        // "items" --> write the json path that matches all the jobs in the response
        // Jobs.class --> which pojo class do you want to convert to
        // convert the values to job object
        // put all the job objects into the new list
        // list contains Job type
        // deserialization is happening here, from json we get jobs
        List<Job> jobs = response.jsonPath().getList("items", Job.class);

//        System.out.println("jobs.size() = " + jobs.size());
//        System.out.println(jobs);
//        System.out.println("==============================");
//        System.out.println(jobs.get(0));

        Job itJob = response.jsonPath().getObject("items[0]", Job.class);
        System.out.println(itJob);
    }

    @Test
    public void getOneEmployee(){
        Response response = given().pathParam("id", "102").when().get("/employees/{id}");

        Employee emp = response.as(Employee.class);
        System.out.println(emp);


        assertThat(emp.getfirstName(), equalTo("Lex"));
    }

    @Test
    public void allTheEmployeesCount(){
        Response response = given().when().get("/employees");
        response.then().assertThat().statusCode(200);

        List<Employee> emps = response.jsonPath().getList("items", Employee.class);
        System.out.println(emps.size());

        assertThat(emps.size(), is(25));
    }
}
