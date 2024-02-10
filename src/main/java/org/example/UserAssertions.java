package org.example;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;

public class UserAssertions {
    @Step("Compare response")
    public void createdSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(200)
                .body("success", is(true))
        ;

    }
    @Step("Compare response and check that accessToken and refrestToken are not null")
    public void loggedInSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(200)
                .body("accessToken", notNullValue(), "refreshToken", notNullValue())
        ;
    }

    @Step("Compare response")
    public String creationFailed(Response response) {
        return response.then().assertThat()
                .statusCode(403)
                .body("message", notNullValue())
                .extract()
                .path("message")
                ;
    }
    @Step("Compare response")
    public void alreadyExists(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", notNullValue());
    }

    @Step("Compare response")
    public void notFound(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", notNullValue());
    }
    @Step("Compare response")
    public void deletedSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(HTTP_OK)
                .body("ok", is(true))
        ;
    }
    @Step("Compare response")
    public void patchedSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(200)
                .body(notNullValue());
        ;
    }
}
