package org.example;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;


public class Assertions {
    @Step("Compare response")
    public void userCreatedSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true), "accessToken", notNullValue(), "refreshToken", notNullValue())
        ;

    }
    @Step("Compare response and check that accessToken and refrestToken are not null")
    public void loggedInSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_OK)
                .body("accessToken", notNullValue(), "refreshToken", notNullValue())
        ;
    }

    @Step("Compare response")
    public void creationFailed(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", is(false), "message",is("Email, password and name are required fields"));
    }
    @Step("Compare response")
    public void alreadyExists(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", is(false), "message", is("User already exists"));
    }

    @Step("Compare response")
    public void notFound(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false),"message", is("email or password are incorrect"));
    }
    @Step("Compare response")
    public void deletedSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_ACCEPTED)
                .body(notNullValue());;
    }
    @Step("Compare response")
    public void patchedSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true));
    }

    @Step("Compare response")
    public void patchForbidden(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", is(false),"message", is ("User with such email already exists"));
    }
    @Step("Compare response")
    public void patchUnauthorized(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false),"message", is ("You should be authorised"));
    }
    @Step("Compare response")
    public void getSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true), "data", notNullValue());
    }
    @Step("Compare response")
    public void orderCreatedSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true), "order", notNullValue());
    }
    @Step("Compare response")
    public void orderRedirect(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_MULT_CHOICE)
                .body("success", is(false),"message", is (notNullValue()));
    }
    @Step("Compare response")
    public void orderBadRequest(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", is(false),"message", is ("Ingredient ids must be provided"));
    }
    @Step("Compare response")
    public void orderError(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }
    @Step("Compare response")
    public void orderSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true), "orders", notNullValue());
    }
    @Step("Compare response")
    public void gerOrderUnauthorized(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false), "message", is ("You should be authorised"));
    }
}
