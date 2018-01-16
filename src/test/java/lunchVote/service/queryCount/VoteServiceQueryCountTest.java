package lunchVote.service.queryCount;

import lunchVote.model.Vote;
import lunchVote.service.VoteService;
import lunchVote.service.cacheTest.CacheConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static lunchVote.testData.VoteData.SAVE_VOTE_COUNT_QUERY;

public class VoteServiceQueryCountTest extends CacheConfig {

    @Autowired
    private VoteService service;

    @Before
    public void setUp() throws Exception {
        // load lunch in getDayResult method
        cache.getCache("lunch").clear();
    }

    @Test
    public void save() throws Exception {
        queryCounter.setLimit(2);
        Vote save = new Vote(SAVE_VOTE_COUNT_QUERY);
        service.save(save.getLunchId(), save.getUserId(), save.getDate());
    }

    @Test
    public void getDayResult() throws Exception {
        queryCounter.setLimit(2);
        service.getDayResult(LocalDate.now());
    }
}
