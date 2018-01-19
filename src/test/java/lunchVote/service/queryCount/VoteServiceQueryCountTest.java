package lunchVote.service.queryCount;

import lunchVote.model.Vote;
import lunchVote.service.VoteService;
import lunchVote.service.cacheTest.CacheConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static lunchVote.testData.LunchData.BIG_MACK;
import static lunchVote.testData.LunchData.CHIKEN;
import static lunchVote.testData.LunchData.VOPER;
import static lunchVote.testData.VoteData.ADMIN_VOTE;
import static lunchVote.testData.VoteData.SAVE;
import static lunchVote.testData.VoteData.USER_VOTE;

public class VoteServiceQueryCountTest extends CacheConfig {

    @Autowired
    private VoteService service;

    @Before
    public void setUp() throws Exception {
        // load lunch in getDayResult method
        cache.getCache("lunch").clear();
        cache.getCache("vote").clear();
    }

    @Test
    public void save() throws Exception {
        queryCounter.setLimit(1);
        Vote save = new Vote(SAVE);
        service.save(save.getLunchId(), save.getUserId(), save.getDate());
    }

    @Test
    public void getDayResult() throws Exception {
        queryCounter.setLimit(2);
        service.getDayResult(LocalDate.now());
    }

    @Test
    public void update() throws Exception {
        queryCounter.setLimit(1);
        Vote save = new Vote(ADMIN_VOTE);
        cache.getCache("vote").put(save.getUserId(), save.getId());
        save.setLunchId(BIG_MACK.getId());
        service.save(save.getLunchId(), save.getUserId(), save.getDate());
    }
}
