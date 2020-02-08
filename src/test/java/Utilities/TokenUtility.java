package Utilities;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TokenUtility {


   public enum USER_TYPE{TEACHER, TEAM_LEADER, TEAM_MEMBER};


    public static String getToken(USER_TYPE type){
        String token = null,
                email = null,
                password = null;
        switch(type){
            case TEACHER:
                email = ConfigurationReader.get("teacher_email");
                password = ConfigurationReader.get("teacher_password");
                break;
            case TEAM_LEADER:
                email = ConfigurationReader.get("team_leader_email");
                password = ConfigurationReader.get("team_leader_password");
                break;
            case TEAM_MEMBER:
                email = ConfigurationReader.get("team_member_email");
                password = ConfigurationReader.get("team_member_password");
                break;
        }
        Response response = given().
                queryParams("email", email).
                queryParams("password", password).
                when().get("/sign");
        response.then().statusCode(200);
        token = response.path("accessToken");
        return token;
    }
}
