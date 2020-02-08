package tests.day08_;

import Utilities.ConfigurationReader;
import Utilities.TokenUtility;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Utilities.TokenUtility.USER_TYPE.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookitStudentTests {

    @BeforeAll
    public void setup(){
        RestAssured.baseURI = ConfigurationReader.get("bookitQa1Url");
    }

    /**
     * get all students from the /api/students endpoint
     * using token from a team member
     * verify status code 200
     * verify data type json
     */
    @Test
    public void testAllStudents(){
        // get a token
        String token = TokenUtility.getToken(TEAM_MEMBER);
        System.out.println("token = " + token);
        assertThat(token, not(emptyOrNullString()));
        // get all students
        given().header("Authorization", token)
                .when().get("/api/students")
        .prettyPeek()
        .then().statusCode(200).
        and().contentType(ContentType.JSON);

        // get count of all the students
        JsonPath jsonPath = given().header("Authorization", token)
                .when().get("/api/students").jsonPath();
        List<String> allStudent = jsonPath.getList("firstName");
        System.out.println("allStudent.size() = " + allStudent.size());
        System.out.println("allStudent = " + allStudent);
    }


    /**
     * try to create new student
     * by using the token of a team member
     * verify error message only teacher allowed to modify database
     */

    public Map<String, Object> getNewStudent(){
        Map<String, Object> student = new HashMap<>();
        Faker faker = new Faker();
        student.put("first-name", faker.name().firstName());
        student.put("last-name", faker.name().lastName());
        student.put("email", faker.internet().emailAddress());
        student.put("password", "password");
        student.put("role", "student-team-member");
        student.put("campus-location", "VA");
        student.put("batch-number", 12);
        student.put("team-name", "bestteam");
        return student;
    }

    @Test
    public void testTeamMember(){
        // generate a new student
        Map<String, Object> newStudent = getNewStudent();
        System.out.println("newStudent = " + newStudent);

        given(). // pass token for authorization
//                header("Authorization",TokenUtility.getToken(TEAM_MEMBER)). // will throw 403
                header("Authorization",TokenUtility.getToken(TEACHER)).
                queryParams(newStudent)     // query params will accept map to pass all query params at one time
                .when().post("/api/students/student").
                prettyPeek().
                then().
//                statusCode(403) // 403 -> forbidden
                statusCode(201) // 403 -> forbidden
                .and().
                contentType(ContentType.JSON)
//        .and().body(containsString("only teacher allowed to modify database.")) // in case of 403
        .and().body(containsString("user "+newStudent.get("first-name")+" "+newStudent.get("last-name")+" has been added to database.")) // in case of success
        ;


        /*
        result of this request: we don't have the Authorization to do a post request ! (no rights for post())
        although we have authentication to work with get requests (we have the rights for get())

            HTTP/1.1 403 Forbidden
            Connection: close
            Date: Tue, 04 Feb 2020 16:47:08 GMT
            Content-Type: text/html;charset=utf-8
            Server: Jetty(9.4.8.v20171121)
            Via: 1.1 vegur

            <html>
              <body>only teacher allowed to modify database.</body>
            </html>







            when Authentication fails we get 401 Not Authorized error. It means we didn't provide proof of wo we are. it can be using username/password.
             Apices, token, certificates
             authentication do have access to the api in general.
             Authorization
             .. is done after we are authenticated. When authorization fails get 403 Forbidden
             , it means server knows who we are. But we do not have enough
             permission to do certain action/ to make certain call.

             402 -> wrong request

         */
    }


    /**
     * create a new student using the post method to /api/students/student
     * by using a token of a team leader
     * verify status code 403
     * verify error message only teacher allowed to modify database
     */
    @Test
    public void testTeamLeader(){
        Map<String, Object> newStudent = getNewStudent();
        String token = TokenUtility.getToken(TEAM_LEADER);

        given().
                accept(ContentType.JSON).
                header("Authorization", token).
                params(newStudent).
                when().
                post("/api/students/student").
                prettyPeek().
                then().statusCode(403).
                and().
        body(containsString("only teacher allowed to modify database."));
    }



    /**
     * create a new student using the post method to /api/students/student
     * by using a token of a teacher
     * verify status code 201
     * verify error message only teacher allowed to modify database
     */
    @Test
    public void testTeacher(){
        Map<String, Object> newStudent = getNewStudent();
        String token = TokenUtility.getToken(TEACHER);

        given().
                header("Authorization", token).
                queryParams(newStudent).
            when().
                post("/api/students/student").
                prettyPeek().
                then().
                statusCode(201).
                and().body(containsString("user "+newStudent.get("first-name")+" "+newStudent.get("last-name")+" has been added to database."));
    }

}
