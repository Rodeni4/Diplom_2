package order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;

public class OrderClient {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String DATA_INGREDIENTS_PATH = "/api/ingredients";
    private static final String DATA_ORDERS_PATH = "/api/orders";
    private static final String DATA_ORDERS_ALL_PATH = "/api/orders/all";

    @Step("Получение данных об ингредиентах")
    public ValidatableResponse getOrderDataIngredients() {
        return given().log().all()
                .baseUri(BASE_URL)
                .get(DATA_INGREDIENTS_PATH)
                .then().log().all();
    }

    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrder(Order ingredientsList, String token) {
        return given().log().all()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(ingredientsList)
                .when()
                .post(DATA_ORDERS_PATH)
                .then().log().all();
    }

    @Step("Создание заказа, без авторизации")
    public ValidatableResponse createOrderWithoutAuthorization(Order ingredientsList) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(ingredientsList)
                .when()
                .post(DATA_ORDERS_PATH)
                .then().log().all();
    }

    @Step("Получение заказов неавторизованного пользователя")
    public ValidatableResponse getAllOrdersUnauthorized() {
        return given().log().all()
                .baseUri(BASE_URL)
                .get(DATA_ORDERS_ALL_PATH)
                .then().log().all();
    }

    @Step("Получение заказов авторизованного пользователя")
    public ValidatableResponse getAllOrdersAuthorized( String token) {
        return given().log().all()
                .auth().oauth2(token)
                .baseUri(BASE_URL)
                .get(DATA_ORDERS_PATH)
                .then().log().all();
    }
}
