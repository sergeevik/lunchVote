package lunchVote.repository;

import lunchVote.model.Lunch;
import lunchVote.repository.inMemoryRepository.InMemoryLunchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static lunchVote.CustomAssert.assertMatch;

@ContextConfiguration(locations = "classpath:spring/spring-test-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
//@Sql(scripts = "classpath:db/populateDb.sql", config = @SqlConfig(encoding = "UTF-8"))
public class LunchRepositoryTest {

    @Autowired
    LunchRepository repository;

    @Before
    public void setUp() throws Exception {
        ((InMemoryLunchRepository)repository).initRepo();
    }

    @Test
    public void saveNotReturnNullOnNewLunch() throws Exception {
        Lunch save = repository.save(new Lunch());
        assertThat(save).isNotNull();
    }

    @Test
    public void saveSetIdOnNewLunch() throws Exception {
        Lunch save = repository.save(new Lunch());
        assertThat(save.getId()).isNotNull();
    }


}