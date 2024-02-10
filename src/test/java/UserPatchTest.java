import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.UserAssertions;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Test;

public class UserPatchTest {

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
    @DisplayName("Update user data")
    @Description("Creating new user and change their name and email")
    public void updateAuthorizedUser(){
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.createdSuccessfully(creationResponse);
        this.accessToken = creationResponse.path("accessToken");

        var newUser = generator.newCredentials();
        Response updationResponse = client.updateUser(newUser, accessToken);
        check.patchedSuccessfully(updationResponse);
    }

    @Test
    @DisplayName("Update user data without token")
    @Description("Creating new user and try to patch information without authorization")
    public void updateUnauthorizedUser(){
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.createdSuccessfully(creationResponse);
        this.accessToken = creationResponse.path("accessToken");

        var newUser = generator.newCredentials();
        Response updationResponse = client.updateUnauthUser(newUser);
        check.patchWithoutAuth(updationResponse);
    }

    @Test
    @DisplayName("Update user data with non-unique data")
    @Description("Creating new user and change their name and email")
    public void updateWithExistingInfo(){
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.createdSuccessfully(creationResponse);
        this.accessToken = creationResponse.path("accessToken");

        var newData = generator.repeats();
        Response updationResponse = client.updateUser(newData, accessToken);
        check.patchedEmailExist(updationResponse);
    }
}

