import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.*;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Map;
import static java.util.Collections.EMPTY_LIST;

public class OrderTest {
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
    @DisplayName("create new order")
    @Description("Creating user and making an order")
    public void orderCreate() {
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.userCreatedSuccessfully(creationResponse);
        this.accessToken = creationResponse.path("accessToken");

        Response getIngredient = order.getIngredients();
        check.getSuccessfully(getIngredient);

        ArrayList ingredients = getIngredient.path("data[0,2]._id");
        Response creationOrderResponse = order.createOrder(Map.of("ingredients", ingredients),accessToken);
        check.orderCreatedSuccessfully(creationOrderResponse);
    }

    @Test
    @DisplayName("create new order")
    @Description("Creating user and making an order")
    public void orderCreateUnauthorized() {
        Response getIngredient = order.getIngredients();
        check.getSuccessfully(getIngredient);

        ArrayList ingredients = getIngredient.path("data[0,2]._id");
        Response creationOrderResponse = order.createOrderUnauthorized(Map.of("ingredients", ingredients));
        check.orderRedirect(creationOrderResponse);

        // заказ не должен создаваться согласно требованиям:
        // "Только авторизованные пользователи могут делать заказы. Структура эндпоинтов
        //не меняется, но нужно предоставлять токен при запросе к серверу в поле
        //Authorization."
    }
    @Test
    @DisplayName("create order without ingredients")
    @Description("Creating user and don't add ingredients to te order")
    public void orderCreateNoIngredients() {
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.userCreatedSuccessfully(creationResponse);
        this.accessToken = creationResponse.path("accessToken");

        Response getIngredient = order.getIngredients();
        check.getSuccessfully(getIngredient);

        Object[] ingredients = EMPTY_LIST.toArray();
        Response creationOrderResponse = order.createOrderNoIngredients(Map.of("ingredients", ingredients),accessToken);
        check.orderBadRequest(creationOrderResponse);
    }
    @Test
    @DisplayName("create new order")
    @Description("Creating user and making an order")
    public void invalidOrderCreate() {
        var user = generator.random();
        Response creationResponse = client.createUser(user);
        check.userCreatedSuccessfully(creationResponse);
        this.accessToken = creationResponse.path("accessToken");

        Response getIngredient = order.getIngredients();
        check.getSuccessfully(getIngredient);

        ArrayList ingredients = getIngredient.path("data[1, 2].name");
        Response creationOrderResponse = order.createOrder(Map.of("ingredients", ingredients),accessToken);
        check.orderError(creationOrderResponse);
    }
}

