package lunchVote.service;

import lunchVote.SpringConfigOnTests;
import lunchVote.exceptions.NotTodayLunchException;
import lunchVote.model.Vote;
import lunchVote.repository.dataJpa.springCrud.VoteCrud;
import lunchVote.service.cacheTest.CacheConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static lunchVote.testData.LunchData.BIG_MACK;
import static lunchVote.testData.LunchData.CHIKEN;
import static lunchVote.testData.LunchData.VOPER;
import static lunchVote.testData.VoteData.ADMIN_VOTE;
import static lunchVote.testData.VoteData.SAVE;
import static lunchVote.testData.VoteData.USER_VOTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VoteServiceTest extends CacheConfig {

    private VoteService service;
    private LunchService lunchService;
    private VoteCrud repository;
    private Cache cacheVote;

    @Before
    public void setUp() throws Exception {
        repository = mock(VoteCrud.class);
        lunchService = mock(LunchService.class);
        service = new VoteServiceImpl(repository, lunchService, cache);
        initLunchServiceMock();
        cacheVote = cache.getCache("vote");
        cacheVote.clear();
    }

    @Test(expected = NotTodayLunchException.class)
    public void saveException() throws Exception {
        Vote voteSave = new Vote(SAVE);
        Vote save = service.save(voteSave.getLunchId(), voteSave.getUserId(),
                LocalDateTime.of(voteSave.getDate(), LocalTime.of(11, 0)));
    }

    @Test
    public void save() throws Exception {
        Vote vote = new Vote(USER_VOTE);
        Vote toSave = new Vote(null, vote.getUserId(), vote.getLunchId(), vote.getDate());
        when(repository.save(toSave)).thenReturn(vote);
        Vote save = service.save(vote.getLunchId(), vote.getUserId(),
                LocalDateTime.of(vote.getDate(), LocalTime.of(11, 0)));

        verify(repository, times(1)).save(toSave);
        assertThat(save).isNotNull()
                .isEqualToComparingFieldByField(vote);
    }

    @Test
    public void update() throws Exception {
        Vote vote = new Vote(ADMIN_VOTE);
        when(repository.update(vote.getId(), vote.getLunchId())).thenReturn(1);
        cacheVote.put(vote.getUserId(), vote.getId());

        Vote save = service.save(vote.getLunchId(), vote.getUserId(),
                LocalDateTime.of(vote.getDate(), LocalTime.of(11, 0)));

        verify(repository, times(1)).update(vote.getId(), vote.getLunchId());

        assertThat(save).isNotNull().isEqualToComparingFieldByField(vote);
    }

    @Test
    public void getLunchVotes() throws Exception {
        LocalDate now = LocalDate.now();
        service.getDayResult(now);
        verify(repository, times(1)).getLunchVoteOnDate(now);
    }

    private void initLunchServiceMock(){
        when(lunchService.get(VOPER.getId())).thenReturn(VOPER);
        when(lunchService.get(CHIKEN.getId())).thenReturn(CHIKEN);
        when(lunchService.get(BIG_MACK.getId())).thenReturn(BIG_MACK);
    }


}