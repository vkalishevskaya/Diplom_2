package org.example;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;

public class UserAssertions {
    @Step("Compare response")
    public void createdSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
        ;

    }
    @Step("Compare response and check that accessToken and refrestToken are not null")
    public void loggedInSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("accessToken", notNullValue(), "refreshToken", notNullValue())
        ;
    }

    @Step("Compare response")
    public String creationFailed(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", notNullValue())
                .extract()
                .path("message")
                ;
    }
    @Step("Compare response")
    public void alreadyExists(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", notNullValue());
    }

    @Step("Compare response")
    public void notFound(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", notNullValue());
    }
    @Step("Compare response")
    public void deletedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("ok", is(true))
        ;
    }
    @Step("Compare response")
    public void patchedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_ACCEPTED)
                .body("message", notNullValue());
        ;
    }
}
