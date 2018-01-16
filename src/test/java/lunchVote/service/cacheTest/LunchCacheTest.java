package lunchVote.service.cacheTest;

import lunchVote.service.LunchService;
import lunchVote.util.LunchConverter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static lunchVote.testData.LunchData.*;

public class LunchCacheTest extends CacheConfig {

    @Autowired
    private LunchService service;


    @Before
    public void setUp() throws Exception {
        cache.getCache("lunch").clear();
    }

    @Test
    public void getAll() throws Exception {
        queryCounter.setLimit(1);
        LocalDate now = LocalDate.now();
        for (int i = 0; i < 3; i++) {
            service.getAllForDate(now);
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

        service.get(CHIKEN.getId());
        service.delete(VOPER.getId());
        for (int i = 0; i < 3; i++) {
            service.get(CHIKEN.getId());
        }
    }

    /**
     * Queries:
     * 1 - getAll
     * 2 - get id (PK auto_generate)
     * 3 - save
     * 4 - getAll
     */
    @Test
    public void evictSave() throws Exception {
        queryCounter.setLimit(4);
        
        LocalDate now = LocalDate.now();
        service.getAllForDate(now);

        service.save(LunchConverter.asTo(SAVE_NEW));
        for (int i = 0; i < 3; i++) {
            service.getAllForDate(now);
        }
    }

    /**
     * Queries:
     * 1 - get
     * 2 - delete
     * 3 - get
     */
    @Test
    public void get() throws Exception {
        queryCounter.setLimit(3);

        service.get(CHIKEN.getId());
        service.delete(VOPER.getId());
        for (int i = 0; i < 3; i++) {
            service.get(CHIKEN.getId());
        }
    }
}
