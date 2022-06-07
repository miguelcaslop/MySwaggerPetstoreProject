package com.cucumber.stepdefs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
//import io.cucumber.messages.internal.com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PetImplementation implements Serializable {
    private Response putPets = null;
    private Response postPets = null;
    private Response deletePets = null;


    @Before
    public void before() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    @Given("the following get request that brings us the pets")
    public Response getPets() {
        Response responseGetPets = given().log().all().get("/pet/197");
        return responseGetPets;
    }

    @And("the response is 200")
    public void validateResponse() {
        assertTrue("The response is not 200", getPets().statusCode() == 200);
    }

    @Given("the following get request that brings us the pets finding by status")
    public Response getPetsFindByStatus() {
        Response responseGetPetsFindByStatus = given().log().all().param("status", "available").param("status", "pending").param("status", "sold").get("/pet/findByStatus");
        return responseGetPetsFindByStatus;
    }

    @And("the response is 200 for getPetsFindByStatus")
    public void validateResponseGetPetsFindByStatus() {
        assertTrue("The response is not 200", getPetsFindByStatus().statusCode() == 200);
    }

    @Given("the following post request that add pets")
    public void postPets() {
        File jsonFile = new File("src/test/resources/pet.json");
        postPets =
                given().contentType(ContentType.JSON).body(jsonFile).post("/pet");
    }

    @And("the response is 200 for the pet post")
    public void validateResponsePetPost() {
        assertTrue("The response is not 200", postPets.statusCode() == 200);
    }

    @And("the pet body response contains key name")
    public void validateResponsePostKeyBody() {
        postPets.then().body("$", hasKey("id"));
    }

    @Then("the body response contains the {string} of the pet created")
    public void validateResponsePostBodyValueName(String valueName) {
        JsonPath jsonPathPets = new JsonPath(postPets.body().asString());
        String jsonPets = jsonPathPets.getString("name");
        assertEquals("The value of the name field is not what is expected", valueName, jsonPets);
    }

    @Given("the following put request that update pet")
    public void putPets() {
        postPets();
        JsonPath jsonPathUsers = new JsonPath(postPets.body().asString());
        String jsonIdCreate = jsonPathUsers.getString("id");
        HashMap<String, Object> bodyRequestMapPut = new HashMap<>();
        bodyRequestMapPut.put("id", jsonIdCreate);
        bodyRequestMapPut.put("name", "kira");
        bodyRequestMapPut.put("status", "available");
        putPets =
                given().contentType(ContentType.JSON).body(bodyRequestMapPut).put("/pet");
    }

    @And("the response is 200 for the pet put")
    public void validateResponsePetPut() {
        assertTrue("The response is not 200", putPets.statusCode() == 200);
    }

    @Then("the body response contains update {string}")
    public void validateResponsePetPutBodyUpdatedValueName(String updatedName) {
        JsonPath jsonPathPets = new JsonPath(putPets.body().asString());
        String jsonPetName = jsonPathPets.getString("name");
        assertEquals("The value of the name field is not what is expected", updatedName, jsonPetName);
    }

    @And("the following delete request that delete pet")
    public void deletePet() {
        JsonPath jsonPathPets = new JsonPath(postPets.body().asString());
        String jsonIdCreate = jsonPathPets.getString("id");
        deletePets =
                given().accept(ContentType.JSON).delete("/pet/" + jsonIdCreate);
    }

    @And("the response is 200 for the pet delete")
    public void validatePetCodeResponseDelete() {
        assertTrue("The response is not 200", deletePets.statusCode() == 200);
    }
}