package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserGenerator;


public class GetOrdersTest {
    private OrderClient clientOrder;
    private UserClient clientUser;
    private OrderAssertions orderAssertions;
    private String accessToken;

    @Before
    public void setUp() {
        clientOrder = new OrderClient();
        orderAssertions = new OrderAssertions();
        clientUser = new UserClient();
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    @Description("Вернёт код 200 и количество всех заказов")
    public void getAllOrders() {
        ValidatableResponse creationResponse = clientOrder.getAllOrdersUnauthorized();
        orderAssertions.assertAllOrders(creationResponse);
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    @Description("Вернёт код 200 и заказ пользователя со статусом done")
    public void getAllOrdersAuthorizedUser() {
        User user = new UserGenerator().randomUser();
        clientUser.createUser(user);
        accessToken = clientUser.getUserAccessToken(user);
        Order order = new Order(IngredientsList.fluorescentImmortalBurgerList());
        clientOrder.createOrder(order, accessToken);

        ValidatableResponse creationResponse = clientOrder.getAllOrdersAuthorized(accessToken);
        orderAssertions.assertAllOrdersAuthorizedUser(creationResponse);
    }

    @After
    public void userDelete() {
        if (accessToken != null) {
            clientUser.deleteUser(accessToken);
        }
    }
}
