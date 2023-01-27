package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class LoginUserTest {
    private User user;
    private UserData userData;
    private UserClient client;
    private UserAssertions userAssertions;
    private String accessToken;

    @Before
    public void setUp() {
        client = new UserClient();
        userAssertions = new UserAssertions();
        user = new UserGenerator().randomUser();
        client.createUser(user);
        accessToken = client.getUserAccessToken(user);
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Пользователь может авторизоваться")
    public void authorizationCourier() {
        userData = UserData.from(user);
        ValidatableResponse authorizationResponse = client.loginUser(userData);
        userAssertions.assertSuccessfullyLoginUser(authorizationResponse);
    }

    @Test
    @DisplayName("Логин, нет поля email")
    @Description("Запрос возвращает ошибку: email or password are incorrect")
    public void noAuthorizationUserNoEmail() {
        userData = new UserData(null, user.getPassword(), user.getName());
        ValidatableResponse authorizationResponse = client.loginUser(userData);
        userAssertions.assertNoAuthorizationUser(authorizationResponse);
    }

    @Test
    @DisplayName("Логин с неверным email")
    @Description("Запрос возвращает ошибку: email or password are incorrect")
    public void noAuthorizationUserIncorrectEmail() {
        userData = new UserData("Incorrect" + user.getEmail(), user.getPassword(), user.getName());
        ValidatableResponse authorizationResponse = client.loginUser(userData);
        userAssertions.assertNoAuthorizationUser(authorizationResponse);
    }

    @Test
    @DisplayName("Логин, нет поля пароль")
    @Description("Запрос возвращает ошибку: email or password are incorrect")
    public void noAuthorizationUserNoPassword() {
        userData = new UserData(user.getEmail(), null, user.getName());
        ValidatableResponse authorizationResponse = client.loginUser(userData);
        userAssertions.assertNoAuthorizationUser(authorizationResponse);
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Запрос возвращает ошибку: email or password are incorrect")
    public void noAuthorizationUserIncorrectPassword() {
        userData = new UserData(user.getEmail(), "Incorrect" + user.getPassword(), user.getName());
        ValidatableResponse authorizationResponse = client.loginUser(userData);
        userAssertions.assertNoAuthorizationUser(authorizationResponse);
    }

    @Ignore("Тест отключён, проверка корректного удаления")
    @Test
    @DisplayName("Удалить пользователя")
    @Description("Пользователь удалён, возвращает: User successfully removed")
    public void deleteUser() {
        ValidatableResponse authorizationResponse = client.deleteUser(accessToken);
        userAssertions.assertUserSuccessfullyRemoved(authorizationResponse);
    }

    @After
    public void userDelete() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }
}
