import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Credentials;
import org.example.UserAssertions;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Test;
import java.util.Map;


public class UserLoginTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();


    /*@After
    public void deleteUser() {
        if (userToken > 0) {
            ValidatableResponse response = client.deleteUser(token);
            check.deletedSuccessfully(response);
        }
    }*/

    @Test
    @DisplayName("successful login")
    @Description("Creating new user and login with data")
    public void userLogin() {
        var user = generator.random();
        ValidatableResponse creationResponse = client.createUser(user);
        check.createdSuccessfully(creationResponse);

        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds);
        check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("login with incorrect password")
    @Description("Using valid email and invalid password")
    public void incorrectPassword() {
        var user = generator.repeats();
        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds);
        check.notFound(loginResponse);
    }

    @Test
    @DisplayName("login fails response")
    @Description("Login without email")
    public void loginFails() {
        ValidatableResponse loginResponse = client.login(Map.of("password", "null"));
        check.notFound(loginResponse);
    }

    @Test
    @DisplayName("login without password field")
    @Description("Login without password")
    public void loginWithoutPassword() {
        ValidatableResponse loginResponse = client.login(Map.of("email", "email"));
        check.notFound(loginResponse);
    }


    @Test
    @DisplayName("login with invalid data")
    @Description("Login with invalid email and password")
    public void userNotExist(){
        var user = generator.notExist();
        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds);
        check.notFound(loginResponse);
    }
}