package user;

import java.util.UUID;

public class UserGenerator {

    public User defaultUser() {
        return new User("test-data@yandex.ru", "password", "Username");
    }

    public User randomUser() {
        return new User("test-" + generateString() + "@yandex.ru",
                "password" + generateString(),
                "Username" + generateString());
    }

    public User randomUserNoFieldEmail() {
        return new User(null,
                "password" + generateString(),
                "Username" + generateString());
    }

    public User randomUserNoFieldPassword() {
        return new User("test-" + generateString() + "@yandex.ru",
                null,
                "Username" + generateString());
    }

    public User randomUserNoFieldName() {
        return new User("test-" + generateString() + "@yandex.ru",
                "password" + generateString(),
                null);
    }

    public static String generateString() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
