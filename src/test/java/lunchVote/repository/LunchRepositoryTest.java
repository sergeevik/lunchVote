package lunchVote.repository;

import lunchVote.model.Lunch;
import lunchVote.repository.queryCounter.CountInterceptor;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static lunchVote.CustomAssert.assertMatch;
import static lunchVote.testData.LunchData.*;
import static lunchVote.testData.RestaurantData.*;
import static org.assertj.core.api.Assertions.assertThat;


public class LunchRepositoryTest extends SpringConfigOnTests{

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

    @Test
    public void getLunchWithRestaurant() throws Exception {
        Lunch byId = repository.getById(VOPER.getId());
        assertThat(byId).isNotNull();
        assertThat(byId.getRestaurant()).isNotNull();
        assertThat(byId.getRestaurant()).isEqualToComparingFieldByField(VOPER.getRestaurant());
    }

    /*
    ===========================
    =  Test to count queries  =
    ===========================
     */

    @Test
    public void saveQueryCount() throws Exception {
        countQueries.setLimit(2);
        repository.save(new Lunch(null, LocalDate.now(), "Nagets", 9000, MC_DONALD));
    }

    @Test
    public void updateQueryCount() throws Exception {
        countQueries.setLimit(2);
        Lunch lunch = new Lunch(CHIKEN);
        lunch.setPrice(22000);
        repository.save(lunch);
    }

    @Test
    public void getAllForDateQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.getAllForDate(LocalDate.now());
    }

    @Test
    public void getByIdQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.getById(BIG_MACK.getId());
    }

    @Test
    public void getAllQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.getAll();
    }

    @Test
    public void deleteQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.delete(VOPER.getId());
    }
}