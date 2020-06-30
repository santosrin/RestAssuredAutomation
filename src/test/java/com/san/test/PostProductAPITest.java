package com.san.test;

import com.san.common.Payloads;
import com.san.common.RestUtilities;
import com.san.constants.Path;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class PostProductAPITest {

    public static String prodID;
    Response response;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void setUp() {
        requestSpecification  = RestUtilities.postRequestSpecification();
        responseSpecification = RestUtilities.getResponseSpecification();
//        AuthenticationScheme authenticationScheme = RestAssured.basic
//                ("0364fd471299132265f670b8a9ae54ec","fcfb7959ffe3e6babda08ce8dbe3ee0d");
//        requestSpecBuilder = new RequestSpecBuilder();
//        requestSpecBuilder.setBaseUri("https://autdemo.myshopify.com/admin/api/2019-04");
//        //requestSpecBuilder.setBasePath("/admin/api/2019-04");
//        requestSpecBuilder.setAuth(authenticationScheme);
//        requestSpecBuilder.addHeader("Content-Type","application/json");
//        requestSpecBuilder.setBody(Payloads.PostPayloadShopify());
//        requestSpecification = requestSpecBuilder.build();
//
//        responseSpecBuilder = new ResponseSpecBuilder();
//        responseSpecBuilder.expectResponseTime(lessThan(6L), TimeUnit.SECONDS);
//        responseSpecBuilder.expectStatusCode(201);
//        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void postShopifyProduct() {
        given().
                spec(requestSpecification).
        when().
                post(Path.PRODUCT_ENDPOINT).
        then().
                log().all().
                spec(responseSpecification).
                statusCode(201);
//        response = given().
//                spec(requestSpecification).
//                when().
//                post("/products.json").
//                then().extract().response();
    }

    @Test
    public void postShopifyProduct1() {
        RestUtilities.ResponseVerif();
    }

    public Response ResponseVerif() {
        RestUtilities.setEndPoint(Path.PRODUCT_ENDPOINT);
        response = RestUtilities.getResponse(requestSpecification,"post");
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.statusCode(),201);
        Map<String,Object> prodResponse = response.jsonPath().get("product");
        prodID = prodResponse.get("id").toString();
        System.out.println(prodID);
        return response;
    }
}
