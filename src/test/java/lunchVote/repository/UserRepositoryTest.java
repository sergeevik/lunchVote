package lunchVote.repository;

import lunchVote.model.Role;
import lunchVote.model.User;
import lunchVote.repository.dataJpa.springCrud.UserCrud;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static lunchVote.testData.UserData.*;
import static org.assertj.core.api.Assertions.assertThat;


public class UserRepositoryTest extends SQLAnnotation {

    @Autowired
    private UserCrud repository;


    @Test
    public void getNotExistUser() throws Exception {
        Optional<User> byId = repository.findById(12);
        assertThat(byId).isEmpty();
    }

    @Test
    public void getExistUser() throws Exception {
        Optional<User> byId = repository.findById(JURA.getId());
        assertThat(byId).isPresent()
                    .contains(JURA);
    }

    @Test
    public void getAllReturnNotNull() throws Exception {
        List<User> all = repository.findAll();
        assertThat(all).isNotNull();
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = repository.findAll();
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
        int delete = repository.delete(42);
        assertThat(delete).isEqualTo(0);
    }

    @Test
    public void deleteExist() throws Exception {
        int delete = repository.delete(JURA.getId());
        assertThat(delete).isNotEqualTo(0);
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
        List<User> all = repository.findAll();
        assertThat(all).usingElementComparatorIgnoringFields("registered")
                .containsExactlyInAnyOrder(ADMIN, USER, JURA, user);
    }

    @Test
    public void updateInDb() throws Exception {
        User user = new User(ADMIN);
        user.setName("Pavel");
        repository.save(user);
        List<User> all = repository.findAll();

        assertThat(all).usingElementComparatorIgnoringFields("registered")
                .containsExactlyInAnyOrder(USER, JURA, user);
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
        repository.findById(JURA.getId());
    }

    @Test
    public void getAllQueryCount() throws Exception {
        countQueries.setLimit(2);
        repository.findAll();
    }

    @Test
    public void deleteQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.delete(USER.getId());
    }
}