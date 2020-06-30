package com.san.common;

import com.san.constants.Auth;
import com.san.constants.Path;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.regexp.RE;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class RestUtilities {

    public static Response response;
    public static String ProdID;
    public static String ENDPOINT;
    public static RequestSpecBuilder REQUEST_BUILDER;
    public static RequestSpecification REQUEST_SPEC;
    public static ResponseSpecBuilder RESPONSE_BUILDER;
    public static ResponseSpecification RESPONSE_SPEC;

    public static void setEndPoint(String endPoint) {
        ENDPOINT = endPoint;
    }

    public static RequestSpecification getRequestSpecification(){
        AuthenticationScheme authenticationScheme = RestAssured.basic
                (Auth.SHOPIFY_STORE_USERNAME,Auth.SHOPIFY_STORE_PASSWORD);
        REQUEST_BUILDER = new RequestSpecBuilder();
        REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
        REQUEST_BUILDER.setAuth(authenticationScheme);
        REQUEST_BUILDER.addHeader("Content-Type","application/json");
        REQUEST_SPEC = REQUEST_BUILDER.build();
        return  REQUEST_SPEC;
    }

    public static RequestSpecification postRequestSpecification(){
        AuthenticationScheme authenticationScheme = RestAssured.basic
                (Auth.SHOPIFY_STORE_USERNAME,Auth.SHOPIFY_STORE_PASSWORD);
        REQUEST_BUILDER = new RequestSpecBuilder();
        REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
        REQUEST_BUILDER.setAuth(authenticationScheme);
        REQUEST_BUILDER.addHeader("Content-Type","application/json");
        REQUEST_BUILDER.setBody(Payloads.PostPayloadShopify());
        REQUEST_SPEC = REQUEST_BUILDER.build();
        return  REQUEST_SPEC;
    }

    public static ResponseSpecification getResponseSpecification() {
        RESPONSE_BUILDER = new ResponseSpecBuilder();
        RESPONSE_BUILDER.expectResponseTime(lessThan(10L), TimeUnit.SECONDS);
        RESPONSE_SPEC = RESPONSE_BUILDER.build();
        return RESPONSE_SPEC;
    }

    public static RequestSpecification createQueryParam(RequestSpecification rspec, String key , String value) {
        return rspec.queryParam(key,value);
    }

    public static RequestSpecification createQueryParam(RequestSpecification rspec, HashMap<String,String> queryParam) {
        return rspec.queryParams(queryParam);
    }

    public static RequestSpecification createPathParam(RequestSpecification rspec, String key , String value) {
        return rspec.pathParam(key,value);
    }

    public static Response getResponse() {
        return given().get(ENDPOINT);
    }

    public static Response getResponse(RequestSpecification reqSpec,String type) {
        REQUEST_SPEC.spec(reqSpec);
        Response response = null;
        if(type.equalsIgnoreCase("get")) {
            response = given().spec(REQUEST_SPEC).get(ENDPOINT);
        } else if(type.equalsIgnoreCase("post")) {
            response = given().spec(REQUEST_SPEC).post(ENDPOINT);
        } else {
            System.out.println("Type not supported");
        }
        response.then().log().all();
        response.then().spec(RESPONSE_SPEC);
        return response;
    }

    public static JsonPath getJsonPath(Response res) {
        String jsonPath = res.asString();
        return new JsonPath(jsonPath);
    }

    public static void resetBasePath() {
        RestAssured.basePath = null;
    }

    public static void setContentType(ContentType type) {
        given().contentType(type);
    }

    public static Response ResponseVerif() {
        RestUtilities.setEndPoint(Path.PRODUCT_ENDPOINT);
        response = RestUtilities.getResponse(postRequestSpecification(),"post");
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.statusCode(),201);
        Map<String,Object> prodResponse = response.jsonPath().get("product");
        ProdID = prodResponse.get("id").toString();
        System.out.println(ProdID);
        return response;
    }

}
