package org.example;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.Map;

public class UserClient extends Client {
    static final String LOGIN_PATH = "/auth/login";
    static final String REG_PATH = "/auth/register";
    static final String PATCH_PATH = "/auth/user";
    static final String DELETE_PATH = "/auth/user";

    @Step("Send POST request to /api/auth/register")
    public Response createUser(User user) {
        return spec()
                .contentType("application/json")
                .body(user)
                .when()
                .post(REG_PATH);
    }

    @Step("Send POST request to /api/auth/login")
    public Response login(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(LOGIN_PATH);
    }

    @Step("Send POST request to /api/auth/login")
    public Response login(Map<String, String> creds) {
        return spec()
                .body(creds)
                .when()
                .post(LOGIN_PATH);
    }

    @Step("Send PATCH request to /api/auth/user")
    public Response updateUser(User user, String token){
        return spec()
                .header("Authorization", token)
                .body(user)
                .when()
                .patch(PATCH_PATH);
    }
    @Step("Send PATCH request to /api/auth/user")
    public Response updateUnauthorizedUser(User user){
        return spec()
                .body(user)
                .when()
                .patch(PATCH_PATH);
    }

    @Step("Send DELETE request to /api/auth/user")
    public Response deleteUser(String token) {
        return spec()
                .header("Authorization", token)
                .when()
                .delete(DELETE_PATH);
    }
}