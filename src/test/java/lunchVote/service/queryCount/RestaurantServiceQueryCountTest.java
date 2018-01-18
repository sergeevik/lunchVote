package lunchVote.service.queryCount;

import lunchVote.model.Restaurant;
import lunchVote.service.RestaurantService;
import lunchVote.service.cacheTest.CacheConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static lunchVote.testData.RestaurantData.KFC;
import static lunchVote.testData.RestaurantData.MC_DONALD;
import static lunchVote.testData.RestaurantData.SAVE_NEW;

public class RestaurantServiceQueryCountTest extends CacheConfig {

    @Autowired
    private RestaurantService service;

    @Test
    public void create() throws Exception {
        queryCounter.setLimit(2);
        service.create(new Restaurant(SAVE_NEW));
    }

    @Test
    public void update() throws Exception {
        queryCounter.setLimit(1);
        Restaurant restaurant = new Restaurant(MC_DONALD);
        restaurant.setName("Update MCDonald");
        service.update(restaurant, restaurant.getId());
    }

    @Test
    public void get() throws Exception {
        queryCounter.setLimit(1);
        service.get(MC_DONALD.getId());
    }

    @Test
    public void delete() throws Exception {
        queryCounter.setLimit(1);
        service.delete(KFC.getId());
    }

    @Test
    public void getAll() throws Exception {
        queryCounter.setLimit(1);
        service.getAll();
    }
}
