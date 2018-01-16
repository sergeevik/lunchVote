package lunchVote.service.cacheTest;

import lunchVote.model.User;
import lunchVote.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static lunchVote.testData.UserData.*;

public class UserCacheTest extends CacheConfig {

    @Autowired
    private UserService service;

    @Before
    public void setUp() throws Exception {
        for (String s : cache.getCacheNames()) {
            cache.getCache(s).clear();
        }
    }

    @Test
    public void get() throws Exception {
        queryCounter.setLimit(1);
        for (int i = 0; i < 3; i++) {
            service.getById(USER.getId());
        }
    }

    /**
     * Queries:
     * 1 - getAll user
     * 2 - getAll role
     */
    @Test
    public void getAll() throws Exception {
        queryCounter.setLimit(2);
        for (int i = 0; i < 3; i++) {
            service.getAll();
        }
    }

    /**
     * Queries:
     * 1 - getAll user
     * 2 - getAll role
     * 3 - delete user
     * 4 - getAll user
     * 5 - getAll role
     */
    @Test
    public void deleteEvict() throws Exception {
        queryCounter.setLimit(5);
        service.getAll();
        service.delete(JURA.getId());
        for (int i = 0; i < 3; i++) {
            service.getAll();
        }
        System.out.println();
    }

    /**
     * Queries:
     * 1 - getAll user
     * 2 - getAll role
     * 3 - get next id
     * 4 - save user
     * 5 - save role
     * 6 - getAll user
     * 7 - getAll role
     */
    @Test
    public void saveEvict() throws Exception {
        queryCounter.setLimit(7);

        service.getAll();

        service.save(new User(SAVE_NEW));
        for (int i = 0; i < 3; i++) {
            service.getAll();
        }
    }
}
