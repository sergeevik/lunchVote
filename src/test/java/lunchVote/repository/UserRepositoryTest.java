package lunchVote.repository;

import lunchVote.model.Role;
import lunchVote.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static lunchVote.testData.UserData.*;
import static org.assertj.core.api.Assertions.assertThat;


public class UserRepositoryTest extends SpringConfigOnTests {

    @Autowired
    private UserRepository repository;


    @Test
    public void getNotExistUser() throws Exception {
        User byId = repository.getById(12);
        assertThat(byId).isNull();
    }

    @Test
    public void getExistUser() throws Exception {
        User byId = repository.getById(JURA.getId());
        assertThat(byId).isNotNull()
                .isEqualToIgnoringGivenFields(JURA, "registered");
    }

    @Test
    public void getAllReturnNotNull() throws Exception {
        List<User> all = repository.getAll();
        assertThat(all).isNotNull();
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = repository.getAll();
        assertThat(all).usingElementComparatorIgnoringFields("registered")
                .containsExactlyInAnyOrder(ADMIN, USER, JURA);
    }

    @Test
    public void getByEmailNotExist() throws Exception {
        User fake = repository.getByEmail("fake@email.com");
        assertThat(fake).isNull();
    }

    @Test
    public void getByEmailExist() throws Exception {
        User admin = repository.getByEmail(ADMIN.getEmail());
        assertThat(admin).isNotNull()
                .isEqualToIgnoringGivenFields(ADMIN, "registered");
    }

    @Test
    public void deleteNotExist() throws Exception {
        boolean delete = repository.delete(42);
        assertThat(delete).isFalse();
    }

    @Test
    public void deleteExist() throws Exception {
        boolean delete = repository.delete(JURA.getId());
        assertThat(delete).isTrue();
    }

    @Test
    public void saveNewUserNotReturnNull() throws Exception {
        User save = repository.save(new User(null, "Ola", "ola@mail.ru", "petty123", Role.ROLE_USER));
        assertThat(save).isNotNull();
    }

    @Test
    public void saveSetIdOnNewUser() throws Exception {
        User save = repository.save(new User(null, "Ola", "ola@mail.ru", "petty123", Role.ROLE_USER));
        assertThat(save.getId()).isNotNull();
    }

    @Test
    public void saveNewUserInDb() throws Exception {
        User user = new User(null, "Ola", "ola@mail.ru", "petty123", Role.ROLE_USER);
        repository.save(user);
        List<User> all = repository.getAll();
        assertThat(all).usingElementComparatorIgnoringFields("registered")
                .containsExactlyInAnyOrder(ADMIN, USER, JURA, user);
    }

    @Test
    public void updateInDb() throws Exception {
        User user = new User(ADMIN);
        user.setName("Pavel");
        User save = repository.save(user);
        List<User> all = repository.getAll();

        assertThat(all).usingElementComparatorIgnoringFields("registered")
                .containsExactlyInAnyOrder(USER, JURA, user);
    }

    @Test
    public void updateNotExistReturnNull() throws Exception {
        User user = new User(7, "Ola", "ola@mail.ru", "petty123", Role.ROLE_USER);
        User save = repository.save(user);
        assertThat(save).isNull();

        List<User> all = repository.getAll();
        assertThat(all).usingElementComparatorIgnoringFields("registered")
                .containsExactlyInAnyOrder(ADMIN, USER, JURA)
                .doesNotContain(user);
    }



    /*
    ===========================
    =  Test to count queries  =
    ===========================
     */


    @Test
    public void saveQueryCount() throws Exception {
        countQueries.setLimit(3);
        repository.save(new User(null, "Ola", "ola@mail.ru", "petty123", Role.ROLE_USER));
    }

    @Test
    public void updateQueryCount() throws Exception {
        countQueries.setLimit(2);
        User user = new User(ADMIN);
        user.setName("Pavel");
        repository.save(user);
    }

    @Test
    public void getByEmailQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.getByEmail(ADMIN.getEmail());
    }

    @Test
    public void getByIdQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.getById(JURA.getId());
    }

    @Test
    public void getAllQueryCount() throws Exception {
        countQueries.setLimit(2);
        repository.getAll();
    }

    @Test
    public void deleteQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.delete(USER.getId());
    }
}