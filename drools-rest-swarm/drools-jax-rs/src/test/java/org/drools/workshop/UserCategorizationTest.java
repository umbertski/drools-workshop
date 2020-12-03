package org.drools.workshop;

import static org.hamcrest.CoreMatchers.is;

import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import io.restassured.RestAssured;

@RunWith(Arquillian.class)
@DefaultDeployment
public class UserCategorizationTest {

    @Test
    @RunAsClient
    public void testUserCategorizationAdult2() {

    	RestAssured.given()
                    .body("{\"name\": \"Simon\", \"age\": \"33\"}")
                    .header("Content-Type", MediaType.APPLICATION_JSON)
                    .when()
                    .post("/api/users/categorize")
                    .then()
                    .statusCode(200)
                    .body(  "name", is("Simon"),
                            "age", is(33),
                            "category", is("Adult"));
    }
    
    @Test
    @RunAsClient
    public void testUserCategorizationTeenager2() {

    	RestAssured.given()
                    .body("{\"name\": \"John\", \"age\": \"16\"}")
                    .header("Content-Type", MediaType.APPLICATION_JSON)
                    .when()
                    .post("/api/users/categorize")
                    .then()
                    .statusCode(200)
                    .body(  "name", is("John"),
                            "age", is(16),
                            "category", is("Teenager"));
    }    
    
    @Test
    public void doNothing() {
    }

}