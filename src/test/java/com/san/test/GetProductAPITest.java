package com.san.test;

import com.san.common.RestUtilities;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class GetProductAPITest {

 Response response;
 String prodID;
 RequestSpecification requestSpecification;
 ResponseSpecification responseSpecification;


    @BeforeClass
    public void setUp() {
        requestSpecification = RestUtilities.getRequestSpecification();
        responseSpecification = RestUtilities.getResponseSpecification();
        response = RestUtilities.ResponseVerif();
        Map<String,Object> prodResponse = response.jsonPath().get("product");
        prodID = prodResponse.get("id").toString();
        System.out.println(prodID);
//        AuthenticationScheme authenticationScheme = RestAssured.basic
//                ("0364fd471299132265f670b8a9ae54ec","fcfb7959ffe3e6babda08ce8dbe3ee0d");
//        requestSpecBuilder = new RequestSpecBuilder();
//        requestSpecBuilder.setBaseUri("https://autdemo.myshopify.com/admin/api/2019-04");
//        //requestSpecBuilder.setBasePath("/admin/api/2019-04");
//        requestSpecBuilder.setAuth(authenticationScheme);
//        requestSpecification = requestSpecBuilder.build();
//
//        responseSpecBuilder = new ResponseSpecBuilder();
//        responseSpecBuilder.expectResponseTime(lessThan(3L), TimeUnit.SECONDS);
//        responseSpecBuilder.expectStatusCode(200);
//        responseSpecBuilder.expectBody("product.id",equalTo(Long.parseLong("4926203134083")));
//        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void getShopifyProduct() {
        given().
                spec(requestSpecification).
        when().
                get("/products/"+prodID+".json").
        then().
                log().all().
                spec(responseSpecification).
                statusCode(200);
    }
}
