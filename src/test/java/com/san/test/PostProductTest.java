package com.san.test;

import com.san.common.Payloads;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.internal.http.HTTPBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class PostProduct {

    Response response;
    RequestSpecBuilder requestSpecBuilder;
    static RequestSpecification requestSpecification;

    ResponseSpecBuilder responseSpecBuilder;
    static ResponseSpecification responseSpecification;

    @BeforeClass
    public void setUp() {
        AuthenticationScheme authenticationScheme = RestAssured.basic
                ("0364fd471299132265f670b8a9ae54ec","fcfb7959ffe3e6babda08ce8dbe3ee0d");
        requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://autdemo.myshopify.com/admin/api/2019-04");
        //requestSpecBuilder.setBasePath("/admin/api/2019-04");
        requestSpecBuilder.setAuth(authenticationScheme);
        requestSpecBuilder.addHeader("Content-Type","application/json");
        requestSpecBuilder.setBody(Payloads.PostPayloadShopify());
        requestSpecification = requestSpecBuilder.build();

        responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectResponseTime(lessThan(6L), TimeUnit.SECONDS);
        responseSpecBuilder.expectStatusCode(201);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void postShopifyProduct() {
        given().
                spec(requestSpecification).
        when().
                post("/products.json").
        then().
                spec(responseSpecification);

//        response = given().
//                spec(requestSpecification).
//                when().
//                post("/products.json").
//                then().extract().response();

    }
}
