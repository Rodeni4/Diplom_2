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

    public void assertSuccessfullyLoginUser(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    public void assertNoAuthorizationUser(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false), "message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
    }

    public  void assertUserSuccessfullyRemoved(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(true), "message", equalTo("User successfully removed"))
                .and()
                .statusCode(202);
    }

    public void assertPatchUserEmailSuccessfully(ValidatableResponse response, String modifiedEmail) {
        response.assertThat()
                .body("success", equalTo(true), "user.email", equalTo(modifiedEmail))
                .and()
                .statusCode(200);
    }

    public void assertPatchUserDataNoAuthorization(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false), "message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }

    public void assertPatchUserNameSuccessfully(ValidatableResponse response, String modifiedName) {
        response.assertThat()
                .body("success", equalTo(true), "user.name", equalTo(modifiedName))
                .and()
                .statusCode(200);
    }

    public void assertPatchUserEmailAlreadyExists(ValidatableResponse response) {
        response.assertThat()
                .body("success", equalTo(false), "message", equalTo("User with such email already exists"))
                .and()
                .statusCode(403);
    }
}
