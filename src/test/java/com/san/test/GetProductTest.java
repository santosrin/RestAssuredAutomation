package com.san.test;

import com.san.common.Payloads;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class GetProductTest {

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
        requestSpecification = requestSpecBuilder.build();

        responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectResponseTime(lessThan(3L), TimeUnit.SECONDS);
        responseSpecBuilder.expectStatusCode(200);
        responseSpecBuilder.expectBody("product.id",equalTo(Long.parseLong("4926203134083")));
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void getShopifyProduct() {
        given().
                spec(requestSpecification).
        when().
                get("/products/4926203134083.json").
        then().
                spec(responseSpecification);
    }
}
