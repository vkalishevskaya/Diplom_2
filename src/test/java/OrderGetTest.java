import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.OrderClient;
import org.example.Assertions;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Map;

public class OrderGetTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final OrderClient order = new OrderClient();
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
    @DisplayName("Get list of orders")
    @Description("Creating user, making an order and getting list of their orders")
    public void orderListGet() {
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.userCreatedSuccessfully(creationResponse);
        this.accessToken = creationResponse.path("accessToken");

        Response getIngredient = order.getIngredients();
        check.getSuccessfully(getIngredient);

        ArrayList ingredients = getIngredient.path("data[0,2]._id");
        Response creationOrderResponse = order.createOrder(Map.of("ingredients", ingredients),accessToken);
        check.orderCreatedSuccessfully(creationOrderResponse);

        Response getOrderResponse = order.getOrder(accessToken);
        check.orderSuccessfully(getOrderResponse);
    }

    @Test
    @DisplayName("Get orders unauthorized")
    @Description("Getting list of orders without authorization")
    public void orderListGetUnauthorized() {
        Response getOrderResponse = order.getOrderUnauthorized();
        check.gerOrderUnauthorized(getOrderResponse);
    }
}
