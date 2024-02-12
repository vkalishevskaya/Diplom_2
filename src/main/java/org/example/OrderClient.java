package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Map;

public class OrderClient extends Client {

    static final String GET_LIST = "/ingredients";
    static final String ORDER_PREFIX = "/orders";

    @Step("Send POST request to /api/auth/register")
    public Response getIngredients() {
        return spec()
                .contentType("application/json")
                .when()
                .get(GET_LIST);
    }

    @Step("Send POST request to /api/auth/login")
    public Response createOrder(Map<String, ArrayList> ingredient, String token) {
        return spec()
                .contentType("application/json")
                .header("Authorization", token)
                .body(ingredient)
                .when()
                .post(ORDER_PREFIX);
    }
    @Step("Send POST request to /api/auth/login")
    public Response createOrderUnauthorized(Map<String, ArrayList> ingredient) {
        return spec()
                .contentType("application/json")
                .body(ingredient)
                .when()
                .post(ORDER_PREFIX);
    }
    @Step("Send POST request to /api/auth/login")
    public Response createOrderNoIngredients(Map<String, Object> ingredient,String token) {
        return spec()
                .contentType("application/json")
                .header("Authorization", token)
                .body(ingredient)
                .when()
                .post(ORDER_PREFIX);
    }
    @Step("Send POST request to /api/auth/register")
    public Response getOrder(String token) {
        return spec()
                .contentType("application/json")
                .header("Authorization", token)
                .when()
                .get(ORDER_PREFIX);
    }

    @Step("Send POST request to /api/auth/register")
    public Response getOrderUnauthorized() {
        return spec()
                .get(ORDER_PREFIX);
    }
}
