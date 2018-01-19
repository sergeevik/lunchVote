package lunchVote.service;

import lunchVote.SpringConfigOnTests;
import lunchVote.model.Vote;
import lunchVote.repository.dataJpa.springCrud.VoteCrud;
import lunchVote.service.cacheTest.CacheConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import java.time.LocalDate;

import static lunchVote.testData.VoteData.ADMIN_VOTE;
import static lunchVote.testData.VoteData.SAVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VoteServiceTest extends CacheConfig {

    private VoteService service;
    private VoteCrud repository;
    private Cache cacheVote;

    @Before
    public void setUp() throws Exception {
        repository = mock(VoteCrud.class);
        service = new VoteServiceImpl(repository, cache);
        cacheVote = cache.getCache("vote");
    }

    @Test
    public void save() throws Exception {
        Vote voteSave = new Vote(SAVE);
        when(repository.save(voteSave)).thenReturn(voteSave);
        Vote save = service.save(voteSave.getLunchId(), voteSave.getUserId(), voteSave.getDate());

        verify(repository, times(1)).save(voteSave);
        assertThat(save).isNotNull()
                        .isEqualToComparingFieldByField(voteSave);
    }

    @Test
    public void update() throws Exception {
        Vote vote = new Vote(ADMIN_VOTE);
        when(repository.update(vote.getId(), vote.getLunchId())).thenReturn(1);
        cacheVote.put(vote.getUserId(), vote.getId());

        Vote save = service.save(vote.getLunchId(), vote.getUserId(), vote.getDate());

        verify(repository, times(1)).update(vote.getId(), vote.getLunchId());

        assertThat(save).isNotNull().isEqualToComparingFieldByField(vote);
    }

    @Test
    public void getLunchVotes() throws Exception {
        LocalDate now = LocalDate.now();
        service.getDayResult(now);
        verify(repository, times(1)).getLunchVoteOnDate(now);
    }


}