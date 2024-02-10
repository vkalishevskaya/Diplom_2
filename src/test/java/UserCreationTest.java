import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.UserAssertions;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Test;

public class UserCreationTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();

    private String accessToken;

    @After
    public void deleteCourier() {
        if (accessToken!=null) {
            Response response = client.deleteUser(accessToken);
            check.deletedSuccessfully(response);
        }
    }


    @Test
    @DisplayName("create new user")
    @Description("checking status-code")
    public void userCreate() {
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.createdSuccessfully(creationResponse);
        this.accessToken = creationResponse.path("accessToken");
    }

    @Test
    @DisplayName("user data repeats")
    @Description("unable to create a new user with non-unique data")
    public void userRepeats(){
        var user = generator.generic();
        Response creationResponse = client.createUser(user);
        check.alreadyExists(creationResponse);
        this.accessToken = creationResponse.path("accessToken");
    }

    @Test
    @DisplayName("creating without password")
    @Description("creating is unable without password")
    public void creationFails() {
        var user = generator.noPassword();
        Response creationResponse = client.createUser(user);
        String message = check.creationFailed(creationResponse);
        this.accessToken = creationResponse.path("accessToken");
        assert !message.isBlank();
    }

    @Test
    @DisplayName("creating with repeating login")
    @Description("unable to create a new user with non-unique login")
    public void loginAlreadyExists(){
        var user = generator.repeats();
        Response creationResponse = client.createUser(user);
        check.alreadyExists(creationResponse);
        this.accessToken = creationResponse.path("accessToken");
    }

    @Test
    @DisplayName("creating without email field")
    @Description("creating is unable without email")
    public void creationWithoutLogin() {
        var user = generator.noEmail();
        Response creationResponse = client.createUser(user);
        check.creationFailed(creationResponse);
        this.accessToken = creationResponse.path("accessToken");
    }

    @Test
    @DisplayName("creating without name field")
    @Description("creating is unable without name")
    public void creationWithoutName() {
        var user = generator.noName();
        Response creationResponse = client.createUser(user);
        check.creationFailed(creationResponse);
        this.accessToken = creationResponse.path("accessToken");
    }
}