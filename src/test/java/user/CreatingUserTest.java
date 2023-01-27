package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreatingUserTest {
    protected final UserGenerator generator = new UserGenerator();
    private User user;
    private UserClient client;
    private UserAssertions userAssertions;
    private String accessToken;

    @Before
    public void setUp() {
        client = new UserClient();
        userAssertions = new UserAssertions();
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Пользователя можно создать, возвращает 200 код ответа, возвращает success: true")
    public void creatingUser() {
        user = generator.randomUser();
        ValidatableResponse creationResponse = client.createUser(user);
        userAssertions.assertCreatedSuccessfully(creationResponse);
        accessToken = client.getUserAccessToken(user);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Запрос возвращает ошибку: User already exists")
    public void creatingTwoIdenticalUsers() {
        user = generator.randomUser();
        client.createUser(user);
        User identicalUser = user;
        ValidatableResponse creationResponse = client.createUser(identicalUser);
        userAssertions.assertCreatingUserRecurringLogin(creationResponse);
    }

    @Test
    @DisplayName("Создание пользователя, без поля Email")
    @Description("Запрос возвращает ошибку: Email, password and name are required fields")
    public void creatingUserNoFieldEmail() {
        user = generator.randomUserNoFieldEmail();
        ValidatableResponse creationResponse = client.createUser(user);
        userAssertions.assertCreationUserNoField(creationResponse);
    }

    @Test
    @DisplayName("Создание пользователя, без поля Password")
    @Description("Запрос возвращает ошибку: Email, password and name are required fields")
    public void creatingUserNoFieldPassword() {
        user = generator.randomUserNoFieldPassword();
        ValidatableResponse creationResponse = client.createUser(user);
        userAssertions.assertCreationUserNoField(creationResponse);
    }

    @Test
    @DisplayName("Создание пользователя, без поля Name")
    @Description("Запрос возвращает ошибку: Email, password and name are required fields")
    public void creatingUserNoFieldName() {
        user = generator.randomUserNoFieldName();
        ValidatableResponse creationResponse = client.createUser(user);
        userAssertions.assertCreationUserNoField(creationResponse);
    }

    @After
    public void userDelete() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }
}
