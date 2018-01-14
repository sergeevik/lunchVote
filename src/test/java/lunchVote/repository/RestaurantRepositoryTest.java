package lunchVote.repository;

import lunchVote.model.Restaurant;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import lunchVote.testData.RestaurantData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static lunchVote.CustomAssert.assertMatch;
import static lunchVote.testData.RestaurantData.*;
import static org.assertj.core.api.Assertions.assertThat;


public class RestaurantRepositoryTest extends SQLAnnotation {
    @Autowired
    RestaurantCrud repository;

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
        List<Restaurant> all = repository.findAll();
        assertMatch(all, BURGER_KING, MC_DONALD, KFC, save);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> all = repository.findAll();
        assertMatch(all, RestaurantData.getAllRestaurant());
    }

    @Test
    public void update() throws Exception {
        Restaurant restaurant = new Restaurant(MC_DONALD);
        restaurant.setName("Sushi");
        Restaurant save = repository.save(restaurant);
        assertThat(save).isNotNull();
        List<Restaurant> all = repository.findAll();
        assertMatch(all, restaurant, BURGER_KING, KFC);
    }

    @Test
    public void getByIdNotReturnNull() throws Exception {
        Restaurant byId = repository.findById(BURGER_KING.getId()).orElse(null);
        assertThat(byId).isNotNull();
    }

    @Test
    public void getByIdCorrectRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(BURGER_KING);
        Restaurant byId = repository.findById(restaurant.getId()).orElse(null);
        assertThat(byId).isEqualToComparingFieldByField(BURGER_KING);
    }


    @Test
    public void deleteNotExistRestaurant() throws Exception {
        int delete = repository.delete(50);
        assertThat(delete).isEqualTo(0);
        List<Restaurant> all = repository.findAll();
        assertMatch(all, RestaurantData.getAllRestaurant());
    }

    @Test
    public void deleteExistRestaurant() throws Exception {
        int delete = repository.delete(BURGER_KING.getId());
        assertThat(delete).isNotEqualTo(0);
        List<Restaurant> all = repository.findAll();
        assertMatch(all, MC_DONALD, KFC);
    }

    /*
    ===========================
    =  Test to count queries  =
    ===========================
     */


    @Test
    public void saveQueryCount() throws Exception {
        countQueries.setLimit(2);
        Restaurant restaurant = new Restaurant(null, "Sashlik", "Palatka");
        repository.save(restaurant);
    }

    @Test
    public void updateQueryCount() throws Exception {
        countQueries.setLimit(2);
        Restaurant restaurant = new Restaurant(MC_DONALD);
        restaurant.setName("Sushi");
        repository.save(restaurant);
    }

    @Test
    public void getByIdQueryCount() throws Exception {
        countQueries.setLimit(1);
        Restaurant restaurant = new Restaurant(BURGER_KING);
        repository.findById(restaurant.getId());
    }

    @Test
    public void getAllQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.findAll();
    }

    @Test
    public void deleteQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.delete(BURGER_KING.getId());
    }
}