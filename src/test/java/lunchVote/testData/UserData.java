package lunchVote.testData;

import lunchVote.model.Role;
import lunchVote.model.User;

import java.util.Arrays;

public class UserData {

    public static final User ADMIN =
            new User(100000, "admin", "admin@lunch.com", "12345", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static final User JURA =
            new User(100001, "Jura", "jura@lunch.com", "qwerty", Role.ROLE_USER);

    public static final User USER =
            new User(100002, "user", "user@lunch.com", "pass", Role.ROLE_USER);

    public static Iterable<User> getAllUsers(){
        return Arrays.asList(ADMIN, USER, JURA);
    }
}
