package lunchVote.model;

import java.time.LocalDate;
import java.util.Set;

public class User extends AbstractBaseEntity{
    private String name;
    private String email;
    private String password;
    private LocalDate registered;
    private boolean enabled;
    private Set<Role> roles;
}
