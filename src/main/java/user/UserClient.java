package user;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String CREATE_USER_PATH = "/api/auth/register";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(user)
                .when()
                .post(CREATE_USER_PATH)
                .then().log().all();
    }
}
