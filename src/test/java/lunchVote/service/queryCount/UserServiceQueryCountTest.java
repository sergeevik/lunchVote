package lunchVote.service.queryCount;

import lunchVote.model.User;
import lunchVote.service.UserService;
import lunchVote.service.cacheTest.CacheConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static lunchVote.testData.UserData.*;

public class UserServiceQueryCountTest extends CacheConfig {

    @Autowired
    private UserService service;

    @Test
    public void getById() throws Exception {
        queryCounter.setLimit(1);
        service.getById(ADMIN.getId());
    }

    @Test
    public void getByEmail() throws Exception {
        queryCounter.setLimit(1);
        service.getByEmail(USER.getEmail());
    }

    @Test
    public void save() throws Exception {
        queryCounter.setLimit(3);
        service.save(new User(SAVE_NEW));
    }

    @Test
    public void delete() throws Exception {
        queryCounter.setLimit(1);
        service.delete(USER.getId());
    }

    @Test
    public void getAll() throws Exception {
        queryCounter.setLimit(2);
        service.getAll();
    }
}
