package tests.day08_;

import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;

public class SSL_Certificates {
    /**
        bas SSL examples: https://badssl.com/


     */

    @Test
    public void badSSL(){
        given().
                relaxedHTTPSValidation(). //  handles certificates in rest assured, giving command that we can trust this website
                when().get("https://untrusted-root.badssl.com/").
                prettyPeek().
                then().
                statusCode(200);
    }

    @Test
    public void useKeyStore(){ // this method is just example, it not going to do noting
        // in the trustStore we pass the location of trust store file.
        given()
                .trustStore("path/to/file", "password")
                .when().get()
                .then().statusCode(200);
    }
}
