package user;

import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.equalTo;

public class UserAssertions {
    public void assertCreatedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    public void assertCreatingUserRecurringLogin(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false), "message", equalTo("User already exists"))
                .and()
                .statusCode(403);
    }

    public void assertCreationUserNoField(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false), "message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }
}
