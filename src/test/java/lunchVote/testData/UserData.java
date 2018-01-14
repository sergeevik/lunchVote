package lunchVote.testData;

import lunchVote.model.Role;
import lunchVote.model.User;

public class UserData {

    public static final User ADMIN =
            new User(100000, "admin", "admin@lunch.com", "12345", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static final User JURA =
            new User(100001, "Jura", "jura@lunch.com", "qwerty", Role.ROLE_USER);

    public static final User USER =
            new User(100002, "user", "user@lunch.com", "pass", Role.ROLE_USER);

    public static final User SAVE_NEW =
            new User(null, "Ola", "ola@mail.ru", "petty123", Role.ROLE_USER);

}
