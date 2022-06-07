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
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserImplementation implements Serializable {

    private Response postUsers = null;
    private Response postUsersWithArray = null;
    private Response postUsersWithList = null;
    private Response deleteUser = null;
    private Response putUsers = null;

    @Before
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }


    @Given("the following get request that brings us the users")
    public Response getUsers() {
        Response responseGetUsers = given().log().all().get("/user/mcasado20");
        return responseGetUsers;
    }

    @And("the response is 200 for getUsers")
    public void validateResponseGetUsers(){
        assertTrue("The response is not 200", getUsers().statusCode()==200);
    }
    @Given("the following get request that brings us the user login")
    public Response getUserLogin() {
        Response responseGetUserLogin = given().log().all().param("username", "mcasado").param( "password", "contraseña").get("/user/login");
        return responseGetUserLogin;
    }

    @And("the response is 200 for getUserLogin")
    public void validateResponseGetUserLogin(){
        assertTrue("The response is not 200", getUserLogin().statusCode()==200);
    }

    @Given("the following get request that brings us the user logout")
    public Response getUserLogout() {
        Response responseGetUserLogout = given().log().all().get("/user/logout");
        return responseGetUserLogout;
    }

    @And("the response is 200 for getUserLogout")
    public void validateResponseGetUserLogout(){
        assertTrue("The response is not 200", getUserLogout().statusCode()==200);
    }

    @Given("the following post request that add users")
    public void postUsers(){
        HashMap<String, Object> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", "120");
        bodyRequestMap.put("username", "mcasado20");
        bodyRequestMap.put("firstName", "Miguel");
        bodyRequestMap.put("lastName", "Casado");
        bodyRequestMap.put("email", "mcasado20@email.com");
        bodyRequestMap.put("password", "contraseña");
        bodyRequestMap.put("phone", "0");
        bodyRequestMap.put("userStatus", "0");
        postUsers =
                given().contentType(ContentType.JSON).body(bodyRequestMap).post("/user");
    }

    @And("the response is 200 for the user post")
    public void validateResponseUserPost() {
        assertTrue("The response is not 200", postUsers.statusCode()==200);
    }

    @And("the user body response contains key name")
    public void validateResponseUserPostKeyBody(){
        postUsers.then().body("$",hasKey("message"));
    }

    @Then("the body response contains the {string} of the user created")
    public void validateResponseUserPostBodyValueName(String valueName) {
        JsonPath jsonPathUsers = new JsonPath(postUsers.body().asString());
        String jsonUsers=jsonPathUsers.getString("code");
        assertEquals("The value of the name field is not what is expected",valueName,jsonUsers);
    }

    @Given("the following post request that add users with array")
    public void postUsersWithArray(){
        ArrayList<HashMap<String, Object>> user_list = new ArrayList<>();
        HashMap<String, Object> bodyRequestMap1 = new HashMap<>();
        bodyRequestMap1.put("id", "110");
        bodyRequestMap1.put("username", "mcasado10");
        bodyRequestMap1.put("firstName", "Miguel");
        bodyRequestMap1.put("lastName", "Casado");
        bodyRequestMap1.put("email", "mcasado10@email.com");
        bodyRequestMap1.put("password", "contraseña");
        bodyRequestMap1.put("phone", "0");
        bodyRequestMap1.put("userStatus", "0");
        HashMap<String, Object> bodyRequestMap2 = new HashMap<>();
        bodyRequestMap2.put("id", "111");
        bodyRequestMap2.put("username", "mcasado11");
        bodyRequestMap2.put("firstName", "Miguel");
        bodyRequestMap2.put("lastName", "Casado");
        bodyRequestMap2.put("email", "mcasado11@email.com");
        bodyRequestMap2.put("password", "contraseña");
        bodyRequestMap2.put("phone", "0");
        bodyRequestMap2.put("userStatus", "0");
        user_list.add(bodyRequestMap1);
        user_list.add(bodyRequestMap2);
        postUsersWithArray =
                given().contentType(ContentType.JSON).body(user_list).post("/user/createWithArray");
    }

    @And("the response is 200 for the user with array post")
    public void validateResponseUserWithArrayPost() {
        assertTrue("The response is not 200", postUsersWithArray.statusCode()==200);
    }

    @And("the users with array body response contains key name")
    public void validateResponseUserWithArrayPostKeyBody(){
        postUsersWithArray.then().body("$",hasKey("message"));
    }

    @Then("the body response contains the {string} of the users with array created")
    public void validateResponseUserWithArrayPostBodyValueName(String valueName) {
        JsonPath jsonPathUsersWithArray = new JsonPath(postUsersWithArray.body().asString());
        String jsonUsers=jsonPathUsersWithArray.getString("message");
        assertEquals("The value of the name field is not what is expected",valueName,jsonUsers);
    }

    @Given("the following post request that add users with list")
    public void postUsersWithList(){
        ArrayList<HashMap<String, Object>> user_list2 = new ArrayList<>();
        HashMap<String, Object> bodyRequestMap1 = new HashMap<>();
        bodyRequestMap1.put("id", "112");
        bodyRequestMap1.put("username", "mcasado12");
        bodyRequestMap1.put("firstName", "Miguel");
        bodyRequestMap1.put("lastName", "Casado");
        bodyRequestMap1.put("email", "mcasado12@email.com");
        bodyRequestMap1.put("password", "contraseña");
        bodyRequestMap1.put("phone", "0");
        bodyRequestMap1.put("userStatus", "0");
        HashMap<String, Object> bodyRequestMap2 = new HashMap<>();
        bodyRequestMap2.put("id", "113");
        bodyRequestMap2.put("username", "mcasado13");
        bodyRequestMap2.put("firstName", "Miguel");
        bodyRequestMap2.put("lastName", "Casado");
        bodyRequestMap2.put("email", "mcasado13@email.com");
        bodyRequestMap2.put("password", "contraseña");
        bodyRequestMap2.put("phone", "0");
        bodyRequestMap2.put("userStatus", "0");
        user_list2.add(bodyRequestMap1);
        user_list2.add(bodyRequestMap2);
        postUsersWithList =
                given().contentType(ContentType.JSON).body(user_list2).post("/user/createWithList");
    }

    @And("the response is 200 for the user with list post")
    public void validateResponseUserWithListPost() {
        assertTrue("The response is not 200", postUsersWithList.statusCode()==200);
    }

    @And("the users with list body response contains key name")
    public void validateResponseUserWithListPostKeyBody(){
        postUsersWithList.then().body("$",hasKey("message"));
    }

    @Then("the body response contains the {string} of the users with list created")
    public void validateResponseUserWithListPostBodyValueName(String valueName) {
        JsonPath jsonPathUsersWithList = new JsonPath(postUsersWithList.body().asString());
        String jsonUsers=jsonPathUsersWithList.getString("message");
        assertEquals("The value of the name field is not what is expected",valueName,jsonUsers);
    }

    @Given("the following put request that update user")
    public void putUsers(){
        postUsers();
        JsonPath jsonPathUsers = new JsonPath(getUsers().body().asString());
        String jsonIdCreate=jsonPathUsers.getString("id");
        String jsonUsernameCreate=jsonPathUsers.getString("username");
        HashMap<String, Object> bodyRequestMapPut = new HashMap<>();
        bodyRequestMapPut.put("id", jsonIdCreate);
        bodyRequestMapPut.put("username", "mcasado200");
        bodyRequestMapPut.put("firstName", "Miguel");
        bodyRequestMapPut.put("lastName", "Casado");
        bodyRequestMapPut.put("email", "mcasado20@email.com");
        bodyRequestMapPut.put("password", "contraseña");
        bodyRequestMapPut.put("phone", "0");
        bodyRequestMapPut.put("userStatus", "0");
        putUsers =
                given().contentType(ContentType.JSON).body(bodyRequestMapPut).put("/user/"+jsonUsernameCreate);
    }

    @And("the response is 200 for the user put")
    public void validateResponseUserPut() {
        assertTrue("The response is not 200", putUsers.statusCode()==200);
    }

    @Then("the body response contains update {string} of the user put")
    public void validateResponseUserPutBodyUpdatedValueName(String updatedName) {
        JsonPath jsonPathPets = new JsonPath(putUsers.body().asString());
        String jsonPetName=jsonPathPets.getString("code");
        assertEquals("The value of the name field is not what is expected",updatedName,jsonPetName);
    }

    @And("the following delete request that delete user")
    public void deleteUser(){
        JsonPath jsonPathOrders = new JsonPath(getUsers().body().asString());
        String jsonUsernameCreate=jsonPathOrders.getString("username");
        deleteUser =
                given().accept(ContentType.JSON).delete("/user/"+jsonUsernameCreate);
    }

    @And("the response is 200 for the user delete")
    public void validateUserCodeResponseDelete() {
        assertTrue("The response is not 200", deleteUser.statusCode()==200);
    }

}
