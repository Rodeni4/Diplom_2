package user;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String CREATE_USER_PATH = "/api/auth/register";
    private static final String LOGIN_USER_PATH = "/api/auth/login";
    private static final String DELETE_USER_PATH = "api/auth/user";

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

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(UserData userData) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(userData)
                .when()
                .post(LOGIN_USER_PATH)
                .then().log().all();
    }

    @Step("Получить токен accessToken")
    public String getUserAccessToken(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(user)
                .when()
                .post(LOGIN_USER_PATH)
                .then().extract().body().path("accessToken").toString().substring(7);
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String token) {
        return given().log().all()
                .auth().oauth2(token)
                .delete(BASE_URL + DELETE_USER_PATH)
                .then().log().all();
    }
}
