package order;

import io.restassured.response.ValidatableResponse;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderAssertions {
    public void assertReturnSuccessfully(ValidatableResponse response, List<String> ingredientsDefaultList) {
        response.assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200)
                .body("data._id", equalTo(ingredientsDefaultList));
    }

    public void assertCreatedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("order.status", equalTo("done"))
                .and()
                .statusCode(200);
    }

    public void assertCreatedSuccessfullyWithoutAuthorization(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    public void assertNotCreated(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false), "message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }

    public void assertNotCreatedWithIncorrectHashIngredients(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false), "message", equalTo("One or more ids provided are incorrect"))
                .and()
                .statusCode(400);
    }

    public void assertAllOrders(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true), "total", notNullValue())
                .and()
                .statusCode(200);
    }

    public void assertAllOrdersAuthorizedUser(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true), "orders.status", equalTo(List.of("done")))
                .and()
                .statusCode(200);
    }
}
