package org.example;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class UserClient extends Client {
    static final String LOGIN_PATH = "/auth/login";
    static final String REG_PATH = "/auth/register";
    static final String PATCH_PATH = "/auth/user";
    static final String DELETE_PATH = "/auth/user";

    @Step("Send POST request to /api/auth/register")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(REG_PATH)
                .then().log().all();
    }

    @Step("Send POST request to /api/auth/login")
    public ValidatableResponse login(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then().log().all();
    }
    @Step("Send POST request to /api/auth/login")
    public ValidatableResponse login(Map<String, String> creds) {
        return spec()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then().log().all();
    }

    @Step("Send PATCH request to /api/auth/user")
    public static Response updateUser(Credentials creds, String token){
        return given()
                .header("Authorization", token)
                .body(creds)
                .when()
                .patch(PATCH_PATH);
}

    @Step("Send DELETE request to /api/auth/user")
    public ValidatableResponse deleteUser(Credentials creds, Credentials token) {
        return given()
                .header("Authorization", token)
                .body(creds)
                .when()
                .delete(DELETE_PATH)
                .then().log().all();
    }
}
