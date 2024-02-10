import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.example.Credentials;
import org.example.UserAssertions;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.Test;

public class UserPatchTest {

    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private String accessToken;


    @Test
    @DisplayName("Update user data")
    @Description("Creating new user and change they name and email")
    public void updateAuthorizedUser(){
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.createdSuccessfully(creationResponse);
        this.accessToken = creationResponse.path("accessToken");

        var newUser = generator.newCredentials();
        Credentials newCreds = Credentials.from(newUser);
        Response updationResponse = client.updateUser(newCreds, accessToken);
        check.patchedSuccessfully(updationResponse);
    }
}

