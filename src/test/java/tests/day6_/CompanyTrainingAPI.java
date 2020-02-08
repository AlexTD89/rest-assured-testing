package tests.day6_;

import Utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojos.Address;
import pojos.Company;
import pojos.Contact;
import pojos.Student;

import static io.restassured.RestAssured.given;

public class CompanyTrainingAPI {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = ConfigurationReader.get("companyAPIBaseURL");
    }

    /**
     * post new student using: /student/create
     *
     * verify success message
     */
    @Test
    public void postAStudent(){
        //create a student pojo with all required info
        Address address = new Address( "133 Some st", "Alexandria", "VA", 44324);
        //create company
        Company company = new Company( "Service API", "Managher","12-03-199", address);
        //create contact
        Contact contact = new Contact("1983467854", "bestStudent@studentemail.com","45578 address str");
        // create student
        Student student = new Student("Alex","Stalone",12,"08/01/2019", "07/09/1989","lalalala","SDET","Male","1234 there street","major", "section", contact, company);
        System.out.println("student = " + student);

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(student)
        .when().post("/student/create").prettyPeek()
        .then().statusCode(201);
    }

    @Test
    public void test1(){
        given().queryParams("apikey", "asasas").
                queryParams("t", "50moive").when().get("url");


        //how to submit a key(token) when asking for using an api
        given().header("keyheadername", "actualKey").when().get("URL");
    }
}
