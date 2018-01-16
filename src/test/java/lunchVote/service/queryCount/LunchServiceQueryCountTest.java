package lunchVote.service.queryCount;

import lunchVote.model.Lunch;
import lunchVote.service.LunchService;
import lunchVote.service.cacheTest.CacheConfig;
import lunchVote.util.LunchConverter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static lunchVote.testData.LunchData.CHIKEN;
import static lunchVote.testData.LunchData.SAVE_NEW;
import static lunchVote.testData.LunchData.VOPER;

public class LunchServiceQueryCountTest extends CacheConfig {

    @Autowired
    private LunchService service;

    @Before
    public void setUp() throws Exception {
        cache.getCache("lunch").clear();
    }

    @Test
    public void get() throws Exception {
        queryCounter.setLimit(1);
        service.get(CHIKEN.getId());
    }

    @Test
    public void create() throws Exception {
        queryCounter.setLimit(2);
        service.save(LunchConverter.asTo(SAVE_NEW));
    }

    @Test
    public void update() throws Exception {
        queryCounter.setLimit(3);
        Lunch lunch = new Lunch(VOPER);
        lunch.setPrice(220);
        service.save(LunchConverter.asTo(lunch));
    }

    @Test
    public void delete() throws Exception {
        queryCounter.setLimit(1);
        service.delete(CHIKEN.getId());
    }

    @Test
    public void getAllForDate() throws Exception {
        queryCounter.setLimit(1);
        service.getAllForDate(LocalDate.now());
    }
}
