package order;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import user.*;


public class CreatingOrderTest {
    private OrderClient clientOrder;
    private UserClient clientUser;
    private OrderAssertions orderAssertions;
    private Order order;
    private String accessToken;

    @Before
    public void setUp() {
        clientOrder = new OrderClient();
        orderAssertions = new OrderAssertions();
        clientUser = new UserClient();
        User user = new UserGenerator().randomUser();
        clientUser.createUser(user);
        accessToken = clientUser.getUserAccessToken(user);
    }

    @Test
    @DisplayName("Получение данных об ингредиентах")
    @Description("Ввозвращает список id ингредиентов")
    public void getOrderIngredients() {
        ValidatableResponse creationResponse = clientOrder.getOrderDataIngredients();
        orderAssertions.assertReturnSuccessfully(creationResponse, IngredientsList.defaultList());
    }

    @Test
    @DisplayName("Создание заказа, с авторизацией")
    @Description("Заказ можно создать, возвращает 200 код ответа, возвращает order.status: done")
    public void creatingOrder() {
        order = new Order(IngredientsList.fluorescentImmortalBurgerList());
        ValidatableResponse creationResponse = clientOrder.createOrder(order, accessToken);
        orderAssertions.assertCreatedSuccessfully(creationResponse);
    }

    @Test
    @DisplayName("Создание заказа, без авторизации")
    @Description("Заказ можно создать, возвращает 200 код ответа, возвращает success: true")
    public void creatingOrderWithoutAuthorization() {
        order = new Order(IngredientsList.fluorescentImmortalBurgerList());
        ValidatableResponse creationResponse = clientOrder.createOrderWithoutAuthorization(order);
        orderAssertions.assertCreatedSuccessfullyWithoutAuthorization(creationResponse);
    }

    @Test
    @DisplayName("Создание заказа, без авторизации и без ингредиентов")
    @Description("Запрос возвращает ошибку: Ingredient ids must be provided")
    public void creatingOrderWithoutAuthorizationNoIngredients() {
        order = new Order(IngredientsList.emptyList());
        ValidatableResponse creationResponse = clientOrder.createOrderWithoutAuthorization(order);
        orderAssertions.assertNotCreated(creationResponse);
    }

    @Ignore("Тест отключён, так как по документации API ожидается ошибка с кодом 500, нужно уточнить требования")
    @Test
    @DisplayName("Создание заказа, без авторизации и с неверным хешем ингредиентов")
    @Description("Запрос возвращает ошибку: One or more ids provided are incorrect")
    public void creatingOrderWithIncorrectHashIngredients() {
        order = new Order(IngredientsList.withIncorrectHashIngredientsList());
        ValidatableResponse creationResponse = clientOrder.createOrderWithoutAuthorization(order);
        orderAssertions.assertNotCreatedWithIncorrectHashIngredients(creationResponse);
    }

    @After
    public void userDelete() {
        if (accessToken != null) {
            clientUser.deleteUser(accessToken);
        }
    }
}