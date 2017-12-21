package lunchVote.repository;

import lunchVote.model.Restaurant;
import lunchVote.testData.RestaurantData;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static lunchVote.testData.RestaurantData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static lunchVote.CustomAssert.assertMatch;

@ContextConfiguration(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDb.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantRepositoryTest {
    @Autowired
    RestaurantRepository repository;

    @Test
    public void updateNotExistRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(4, "Mac", "Kontinent");
        Restaurant save = repository.save(restaurant);
        assertThat(save).isNull();
    }

    @Test
    public void saveSetId() throws Exception {
        Restaurant save = repository.save(new Restaurant(null, "KFC", "Park"));
        assertThat(save.getId()).isNotNull();
    }

    @Test
    public void save() throws Exception {
        Restaurant restaurant = new Restaurant(null, "Sashlik", "Palatka");
        Restaurant save = repository.save(restaurant);
        assertThat(save.getId()).isNotNull();
        List<Restaurant> all = repository.getAll();
        assertMatch(all, BURGER_KING, MC_DONALD, KFC, save);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> all = repository.getAll();
        assertMatch(all, RestaurantData.getAllData());
    }

    @Test
    public void update() throws Exception {
        Restaurant restaurant = new Restaurant(MC_DONALD);
        restaurant.setName("Sushi");
        Restaurant save = repository.save(restaurant);
        assertThat(save).isNotNull();
        List<Restaurant> all = repository.getAll();
        assertMatch(all, restaurant, BURGER_KING, KFC);
    }

    @Test
    public void getByIdNotReturnNull() throws Exception {
        Restaurant byId = repository.getById(100003);
        assertThat(byId).isNotNull();
    }

    @Test
    public void getByIdCorrectRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(BURGER_KING);
        Restaurant byId = repository.getById(restaurant.getId());
        assertThat(byId).isEqualToComparingFieldByField(BURGER_KING);
    }


    @Test
    public void deleteNotExistRestaurant() throws Exception {
        boolean delete = repository.delete(50);
        assertThat(delete).isFalse();
        List<Restaurant> all = repository.getAll();
        assertMatch(all, RestaurantData.getAllData());
    }

    @Test
    public void deleteExistRestaurant() throws Exception {
        boolean delete = repository.delete(100003);
        assertThat(delete).isTrue();
        List<Restaurant> all = repository.getAll();
        assertMatch(all, MC_DONALD, KFC);
    }
}