package lunchVote.service.cacheTest;

import lunchVote.model.Restaurant;
import lunchVote.service.RestaurantService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static lunchVote.testData.RestaurantData.*;

public class RestaurantCacheTest extends CacheConfig{

    @Autowired
    private RestaurantService service;

    @Before
    public void setUp() throws Exception {
        cache.getCache("restaurant").clear();
    }

    @Test
    public void getAll() throws Exception {
        queryCounter.setLimit(1);
        for (int i = 0; i < 3; i++) {
            service.getAll();
        }
    }

    /**
     * Queries:
     * 1 - getAll
     * 2 - delete
     * 3 - getAll
     */
    @Test
    public void evictDelete() throws Exception {
        queryCounter.setLimit(3);

        service.getAll();
        service.delete(BURGER_KING.getId());
        for (int i = 0; i < 3; i++) {
            service.getAll();
        }
    }

    /**
     * Queries:
     * 1 - getAll
     * 2 - get id (PK auto_generate)
     * 3 - create
     * 4 - getAll
     */
    @Test
    public void evictCreate() throws Exception {
        queryCounter.setLimit(4);

        service.getAll();
        service.create(new Restaurant(SAVE_NEW));
        for (int i = 0; i < 3; i++) {
            service.getAll();
        }
    }

    /**
     * Queries:
     * 1 - getAll
     * 2 - get id (PK auto_generate)
     * 3 - update
     * 4 - getAll
     */
    @Test
    public void evictUpdate() throws Exception {
        queryCounter.setLimit(4);

        service.getAll();
        Restaurant restaurant = new Restaurant(MC_DONALD);
        restaurant.setName("Chio-Rio");
        service.update(restaurant, restaurant.getId());
        for (int i = 0; i < 3; i++) {
            service.getAll();
        }
    }

    /**
     * Queries:
     * 1 - get
     * 2 - delete
     * 3 - get
     */
    @Test
    public void getCache() throws Exception {
        queryCounter.setLimit(3);

        service.get(MC_DONALD.getId());
        service.delete(KFC.getId());
        for (int i = 0; i < 3; i++) {
            service.get(MC_DONALD.getId());
        }
    }
}
