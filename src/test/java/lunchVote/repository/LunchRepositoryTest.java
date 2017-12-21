package lunchVote.repository;

import lunchVote.model.Lunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = "classpath:spring/spring-test-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
//@Sql(scripts = "classpath:db/populateDb.sql", config = @SqlConfig(encoding = "UTF-8"))
public class LunchRepositoryTest {

    LunchRepository repository;

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