package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChangingUserDataTest {
    protected final UserGenerator generator = new UserGenerator();
    private User user;
    private UserData userData;
    private UserClient client;
    private UserAssertions userAssertions;
    private String accessToken;
    private String modifiedEmail;
    private String modifiedName;

    public ChangingUserDataTest() {
    }

    @Before
    public void setUp() {
        client = new UserClient();
        userAssertions = new UserAssertions();
        user = generator.randomUser();
        client.createUser(user);
        accessToken = client.getUserAccessToken(user);
        modifiedEmail = "modified" + user.getEmail();
        modifiedName = "modified" + user.getName();
    }

    @Test
    @DisplayName("Получение информации о пользователе")
    @Description("Пользователя можно создать, возвращает 200 код ответа, возвращает success: true")
    public void getUserInformation() {
        ValidatableResponse creationResponse = client.getUserData(accessToken);
        userAssertions.assertCreatedSuccessfully(creationResponse);
    }

    @Test
    @DisplayName("Обновление Email пользователя")
    @Description("Email изменён, возвращает 200 код ответа, возвращает success: true")
    public void patchUserModifiedEmail() {
        userData = new UserData(modifiedEmail, user.getPassword(), user.getName());
        ValidatableResponse creationResponse = client.patchUserData(accessToken, userData);
        userAssertions.assertPatchUserEmailSuccessfully(creationResponse, modifiedEmail);
    }

    @Test
    @DisplayName("Обновление Email пользователя, без авторизации")
    @Description("Запрос возвращает ошибку: You should be authorised")
    public void patchUserModifiedEmailNoAuthorization() {
        userData = new UserData(modifiedEmail, user.getPassword(), user.getName());
        ValidatableResponse creationResponse = client.patchUserDataNoAuthorization(userData);
        userAssertions.assertPatchUserDataNoAuthorization(creationResponse);
    }

    @Test
    @DisplayName("Обновление Email пользователя,  передать почту, которая уже используется")
    @Description("Запрос возвращает ошибку: User with such email already existsd")
    public void patchUserModifiedEmailAlreadyExists() {
        User userDefault = generator.defaultUser();
        client.createUser(userDefault);
        userData = new UserData(userDefault.getEmail(), user.getPassword(), user.getName());
        ValidatableResponse creationResponse = client.patchUserData(accessToken, userData);
        userAssertions.assertPatchUserEmailAlreadyExists(creationResponse);
    }

    @Test
    @DisplayName("Обновление имени пользователя")
    @Description("Имя изменено, возвращает 200 код ответа, возвращает success: true")
    public void patchUserModifiedName() {
        userData = new UserData(user.getEmail(), user.getPassword(), modifiedName);
        ValidatableResponse creationResponse = client.patchUserData(accessToken, userData);
        userAssertions.assertPatchUserNameSuccessfully(creationResponse, modifiedName);
    }

    @Test
    @DisplayName("Обновление имени пользователя, без авторизации")
    @Description("Запрос возвращает ошибку: You should be authorised")
    public void patchUserModifiedNameNoAuthorization() {
        userData = new UserData(user.getEmail(), user.getPassword(), modifiedName);
        ValidatableResponse creationResponse = client.patchUserDataNoAuthorization(userData);
        userAssertions.assertPatchUserDataNoAuthorization(creationResponse);
    }

    @After
    public void userDelete() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }
}
