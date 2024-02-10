import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.example.UserAssertions;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class UserCreationTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();

    private String accessToken;  // default value

    /*@After
    public void deleteUser() {
        if (accessToken) {
            ValidatableResponse response = client.deleteUser(accessToken);
            check.deletedSuccessfully(response);
        }
    }*/

    @Test
    @DisplayName("create new user")
    @Description("checking status-code")
    public void userCreate() {
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.createdSuccessfully(creationResponse);
    }

    @Test
    @DisplayName("user data repeats")
    @Description("unable to create a new user with non-unique data")
    public void userRepeats(){
        var user = generator.generic();
        Response creationResponse = client.createUser(user);
        check.alreadyExists(creationResponse);
    }

    @Test
    @DisplayName("creating without password")
    @Description("creating is unable without password")
    public void creationFails() {
        var user = generator.noPassword();
        Response loginResponse = client.createUser(user);
        String message = check.creationFailed(loginResponse);
        assert !message.isBlank();
    }

    @Test
    @DisplayName("creating with repeating login")
    @Description("unable to create a new user with non-unique login")
    public void loginAlreadyExists(){
        var user = generator.repeats();
        Response creationResponse = client.createUser(user);
        check.alreadyExists(creationResponse);
    }

    @Test
    @DisplayName("creating without email field")
    @Description("creating is unable without email")
    public void creationWithoutLogin() {
        var user = generator.noEmail();
        Response creationResponse = client.createUser(user);
        check.creationFailed(creationResponse);
    }

    @Test
    @DisplayName("creating without name field")
    @Description("creating is unable without name")
    public void creationWithoutName() {
        var user = generator.noName();
        Response creationResponse = client.createUser(user);
        check.creationFailed(creationResponse);
    }
}