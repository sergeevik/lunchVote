package lunchVote.repository;

import lunchVote.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static lunchVote.testData.UserData.JURA;
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
}