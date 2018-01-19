package lunchVote.service.cacheTest;

import lunchVote.model.Restaurant;
import lunchVote.model.Vote;
import lunchVote.repository.dataJpa.springCrud.VoteCrud;
import lunchVote.service.LunchService;
import lunchVote.service.RestaurantService;
import lunchVote.service.VoteService;
import lunchVote.service.VoteServiceImpl;
import lunchVote.testData.RestaurantData;
import lunchVote.util.LunchConverter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import static lunchVote.testData.LunchData.SAVE_NEW;
import static lunchVote.testData.LunchData.VOPER;
import static lunchVote.testData.RestaurantData.MC_DONALD;
import static lunchVote.testData.VoteData.USER_VOTE;
import static org.mockito.Mockito.*;


public class VoteCacheTest extends CacheConfig {

    private VoteService service;
    private VoteCrud crud;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private LunchService lunchService;

    private Cache voteCahce;


    private final Vote UPDATE = new Vote(USER_VOTE);
    private final Vote SAVE = new Vote(null, UPDATE.getUserId(), UPDATE.getLunchId(), UPDATE.getDate());


    @Before
    public void setUp() throws Exception {
        voteCahce = cache.getCache("vote");
        voteCahce.clear();
        crud = mock(VoteCrud.class);
        service = new VoteServiceImpl(crud, cache);
        initMockData();
    }

    private void initMockData() throws Exception {
        when(crud.save(SAVE)).thenReturn(UPDATE);
        when(crud.update(UPDATE.getId(), UPDATE.getLunchId())).thenReturn(1);
    }

    @Test
    public void restaurantCreateEvict(){
        updateBeforeEvict();
        restaurantService.create(new Restaurant(RestaurantData.SAVE_NEW));
        saveAfterEvict();
    }

    @Test
    public void restaurantUpdateEvict(){
        updateBeforeEvict();
        Restaurant restaurant = new Restaurant(MC_DONALD);
        restaurantService.update(restaurant, restaurant.getId());
        saveAfterEvict();
    }

    @Test
    public void restaurantDeleteEvict(){
        updateBeforeEvict();
        restaurantService.delete(MC_DONALD.getId());
        saveAfterEvict();
    }

    @Test
    public void lunchCreateEvict(){
        updateBeforeEvict();
        lunchService.create(LunchConverter.asTo(SAVE_NEW));
        saveAfterEvict();
    }

    @Test
    public void lunchUpdateEvict(){
        updateBeforeEvict();
        lunchService.update(LunchConverter.asTo(VOPER), VOPER.getId());
        saveAfterEvict();
    }

    @Test
    public void lunchDeleteEvict(){
        updateBeforeEvict();
        lunchService.delete(VOPER.getId());
        saveAfterEvict();
    }

    private void saveAfterEvict() {
        service.save(UPDATE.getLunchId(), UPDATE.getUserId(), UPDATE.getDate());
        verify(crud, times(1)).save(SAVE);
        verify(crud, times(1)).update(UPDATE.getId(), UPDATE.getLunchId());
    }

    private void updateBeforeEvict() {
        putVoteInCache(UPDATE);
        service.save(UPDATE.getLunchId(), UPDATE.getUserId(), UPDATE.getDate());
        verify(crud, times(1)).update(UPDATE.getId(), UPDATE.getLunchId());
    }

    private void putVoteInCache(Vote vote){
        voteCahce.put(vote.getUserId(), vote.getId());
    }

}
