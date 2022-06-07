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

public class StoreImplementation implements Serializable {

    private Response postOrderForPet = null;
    private Response deleteOrder = null;


    @Before
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }


    @Given("the following get request that brings us the order by ID")
    public Response getOrderByID() {
        Response responseGetOrderByID = given().log().all().get("/store/order/5");
        return responseGetOrderByID;
    }

    @And("the response is 200 for getOrderByID")
    public void validateResponseGetOrderByID() {
        assertTrue("The response is not 200", getOrderByID().statusCode()==200);
    }

    @Given("the following post request that add order for a pet")
    public void postOrderForPet(){
        HashMap<String, Object> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", "5");
        bodyRequestMap.put("petId", "197");
        bodyRequestMap.put("quantity", "4");
        bodyRequestMap.put("status", "placed");
        bodyRequestMap.put("complete", "true");
        postOrderForPet =
                given().contentType(ContentType.JSON).body(bodyRequestMap).post("/store/order");
    }

    @And("the response is 200 for the store post")
    public void validateResponseStorePost() {
        assertTrue("The response is not 200", postOrderForPet.statusCode()==200);
    }

    @And("the store body response contains key name")
    public void validateResponseStorePostKeyBody(){
        postOrderForPet.then().body("$",hasKey("petId"));
    }

    @And("the following delete request that delete order")
    public void deleteOrder(){
        JsonPath jsonPathOrders = new JsonPath(postOrderForPet.body().asString());
        String jsonIdCreate=jsonPathOrders.getString("id");
        deleteOrder =
                given().accept(ContentType.JSON).delete("/store/order/"+jsonIdCreate);
    }

    @And("the response is 200 for the order delete")
    public void validateOrderCodeResponseDelete() {
        assertTrue("The response is not 200", deleteOrder.statusCode()==200);
    }


    @Given("the following get request that brings us the inventories finding by status")
    public  Response getInventories() {
        Response responseGetInventories = given().log().all().get("/store/inventory");
        return responseGetInventories;
    }

    @And("the response is 200 for getInventories")
    public void validateResponseGetInventories(){
        assertTrue("The response is not 200", getInventories().statusCode()==200);
    }
}
