import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.Assertions;
import org.example.Credentials;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Test;
import java.util.Map;

public class UserLoginTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final Assertions check = new Assertions();

    private String accessToken;

    @After
    public void deleteUser() {
        if (accessToken!=null) {
            Response response = client.deleteUser(accessToken);
            check.deletedSuccessfully(response);
        }
    }

    @Test
    @DisplayName("successful login")
    @Description("Creating new user and login with data")
    public void userLogin() {
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.userCreatedSuccessfully(creationResponse);

        Credentials creds = Credentials.from(user);
        Response loginResponse = client.login(creds);
        check.loggedInSuccessfully(loginResponse);
        this.accessToken = loginResponse.path("accessToken");
    }

    @Test
    @DisplayName("login with incorrect password")
    @Description("Using valid email and invalid password")
    public void incorrectPassword() {
        var user = generator.repeats();
        Credentials creds = Credentials.from(user);
        Response loginResponse = client.login(creds);
        check.notFound(loginResponse);
        this.accessToken = loginResponse.path("accessToken");
    }

    @Test
    @DisplayName("login fails response")
    @Description("Login without email")
    public void loginWithoutEmail() {
        var password = RandomStringUtils.randomAlphanumeric(5);
        Response loginResponse = client.login(Map.of("password", password));
        check.notFound(loginResponse);
        this.accessToken = loginResponse.path("accessToken");
    }

    @Test
    @DisplayName("login without password field")
    @Description("Login without password")
    public void loginWithoutPassword() {
        var email= RandomStringUtils.randomAlphanumeric(5)+"@sparrow.com";
        Response loginResponse = client.login(Map.of("email", email));
        check.notFound(loginResponse);
        this.accessToken = loginResponse.path("accessToken");
    }


    @Test
    @DisplayName("login with invalid data")
    @Description("Login with invalid email and password")
    public void userNotExist(){
        var user = generator.notExist();
        Credentials creds = Credentials.from(user);
        Response loginResponse = client.login(creds);
        check.notFound(loginResponse);
        this.accessToken = loginResponse.path("accessToken");
    }
}