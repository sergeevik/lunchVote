package lunchVote.repository;

import lunchVote.CustomAssert;
import lunchVote.model.Lunch;
import lunchVote.testData.LunchData;
import lunchVote.testData.RestaurantData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static lunchVote.CustomAssert.assertMatch;
import static lunchVote.testData.LunchData.*;
import static lunchVote.testData.RestaurantData.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDb.sql", config = @SqlConfig(encoding = "UTF-8"))
public class LunchRepositoryTest {

    @Autowired
    LunchRepository repository;

    @Test
    public void saveNotReturnNullOnNewLunch() throws Exception {
        Lunch save = repository.save(new Lunch(null, LocalDate.now(), "Gamburger", 4500, BURGER_KING));
        assertThat(save).isNotNull();
    }

    @Test
    public void saveSetIdOnNewLunch() throws Exception {
        Lunch save = repository.save(new Lunch(null, LocalDate.now(), "Zinger", 4500, KFC));
        assertThat(save.getId()).isNotNull();
    }

    @Test
    public void getByIdExistLunch() throws Exception {
        Lunch byId = repository.getById(100007);
        assertThat(byId).isNotNull()
                .isEqualToComparingFieldByField(BIG_MACK);
    }

    @Test
    public void getByIdNotExistLunch() throws Exception {
        Lunch byId = repository.getById(0);
        assertThat(byId).isNull();
    }

    @Test
    public void getByIdOnNegativeId() throws Exception {
        Lunch byId = repository.getById(-200);
        assertThat(byId).isNull();
    }

    @Test
    public void getAll() throws Exception {
        List<Lunch> all = repository.getAll();
        assertMatch(all, allLunch());
    }

    @Test
    public void updateNotExistLunch() throws Exception {
        Lunch save = repository.save(new Lunch(10, LocalDate.now(), "Zinger", 4500, KFC));
        assertThat(save).isNull();
    }

    @Test
    public void updateExistLunchNotReturnNull() throws Exception {
        Lunch lunch = new Lunch(VOPER);
        lunch.setPrice(200000);
        Lunch save = repository.save(lunch);

        assertThat(save).isNotNull();
    }

    @Test
    public void updateInDatabase() throws Exception {
        Lunch lunch = new Lunch(VOPER);
        lunch.setPrice(250000);
        Lunch save = repository.save(lunch);

        List<Lunch> all = repository.getAll();

        assertMatch(all, BIG_MACK, CHIKEN, lunch);
    }

    @Test
    public void saveInDatabase() throws Exception {
        Lunch nagets = new Lunch(null, LocalDate.now(), "Nagets", 9000, MC_DONALD);
        repository.save(nagets);
        List<Lunch> all = repository.getAll();
        assertMatch(all, VOPER, BIG_MACK, CHIKEN, nagets);
    }

    @Test
    public void deleteNotExistLunch() throws Exception {
        boolean delete = repository.delete(12);
        assertThat(delete).isFalse();
    }

    @Test
    public void deleteExistLunch() throws Exception {
        boolean delete = repository.delete(CHIKEN.getId());
        assertThat(delete).isTrue();
    }

    @Test
    public void getByDayWhenLunchNotExist() throws Exception {
        List<Lunch> allForDate = repository.getAllForDate(LocalDate.of(2017, 2, 19));
        assertThat(allForDate).isNotNull()
                              .isEmpty();
    }

    @Test
    public void getByDayWhenLunchExist() throws Exception {
        List<Lunch> allForDate = repository.getAllForDate(BIG_MACK.getDate());
        assertThat(allForDate).isNotNull()
                              .isNotEmpty();

        assertMatch(allForDate, BIG_MACK, CHIKEN, VOPER);
    }
}