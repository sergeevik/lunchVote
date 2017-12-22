package lunchVote.repository;

import lunchVote.CustomAssert;
import lunchVote.model.User;
import lunchVote.testData.UserData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static lunchVote.CustomAssert.assertMatch;
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
}